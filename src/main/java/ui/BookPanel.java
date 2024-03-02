package ui;
import actions.BookAction;
import models.Book;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookPanel extends JPanel {
    private final BookAction bookAction;

    private final JTextField bookTitleField;
    private final JTextField publicationYearField;
    private final JButton addBookButton;
    private final JButton updateBookButton;
    private final JButton deleteBookButton;
    private final JTable bookTable;

    public BookPanel(BookAction bookAction) {
        this.bookAction = bookAction;

        setLayout(new BorderLayout());

        // Book Input Panel
        JPanel bookInputPanel = new JPanel();
        bookInputPanel.setLayout(new FlowLayout());

        bookTitleField = new JTextField(20);
        publicationYearField = new JTextField(4);  // Ajout du champ pour l'année de publication
        addBookButton = new JButton("Add Book");
        updateBookButton = new JButton("Update Book");
        deleteBookButton = new JButton("Delete Book");

        bookInputPanel.add(new JLabel("Book Title:"));
        bookInputPanel.add(bookTitleField);
        bookInputPanel.add(new JLabel("Publication Year:"));
        bookInputPanel.add(publicationYearField);
        bookInputPanel.add(addBookButton);
        bookInputPanel.add(updateBookButton);
        bookInputPanel.add(deleteBookButton);

        // Book Table
        String[] bookColumnNames = { "Title", "Publication Year"};
        DefaultTableModel bookTableModel = new DefaultTableModel(bookColumnNames, 0);
        bookTable = new JTable(bookTableModel);
        JScrollPane bookScrollPane = new JScrollPane(bookTable);

        // Add components to the panel
        add(bookInputPanel, BorderLayout.NORTH);
        add(bookScrollPane, BorderLayout.CENTER);

        // Add Book Action
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = bookTitleField.getText();
                String publicationYearStr = publicationYearField.getText();

                if (!bookTitle.isEmpty() && !publicationYearStr.isEmpty()) {
                    int publicationYear = Integer.parseInt(publicationYearStr);
                    Book newBook = new Book();
                    newBook.setTitle(bookTitle);
                    newBook.setPublicationYear(publicationYear);
                    bookAction.createBook(newBook);
                    updateBookTable();
                    clearBookFields();
                } else {
                    JOptionPane.showMessageDialog(BookPanel.this, "Book title and publication year cannot be empty!");
                }
            }
        });

        // Update Book Action
        updateBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = bookTable.getSelectedRow();

                if (selectedRowIndex != -1) {
                    String bookId = bookTable.getValueAt(selectedRowIndex, 0).toString();
                    String updatedTitle = bookTitleField.getText();
                    String updatedPublicationYearStr = publicationYearField.getText();

                    if (!updatedTitle.isEmpty() && !updatedPublicationYearStr.isEmpty()) {
                        int updatedPublicationYear = Integer.parseInt(updatedPublicationYearStr);
                        Book updatedBook = new Book();
                        updatedBook.setId(new ObjectId(bookId));
                        updatedBook.setTitle(updatedTitle);
                        updatedBook.setPublicationYear(updatedPublicationYear);
                        bookAction.updateBook(updatedBook);
                        updateBookTable();
                        clearBookFields();
                    } else {
                        JOptionPane.showMessageDialog(BookPanel.this, "Updated title and publication year cannot be empty!");
                    }
                } else {
                    JOptionPane.showMessageDialog(BookPanel.this, "Please select a book to update.");
                }
            }
        });

        // Delete Book Action
        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = bookTable.getSelectedRow();

                if (selectedRowIndex != -1) {
                    String bookId = bookTable.getValueAt(selectedRowIndex, 0).toString();
                    bookAction.deleteBook(bookId);
                    updateBookTable();
                    clearBookFields();
                } else {
                    JOptionPane.showMessageDialog(BookPanel.this, "Please select a book to delete.");
                }
            }
        });

        // Initialize table
        updateBookTable();
    }

    private void updateBookTable() {
        DefaultTableModel bookTableModel = (DefaultTableModel) bookTable.getModel();
        bookTableModel.setRowCount(0);

        List<Book> books = bookAction.getAllBooks();
        for (Book book : books) {
            Object[] rowData = { book.getTitle(), book.getPublicationYear()};
            bookTableModel.addRow(rowData);
        }
    }

    private void clearBookFields() {
        bookTitleField.setText("");
        publicationYearField.setText("");
    }
}
