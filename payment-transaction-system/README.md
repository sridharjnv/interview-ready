# 💳 Payment Transaction System — Complete Solution

> **Problem #1** from Alacriti System Test Practice

## Project Structure

```
payment-transaction-system/
├── pom.xml
└── src/main/java/com/alacriti/payment/
    ├── PaymentApplication.java
    ├── model/
    │   ├── Account.java
    │   ├── Transaction.java
    │   ├── TransactionType.java
    │   └── TransactionStatus.java
    ├── repository/
    │   ├── AccountRepository.java
    │   └── TransactionRepository.java
    ├── service/
    │   └── AccountService.java
    ├── controller/
    │   ├── AccountController.java
    │   └── TransferController.java
    ├── dto/
    │   ├── CreateAccountRequest.java
    │   ├── AmountRequest.java
    │   ├── TransferRequest.java
    │   └── TransferResponse.java
    └── exception/
        ├── ResourceNotFoundException.java
        ├── InsufficientBalanceException.java
        └── GlobalExceptionHandler.java
```

## How to Build & Run (on Linux VM)

```bash
# 1. Create project directory
mkdir -p payment-system && cd payment-system

# 2. Use Spring Initializr via curl (if internet available)
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2 \
  -d type=maven-project \
  -d javaVersion=17 \
  -d groupId=com.alacriti \
  -d artifactId=payment \
  -d name=payment \
  -o payment.zip && unzip payment.zip

# 3. OR create pom.xml manually (see pom.xml file)

# 4. Create all Java files (see src/ directory)

# 5. Build and run
mvn clean install -DskipTests
mvn spring-boot:run

# OR
mvn clean package -DskipTests
java -jar target/payment-0.0.1-SNAPSHOT.jar
```

## Test with curl

```bash
# Create accounts
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountHolderName": "Shanmuk", "email": "shanmuk@test.com"}'

curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountHolderName": "Ravi", "email": "ravi@test.com"}'

# Get account
curl http://localhost:8080/api/accounts/1

# Deposit
curl -X POST http://localhost:8080/api/accounts/1/deposit \
  -H "Content-Type: application/json" \
  -d '{"amount": 5000}'

# Withdraw
curl -X POST http://localhost:8080/api/accounts/1/withdraw \
  -H "Content-Type: application/json" \
  -d '{"amount": 2000}'

# Withdraw (should fail — insufficient balance)
curl -X POST http://localhost:8080/api/accounts/1/withdraw \
  -H "Content-Type: application/json" \
  -d '{"amount": 100000}'

# Transfer
curl -X POST http://localhost:8080/api/transfers \
  -H "Content-Type: application/json" \
  -d '{"fromAccountId": 1, "toAccountId": 2, "amount": 1000}'

# Transaction history
curl http://localhost:8080/api/accounts/1/transactions
```
