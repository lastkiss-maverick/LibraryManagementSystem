# Library Management System

A Java-based Library Management System demonstrating Object-Oriented Programming (OOP) principles and SOLID design patterns.

## Features

- **Book Management**: Handle both EBooks and Printed Books
- **Late Fee Calculation**: Different rates for different book types
- **Payment Processing**: Multiple payment methods supported
- **OOP Principles**: Encapsulation, Inheritance, Polymorphism, Abstraction
- **SOLID Principles**: Interface Segregation, Dependency Injection

## OOP Concepts Demonstrated

### 1. Encapsulation
- Private fields in `Book` class
- Public getters and setters for controlled access

### 2. Inheritance
- `EBook` and `PrintedBook` inherit from `Book` base class
- Code reusability and hierarchical relationships

### 3. Polymorphism
- `calculateLateFee()` method overridden in child classes
- Different implementations for different book types

### 4. Abstraction
- `Book` is an abstract class with abstract method
- `Payment` interface defines contract for payment methods

## SOLID Principles Applied

### Single Responsibility Principle (SRP)
- Each class has one clear responsibility

### Interface Segregation Principle (ISP)
- `Payment` interface provides focused contract

### Dependency Injection
- `LibraryManager` accepts `Payment` through setter
- Flexible payment method switching

## How to Run

1. Clone the repository
2. Open in IntelliJ IDEA
3. Run `Main.java`

## Project Structure

- `Book.java` - Abstract base class
- `EBook.java` - Electronic book implementation
- `PrintedBook.java` - Physical book implementation
- `Payment.java` - Payment interface
- `CreditCardPayment.java` - Credit card payment
- `CashPayment.java` - Cash payment
- `LibraryManager.java` - Library operations manager
- `Main.java` - Application entry point

## Author

Nimira Aman Cooray