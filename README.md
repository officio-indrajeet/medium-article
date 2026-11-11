# Java 8 Streams Interview Questions

A runnable collection of 20 Java 8 Streams interview examples, with one method per question.

## Requirements

- JDK 8 or later
- No Maven or external dependencies

## Project structure

```text
java-streams-interview/
├── README.md
├── .gitignore
└── src/
    └── StreamsInterview.java
```

## Compile and run

From this directory:

```bash
javac -d out src/StreamsInterview.java
java -cp out StreamsInterview
```

## Questions covered

1. What are Streams? Collections vs Streams
2. `stream()` vs `parallelStream()`
3. Intermediate vs terminal operations
4. `filter()`
5. `map()` vs `flatMap()`
6. `reduce()`
7. Collectors
8. `sorted()`
9. `findFirst()` vs `findAny()`
10. `forEach()` vs a traditional loop
11. Lazy evaluation
12. Exception handling in Streams
13. `peek()`
14. Convert Stream to Collection
15. `anyMatch()`, `allMatch()`, `noneMatch()`
16. `limit()` and `skip()`
17. `Stream.concat()`
18. Creating parallel Streams
19. Short-circuiting operations
20. `distinct()`

## Notes

- The source code uses Java 8 APIs and syntax.
- Parallel execution order can vary between runs.
- `Collectors.toSet()` does not guarantee a particular iteration order.

## Author

Indrajeet Kumar
https://www.linkedin.com/in/indrajeet-1103/
