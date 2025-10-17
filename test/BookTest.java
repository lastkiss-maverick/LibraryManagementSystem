public class BookTest {

    public static void main(String[] args) {
        System.out.println("Running tests...\n");

        testEBookLateFee();
        testPrintedBookLateFee();
        testBookEncapsulation();

        System.out.println("\nAll tests passed!");
    }

    public static void testEBookLateFee() {
        EBook ebook = new EBook("Test Book", "Test Author", "TEST001", 2.5, "PDF");
        ebook.setDaysLate(4);
        double fee = ebook.calculateLateFee();

        assert fee == 2.0 : "EBook late fee calculation failed";
        System.out.println("✓ testEBookLateFee passed");
    }

    public static void testPrintedBookLateFee() {
        PrintedBook book = new PrintedBook("Test Book", "Test Author", "TEST002", 300, "New");
        book.setDaysLate(4);
        double fee = book.calculateLateFee();

        assert fee == 4.0 : "PrintedBook late fee calculation failed";
        System.out.println("✓ testPrintedBookLateFee passed");
    }

    public static void testBookEncapsulation() {
        EBook ebook = new EBook("Test", "Author", "TEST003", 1.0, "PDF");
        ebook.setTitle("New Title");

        assert ebook.getTitle().equals("New Title") : "Encapsulation test failed";
        System.out.println("✓ testBookEncapsulation passed");
    }
}