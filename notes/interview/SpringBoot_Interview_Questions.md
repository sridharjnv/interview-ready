# 🍃 Top 100 Spring Boot Interview Questions — SDE-1 Backend

> **Difficulty Legend:** 🟢 Basic | 🟡 Intermediate | 🔴 Advanced

---

## 1. Spring Core & Fundamentals (Q1–Q15)

1. 🟢 What is the Spring Framework? What problems does it solve?
2. 🟢 What is Spring Boot? How is it different from the Spring Framework?
3. 🟢 What are the advantages of using Spring Boot?
4. 🟢 What is the role of `@SpringBootApplication` annotation? What does it combine?
5. 🟢 What is auto-configuration in Spring Boot? How does it work?
6. 🟢 What are Spring Boot starters? Name some commonly used starters.
7. 🟢 What is Inversion of Control (IoC)? How does Spring implement it?
8. 🟢 What is Dependency Injection (DI)? What are its types?
9. 🟡 What is the difference between constructor injection and setter injection? Which one is preferred and why?
10. 🟡 What is `@Autowired`? How does it resolve dependencies?
11. 🟡 What is the difference between `@Component`, `@Service`, `@Repository`, and `@Controller`?
12. 🟡 What is component scanning in Spring? How does it discover beans?
13. 🟡 What is `@Configuration` and `@Bean`? When would you use them?
14. 🟡 What is the difference between `@Bean` and `@Component`?
15. 🟡 What is the `ApplicationContext`? How is it different from `BeanFactory`?

---

## 2. Spring Boot Configuration (Q16–Q25)

16. 🟢 What is `application.properties` vs `application.yml`? Which one do you prefer?
17. 🟡 How do you externalize configuration in Spring Boot?
18. 🟡 What is `@Value` annotation? How do you inject property values?
19. 🟡 What is `@ConfigurationProperties`? How is it different from `@Value`?
20. 🟡 What are Spring Profiles? How do you activate them?
21. 🟡 How do you configure different databases for different environments?
22. 🟡 What is the order of precedence for configuration properties in Spring Boot?
23. 🟡 How do you override default properties provided by auto-configuration?
24. 🟡 What is `spring.config.import`? How do you load additional config files?
25. 🟡 How do you handle sensitive properties like passwords in configuration?

---

## 3. Spring Boot REST API (Q26–Q45)

26. 🟢 What is a REST API? What are the HTTP methods used in REST?
27. 🟢 What is the difference between `@Controller` and `@RestController`?
28. 🟢 What is `@RequestMapping`? How do shorthand annotations like `@GetMapping`, `@PostMapping` work?
29. 🟢 What is the difference between `@RequestParam`, `@PathVariable`, and `@RequestBody`?
30. 🟡 How do you handle request validation in Spring Boot? What is `@Valid` and `@Validated`?
31. 🟡 What are the common Bean Validation annotations (`@NotNull`, `@Size`, `@Email`, etc.)?
32. 🟡 How do you create custom validators in Spring Boot?
33. 🟡 What is `@ResponseStatus`? How do you set custom HTTP status codes?
34. 🟡 What is `ResponseEntity`? When would you use it over `@ResponseBody`?
35. 🟡 How do you handle exceptions globally in Spring Boot? Explain `@ControllerAdvice` and `@ExceptionHandler`.
36. 🟡 What is the difference between `@RequestBody` and `@ModelAttribute`?
37. 🟡 How does content negotiation work in Spring Boot (JSON vs XML)?
38. 🟡 What is `@CrossOrigin`? How do you configure CORS globally?
39. 🟡 How do you implement pagination and sorting in REST APIs?
40. 🟡 What is HATEOAS? How is it implemented in Spring Boot?
41. 🟡 How do you version your REST APIs? What strategies are available?
42. 🟡 What is the difference between `PUT` and `PATCH`? How do you implement partial updates?
43. 🟡 How do you upload and download files using Spring Boot REST API?
44. 🟡 What is `@JsonIgnore` and `@JsonProperty`? How do you control JSON serialization?
45. 🟡 How do you implement rate limiting in Spring Boot REST APIs?

---

## 4. Spring Data JPA & Database (Q46–Q65)

46. 🟢 What is Spring Data JPA? How does it simplify database operations?
47. 🟢 What is the difference between JPA, Hibernate, and Spring Data JPA?
48. 🟢 What is an Entity? Explain `@Entity`, `@Table`, `@Id`, and `@GeneratedValue`.
49. 🟢 What are the different JPA repository interfaces (`CrudRepository`, `JpaRepository`, `PagingAndSortingRepository`)?
50. 🟡 What are derived query methods? How does Spring Data JPA auto-generate SQL from method names?
51. 🟡 What is `@Query`? How do you write custom JPQL and native SQL queries?
52. 🟡 What is the difference between JPQL and native query?
53. 🟡 What are JPA relationships? Explain `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`.
54. 🟡 What is `FetchType.LAZY` vs `FetchType.EAGER`? When should you use which?
55. 🟡 What is the N+1 query problem? How do you solve it?
56. 🟡 What are `@Transactional` semantics? Explain propagation and isolation levels.
57. 🟡 What is the difference between `save()` and `saveAndFlush()` in JPA?
58. 🟡 What is the difference between `findById()` and `getById()`?
59. 🟡 What is Spring Data JPA Auditing? How do you use `@CreatedDate` and `@LastModifiedDate`?
60. 🟡 What is an `EntityManager`? When would you use it directly?
61. 🟡 How do you implement soft delete in Spring Data JPA?
62. 🔴 What is second-level caching in Hibernate? How do you configure it?
63. 🟡 What are database migrations? How do you use Flyway or Liquibase with Spring Boot?
64. 🟡 What is connection pooling? How does HikariCP work with Spring Boot?
65. 🟡 What is optimistic vs pessimistic locking in JPA?

---

## 5. Spring Security (Q66–Q78)

66. 🟢 What is Spring Security? What does it provide out of the box?
67. 🟢 What is the difference between authentication and authorization?
68. 🟡 How does the Spring Security filter chain work?
69. 🟡 What is `SecurityFilterChain`? How do you configure it in Spring Boot 3.x?
70. 🟡 What is `UserDetailsService`? How do you implement custom authentication?
71. 🟡 What is `BCryptPasswordEncoder`? Why should you hash passwords?
72. 🟡 What are roles and authorities in Spring Security? What is `@PreAuthorize`?
73. 🟡 What is JWT (JSON Web Token)? How do you implement JWT-based authentication?
74. 🟡 What is OAuth 2.0? Explain the different grant types.
75. 🟡 What is CSRF protection? When should you disable it?
76. 🟡 What is the difference between session-based and token-based authentication?
77. 🟡 How do you implement method-level security using `@Secured` and `@RolesAllowed`?
78. 🔴 How do you secure REST APIs with Spring Security + JWT?

---

## 6. Spring Boot Testing (Q79–Q88)

79. 🟢 How do you test a Spring Boot application? What testing frameworks are used?
80. 🟡 What is the difference between `@SpringBootTest`, `@WebMvcTest`, and `@DataJpaTest`?
81. 🟡 What is `MockMvc`? How do you test REST controllers?
82. 🟡 What is `@MockBean`? How do you mock dependencies in Spring Boot tests?
83. 🟡 What is the difference between `@Mock` and `@MockBean`?
84. 🟡 How do you test repository layer with `@DataJpaTest`?
85. 🟡 What is `TestRestTemplate`? How do you do integration testing?
86. 🟡 How do you use H2 in-memory database for testing?
87. 🟡 What is test slicing in Spring Boot? Why is it useful?
88. 🟡 How do you test asynchronous operations in Spring Boot?

---

## 7. Spring Boot Advanced Features (Q89–Q95)

89. 🟡 What is Spring Boot Actuator? What endpoints does it expose?
90. 🟡 How do you create custom health indicators in Actuator?
91. 🟡 What is Spring AOP? Explain `@Aspect`, `@Before`, `@After`, `@Around`.
92. 🟡 What are interceptors in Spring? How are they different from filters?
93. 🟡 What is `@Async` in Spring Boot? How do you enable asynchronous processing?
94. 🟡 What is `@Scheduled`? How do you schedule tasks in Spring Boot?
95. 🟡 What is Spring Cache abstraction? How do you use `@Cacheable`, `@CacheEvict`?

---

## 8. Spring Boot Deployment & Production (Q96–Q100)

96. 🟡 What is the embedded server in Spring Boot? How do you switch from Tomcat to Jetty/Undertow?
97. 🟡 How do you create a Docker image for a Spring Boot application?
98. 🟡 What are the different ways to deploy a Spring Boot application?
99. 🟡 How do you monitor a Spring Boot application in production?
100. 🟡 What is graceful shutdown in Spring Boot? How do you configure it?

---

> 💡 **Tip for candidates:** For SDE-1 backend roles, focus heavily on **REST API design**, **Spring Data JPA**, **Exception Handling**, and **Spring Security basics**. These are the areas most commonly tested in interviews.
