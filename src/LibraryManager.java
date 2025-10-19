import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private List<Book> books;
    private Payment paymentMethod; // Dependency injection

    public LibraryManager() {
        this.books = new ArrayList<>();
    }

    // Dependency injection - allows flexible payment methods
    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Add book to library
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Issue a book
    public void issueBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isIssued()) {
                book.setIssued(true);
                System.out.println("Book issued: " + book.getTitle());
                return;
            }
        }
        System.out.println("Book not available or already issued.");
    }

    // Return a book and calculate late fee
    public void returnBook(String isbn, int daysLate) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isIssued()) {
                book.setIssued(false);
                book.setDaysLate(daysLate);

                double lateFee = book.calculateLateFee();
                System.out.println("Book returned: " + book.getTitle());

                if (lateFee > 0) {
                    System.out.println("Late fee: $" + lateFee);
                    if (paymentMethod != null) {
                        paymentMethod.processPayment(lateFee);
                    }
                } else {
                    System.out.println("No late fee. Thank you!");
                }
                return;
            }
        }
        System.out.println("Book not found or not issued.");
    }

    // Display all books (for console)
    public void displayAllBooks() {
        System.out.println("\n=== Library Books ===");
        for (Book book : books) {
            book.displayInfo();
            System.out.println("---");
        }
    }


    public List<Book> getAllBooks() {
        return books;
    }


    public Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }


    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book book : books) {
            if (!book.isIssued()) {
                available.add(book);
            }
        }
        return available;
    }


    public List<Book> getIssuedBooks() {
        List<Book> issued = new ArrayList<>();
        for (Book book : books) {
            if (book.isIssued()) {
                issued.add(book);
            }
        }
        return issued;
    }
}