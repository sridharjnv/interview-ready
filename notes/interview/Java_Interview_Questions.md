# 🔥 Top 100 Java Interview Questions — SDE-1 Backend

> **Difficulty Legend:** 🟢 Basic | 🟡 Intermediate | 🔴 Advanced

---

## 1. Core Java Fundamentals (Q1–Q15)

1. 🟢 What are the main features of Java? Why is Java called platform-independent?
2. 🟢 Explain JDK, JRE, and JVM. How do they differ?
3. 🟢 What is the difference between `==` and `.equals()` in Java?
4. 🟢 What are wrapper classes? Explain autoboxing and unboxing.
5. 🟢 What is the difference between `String`, `StringBuilder`, and `StringBuffer`?
6. 🟢 Why is `String` immutable in Java? What is the String Pool?
7. 🟡 What are the access modifiers in Java? Explain each with scope.
8. 🟢 What is the difference between `final`, `finally`, and `finalize`?
9. 🟢 What is the difference between `static` and `non-static` members?
10. 🟡 What is type casting in Java? Explain widening and narrowing conversions.
11. 🟡 What is the difference between `this` and `super` keywords?
12. 🟡 What is a `transient` variable? When would you use it?
13. 🟡 What is the `volatile` keyword? How does it work in multithreading?
14. 🟢 What are the primitive data types in Java and their sizes?
15. 🟡 What is the difference between pass-by-value and pass-by-reference? How does Java handle this?

---

## 2. OOP Concepts (Q16–Q30)

16. 🟢 What are the four pillars of OOP? Explain each briefly.
17. 🟢 What is the difference between abstraction and encapsulation?
18. 🟢 What is inheritance? What are the types of inheritance supported in Java?
19. 🟢 Why doesn't Java support multiple inheritance with classes? How do interfaces solve this?
20. 🟢 What is polymorphism? Explain compile-time vs runtime polymorphism.
21. 🟡 What is method overloading vs method overriding? What are the rules for each?
22. 🟡 Can we override a `static` method? Can we overload `main()`?
23. 🟡 What is the difference between an abstract class and an interface?
24. 🟡 When would you choose an abstract class over an interface?
25. 🟡 What are default and static methods in interfaces (Java 8)?
26. 🟡 What is a marker interface? Give examples.
27. 🟢 What is the difference between composition and inheritance?
28. 🟡 Explain the SOLID principles with examples.
29. 🟡 What is the diamond problem in Java, and how does Java handle it?
30. 🟡 What is covariant return type in Java?

---

## 3. Exception Handling (Q31–Q40)

31. 🟢 What is the difference between checked and unchecked exceptions?
32. 🟢 What is the exception hierarchy in Java? Where does `Error` fit in?
33. 🟢 What is the difference between `throw` and `throws`?
34. 🟢 Can we have a `try` block without a `catch` block?
35. 🟡 What happens if an exception is thrown inside a `finally` block?
36. 🟡 What is `try-with-resources`? How does it differ from a traditional `try-finally`?
37. 🟡 What is a custom exception? When and how should you create one?
38. 🟡 What is the difference between `NoClassDefFoundError` and `ClassNotFoundException`?
39. 🟡 What is exception propagation in Java?
40. 🟡 Can we rethrow an exception? What is exception chaining?

---

## 4. Collections Framework (Q41–Q60)

41. 🟢 What are the main interfaces in the Java Collections Framework?
42. 🟢 What is the difference between `ArrayList` and `LinkedList`?
43. 🟢 What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?
44. 🟢 What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?
45. 🟡 How does `HashMap` work internally? Explain the bucket, hashing, and collision handling.
46. 🟡 What happens when two keys have the same hashcode in `HashMap`?
47. 🟡 What is the significance of `hashCode()` and `equals()` contract?
48. 🟡 What is the difference between `HashMap` and `ConcurrentHashMap`?
49. 🟡 What is the difference between `HashMap` and `Hashtable`?
50. 🟡 What is the difference between `Comparable` and `Comparator`?
51. 🟡 What is `fail-fast` and `fail-safe` in iterators? Give examples.
52. 🟡 What is the difference between `Iterator` and `ListIterator`?
53. 🟡 What is `EnumSet` and `EnumMap`? When should you use them?
54. 🟡 What is the difference between `Queue`, `Deque`, and `PriorityQueue`?
55. 🟡 What is `CopyOnWriteArrayList`? When would you use it?
56. 🟡 How to make a collection thread-safe? What are the different approaches?
57. 🟡 What is the difference between `Collections.synchronizedMap()` and `ConcurrentHashMap`?
58. 🟡 What is `WeakHashMap`? When would you use it?
59. 🟢 What is the difference between `Array` and `ArrayList`?
60. 🔴 How does `TreeMap` maintain sorted order internally? What data structure does it use?

---

## 5. Java 8+ Features (Q61–Q75)

61. 🟢 What are the major features introduced in Java 8?
62. 🟢 What is a Lambda expression? How does it relate to functional interfaces?
63. 🟢 What is a functional interface? Give examples from `java.util.function`.
64. 🟡 What is the Stream API? How is it different from collections?
65. 🟡 What are intermediate and terminal operations in Streams? Give examples.
66. 🟡 What is the difference between `map()` and `flatMap()` in Streams?
67. 🟡 What is `Optional` in Java? How does it help avoid `NullPointerException`?
68. 🟡 What is method reference in Java 8? What are its types?
69. 🟡 What is the difference between `Stream.of()` and `Arrays.stream()`?
70. 🟡 What are `Predicate`, `Function`, `Consumer`, and `Supplier`?
71. 🟡 How does `filter()`, `reduce()`, and `collect()` work in Streams?
72. 🟡 What are `Collectors.groupingBy()` and `Collectors.partitioningBy()`?
73. 🟡 What is the difference between `findFirst()` and `findAny()` in Streams?
74. 🟡 What are parallel streams? When should you use them?
75. 🔴 What is the `CompletableFuture` in Java? How does it differ from `Future`?

---

## 6. Multithreading & Concurrency (Q76–Q90)

76. 🟢 What is a thread? How do you create a thread in Java?
77. 🟢 What is the difference between `Thread` class and `Runnable` interface?
78. 🟡 What are the different states of a thread lifecycle?
79. 🟡 What is the difference between `start()` and `run()` methods?
80. 🟡 What is synchronization? Explain `synchronized` keyword with examples.
81. 🟡 What is the difference between `wait()`, `notify()`, and `notifyAll()`?
82. 🟡 What is a deadlock? How can you detect and prevent it?
83. 🟡 What is `ReentrantLock`? How is it different from `synchronized`?
84. 🟡 What is the `ExecutorService`? What are the types of thread pools?
85. 🟡 What is `Callable` vs `Runnable`? What is `Future`?
86. 🟡 What is `CountDownLatch`, `CyclicBarrier`, and `Semaphore`?
87. 🔴 What is the `ForkJoinPool`? How does work-stealing algorithm work?
88. 🟡 What is a race condition? How do you prevent it?
89. 🟡 What is `ThreadLocal`? When would you use it?
90. 🔴 What is the Java Memory Model (JMM)? Explain happens-before relationship.

---

## 7. JVM Internals & Memory Management (Q91–Q96)

91. 🟡 What are the different memory areas in JVM (Heap, Stack, Method Area, etc.)?
92. 🟡 What is Garbage Collection? Explain the different GC algorithms (Serial, Parallel, G1, ZGC).
93. 🟡 What is the difference between Stack and Heap memory?
94. 🟡 What are `StackOverflowError` and `OutOfMemoryError`?
95. 🔴 What are strong, weak, soft, and phantom references in Java?
96. 🔴 How does the JVM class loading mechanism work? What are the classloaders?

---

## 8. Design Patterns & Best Practices (Q97–Q100)

97. 🟡 Explain the Singleton design pattern. How do you make it thread-safe?
98. 🟡 What is the Factory design pattern? When would you use it?
99. 🟡 What is the Builder design pattern? How does it differ from the telescoping constructor anti-pattern?
100. 🟡 What is the Observer design pattern? Where is it used in Java?

---

> 💡 **Tip for candidates:** Focus heavily on **Collections internals**, **Java 8 Streams**, **Multithreading**, and **OOP concepts** — these are the most frequently tested areas for SDE-1 backend roles.
