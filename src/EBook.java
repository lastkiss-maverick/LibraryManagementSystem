public class EBook extends Book {
    private double fileSize; // in MB
    private String format; // PDF, EPUB, etc.

    // Constructor
    public EBook(String title, String author, String isbn, double fileSize, String format) {
        super(title, author, isbn);
        this.fileSize = fileSize;
        this.format = format;
    }

    // Getters
    public double getFileSize() {
        return fileSize;
    }

    public String getFormat() {
        return format;
    }

    // Override calculateLateFee - EBooks have lower late fees
    @Override
    public double calculateLateFee() {
        // EBooks charge $0.50 per day late
        return getDaysLate() * 0.50;
    }

    // Override display to include EBook-specific info (for console)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: EBook");
        System.out.println("File Size: " + fileSize + " MB");
        System.out.println("Format: " + format);
    }

    // NEW: Override for GUI detailed info
    @Override
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append(super.getDetailedInfo());
        info.append("Type: EBook\n");
        info.append("File Size: ").append(fileSize).append(" MB\n");
        info.append("Format: ").append(format).append("\n");
        return info.toString();
    }
}