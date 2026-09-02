# 🏛️ Low-Level Design (LLD) — Design Patterns & Interview Guide

> **Context**: SDE-1/SDE-2 interviews at Amazon, Salesforce, and similar companies
> **What interviewers test**: SOLID principles, design patterns, clean OOP, extensibility

---

## 🎯 What Is the LLD Round?

You're given a real-world system (e.g., "Design a Parking Lot") and asked to:
1. Identify **classes, interfaces, and relationships**
2. Apply **design patterns** where appropriate
3. Write **clean, extensible, working code** (not pseudocode)

> [!IMPORTANT]
> LLD is NOT system design. LLD = class-level design + code. System design = distributed architecture + components.

---

## 📐 SOLID Principles (Know These Cold)

These are the **foundation** of every LLD answer. Interviewers check if your design follows these.

| Principle | What It Means | Violation Example |
|-----------|--------------|-------------------|
| **S** — Single Responsibility | A class should have only one reason to change | A `User` class that handles authentication AND sends emails |
| **O** — Open/Closed | Open for extension, closed for modification | Adding a new payment type requires modifying existing `PaymentProcessor` code |
| **L** — Liskov Substitution | Subtypes must be substitutable for their base types | A `Square` extending `Rectangle` that breaks `setWidth()`/`setHeight()` |
| **I** — Interface Segregation | Don't force clients to implement interfaces they don't use | A `Worker` interface with both `work()` and `eat()` — robots can't eat |
| **D** — Dependency Inversion | Depend on abstractions, not concretions | A `NotificationService` directly instantiating `EmailSender` instead of using an `INotificationSender` interface |

---

## 🧩 The 10 Most Important Design Patterns

> [!TIP]
> You don't need to memorize all 23 GoF patterns. These 10 cover **95% of LLD interview questions**. Learn them in this order.

---

### 1. Strategy Pattern ⭐ (Most Important)

**When to use**: When you have multiple algorithms/behaviors and want to swap them at runtime.

**Real-world**: Payment methods (UPI, Credit Card, PayPal), sorting strategies, pricing strategies.

```java
// Strategy interface
interface PaymentStrategy {
    void pay(int amount);
}

// Concrete strategies
class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " via Credit Card"); }
}

class UPIPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " via UPI"); }
}

// Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(int amount) {
        paymentStrategy.pay(amount);  // Delegates to strategy
    }
}
```

**Interview signal**: Whenever a problem says "support multiple types of X" → Strategy.

---

### 2. Observer Pattern ⭐

**When to use**: When one object changes state and multiple others need to be notified.

**Real-world**: Event listeners, notification systems, stock price updates, pub-sub.

```java
interface Observer {
    void update(String event, Object data);
}

interface Subject {
    void subscribe(Observer o);
    void unsubscribe(Observer o);
    void notifyObservers(String event, Object data);
}

class Store implements Subject {
    private List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer o) { observers.add(o); }
    public void unsubscribe(Observer o) { observers.remove(o); }

    public void notifyObservers(String event, Object data) {
        for (Observer o : observers) o.update(event, data);
    }

    public void newItemArrived(String item) {
        notifyObservers("NEW_ITEM", item);  // All subscribers get notified
    }
}
```

**Interview signal**: "Notify users when X happens", "Real-time updates" → Observer.

---

### 3. Factory Method / Abstract Factory ⭐

**When to use**: When object creation logic is complex or you want to decouple creation from usage.

**Real-world**: Document creators (PDF, Word, Excel), notification channels (Email, SMS, Push).

```java
// Factory Method
interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    public void send(String message) { System.out.println("Email: " + message); }
}

class SMSNotification implements Notification {
    public void send(String message) { System.out.println("SMS: " + message); }
}

class NotificationFactory {
    public static Notification create(String type) {
        return switch (type) {
            case "EMAIL" -> new EmailNotification();
            case "SMS"   -> new SMSNotification();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}

// Usage — client doesn't know concrete classes
Notification n = NotificationFactory.create("EMAIL");
n.send("Hello!");
```

**Interview signal**: "Create different types of X based on input" → Factory.

---

### 4. Singleton Pattern

**When to use**: When exactly one instance of a class should exist globally.

**Real-world**: Database connection pool, logger, configuration manager, cache.

```java
class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() { /* private constructor */ }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();  // Double-checked locking
                }
            }
        }
        return instance;
    }
}
```

> [!WARNING]
> Interviewers often ask about **thread-safety** with Singleton. Know the double-checked locking approach and why `volatile` is needed.

---

### 5. Decorator Pattern ⭐

**When to use**: When you want to add responsibilities to objects dynamically without modifying existing code.

**Real-world**: Pizza toppings, coffee add-ons, I/O streams (`BufferedReader` wrapping `FileReader`).

```java
interface Coffee {
    double cost();
    String description();
}

class BasicCoffee implements Coffee {
    public double cost() { return 50; }
    public String description() { return "Basic Coffee"; }
}

// Decorators
abstract class CoffeeDecorator implements Coffee {
    protected Coffee wrapped;
    CoffeeDecorator(Coffee coffee) { this.wrapped = coffee; }
}

class MilkDecorator extends CoffeeDecorator {
    MilkDecorator(Coffee coffee) { super(coffee); }
    public double cost() { return wrapped.cost() + 15; }
    public String description() { return wrapped.description() + " + Milk"; }
}

class WhipDecorator extends CoffeeDecorator {
    WhipDecorator(Coffee coffee) { super(coffee); }
    public double cost() { return wrapped.cost() + 25; }
    public String description() { return wrapped.description() + " + Whip"; }
}

// Usage: stackable!
Coffee order = new WhipDecorator(new MilkDecorator(new BasicCoffee()));
// → "Basic Coffee + Milk + Whip", cost = 90
```

**Interview signal**: "Add optional features/add-ons to a base object" → Decorator.

---

### 6. Adapter Pattern

**When to use**: When you need to make incompatible interfaces work together.

**Real-world**: Legacy system integration, third-party API wrappers, plug adapters.

```java
// Existing interface your code expects
interface MediaPlayer {
    void play(String filename);
}

// Third-party library with a different interface
class VLCPlayer {
    void playVLC(String filename) { System.out.println("Playing VLC: " + filename); }
}

// Adapter makes VLCPlayer compatible with MediaPlayer
class VLCAdapter implements MediaPlayer {
    private VLCPlayer vlcPlayer = new VLCPlayer();

    public void play(String filename) {
        vlcPlayer.playVLC(filename);  // Translates the call
    }
}
```

**Interview signal**: "Integrate with an existing/legacy system" → Adapter.

---

### 7. Builder Pattern

**When to use**: When constructing complex objects with many optional parameters.

**Real-world**: Query builders, meal ordering, configuration objects, HTTP request builders.

```java
class User {
    private final String name;       // required
    private final String email;      // required
    private final int age;           // optional
    private final String phone;      // optional

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.phone = builder.phone;
    }

    static class Builder {
        private String name;
        private String email;
        private int age;
        private String phone;

        Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }

        Builder age(int age) { this.age = age; return this; }
        Builder phone(String phone) { this.phone = phone; return this; }

        User build() { return new User(this); }
    }
}

// Usage — clean, readable
User user = new User.Builder("Sridhar", "sridhar@email.com")
                .age(25)
                .phone("9876543210")
                .build();
```

**Interview signal**: "Object with many optional fields" or "step-by-step construction" → Builder.

---

### 8. Command Pattern

**When to use**: When you want to encapsulate a request as an object (supports undo/redo, queuing, logging).

**Real-world**: Text editor undo/redo, remote control buttons, task queues, transaction management.

```java
interface Command {
    void execute();
    void undo();
}

class LightOnCommand implements Command {
    private Light light;
    LightOnCommand(Light light) { this.light = light; }

    public void execute() { light.turnOn(); }
    public void undo() { light.turnOff(); }
}

class RemoteControl {
    private Stack<Command> history = new Stack<>();

    void pressButton(Command cmd) {
        cmd.execute();
        history.push(cmd);
    }

    void pressUndo() {
        if (!history.isEmpty()) history.pop().undo();
    }
}
```

**Interview signal**: "Support undo/redo" or "queue actions for later execution" → Command.

---

### 9. State Pattern

**When to use**: When an object's behavior changes based on its internal state (replaces complex if-else/switch chains).

**Real-world**: Vending machine states, order status (Placed → Shipped → Delivered), traffic lights.

```java
interface VendingMachineState {
    void insertCoin(VendingMachine machine);
    void selectItem(VendingMachine machine);
    void dispenseItem(VendingMachine machine);
}

class NoCoinState implements VendingMachineState {
    public void insertCoin(VendingMachine m) {
        System.out.println("Coin inserted");
        m.setState(new HasCoinState());  // Transition to next state
    }
    public void selectItem(VendingMachine m) { System.out.println("Insert coin first!"); }
    public void dispenseItem(VendingMachine m) { System.out.println("Insert coin first!"); }
}

class HasCoinState implements VendingMachineState {
    public void insertCoin(VendingMachine m) { System.out.println("Coin already inserted"); }
    public void selectItem(VendingMachine m) {
        System.out.println("Item selected");
        m.setState(new DispensingState());
    }
    public void dispenseItem(VendingMachine m) { System.out.println("Select item first!"); }
}
```

**Interview signal**: "Object goes through multiple states" or "replace a big switch/if-else" → State.

---

### 10. Iterator Pattern

**When to use**: When you want to traverse a collection without exposing its internal structure.

**Real-world**: Java's `Iterator`, custom collection traversal, paginated results.

```java
interface Iterator<T> {
    boolean hasNext();
    T next();
}

class BookShelf {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book b) { books.add(b); }

    public Iterator<Book> iterator() {
        return new Iterator<Book>() {
            private int index = 0;
            public boolean hasNext() { return index < books.size(); }
            public Book next() { return books.get(index++); }
        };
    }
}
```

**Interview signal**: Less commonly asked directly, but useful when designing custom collections.

---

## 🔥 Quick Reference: Pattern → Use Case

| Pattern | One-Line Trigger | Example Problem |
|---------|-----------------|-----------------|
| **Strategy** | "Support multiple algorithms/behaviors" | Parking lot pricing, payment methods |
| **Observer** | "Notify when something changes" | Notification system, event bus |
| **Factory** | "Create objects based on type/input" | Vehicle factory, document creator |
| **Singleton** | "Only one instance needed" | Logger, config manager |
| **Decorator** | "Add features dynamically" | Pizza toppings, I/O streams |
| **Adapter** | "Make incompatible things work together" | Legacy integration |
| **Builder** | "Complex object with many options" | Query builder, meal builder |
| **Command** | "Undo/redo, queue operations" | Text editor, remote control |
| **State** | "Behavior changes with state" | Vending machine, order lifecycle |
| **Iterator** | "Traverse without exposing internals" | Custom collections |

---

## 📋 Top 12 LLD Interview Problems

| # | Problem | Patterns Used | Frequency |
|---|---------|--------------|-----------|
| 1 | **Design Parking Lot** | Strategy, Factory, Observer | 🔴🔴🔴 |
| 2 | **Design Tic-Tac-Toe** | State, Strategy | 🔴🔴🔴 |
| 3 | **Design Snake & Ladder Game** | State, Factory, Observer | 🔴🔴 |
| 4 | **Design BookMyShow / Movie Ticket Booking** | Strategy, Observer, Singleton | 🔴🔴🔴 |
| 5 | **Design Elevator System** | State, Strategy, Observer | 🔴🔴 |
| 6 | **Design Library Management System** | Factory, Observer, Singleton | 🔴🔴 |
| 7 | **Design Vending Machine** | State, Factory | 🔴🔴🔴 |
| 8 | **Design Chess** | Strategy, State, Factory, Command | 🔴🔴 |
| 9 | **Design ATM Machine** | State, Strategy, Chain of Responsibility | 🔴🔴 |
| 10 | **Design Logger / Logging Framework** | Singleton, Observer, Decorator | 🔴🔴 |
| 11 | **Design File System** | Composite, Iterator | 🔴🔴 |
| 12 | **Design Splitwise (Expense Sharing)** | Strategy, Observer | 🔴🔴🔴 |

---

## 🛠️ How to Approach an LLD Round (Framework)

```
Step 1: CLARIFY (2–3 min)
  → What are the core use cases?
  → What are the actors (user, admin, system)?
  → Any constraints? (concurrent users, real-time?)

Step 2: IDENTIFY CORE OBJECTS (3–5 min)
  → Nouns in requirements = potential classes
  → Verbs = potential methods
  → Draw class relationships (has-a, is-a)

Step 3: DEFINE INTERFACES & CLASSES (5–7 min)
  → Start with interfaces/abstract classes
  → Apply SOLID — especially Single Responsibility & Open/Closed
  → Identify where design patterns fit

Step 4: WRITE CODE (15–20 min)
  → Start with the core classes
  → Implement key methods
  → Use enums for fixed categories (Status, Type)
  → Show design patterns in action

Step 5: DISCUSS EXTENSIBILITY (3–5 min)
  → "If we add a new vehicle type, we just create a new class"
  → "If pricing strategy changes, we swap the Strategy"
  → Show that your design is open for extension
```

---

## 📚 Best Resources for LLD

### YouTube (Free) — Start Here

| Channel | What They Cover | Language |
|---------|----------------|----------|
| [**Concept && Coding (Shreyansh Jain)**](https://www.youtube.com/@ConceptandCoding) | Best LLD playlist in India, covers all 12 problems above with code | Java |
| [**Sudocode**](https://www.youtube.com/@sudocode) | Design patterns + LLD problems | Java |
| [**Tech Dummies (Narendra L)**](https://www.youtube.com/@TechDummiesNaworksL) | Design patterns with real examples | Java |
| [**Christopher Okhravi**](https://www.youtube.com/@ChristopherOkhravi) | Excellent pattern explanations (GoF book walkthrough) | Language-agnostic |

### Books

| Book | Best For |
|------|----------|
| **Head First Design Patterns** | Beginner-friendly, visual, fun to read — START HERE |
| **Design Patterns (GoF)** | The classic reference — use as a lookup, not cover-to-cover |
| **Clean Code (Robert C. Martin)** | Writing clean, maintainable code — SOLID principles |

### GitHub Repos & Websites

| Resource | Link |
|----------|------|
| **Refactoring Guru** | [refactoring.guru/design-patterns](https://refactoring.guru/design-patterns) — Beautiful visual explanations of all patterns |
| **Awesome LLD** | [github.com/ashishps1/awesome-low-level-design](https://github.com/ashishps1/awesome-low-level-design) — Code solutions for 20+ LLD problems |
| **Design Patterns for Humans** | [github.com/kamranahmedse/design-patterns-for-humans](https://github.com/kamranahmedse/design-patterns-for-humans) — Plain English explanations |

### Courses (Paid)

| Course | Platform | Notes |
|--------|----------|-------|
| Concept && Coding LLD Course | Their website | Most comprehensive in India |
| Design Patterns in Java | Udemy (Dmitri Nesteruk) | Deep dive with code |

---

## 📅 Suggested LLD Study Plan (4 Weeks, Parallel with DSA)

| Week | Focus | Action Items |
|------|-------|-------------|
| **1** | SOLID + Strategy, Observer, Factory | Read Head First Design Patterns Ch 1–4, solve Parking Lot |
| **2** | Singleton, Decorator, Builder | Solve Vending Machine, BookMyShow |
| **3** | Adapter, Command, State | Solve Elevator System, Tic-Tac-Toe |
| **4** | Iterator + Revision | Solve 2 more problems from the list, revise all patterns |

> [!TIP]
> **Best approach**: Watch Concept && Coding's video for a problem → pause → try designing it yourself → compare with their solution → code it from scratch without looking.
