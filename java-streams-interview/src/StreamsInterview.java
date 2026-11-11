import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsInterview {

    static class Employee {
        private final int id;
        private final String name;
        private final String city;
        private final double salary;
        private final int age;

        Employee(int id, String name, String city, double salary, int age) {
            this.id = id;
            this.name = name;
            this.city = city;
            this.salary = salary;
            this.age = age;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCity() { return city; }
        public double getSalary() { return salary; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            return name + " (" + city + ", " + salary + ")";
        }
    }

    private static final List<Employee> EMPLOYEES = Arrays.asList(
        new Employee(1, "John", "Dublin", 45000, 25),
        new Employee(2, "David", "Cork", 60000, 32),
        new Employee(3, "Sarah", "Dublin", 75000, 29),
        new Employee(4, "Mike", "Galway", 50000, 40),
        new Employee(5, "Emma", "Cork", 85000, 35),
        new Employee(6, "Robert", "Dublin", 55000, 28)
    );

    private static void title(int number, String question) {
        System.out.println("\n========================================");
        System.out.println("Q" + number + ". " + question);
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        q01WhatAreStreams();
        q02StreamVsParallelStream();
        q03IntermediateVsTerminal();
        q04Filter();
        q05MapVsFlatMap();
        q06Reduce();
        q07Collectors();
        q08Sorted();
        q09FindFirstVsFindAny();
        q10ForEachVsLoop();
        q11LazyEvaluation();
        q12ExceptionHandling();
        q13Peek();
        q14ConvertToCollection();
        q15MatchOperations();
        q16LimitAndSkip();
        q17StreamConcat();
        q18CreateParallelStreams();
        q19ShortCircuiting();
        q20Distinct();
    }

    // Q1. What are Streams? How are they different from Collections?
    private static void q01WhatAreStreams() {
        title(1, "What are Streams? Collections vs Streams");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> even = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Original collection: " + numbers);
        System.out.println("Stream result: " + even);
    }

    // Q2. stream() vs parallelStream()
    private static void q02StreamVsParallelStream() {
        title(2, "stream() vs parallelStream()");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Sequential:");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println("\nParallel (order not guaranteed):");
        numbers.parallelStream().forEach(n -> System.out.print(n + " "));
        System.out.println("\nParallel, encounter order preserved:");
        numbers.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
        int sequentialSum = numbers.stream().mapToInt(Integer::intValue).sum();
        int parallelSum = numbers.parallelStream().mapToInt(Integer::intValue).sum();
        System.out.println("\nSums: " + sequentialSum + " / " + parallelSum);
    }

    // Q3. Intermediate vs terminal operations
    private static void q03IntermediateVsTerminal() {
        title(3, "Intermediate vs terminal operations");
        Stream<String> pipeline = Stream.of("java", "spring", "kafka")
                .filter(s -> {
                    System.out.println("Filtering: " + s);
                    return true;
                })
                .map(String::toUpperCase);
        System.out.println("Pipeline created; no filtering has happened yet.");
        System.out.println("Result: " + pipeline.collect(Collectors.toList()));
    }

    // Q4. filter()
    private static void q04Filter() {
        title(4, "filter()");
        List<Employee> highEarners = EMPLOYEES.stream()
                .filter(e -> e.getSalary() > 50000)
                .collect(Collectors.toList());
        highEarners.forEach(System.out::println);

        List<String> dublinNames = EMPLOYEES.stream()
                .filter(e -> "Dublin".equals(e.getCity()))
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("Employees in Dublin: " + dublinNames);
    }

    // Q5. map() vs flatMap()
    private static void q05MapVsFlatMap() {
        title(5, "map() vs flatMap()");
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );
        List<List<Integer>> mapped = nested.stream()
                .map(list -> list.stream()
                        .map(n -> n * 2)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        List<Integer> flattened = nested.stream()
                .flatMap(List::stream)
                .map(n -> n * 2)
                .collect(Collectors.toList());
        System.out.println("map(): " + mapped);
        System.out.println("flatMap(): " + flattened);

        List<String> sentences = Arrays.asList("Java Streams", "Spring Boot");
        List<String> words = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        System.out.println("Words: " + words);
    }

    // Q6. reduce()
    private static void q06Reduce() {
        title(6, "reduce()");
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40);
        int sum = numbers.stream().reduce(0, Integer::sum);
        Optional<Integer> maximum = numbers.stream().reduce(Integer::max);
        Optional<Integer> minimum = numbers.stream().reduce(Integer::min);
        System.out.println("Sum: " + sum);
        System.out.println("Maximum: " + maximum.orElse(null));
        System.out.println("Minimum: " + minimum.orElse(null));
    }

    // Q7. Collectors: toList, toSet, toMap, groupingBy, partitioningBy, joining
    private static void q07Collectors() {
        title(7, "Collectors");
        List<String> names = EMPLOYEES.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        Set<String> cities = EMPLOYEES.stream()
                .map(Employee::getCity)
                .collect(Collectors.toSet());
        Map<Integer, String> byId = EMPLOYEES.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        Map<String, List<Employee>> byCity = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::getCity));
        Map<String, Long> countByCity = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(Employee::getCity, Collectors.counting()));
        Map<Boolean, List<Employee>> salaryPartition = EMPLOYEES.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 60000));
        String joinedNames = EMPLOYEES.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));
        Map<String, Double> salaryByCity = EMPLOYEES.stream()
                .collect(Collectors.toMap(
                        Employee::getCity,
                        Employee::getSalary,
                        Double::sum
                ));

        System.out.println("toList(): " + names);
        System.out.println("toSet(): " + cities);
        System.out.println("toMap(): " + byId);
        System.out.println("groupingBy(): " + byCity);
        System.out.println("counting by city: " + countByCity);
        System.out.println("partitioningBy(): " + salaryPartition);
        System.out.println("joining(): " + joinedNames);
        System.out.println("Salary total by city: " + salaryByCity);
    }

    // Q8. sorted()
    private static void q08Sorted() {
        title(8, "sorted()");
        List<Integer> numbers = Arrays.asList(5, 2, 9, 1, 7);
        System.out.println("Ascending: " + numbers.stream()
                .sorted().collect(Collectors.toList()));
        System.out.println("Descending: " + numbers.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList()));

        List<Employee> bySalary = EMPLOYEES.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()
                        .thenComparing(Employee::getName))
                .collect(Collectors.toList());
        System.out.println("Employees by salary descending, then name: " + bySalary);
    }

    // Q9. findFirst() vs findAny()
    private static void q09FindFirstVsFindAny() {
        title(9, "findFirst() vs findAny()");
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
        System.out.println("findFirst(): " + numbers.stream()
                .filter(n -> n > 20).findFirst().orElse(-1));
        System.out.println("findAny(): " + numbers.stream()
                .filter(n -> n > 20).findAny().orElse(-1));
        System.out.println("Parallel findAny(): " + numbers.parallelStream()
                .filter(n -> n > 20).findAny().orElse(-1));
    }

    // Q10. forEach() vs a traditional loop
    private static void q10ForEachVsLoop() {
        title(10, "forEach() vs traditional loop");
        List<String> names = Arrays.asList("John", "David", "Sarah", "Emma");
        System.out.println("forEach():");
        names.forEach(System.out::println);
        System.out.println("Loop with break:");
        for (String name : names) {
            if ("Sarah".equals(name)) {
                break;
            }
            System.out.println(name);
        }
    }

    // Q11. Lazy evaluation
    private static void q11LazyEvaluation() {
        title(11, "Lazy evaluation");
        Stream<Integer> pipeline = Stream.of(1, 2, 3, 4, 5)
                .filter(n -> {
                    System.out.println("Filtering: " + n);
                    return n % 2 == 0;
                });
        System.out.println("Stream created. Terminal operation starts next.");
        System.out.println("Result: " + pipeline.collect(Collectors.toList()));
    }

    // Q12. Exception handling in Streams
    private static void q12ExceptionHandling() {
        title(12, "Exception handling in Streams");
        List<String> values = Arrays.asList("10", "20", "abc", "40");
        try {
            List<Integer> parsed = values.stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            System.out.println(parsed);
        } catch (NumberFormatException e) {
            System.out.println("Invalid value caused: " + e.getMessage());
        }

        List<Integer> validValues = values.stream()
                .map(s -> {
                    try {
                        return Integer.valueOf(s);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid value: " + s);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        System.out.println("Valid numbers: " + validValues);
    }

    // Q13. peek()
    private static void q13Peek() {
        title(13, "peek()");
        List<Integer> result = Stream.of(1, 2, 3, 4, 5)
                .peek(n -> System.out.println("Before filter: " + n))
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("After filter: " + n))
                .collect(Collectors.toList());
        System.out.println("Result: " + result);
    }

    // Q14. Convert a Stream into a Collection or array
    private static void q14ConvertToCollection() {
        title(14, "Convert Stream to Collection");
        List<Integer> list = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toList());
        Set<Integer> set = Stream.of(1, 2, 2, 3, 4)
                .collect(Collectors.toSet());
        Integer[] array = Stream.of(1, 2, 3, 4).toArray(Integer[]::new);
        LinkedList<Integer> linkedList = Stream.of(1, 2, 3, 4)
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("List: " + list);
        System.out.println("Set: " + set);
        System.out.println("Array: " + Arrays.toString(array));
        System.out.println("LinkedList: " + linkedList);
    }

    // Q15. anyMatch(), allMatch(), noneMatch()
    private static void q15MatchOperations() {
        title(15, "anyMatch(), allMatch(), noneMatch()");
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
        System.out.println("anyMatch > 40: " + numbers.stream().anyMatch(n -> n > 40));
        System.out.println("allMatch > 5: " + numbers.stream().allMatch(n -> n > 5));
        System.out.println("noneMatch < 0: " + numbers.stream().noneMatch(n -> n < 0));

        System.out.println("Empty anyMatch: " + Stream.<Integer>empty().anyMatch(n -> true));
        System.out.println("Empty allMatch: " + Stream.<Integer>empty().allMatch(n -> true));
        System.out.println("Empty noneMatch: " + Stream.<Integer>empty().noneMatch(n -> true));
    }

    // Q16. limit() and skip()
    private static void q16LimitAndSkip() {
        title(16, "limit() and skip()");
        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
        System.out.println("limit(3): " + numbers.stream()
                .limit(3).collect(Collectors.toList()));
        System.out.println("skip(2): " + numbers.stream()
                .skip(2).collect(Collectors.toList()));

        int pageNumber = 2;
        int pageSize = 2;
        List<Integer> page = numbers.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        System.out.println("Page 2: " + page);
    }

    // Q17. Stream.concat()
    private static void q17StreamConcat() {
        title(17, "Stream.concat()");
        Stream<Integer> first = Stream.of(1, 2, 3);
        Stream<Integer> second = Stream.of(4, 5, 6);
        System.out.println("Combined: " + Stream.concat(first, second)
                .collect(Collectors.toList()));

        System.out.println("Combined distinct: " + Stream.concat(
                Stream.of(1, 2, 3), Stream.of(3, 4, 5))
                .distinct().collect(Collectors.toList()));
    }

    // Q18. Creating parallel Streams
    private static void q18CreateParallelStreams() {
        title(18, "Creating parallel Streams");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("parallelStream(): " + numbers.parallelStream().isParallel());
        System.out.println("stream().parallel(): " + numbers.stream().parallel().isParallel());
        System.out.println("Switched to sequential(): "
                + numbers.parallelStream().sequential().isParallel());
        System.out.println("Common pool parallelism: "
                + ForkJoinPool.commonPool().getParallelism());
    }

    // Q19. Short-circuiting operations
    private static void q19ShortCircuiting() {
        title(19, "Short-circuiting operations");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Optional<Integer> firstMatch = numbers.stream()
                .filter(n -> {
                    System.out.println("Checking: " + n);
                    return n > 3;
                })
                .findFirst();
        System.out.println("First match: " + firstMatch.orElse(-1));

        boolean foundInInfiniteStream = Stream.iterate(1, n -> n + 1)
                .anyMatch(n -> n > 5);
        System.out.println("Found in infinite Stream: " + foundInInfiniteStream);

        List<Integer> firstFive = Stream.iterate(1, n -> n + 1)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("First five: " + firstFive);
    }

    // Q20. distinct()
    private static void q20Distinct() {
        title(20, "distinct()");
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4, 5, 5);
        System.out.println("Unique numbers: " + numbers.stream()
                .distinct().collect(Collectors.toList()));

        List<String> uniqueCities = EMPLOYEES.stream()
                .map(Employee::getCity)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique cities: " + uniqueCities);

        Map<String, Employee> oneEmployeePerCity = EMPLOYEES.stream()
                .collect(Collectors.toMap(
                        Employee::getCity,
                        Function.identity(),
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
        oneEmployeePerCity.forEach((city, employee) ->
                System.out.println(city + " -> " + employee.getName()));
    }
}
