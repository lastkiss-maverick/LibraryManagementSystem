import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class IssueBookDialog extends JDialog {
    private LibraryManager libraryManager;
    private JComboBox<String> bookComboBox;
    private boolean bookIssued = false;

    public IssueBookDialog(JFrame parent, LibraryManager libraryManager) {
        super(parent, "Issue Book", true);
        this.libraryManager = libraryManager;
        setupGUI();
    }

    private void setupGUI() {
        setLayout(new BorderLayout());
        setSize(370, 200);
        setLocationRelativeTo(getParent());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(63, 81, 181)); // Deep blue
        JLabel titleLabel = new JLabel("📖 Issue Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 19));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 9, 9, 9);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Select Book to Issue:"), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        bookComboBox = new JComboBox<>();
        loadAvailableBooks();
        formPanel.add(bookComboBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(63, 81, 181));
        JButton issueButton = new JButton("Issue Book");
        issueButton.setBackground(new Color(76, 175, 80)); // Green
        issueButton.setForeground(Color.WHITE);
        issueButton.setFont(new Font("Arial", Font.BOLD, 13));
        issueButton.setFocusPainted(false);
        issueButton.addActionListener(new IssueButtonListener());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 87, 34)); // Orange
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 13));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(issueButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadAvailableBooks() {
        bookComboBox.removeAllItems();
        List<Book> availableBooks = libraryManager.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            bookComboBox.addItem("No books available");
        } else {
            for (Book book : availableBooks) {
                bookComboBox.addItem(book.getIsbn() + " - " + book.getTitle());
            }
        }
    }

    public boolean isBookIssued() {
        return bookIssued;
    }

    private class IssueButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedItem = (String) bookComboBox.getSelectedItem();
            if (selectedItem == null || selectedItem.equals("No books available")) {
                JOptionPane.showMessageDialog(IssueBookDialog.this,
                        "No books available to issue!", "No Books", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String isbn = selectedItem.split(" - ")[0];
            libraryManager.issueBook(isbn);
            bookIssued = true;

            JOptionPane.showMessageDialog(IssueBookDialog.this,
                    "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
