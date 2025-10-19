public class PrintedBook extends Book {
    private int pages;
    private String condition; // New, Good, Fair, Poor

    // Constructor
    public PrintedBook(String title, String author, String isbn, int pages, String condition) {
        super(title, author, isbn);
        this.pages = pages;
        this.condition = condition;
    }

    // Getters
    public int getPages() {
        return pages;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    // Override calculateLateFee - Printed books have higher late fees
    @Override
    public double calculateLateFee() {
        // Printed books charge $1.00 per day late
        return getDaysLate() * 1.00;
    }

    // Override display to include printed book-specific info (for console)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: Printed Book");
        System.out.println("Pages: " + pages);
        System.out.println("Condition: " + condition);
    }

    @Override
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append(super.getDetailedInfo());
        info.append("Type: Printed Book\n");
        info.append("Pages: ").append(pages).append("\n");
        info.append("Condition: ").append(condition).append("\n");
        return info.toString();
    }
}