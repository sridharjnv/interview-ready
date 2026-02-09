# SOLID Principles - Interview Guide
**For Java Spring Boot Developers**

---

## Table of Contents
1. [Quick Reference](#quick-reference)
2. [Single Responsibility Principle (SRP)](#1-single-responsibility-principle-srp)
3. [Open/Closed Principle (OCP)](#2-openclosed-principle-ocp)
4. [Liskov Substitution Principle (LSP)](#3-liskov-substitution-principle-lsp)
5. [Interface Segregation Principle (ISP)](#4-interface-segregation-principle-isp)
6. [Dependency Inversion Principle (DIP)](#5-dependency-inversion-principle-dip)
7. [Real-World Spring Boot Example](#real-world-spring-boot-example)
8. [Common Interview Questions](#common-interview-questions)
9. [Code Smells to Avoid](#code-smells-to-avoid)

---

## Quick Reference

| Principle | One-Line Definition | Key Benefit |
|-----------|-------------------|-------------|
| **SRP** | A class should have only one reason to change | Easier maintenance & testing |
| **OCP** | Open for extension, closed for modification | Add features without breaking existing code |
| **LSP** | Subtypes must be substitutable for their base types | Reliable polymorphism |
| **ISP** | Many specific interfaces > one general interface | No forced unused dependencies |
| **DIP** | Depend on abstractions, not concretions | Loose coupling & easier testing |

---

## 1. Single Responsibility Principle (SRP)

### Definition
> A class should have **one and only one reason to change**, meaning it should have only one job or responsibility.

### Why We Need It
- ✅ Makes code easier to understand and maintain
- ✅ Reduces coupling between different functionalities
- ✅ Makes testing simpler (test one thing at a time)
- ✅ Changes in one feature don't break unrelated features
- ✅ Easier to reuse code

### Interview Talking Points
- "SRP helps us create focused classes that do one thing well"
- "When a class has multiple responsibilities, changes to one responsibility can affect the other"
- "In Spring Boot, this means separating Controllers, Services, Repositories, and Validators"

### ❌ Bad Example - Multiple Responsibilities

```java
@Service
public class UserService {
    
    public void createUser(User user) {
        // Responsibility 1: Validation
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        
        // Responsibility 2: Database persistence
        userRepository.save(user);
        
        // Responsibility 3: Email notification
        String emailBody = "Welcome " + user.getName();
        emailSender.send(user.getEmail(), emailBody);
        
        // Responsibility 4: Logging/Auditing
        System.out.println("User created: " + user.getId());
    }
}
```

**Problems:**
- Changes to email template require modifying UserService
- Changes to validation rules require modifying UserService
- Changes to logging strategy require modifying UserService
- Hard to test each responsibility independently

### ✅ Good Example - Single Responsibility

```java
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final EmailService emailService;
    private final AuditLogger auditLogger;
    
    @Autowired
    public UserService(UserRepository userRepository, 
                      UserValidator userValidator,
                      EmailService emailService, 
                      AuditLogger auditLogger) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.emailService = emailService;
        this.auditLogger = auditLogger;
    }
    
    public void createUser(User user) {
        userValidator.validate(user);
        User savedUser = userRepository.save(user);
        emailService.sendWelcomeEmail(savedUser);
        auditLogger.logUserCreation(savedUser);
    }
}

@Component
public class UserValidator {
    public void validate(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        // All validation logic here
    }
}

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    
    public void sendWelcomeEmail(User user) {
        String emailBody = "Welcome " + user.getName();
        // Email sending logic
    }
}

@Component
public class AuditLogger {
    private final Logger logger = LoggerFactory.getLogger(AuditLogger.class);
    
    public void logUserCreation(User user) {
        logger.info("User created: {}", user.getId());
    }
}
```

**Benefits:**
- Each class has one clear responsibility
- Easy to modify email templates without touching UserService
- Easy to change validation rules independently
- Each component can be tested in isolation
- Easy to reuse components (e.g., UserValidator in other places)

### How to Identify SRP Violations
Ask yourself:
1. Does this class have more than one reason to change?
2. Can I describe this class's purpose without using "and"?
3. Would a change in business logic X affect this class? What about Y?

---

## 2. Open/Closed Principle (OCP)

### Definition
> Software entities should be **open for extension but closed for modification**. You should be able to add new functionality without changing existing code.

### Why We Need It
- ✅ Prevents breaking existing, tested code
- ✅ Makes the system more flexible and extensible
- ✅ Reduces regression bugs
- ✅ Enables adding features without touching core logic
- ✅ Follows "Don't touch working code" philosophy

### Interview Talking Points
- "OCP allows us to extend behavior without modifying existing code"
- "We achieve this through abstraction and polymorphism"
- "In Spring Boot, we use Strategy Pattern and dependency injection"
- "When we add a new payment method, we shouldn't modify existing payment processing code"

### ❌ Bad Example - Violating OCP

```java
@Service
public class PaymentService {
    
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("CREDIT_CARD")) {
            // Process credit card payment
            System.out.println("Processing credit card payment: " + amount);
            // Credit card specific logic
        } else if (paymentType.equals("PAYPAL")) {
            // Process PayPal payment
            System.out.println("Processing PayPal payment: " + amount);
            // PayPal specific logic
        } else if (paymentType.equals("DEBIT_CARD")) {
            // Process debit card payment
            System.out.println("Processing debit card payment: " + amount);
            // Debit card specific logic
        }
        // Every time we add a new payment method, we modify this class!
        // This violates OCP
    }
}
```

**Problems:**
- Adding UPI requires modifying PaymentService
- Adding Crypto payment requires modifying PaymentService
- The class grows larger with each new payment method
- Risk of breaking existing payment methods
- Hard to test individual payment methods

### ✅ Good Example - Following OCP

```java
// Abstraction
public interface PaymentProcessor {
    void process(double amount);
    String getPaymentType();
}

@Component("creditCard")
public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing credit card payment: " + amount);
        // Credit card specific logic
    }
    
    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }
}

@Component("paypal")
public class PayPalProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing PayPal payment: " + amount);
        // PayPal specific logic
    }
    
    @Override
    public String getPaymentType() {
        return "PAYPAL";
    }
}

@Component("upi")
public class UPIProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing UPI payment: " + amount);
        // UPI specific logic
    }
    
    @Override
    public String getPaymentType() {
        return "UPI";
    }
}

// Adding crypto payment - just create a new class, no modification needed!
@Component("crypto")
public class CryptoProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing Crypto payment: " + amount);
        // Crypto specific logic
    }
    
    @Override
    public String getPaymentType() {
        return "CRYPTO";
    }
}

@Service
public class PaymentService {
    private final Map<String, PaymentProcessor> processors;
    
    // Spring automatically injects all PaymentProcessor beans as a map
    // Key = bean name, Value = bean instance
    @Autowired
    public PaymentService(Map<String, PaymentProcessor> processors) {
        this.processors = processors;
    }
    
    public void processPayment(String paymentType, double amount) {
        PaymentProcessor processor = processors.get(paymentType);
        if (processor == null) {
            throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
        processor.process(amount);
    }
    
    public List<String> getAvailablePaymentMethods() {
        return new ArrayList<>(processors.keySet());
    }
}
```

**Benefits:**
- Adding new payment methods doesn't modify existing code
- Each payment processor can be tested independently
- PaymentService is closed for modification, open for extension
- Easy to enable/disable payment methods via configuration
- Follows Strategy Pattern

### Alternative: Using Factory Pattern

```java
@Component
public class PaymentProcessorFactory {
    private final Map<String, PaymentProcessor> processors;
    
    @Autowired
    public PaymentProcessorFactory(Map<String, PaymentProcessor> processors) {
        this.processors = processors;
    }
    
    public PaymentProcessor getProcessor(String paymentType) {
        PaymentProcessor processor = processors.get(paymentType);
        if (processor == null) {
            throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
        return processor;
    }
}
```

### How to Achieve OCP
- Use **interfaces and abstract classes** for abstraction
- Use **Strategy Pattern** for different algorithms
- Use **Dependency Injection** (Spring makes this easy)
- Use **Factory Pattern** for object creation
- Think about **extension points** when designing

---

## 3. Liskov Substitution Principle (LSP)

### Definition
> Objects of a superclass should be **replaceable with objects of its subclasses** without breaking the application. Subtypes must be substitutable for their base types.

### Why We Need It
- ✅ Ensures inheritance is used correctly
- ✅ Prevents unexpected behavior when using polymorphism
- ✅ Makes code more predictable and reliable
- ✅ Enables proper abstraction
- ✅ Maintains the "is-a" relationship properly

### Interview Talking Points
- "LSP ensures that inheritance doesn't break the contract of the parent class"
- "If you can't substitute a child for a parent, your inheritance hierarchy is wrong"
- "Common example: Square-Rectangle problem, or Bird-Penguin problem"
- "In Spring Boot, this applies to service implementations and repository extensions"

### ❌ Bad Example - Violating LSP

```java
public class Bird {
    public void fly() {
        System.out.println("Flying in the sky...");
    }
}

public class Sparrow extends Bird {
    @Override
    public void fly() {
        System.out.println("Sparrow flying...");
    }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        // Penguins can't fly! This violates LSP
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// Client code
public class BirdWatcher {
    public void makeBirdFly(Bird bird) {
        bird.fly(); // This will throw exception if bird is a Penguin!
    }
}

// Usage - breaks!
BirdWatcher watcher = new BirdWatcher();
watcher.makeBirdFly(new Sparrow()); // Works fine
watcher.makeBirdFly(new Penguin()); // Runtime exception! Violates LSP
```

**Problems:**
- Penguin cannot be substituted for Bird without breaking the program
- Client code has to check the type before calling fly()
- Violates the "is-a" relationship
- Runtime exceptions instead of compile-time safety

### Another Classic Example: Rectangle-Square

```java
public class Rectangle {
    protected int width;
    protected int height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getArea() {
        return width * height;
    }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        // A square's width and height must be equal
        this.width = width;
        this.height = width; // Violates LSP!
    }
    
    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height; // Violates LSP!
    }
}

// Client code
public void testRectangle(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(4);
    // We expect area to be 20
    assert rect.getArea() == 20; // Fails if rect is a Square! (will be 16)
}
```

### ✅ Good Example - Following LSP

```java
// Use abstraction that all birds can follow
public abstract class Bird {
    public abstract void move();
    public abstract void eat();
}

public class FlyingBird extends Bird {
    @Override
    public void move() {
        fly();
    }
    
    private void fly() {
        System.out.println("Flying in the sky...");
    }
    
    @Override
    public void eat() {
        System.out.println("Eating...");
    }
}

public class Sparrow extends FlyingBird {
    @Override
    public void move() {
        System.out.println("Sparrow flying...");
    }
    
    @Override
    public void eat() {
        System.out.println("Sparrow eating seeds...");
    }
}

public class Penguin extends Bird {
    @Override
    public void move() {
        swim();
    }
    
    private void swim() {
        System.out.println("Swimming in water...");
    }
    
    @Override
    public void eat() {
        System.out.println("Penguin eating fish...");
    }
}

// Client code
public class BirdWatcher {
    public void makeBirdMove(Bird bird) {
        bird.move(); // Works for all birds!
    }
}

// Usage - works perfectly
BirdWatcher watcher = new BirdWatcher();
watcher.makeBirdMove(new Sparrow()); // Sparrow flying...
watcher.makeBirdMove(new Penguin()); // Swimming in water...
```

### Spring Boot Example: Repository Pattern

```java
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByStatus(String status);
}

// Correct LSP - both implementations behave consistently
@Repository
public class UserJpaRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<User> findByStatus(String status) {
        // Returns users with given status - consistent behavior
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE u.status = :status", User.class)
            .setParameter("status", status)
            .getResultList();
    }
}

// If we create a custom implementation, it should maintain the same contract
public class CachedUserRepository implements UserRepository {
    private final UserJpaRepository delegate;
    private final Cache cache;
    
    @Override
    public List<User> findByStatus(String status) {
        // Still returns users with given status - just adds caching
        String cacheKey = "users_" + status;
        List<User> cached = cache.get(cacheKey);
        if (cached != null) {
            return cached;
        }
        
        List<User> users = delegate.findByStatus(status);
        cache.put(cacheKey, users);
        return users;
    }
}
```

### Service Layer Example

```java
public interface NotificationService {
    void sendNotification(String recipient, String message);
}

@Service
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        // Sends notification via email
        System.out.println("Email sent to: " + recipient);
    }
}

@Service
public class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        // Sends notification via SMS
        System.out.println("SMS sent to: " + recipient);
    }
}

// Both can be substituted without breaking the client
@RestController
public class NotificationController {
    private final NotificationService notificationService;
    
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @PostMapping("/notify")
    public void notify(@RequestParam String recipient, @RequestParam String message) {
        // Works with any NotificationService implementation
        notificationService.sendNotification(recipient, message);
    }
}
```

### How to Verify LSP Compliance
1. **Substitution Test**: Can you replace parent with child without breaking anything?
2. **Preconditions**: Subclass should not strengthen preconditions
3. **Postconditions**: Subclass should not weaken postconditions
4. **Invariants**: Subclass should maintain parent's invariants
5. **No Unexpected Exceptions**: Subclass shouldn't throw new exceptions

### LSP Checklist
- ✅ Child class can be used wherever parent class is expected
- ✅ Child class doesn't throw new unchecked exceptions
- ✅ Child class doesn't violate parent's contracts
- ✅ Child class maintains parent's invariants
- ✅ No need for `instanceof` checks in client code

---

## 4. Interface Segregation Principle (ISP)

### Definition
> Clients should not be forced to depend on interfaces they don't use. **Many specific interfaces are better than one general-purpose interface.**

### Why We Need It
- ✅ Reduces unnecessary dependencies
- ✅ Makes code more flexible and decoupled
- ✅ Easier to implement and test
- ✅ Prevents "fat" interfaces with unused methods
- ✅ Smaller, focused interfaces are easier to understand

### Interview Talking Points
- "ISP prevents interface pollution - no unused method stubs"
- "Better to have multiple role-specific interfaces than one god interface"
- "Makes mocking easier in unit tests"
- "Follows the principle of least knowledge"

### ❌ Bad Example - Fat Interface

```java
// One large interface - forces implementers to implement everything
public interface Worker {
    void work();
    void eat();
    void sleep();
    void getPaid();
    void attendMeeting();
    void submitTimesheet();
}

@Component
public class HumanWorker implements Worker {
    @Override
    public void work() {
        System.out.println("Human working...");
    }
    
    @Override
    public void eat() {
        System.out.println("Human eating...");
    }
    
    @Override
    public void sleep() {
        System.out.println("Human sleeping...");
    }
    
    @Override
    public void getPaid() {
        System.out.println("Human getting paid...");
    }
    
    @Override
    public void attendMeeting() {
        System.out.println("Human attending meeting...");
    }
    
    @Override
    public void submitTimesheet() {
        System.out.println("Human submitting timesheet...");
    }
}

// Robot worker forced to implement methods it doesn't need!
@Component
public class RobotWorker implements Worker {
    @Override
    public void work() {
        System.out.println("Robot working...");
    }
    
    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat!");
    }
    
    @Override
    public void sleep() {
        throw new UnsupportedOperationException("Robots don't sleep!");
    }
    
    @Override
    public void getPaid() {
        throw new UnsupportedOperationException("Robots don't get paid!");
    }
    
    @Override
    public void attendMeeting() {
        throw new UnsupportedOperationException("Robots don't attend meetings!");
    }
    
    @Override
    public void submitTimesheet() {
        throw new UnsupportedOperationException("Robots don't submit timesheets!");
    }
}
```

**Problems:**
- RobotWorker forced to implement methods it doesn't need
- Lots of `UnsupportedOperationException` throw statements
- Interface is too broad and rigid
- Hard to add new worker types
- Violates SRP at the interface level

### ✅ Good Example - Segregated Interfaces

```java
// Multiple small, focused interfaces
public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public interface Sleepable {
    void sleep();
}

public interface Payable {
    void getPaid();
}

public interface MeetingAttendee {
    void attendMeeting();
}

public interface TimesheetSubmitter {
    void submitTimesheet();
}

// Human implements all interfaces - it needs all behaviors
@Component
public class HumanWorker implements Workable, Eatable, Sleepable, 
                                     Payable, MeetingAttendee, TimesheetSubmitter {
    @Override
    public void work() {
        System.out.println("Human working...");
    }
    
    @Override
    public void eat() {
        System.out.println("Human eating...");
    }
    
    @Override
    public void sleep() {
        System.out.println("Human sleeping...");
    }
    
    @Override
    public void getPaid() {
        System.out.println("Human getting paid...");
    }
    
    @Override
    public void attendMeeting() {
        System.out.println("Human attending meeting...");
    }
    
    @Override
    public void submitTimesheet() {
        System.out.println("Human submitting timesheet...");
    }
}

// Robot only implements what it needs - no unused methods!
@Component
public class RobotWorker implements Workable {
    @Override
    public void work() {
        System.out.println("Robot working 24/7...");
    }
}

// Contractor might need different combination
@Component
public class ContractorWorker implements Workable, Payable {
    @Override
    public void work() {
        System.out.println("Contractor working...");
    }
    
    @Override
    public void getPaid() {
        System.out.println("Contractor getting paid hourly...");
    }
}

// Service classes depend only on what they need
@Service
public class WorkManager {
    private final List<Workable> workers;
    
    @Autowired
    public WorkManager(List<Workable> workers) {
        this.workers = workers;
    }
    
    public void assignWork() {
        workers.forEach(Workable::work);
    }
}

@Service
public class PayrollService {
    private final List<Payable> employees;
    
    @Autowired
    public PayrollService(List<Payable> employees) {
        this.employees = employees;
    }
    
    public void processPayroll() {
        employees.forEach(Payable::getPaid);
    }
}
```

### Spring Boot REST API Example

```java
// ❌ Bad - One large repository interface
public interface UserOperations {
    // Read operations
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
    List<UserDTO> searchUsers(String query);
    
    // Write operations
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(Long id, UserDTO user);
    void deleteUser(Long id);
    
    // Password operations
    void changePassword(Long id, String oldPassword, String newPassword);
    void resetPassword(String email);
    void sendPasswordResetEmail(String email);
    
    // Admin operations
    void deactivateUser(Long id);
    void activateUser(Long id);
    List<UserDTO> getInactiveUsers();
}

// ReadOnlyUserService forced to implement write methods!
public class ReadOnlyUserService implements UserOperations {
    // Has to throw exceptions for all write operations
    
    @Override
    public UserDTO createUser(UserDTO user) {
        throw new UnsupportedOperationException("Read-only service");
    }
    
    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        throw new UnsupportedOperationException("Read-only service");
    }
    
    @Override
    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Read-only service");
    }
    
    // ...and so on
}

// ✅ Good - Segregated interfaces
public interface UserReadOperations {
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
    List<UserDTO> searchUsers(String query);
}

public interface UserWriteOperations {
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(Long id, UserDTO user);
    void deleteUser(Long id);
}

public interface UserPasswordOperations {
    void changePassword(Long id, String oldPassword, String newPassword);
    void resetPassword(String email);
    void sendPasswordResetEmail(String email);
}

public interface UserAdminOperations {
    void deactivateUser(Long id);
    void activateUser(Long id);
    List<UserDTO> getInactiveUsers();
}

// Full service implements all
@Service
public class UserService implements UserReadOperations, UserWriteOperations, 
                                     UserPasswordOperations, UserAdminOperations {
    // Implements all methods
}

// Read-only service implements only what it needs
@Service
public class ReadOnlyUserService implements UserReadOperations {
    @Override
    public UserDTO getUser(Long id) {
        // Implementation
        return null;
    }
    
    @Override
    public List<UserDTO> getAllUsers() {
        // Implementation
        return null;
    }
    
    @Override
    public List<UserDTO> searchUsers(String query) {
        // Implementation
        return null;
    }
}

// Controller depends only on what it needs
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserReadOperations readOps;
    private final UserWriteOperations writeOps;
    
    @Autowired
    public UserController(UserReadOperations readOps, UserWriteOperations writeOps) {
        this.readOps = readOps;
        this.writeOps = writeOps;
    }
    
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return readOps.getUser(id);
    }
    
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return writeOps.createUser(user);
    }
}
```

### Real-World Example: Document Processing

```java
// ❌ Bad
public interface Document {
    void open();
    void close();
    void save();
    void print();
    void fax();
    void scan();
}

// ✅ Good
public interface Openable {
    void open();
}

public interface Closable {
    void close();
}

public interface Savable {
    void save();
}

public interface Printable {
    void print();
}

public interface Faxable {
    void fax();
}

public interface Scannable {
    void scan();
}

@Component
public class PDFDocument implements Openable, Closable, Savable, Printable {
    // Only implements what makes sense for PDF
}

@Component
public class ReadOnlyDocument implements Openable, Closable, Printable {
    // Read-only, so no Save operation
}

@Component
public class MultiFunctionPrinter implements Printable, Faxable, Scannable {
    // Hardware device
}
```

### How to Apply ISP
1. **Start small**: Create focused interfaces with few methods
2. **Role-based**: Design interfaces based on client roles, not capabilities
3. **Composition over inheritance**: Use interface composition
4. **Check dependencies**: If a client doesn't use a method, split the interface
5. **Review regularly**: Refactor when interfaces grow too large

---

## 5. Dependency Inversion Principle (DIP)

### Definition
> - **High-level modules should not depend on low-level modules**. Both should depend on abstractions.
> - **Abstractions should not depend on details**. Details should depend on abstractions.

### Why We Need It
- ✅ Reduces coupling between components
- ✅ Makes code more testable (easier to mock dependencies)
- ✅ Enables flexibility in switching implementations
- ✅ Core principle behind Dependency Injection in Spring Boot
- ✅ Allows parallel development of components

### Interview Talking Points
- "DIP is the foundation of Dependency Injection in Spring Boot"
- "High-level business logic shouldn't care about low-level implementation details"
- "Both should depend on abstractions (interfaces)"
- "Makes unit testing easier - we can mock dependencies"
- "Inversion means the dependency relationship is inverted"

### Traditional Dependency Flow (Without DIP)

```
High-Level Module (Business Logic)
        ↓ depends on
Low-Level Module (Implementation Details)
```

### With DIP

```
High-Level Module (Business Logic)
        ↓ depends on
    Abstraction (Interface)
        ↑ implemented by
Low-Level Module (Implementation Details)
```

### ❌ Bad Example - Direct Dependency on Concrete Class

```java
// Low-level module
public class MySQLOrderRepository {
    public void save(Order order) {
        System.out.println("Saving to MySQL: " + order);
        // MySQL-specific code
        // Connection to MySQL
        // SQL queries
    }
    
    public Order findById(Long id) {
        System.out.println("Finding from MySQL: " + id);
        // MySQL-specific code
        return null;
    }
}

// High-level module directly depends on low-level module
@Service
public class OrderService {
    // Tight coupling to MySQLOrderRepository
    private MySQLOrderRepository orderRepository = new MySQLOrderRepository();
    
    public void createOrder(Order order) {
        // Business logic
        order.setStatus("PENDING");
        orderRepository.save(order);
    }
    
    public Order getOrder(Long id) {
        return orderRepository.findById(id);
    }
}
```

**Problems:**
- OrderService is tightly coupled to MySQLOrderRepository
- Can't switch to PostgreSQL without modifying OrderService
- Hard to unit test OrderService (can't mock the repository)
- Can't reuse OrderService with different storage mechanisms
- High-level business logic depends on low-level database details

### ✅ Good Example - Depend on Abstraction

```java
// Abstraction (Interface)
public interface OrderRepository {
    void save(Order order);
    Order findById(Long id);
    List<Order> findByStatus(String status);
}

// Low-level module implements the abstraction
@Repository
public class MySQLOrderRepository implements OrderRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(Order order) {
        System.out.println("Saving to MySQL: " + order);
        entityManager.persist(order);
    }
    
    @Override
    public Order findById(Long id) {
        System.out.println("Finding from MySQL: " + id);
        return entityManager.find(Order.class, id);
    }
    
    @Override
    public List<Order> findByStatus(String status) {
        return entityManager.createQuery(
            "SELECT o FROM Order o WHERE o.status = :status", Order.class)
            .setParameter("status", status)
            .getResultList();
    }
}

// Alternative low-level module - MongoDB
@Repository
public class MongoOrderRepository implements OrderRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public void save(Order order) {
        System.out.println("Saving to MongoDB: " + order);
        mongoTemplate.save(order);
    }
    
    @Override
    public Order findById(Long id) {
        System.out.println("Finding from MongoDB: " + id);
        return mongoTemplate.findById(id, Order.class);
    }
    
    @Override
    public List<Order> findByStatus(String status) {
        Query query = new Query(Criteria.where("status").is(status));
        return mongoTemplate.find(query, Order.class);
    }
}

// High-level module depends on abstraction
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    
    // Dependency is injected via constructor
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public void createOrder(Order order) {
        // Business logic - doesn't care about storage details
        order.setStatus("PENDING");
        orderRepository.save(order);
    }
    
    public Order getOrder(Long id) {
        return orderRepository.findById(id);
    }
    
    public void processOrder(Long id) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            order.setStatus("PROCESSING");
            orderRepository.save(order);
        }
    }
}
```

**Benefits:**
- OrderService depends on abstraction, not concrete implementation
- Easy to switch between MySQL and MongoDB
- Easy to test - we can inject a mock repository
- High-level business logic is independent of low-level details
- Can add new storage implementations without touching OrderService

### Configuration to Switch Implementation

```java
@Configuration
public class RepositoryConfig {
    
    @Bean
    @Primary
    @ConditionalOnProperty(name = "db.type", havingValue = "mysql", matchIfMissing = true)
    public OrderRepository mysqlOrderRepository() {
        return new MySQLOrderRepository();
    }
    
    @Bean
    @ConditionalOnProperty(name = "db.type", havingValue = "mongo")
    public OrderRepository mongoOrderRepository() {
        return new MongoOrderRepository();
    }
}

// In application.properties
// db.type=mysql  (or mongo to switch)
```

### Unit Testing Example

```java
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;
    
    @InjectMocks
    private OrderService orderService;
    
    @Test
    public void testCreateOrder() {
        // Given
        Order order = new Order();
        order.setId(1L);
        
        // When
        orderService.createOrder(order);
        
        // Then
        verify(orderRepository).save(order);
        assertEquals("PENDING", order.getStatus());
    }
    
    @Test
    public void testGetOrder() {
        // Given
        Long orderId = 1L;
        Order expectedOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(expectedOrder);
        
        // When
        Order result = orderService.getOrder(orderId);
        
        // Then
        assertEquals(expectedOrder, result);
        verify(orderRepository).findById(orderId);
    }
}
```

### Real-World Example: Notification System

```java
// ❌ Bad - Direct dependency
@Service
public class UserService {
    // Directly creates EmailSender - tight coupling
    private EmailSender emailSender = new EmailSender();
    
    public void registerUser(User user) {
        userRepository.save(user);
        emailSender.sendEmail(user.getEmail(), "Welcome!");
    }
}

// ✅ Good - Depends on abstraction
public interface NotificationSender {
    void send(String recipient, String message);
}

@Component
public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending email to: " + recipient);
        // Email sending logic
    }
}

@Component
public class SMSNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending SMS to: " + recipient);
        // SMS sending logic
    }
}

@Component
public class PushNotificationSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending push notification to: " + recipient);
        // Push notification logic
    }
}

@Service
public class UserService {
    private final UserRepository userRepository;
    private final NotificationSender notificationSender;
    
    @Autowired
    public UserService(UserRepository userRepository, 
                      NotificationSender notificationSender) {
        this.userRepository = userRepository;
        this.notificationSender = notificationSender;
    }
    
    public void registerUser(User user) {
        userRepository.save(user);
        notificationSender.send(user.getEmail(), "Welcome!");
    }
}
```

### Layered Architecture with DIP

```java
// Controller Layer (High-level)
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService; // Depends on abstraction
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.ok(created);
    }
}

// Service Layer (High-level business logic)
@Service
public interface OrderService {
    Order createOrder(Order order);
    Order getOrder(Long id);
    void cancelOrder(Long id);
}

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository; // Depends on abstraction
    private final PaymentService paymentService;   // Depends on abstraction
    
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                           PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }
    
    @Override
    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        Order saved = orderRepository.save(order);
        paymentService.processPayment(saved.getId(), saved.getTotal());
        return saved;
    }
    
    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id);
    }
    
    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id);
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}

// Repository Layer (Low-level)
public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);
}

@Repository
public class JpaOrderRepository implements OrderRepository {
    // JPA implementation
}
```

### DIP in Spring Boot - Constructor Injection (Recommended)

```java
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmailService emailService;
    
    // Constructor injection - recommended approach
    @Autowired // Optional in recent Spring versions
    public OrderService(OrderRepository orderRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.emailService = emailService;
    }
}
```

### Benefits of DIP
- ✅ **Testability**: Easy to inject mocks/stubs
- ✅ **Flexibility**: Easy to swap implementations
- ✅ **Decoupling**: Components don't know about each other's internals
- ✅ **Parallel Development**: Teams can work on interfaces and implementations independently
- ✅ **Maintainability**: Changes in low-level modules don't affect high-level modules

### How Spring Boot Helps with DIP
- **@Autowired**: Automatic dependency injection
- **@Component, @Service, @Repository**: Component scanning
- **@Configuration & @Bean**: Manual bean definition
- **Constructor Injection**: Preferred way to inject dependencies
- **Interface-based design**: JPA repositories, service interfaces

---

## Real-World Spring Boot Example

### Scenario: E-Commerce Notification System

Let's build a comprehensive example that demonstrates all SOLID principles:

```java
// ===============================
// Domain Model
// ===============================
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerEmail;
    private String customerPhone;
    private double totalAmount;
    private String status;
    
    // Getters and setters
}

// ===============================
// INTERFACES (DIP, ISP)
// ===============================

// ISP - Small, focused interfaces
public interface NotificationSender {
    void send(String recipient, String message);
    String getChannelType();
}

public interface NotificationFormatter {
    String format(String template, Map<String, Object> params);
}

public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);
    List<Order> findByStatus(String status);
}

// ===============================
// SRP - Each class has single responsibility
// ===============================

// Responsibility: Email notification
@Component("email")
public class EmailNotificationSender implements NotificationSender {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void send(String recipient, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject("Order Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        System.out.println("Email sent to: " + recipient);
    }
    
    @Override
    public String getChannelType() {
        return "EMAIL";
    }
}

// Responsibility: SMS notification
@Component("sms")
public class SMSNotificationSender implements NotificationSender {
    
    @Value("${sms.api.key}")
    private String apiKey;
    
    @Override
    public void send(String recipient, String message) {
        // SMS API call
        System.out.println("SMS sent to: " + recipient + " - " + message);
    }
    
    @Override
    public String getChannelType() {
        return "SMS";
    }
}

// Responsibility: Push notification
@Component("push")
public class PushNotificationSender implements NotificationSender {
    
    @Override
    public void send(String recipient, String message) {
        // Push notification logic
        System.out.println("Push notification sent to: " + recipient);
    }
    
    @Override
    public String getChannelType() {
        return "PUSH";
    }
}

// Responsibility: Message formatting
@Component
public class TemplateNotificationFormatter implements NotificationFormatter {
    
    @Override
    public String format(String template, Map<String, Object> params) {
        String result = template;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", 
                                   String.valueOf(entry.getValue()));
        }
        return result;
    }
}

// ===============================
// OCP - Open for Extension, Closed for Modification
// ===============================

// We can add new notification channels without modifying existing code
@Component("whatsapp")
public class WhatsAppNotificationSender implements NotificationSender {
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("WhatsApp message sent to: " + recipient);
    }
    
    @Override
    public String getChannelType() {
        return "WHATSAPP";
    }
}

// ===============================
// Service Layer (DIP, SRP)
// ===============================

@Service
public class NotificationService {
    private final Map<String, NotificationSender> senders;
    private final NotificationFormatter formatter;
    
    // DIP - Depend on abstractions
    @Autowired
    public NotificationService(Map<String, NotificationSender> senders,
                              NotificationFormatter formatter) {
        this.senders = senders;
        this.formatter = formatter;
    }
    
    // OCP - Adding new channels doesn't require code changes
    public void sendNotification(String channel, String recipient, 
                                 String template, Map<String, Object> params) {
        NotificationSender sender = senders.get(channel);
        if (sender == null) {
            throw new IllegalArgumentException("Unknown channel: " + channel);
        }
        
        String message = formatter.format(template, params);
        sender.send(recipient, message);
    }
    
    public List<String> getAvailableChannels() {
        return new ArrayList<>(senders.keySet());
    }
}

// SRP - Responsible only for order business logic
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    
    @Autowired
    public OrderService(OrderRepository orderRepository,
                       NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }
    
    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        
        // Send confirmation
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", savedOrder.getId());
        params.put("amount", savedOrder.getTotalAmount());
        
        notificationService.sendNotification(
            "email",
            savedOrder.getCustomerEmail(),
            "Order {{orderId}} created. Total: ${{amount}}",
            params
        );
        
        return savedOrder;
    }
    
    public void updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        
        order.setStatus(newStatus);
        orderRepository.save(order);
        
        // Notify customer
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("status", newStatus);
        
        notificationService.sendNotification(
            "sms",
            order.getCustomerPhone(),
            "Order {{orderId}} status: {{status}}",
            params
        );
    }
}

// ===============================
// Repository Layer (DIP)
// ===============================

@Repository
public class JpaOrderRepository implements OrderRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            entityManager.persist(order);
        } else {
            entityManager.merge(order);
        }
        return order;
    }
    
    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }
    
    @Override
    public List<Order> findByStatus(String status) {
        return entityManager.createQuery(
            "SELECT o FROM Order o WHERE o.status = :status", Order.class)
            .setParameter("status", status)
            .getResultList();
    }
}

// ===============================
// Controller Layer
// ===============================

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, 
                                             @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok().build();
    }
}

// ===============================
// Unit Test Example
// ===============================

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private NotificationService notificationService;
    
    @InjectMocks
    private OrderService orderService;
    
    @Test
    public void testCreateOrder() {
        // Given
        Order order = new Order();
        order.setCustomerEmail("test@example.com");
        order.setTotalAmount(100.0);
        
        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerEmail("test@example.com");
        savedOrder.setTotalAmount(100.0);
        savedOrder.setStatus("PENDING");
        
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        
        // When
        Order result = orderService.createOrder(order);
        
        // Then
        assertEquals("PENDING", result.getStatus());
        verify(orderRepository).save(any(Order.class));
        verify(notificationService).sendNotification(
            eq("email"),
            eq("test@example.com"),
            anyString(),
            anyMap()
        );
    }
}
```

### How This Example Demonstrates SOLID

1. **SRP**: Each class has one responsibility
   - `EmailNotificationSender`: Only sends emails
   - `OrderService`: Only handles order business logic
   - `NotificationService`: Only manages notifications

2. **OCP**: Open for extension
   - Add `WhatsAppNotificationSender` without modifying existing code
   - Add new notification channels by implementing `NotificationSender`

3. **LSP**: All notification senders are substitutable
   - Any `NotificationSender` can replace another
   - Client code works with `NotificationSender` interface

4. **ISP**: Focused interfaces
   - `NotificationSender`: Only send method
   - `NotificationFormatter`: Only format method
   - No fat interfaces forcing unused methods

5. **DIP**: Depend on abstractions
   - `OrderService` depends on `OrderRepository` interface, not implementation
   - `NotificationService` depends on `NotificationSender` interface
   - Easy to mock for testing

---

## Common Interview Questions

### Q1: What are SOLID principles?
**Answer**: SOLID is an acronym for five design principles that make software designs more understandable, flexible, and maintainable:
- **S**ingle Responsibility Principle
- **O**pen/Closed Principle
- **L**iskov Substitution Principle
- **I**nterface Segregation Principle
- **D**ependency Inversion Principle

### Q2: Why do we need SOLID principles?
**Answer**: SOLID principles help us:
- Write cleaner, more maintainable code
- Make systems easier to test and debug
- Reduce code duplication
- Make code more flexible and reusable
- Reduce coupling and increase cohesion
- Make teams more productive in the long run

### Q3: Explain SRP with a Spring Boot example
**Answer**: SRP states that a class should have only one reason to change. In Spring Boot:
- **Controllers** handle HTTP requests/responses
- **Services** contain business logic
- **Repositories** handle data access
- **Validators** handle validation logic

Each layer has a single responsibility and changes independently.

### Q4: How does Spring Boot help with SOLID principles?
**Answer**:
- **SRP**: Layered architecture (Controller, Service, Repository)
- **OCP**: Component scanning, interface-based design
- **LSP**: JPA repositories are substitutable
- **ISP**: Spring Data repositories are focused interfaces
- **DIP**: Dependency Injection via @Autowired

### Q5: What's the difference between DIP and Dependency Injection?
**Answer**:
- **DIP (Principle)**: High-level modules should depend on abstractions, not concrete implementations
- **Dependency Injection (Pattern)**: A way to implement DIP - dependencies are provided from outside rather than created inside

DI is a technique to achieve DIP.

### Q6: Can you violate SOLID principles intentionally?
**Answer**: Yes, sometimes for pragmatic reasons:
- Small utility classes might combine responsibilities
- Performance-critical code might sacrifice some principles
- Prototypes or POCs might skip some principles

But in production code, following SOLID leads to better long-term maintainability.

### Q7: Give an example of violating LSP
**Answer**: Classic example is Square-Rectangle problem. If Square extends Rectangle and overrides setWidth/setHeight to keep sides equal, clients expecting Rectangle behavior will break. This violates LSP because Square cannot be substituted for Rectangle without breaking the contract.

### Q8: How do you identify ISP violations?
**Answer**: Look for:
- Interfaces with many methods (5+ is a red flag)
- Implementers throwing `UnsupportedOperationException`
- Implementers with empty method bodies
- Clients using only a subset of interface methods

Solution: Split into smaller, role-specific interfaces.

### Q9: Difference between OCP and adding new features?
**Answer**: OCP means adding features through extension (new classes) rather than modification (changing existing classes). Example: Adding a new payment method should be a new class implementing PaymentProcessor, not adding if-else to existing PaymentService.

### Q10: How do you refactor code to follow SOLID?
**Answer**:
1. **Identify violations**: Large classes, tight coupling, hard to test
2. **Extract responsibilities**: Create new classes for each responsibility
3. **Introduce interfaces**: Create abstractions
4. **Use dependency injection**: Inject dependencies instead of creating them
5. **Test incrementally**: Ensure each refactoring step works

---

## Code Smells to Avoid

### 🚨 Violations of SRP
```java
// BAD: God class doing everything
@Service
public class UserManager {
    public void createUser() { }
    public void validateEmail() { }
    public void sendEmail() { }
    public void logAction() { }
    public void encryptPassword() { }
}
```

### 🚨 Violations of OCP
```java
// BAD: Modifying class for every new type
public class DiscountCalculator {
    public double calculate(String type, double amount) {
        if (type.equals("SUMMER")) return amount * 0.9;
        if (type.equals("WINTER")) return amount * 0.8;
        // Adding new discount requires modifying this class
    }
}
```

### 🚨 Violations of LSP
```java
// BAD: Child throws exception parent doesn't
public class Bird {
    public void fly() { }
}

public class Penguin extends Bird {
    public void fly() {
        throw new UnsupportedOperationException();
    }
}
```

### 🚨 Violations of ISP
```java
// BAD: Fat interface
public interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void submitReport();
}
```

### 🚨 Violations of DIP
```java
// BAD: Direct dependency on concrete class
@Service
public class OrderService {
    private MySQLOrderRepository repo = new MySQLOrderRepository();
}
```

---

## Quick Checklist Before Interview

### When discussing SOLID in interviews

✅ **Understand the "Why"**
- Explain why each principle matters
- Give real-world consequences of violations

✅ **Have Examples Ready**
- Prepare 1-2 examples for each principle
- Include Spring Boot specific examples

✅ **Know the Tradeoffs**
- Sometimes principles conflict
- Know when it's okay to bend rules

✅ **Practice Explaining**
- Explain each principle in 2-3 sentences
- Use analogies (e.g., SRP = Swiss Army knife vs specialized tools)

✅ **Connect to Your Experience**
- Reference projects where you applied SOLID
- Discuss refactoring stories

### Key Phrases to Use

- "In my experience with Spring Boot..."
- "This follows the [X] principle because..."
- "Without this principle, we would face..."
- "The benefit here is..."
- "A common violation I've seen is..."

---

## Final Summary

| Principle | Remember This |
|-----------|--------------|
| **SRP** | One class, one job, one reason to change |
| **OCP** | Extend behavior without modifying existing code |
| **LSP** | Child must be usable wherever parent is used |
| **ISP** | Small, focused interfaces - no unused methods |
| **DIP** | Depend on interfaces, not implementations |

### The Bottom Line
SOLID principles are not strict rules but **guidelines to write better code**. They help you:
- Build maintainable systems
- Write testable code
- Create flexible architectures
- Reduce bugs and technical debt

In Spring Boot, these principles are naturally encouraged through:
- Layered architecture
- Dependency Injection
- Interface-based design
- Component scanning

**Practice applying these principles in your daily coding, and they'll become second nature!** 🚀
