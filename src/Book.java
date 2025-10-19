public abstract class Book {
    // Private fields demonstrate encapsulation
    private String title;
    private String author;
    private String isbn;
    private boolean isIssued;
    private int daysLate;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isIssued = false;
        this.daysLate = 0;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public int getDaysLate() {
        return daysLate;
    }

    public void setDaysLate(int daysLate) {
        this.daysLate = daysLate;
    }

    // Abstract method for polymorphism
    public abstract double calculateLateFee();

    // Display book details (for console)
    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Status: " + (isIssued ? "Issued" : "Available"));
    }


    public String getDisplayText() {
        String status = isIssued ? "Issued" : "Available";
        return title + " - " + author + " [" + status + "]";
    }


    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Title: ").append(title).append("\n");
        info.append("Author: ").append(author).append("\n");
        info.append("ISBN: ").append(isbn).append("\n");
        info.append("Status: ").append(isIssued ? "Issued" : "Available").append("\n");
        return info.toString();
    }
}