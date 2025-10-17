import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReturnBookDialog extends JDialog {
    private LibraryManager libraryManager;
    private JComboBox<String> bookComboBox;
    private JTextField daysLateField;
    private JComboBox<String> paymentTypeComboBox;
    private boolean bookReturned = false;

    public ReturnBookDialog(JFrame parent, LibraryManager libraryManager) {
        super(parent, "Return Book", true);
        this.libraryManager = libraryManager;
        setupGUI();
    }

    private void setupGUI() {
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 193, 7)); // Amber
        JLabel titleLabel = new JLabel("📚 Return Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 19));
        titleLabel.setForeground(new Color(56, 56, 56));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 2));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Book to Return:"), gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        bookComboBox = new JComboBox<>();
        loadIssuedBooks();
        formPanel.add(bookComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Days Late:"), gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        daysLateField = new JTextField("0");
        formPanel.add(daysLateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        paymentTypeComboBox = new JComboBox<>(new String[]{"Credit Card", "Cash"});
        formPanel.add(paymentTypeComboBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 193, 7));
        JButton returnButton = new JButton("Return Book");
        returnButton.setBackground(new Color(33, 150, 243)); // Blue
        returnButton.setForeground(Color.WHITE);
        returnButton.setFont(new Font("Arial", Font.BOLD, 13));
        returnButton.setFocusPainted(false);
        returnButton.addActionListener(new ReturnButtonListener());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(120, 144, 156)); // Gray
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 13));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(returnButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadIssuedBooks() {
        bookComboBox.removeAllItems();
        List<Book> issuedBooks = libraryManager.getIssuedBooks();

        if (issuedBooks.isEmpty()) {
            bookComboBox.addItem("No books issued");
        } else {
            for (Book book : issuedBooks) {
                bookComboBox.addItem(book.getIsbn() + " - " + book.getTitle());
            }
        }
    }

    public boolean isBookReturned() {
        return bookReturned;
    }

    private class ReturnButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String) bookComboBox.getSelectedItem();
            if (selectedItem == null || selectedItem.equals("No books issued")) {
                JOptionPane.showMessageDialog(ReturnBookDialog.this,
                        "No books to return!", "No Books", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String isbn = selectedItem.split(" - ")[0];
                int daysLate = Integer.parseInt(daysLateField.getText().trim());
                String paymentType = (String) paymentTypeComboBox.getSelectedItem();

                Payment payment;
                if ("Credit Card".equals(paymentType)) {
                    payment = new CreditCardPayment("1234567812345678", "Student User");
                } else {
                    payment = new CashPayment("RCP-" + System.currentTimeMillis());
                }
                libraryManager.setPaymentMethod(payment);

                Book book = libraryManager.findBookByISBN(isbn);
                if (book != null) {
                    book.setDaysLate(daysLate);
                    double lateFee = book.calculateLateFee();

                    String message = "Book: " + book.getTitle() + "\n";
                    if (lateFee > 0) {
                        message += "Late Fee: $" + String.format("%.2f", lateFee) + "\n";
                        message += "Payment Method: " + paymentType + "\n\n";
                        message += "Proceed with return?";

                        int result = JOptionPane.showConfirmDialog(ReturnBookDialog.this,
                                message, "Confirm Return", JOptionPane.YES_NO_OPTION);

                        if (result != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }
                }

                libraryManager.returnBook(isbn, daysLate);
                bookReturned = true;

                JOptionPane.showMessageDialog(ReturnBookDialog.this,
                        "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ReturnBookDialog.this,
                        "Please enter a valid number for days late!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
