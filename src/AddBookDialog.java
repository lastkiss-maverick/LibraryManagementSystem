import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookDialog extends JDialog {
    private LibraryManager libraryManager;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JComboBox<String> typeComboBox;
    private JTextField extraField1;
    private JTextField extraField2;
    private JLabel extraLabel1;
    private JLabel extraLabel2;
    private boolean bookAdded = false;

    public AddBookDialog(JFrame parent, LibraryManager libraryManager) {
        super(parent, "Add New Book", true);
        this.libraryManager = libraryManager;
        setupGUI();
    }

    private void setupGUI() {
        setLayout(new BorderLayout());
        setSize(410, 370);
        setLocationRelativeTo(getParent());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(244, 143, 177)); // Pink
        JLabel titleLabel = new JLabel("📚 Add New Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 19));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        authorField = new JTextField(20);
        formPanel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        isbnField = new JTextField(20);
        formPanel.add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        typeComboBox = new JComboBox<>(new String[]{"EBook", "Printed Book"});
        typeComboBox.addActionListener(e -> updateExtraFields());
        formPanel.add(typeComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        extraLabel1 = new JLabel("File Size (MB):");
        formPanel.add(extraLabel1, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        extraField1 = new JTextField(20);
        formPanel.add(extraField1, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        extraLabel2 = new JLabel("Format:");
        formPanel.add(extraLabel2, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        extraField2 = new JTextField(20);
        formPanel.add(extraField2, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(244, 143, 177));
        JButton addButton = new JButton("Add Book");
        addButton.setBackground(new Color(46, 204, 113)); // Green
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 13));
        addButton.setFocusPainted(false);
        addButton.addActionListener(new AddButtonListener());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(156, 39, 176)); // Purple
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 13));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateExtraFields();
    }

    private void updateExtraFields() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        if ("EBook".equals(selectedType)) {
            extraLabel1.setText("File Size (MB):");
            extraLabel2.setText("Format (PDF/EPUB):");
            extraField1.setText("");
            extraField2.setText("");
        } else {
            extraLabel1.setText("Number of Pages:");
            extraLabel2.setText("Condition (New/Good/Fair):");
            extraField1.setText("");
            extraField2.setText("");
        }
    }

    public boolean isBookAdded() {
        return bookAdded;
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String isbn = isbnField.getText().trim();
                String type = (String) typeComboBox.getSelectedItem();

                if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(AddBookDialog.this,
                            "Please fill in all required fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book book;
                if ("EBook".equals(type)) {
                    double fileSize = Double.parseDouble(extraField1.getText().trim());
                    String format = extraField2.getText().trim();
                    book = new EBook(title, author, isbn, fileSize, format);
                } else {
                    int pages = Integer.parseInt(extraField1.getText().trim());
                    String condition = extraField2.getText().trim();
                    book = new PrintedBook(title, author, isbn, pages, condition);
                }

                libraryManager.addBook(book);
                bookAdded = true;

                JOptionPane.showMessageDialog(AddBookDialog.this,
                        "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddBookDialog.this,
                        "Please enter valid numbers for numeric fields!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
