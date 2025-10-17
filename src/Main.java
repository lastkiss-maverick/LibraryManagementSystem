public class Main {
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===\n");

        // Create library manager
        LibraryManager library = new LibraryManager();

        // Add books
        System.out.println("--- Adding Books ---");
        Book ebook1 = new EBook("Java Programming", "John Doe", "ISBN001", 5.2, "PDF");
        Book printedBook1 = new PrintedBook("Data Structures", "Jane Smith", "ISBN002", 450, "Good");
        Book ebook2 = new EBook("Clean Code", "Robert Martin", "ISBN003", 3.8, "EPUB");

        library.addBook(ebook1);
        library.addBook(printedBook1);
        library.addBook(ebook2);

        // Display all books
        library.displayAllBooks();

        // Issue books
        System.out.println("\n--- Issuing Books ---");
        library.issueBook("ISBN001");
        library.issueBook("ISBN002");

        // Return books with late fees
        System.out.println("\n--- Returning Books ---");

        // Set payment method using dependency injection
        Payment creditCard = new CreditCardPayment("1234567812345678", "Ahmad Ali");
        library.setPaymentMethod(creditCard);

        library.returnBook("ISBN001", 3); // EBook 3 days late

        System.out.println();

        // Change payment method
        Payment cash = new CashPayment("RCP-2025-001");
        library.setPaymentMethod(cash);

        library.returnBook("ISBN002", 5); // Printed book 5 days late

        // Display final state
        library.displayAllBooks();

        // Demonstrate polymorphism
        System.out.println("\n=== Polymorphism Demo ===");
        Book[] booksArray = {ebook1, printedBook1, ebook2};
        for (Book book : booksArray) {
            book.setDaysLate(2);
            System.out.println(book.getTitle() + " - Late fee: $" + book.calculateLateFee());
        }
    }
}