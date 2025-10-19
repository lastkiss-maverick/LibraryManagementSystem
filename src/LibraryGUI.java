import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    private LibraryManager libraryManager;
    private JList<String> bookList;
    private DefaultListModel<String> listModel;
    private JLabel statusLabel;

    public LibraryGUI() {
        libraryManager = new LibraryManager();
        loadSampleData();          // Make sure books are loaded FIRST
        setupGUI();                // Build the GUI layout
        refreshBookList();         // Update the book list immediately
    }

    private void setupGUI() {
        setTitle("📚 Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);

        // Colors
        Color headerColor = new Color(40, 53, 147);      // Indigo blue
        Color panelColor = new Color(215, 227, 245);     // Soft blue-grey
        Color buttonGreen = new Color(46, 204, 113);     // Emerald
        Color buttonPurple = new Color(156, 39, 176);    // Deep purple
        Color buttonOrange = new Color(255, 140, 0);     // Orange
        Color buttonBlue = new Color(52, 152, 219);      // Bright Blue
        Color buttonGray = new Color(120, 144, 156);     // Muted gray
        Color bookListBg = new Color(236, 239, 241);     // Light grey-blue
        Color borderColor = new Color(33, 150, 243);     // Blue accent

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(headerColor);
        headerPanel.setPreferredSize(new Dimension(800, 90));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));

        JLabel titleLabel = new JLabel("📚 Library Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(panelColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        JButton addBookButton = createStyledButton("➕ Add Book", buttonGreen);
        JButton issueBookButton = createStyledButton("📖 Issue Book", buttonPurple);
        JButton returnBookButton = createStyledButton("📚 Return Book", buttonOrange);
        JButton viewBooksButton = createStyledButton("👁 View Details", buttonBlue);
        JButton refreshButton = createStyledButton("🔄 Refresh", buttonGray);

        addBookButton.addActionListener(new AddBookListener());
        issueBookButton.addActionListener(new IssueBookListener());
        returnBookButton.addActionListener(new ReturnBookListener());
        viewBooksButton.addActionListener(new ViewBookListener());
        refreshButton.addActionListener(e -> refreshBookList());

        buttonPanel.add(addBookButton);
        buttonPanel.add(issueBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(viewBooksButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Book List
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.BLACK);
        listPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor, 3),
                "📖 Book Library",
                0, 0, new Font("Arial", Font.BOLD, 14), borderColor));

        listModel = new DefaultListModel<>();
        bookList = new JList<>(listModel);
        bookList.setFont(new Font("Monospaced", Font.PLAIN, 15));
        bookList.setBackground(bookListBg);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(bookList);
        scrollPane.setPreferredSize(new Dimension(800, 320));
        scrollPane.getViewport().setBackground(bookListBg);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        add(listPanel, BorderLayout.SOUTH);

        // Status Bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(headerColor);
        statusLabel = new JLabel("Ready - Welcome to Library Management System");
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.PAGE_END);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(160, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void loadSampleData() {

        Book ebook1 = new EBook("Java Programming", "Lincoln", "ISBN001", 5.2, "PDF");
        Book printedBook1 = new PrintedBook("Data Structures", "Jane Smith", "ISBN002", 450, "Good");
        Book ebook2 = new EBook("Clean Code", "Robert Martin", "ISBN003", 3.8, "EPUB");
        Book printedBook2 = new PrintedBook("Design Patterns", "Gang of Four", "ISBN004", 395, "New");

        libraryManager.addBook(ebook1);
        libraryManager.addBook(printedBook1);
        libraryManager.addBook(ebook2);
        libraryManager.addBook(printedBook2);
    }

    private void refreshBookList() {
        listModel.clear();
        for (Book book : libraryManager.getAllBooks()) {
            listModel.addElement(book.getDisplayText());
        }
        updateStatus("Book list refreshed - Total books: " + libraryManager.getAllBooks().size());
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    // Event Listeners
    private class AddBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AddBookDialog dialog = new AddBookDialog(LibraryGUI.this, libraryManager);
            dialog.setVisible(true);

            if (dialog.isBookAdded()) {
                refreshBookList();
                updateStatus("New book added successfully");
            }
        }
    }

    private class IssueBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (libraryManager.getAvailableBooks().isEmpty()) {
                JOptionPane.showMessageDialog(LibraryGUI.this,
                        "No books available to issue!",
                        "No Books Available",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            IssueBookDialog dialog = new IssueBookDialog(LibraryGUI.this, libraryManager);
            dialog.setVisible(true);

            if (dialog.isBookIssued()) {
                refreshBookList();
                updateStatus("Book issued successfully");
            }
        }
    }

    private class ReturnBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (libraryManager.getIssuedBooks().isEmpty()) {
                JOptionPane.showMessageDialog(LibraryGUI.this,
                        "No books are currently issued!",
                        "No Issued Books",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ReturnBookDialog dialog = new ReturnBookDialog(LibraryGUI.this, libraryManager);
            dialog.setVisible(true);

            if (dialog.isBookReturned()) {
                refreshBookList();
                updateStatus("Book returned successfully");
            }
        }
    }

    private class ViewBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = bookList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(LibraryGUI.this,
                        "Please select a book to view details!",
                        "No Book Selected",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book selectedBook = libraryManager.getAllBooks().get(selectedIndex);

            JTextArea textArea = new JTextArea(selectedBook.getDetailedInfo());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setEditable(false);
            textArea.setBackground(new Color(255, 249, 196)); // Light yellow for details

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 220));

            JOptionPane.showMessageDialog(LibraryGUI.this,
                    scrollPane,
                    "Book Details - " + selectedBook.getTitle(),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LibraryGUI().setVisible(true));
    }
}
