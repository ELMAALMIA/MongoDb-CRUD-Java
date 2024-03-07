package ui;

import actions.AuthorAction;
import com.mongodb.client.MongoDatabase;
import models.Author;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AuthorPanel extends JPanel {
    private final JTextField authorNameField;
    private final JTextField nationalityField;
    private final JTextField birthYearField;
    private final JButton addAuthorButton;
    private final JButton updateAuthorButton;
    private final JButton deleteAuthorButton;
    private final JTable authorTable;

    private final AuthorAction authorAction;
    private String selectedAuthorId;

    public AuthorPanel(AuthorAction authorAction) {
        this.authorAction = authorAction;

        setLayout(new BorderLayout());

        // Author Input Panel
        JPanel authorInputPanel = new JPanel();
        authorInputPanel.setLayout(new FlowLayout());

        authorNameField = new JTextField(20);
        nationalityField = new JTextField(20);
        birthYearField = new JTextField(4);
        addAuthorButton = new JButton("Add Author");
        updateAuthorButton = new JButton("Update Author");
        deleteAuthorButton = new JButton("Delete Author");

        authorInputPanel.add(new JLabel("Author Name:"));
        authorInputPanel.add(authorNameField);
        authorInputPanel.add(new JLabel("Nationality:"));
        authorInputPanel.add(nationalityField);
        authorInputPanel.add(new JLabel("Birth Year:"));
        authorInputPanel.add(birthYearField);
        authorInputPanel.add(addAuthorButton);
        authorInputPanel.add(updateAuthorButton);
        authorInputPanel.add(deleteAuthorButton);

        // Author Table
        String[] authorColumnNames = {"ID","Name", "Nationality", "Birth Year"};
        DefaultTableModel authorTableModel = new DefaultTableModel(authorColumnNames, 0);
        
        authorTable = new JTable(authorTableModel);
        JScrollPane authorScrollPane = new JScrollPane(authorTable);

        // Add components to the panel
        add(authorInputPanel, BorderLayout.NORTH);
        add(authorScrollPane, BorderLayout.CENTER);

        // Add Author Action
        addAuthorButton.addActionListener(e -> addAuthor());

        // Update Author Action
        updateAuthorButton.addActionListener(e -> updateAuthor());

        // Delete Author Action
        deleteAuthorButton.addActionListener(e -> deleteAuthor());

        // Set up row selection listener
        authorTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = authorTable.getSelectedRow();

            if (selectedRow != -1) {

                selectedAuthorId  = (String) authorTable.getValueAt(selectedRow, 0);
                String authorName = (String) authorTable.getValueAt(selectedRow, 1);
                String nationality = (String) authorTable.getValueAt(selectedRow, 2);
                int birthYear = (int) authorTable.getValueAt(selectedRow, 3);

                authorNameField.setText(authorName);
                nationalityField.setText(nationality);
                birthYearField.setText(String.valueOf(birthYear));
            }
        });

        // Initialize table
        updateAuthorTable();
    }

    private void updateAuthorTable() {
        DefaultTableModel authorTableModel = (DefaultTableModel) authorTable.getModel();
        authorTableModel.setRowCount(0);

        List<Author> authors = authorAction.getAllAuthors();

        for (Author author : authors) {
            Object[] data = {author.getId().toString(),author.getName(), author.getNationality(), author.getBirthYear()};
            authorTableModel.addRow(data);
        }
    }

    private void clearAuthorFields() {
        authorNameField.setText("");
        nationalityField.setText("");
        birthYearField.setText("");
        selectedAuthorId=null;
    }

    private void addAuthor() {
        String name = authorNameField.getText();
        String nationality = nationalityField.getText();
        String birthYearInput = birthYearField.getText();

        if (!name.isEmpty() && !nationality.isEmpty() && !birthYearInput.isEmpty()) {
            try {
                int birthYear = Integer.parseInt(birthYearInput);

                Author author = new Author();
                author.setName(name);
                author.setNationality(nationality);
                author.setBirthYear(birthYear);

                authorAction.createAuthor(author);
                updateAuthorTable();
                clearAuthorFields();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AuthorPanel.this, "Invalid Birth Year. Please enter a valid number.");
            }
        } else {
            JOptionPane.showMessageDialog(AuthorPanel.this, "Please fill in all author fields.");
        }
    }

    private void updateAuthor() {
        int selectedRowIndex = authorTable.getSelectedRow();

        if (selectedRowIndex != -1) {
            String authorName = authorNameField.getText();
            String nationality = nationalityField.getText();
            String birthYearStr = birthYearField.getText();

            if (!authorName.isEmpty() && !nationality.isEmpty() && !birthYearStr.isEmpty()) {
                try {
                    int birthYear = Integer.parseInt(birthYearStr);

                    Author updatedAuthor = new Author();
                    updatedAuthor.setName(authorName);
                    updatedAuthor.setNationality(nationality);
                    updatedAuthor.setBirthYear(birthYear);
                    updatedAuthor.setId(new ObjectId(selectedAuthorId));
                    System.out.println(updatedAuthor.getId());

                    authorAction.updateAuthor(updatedAuthor);
                    updateAuthorTable();
                    clearAuthorFields();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AuthorPanel.this, "Invalid Birth Year. Please enter a valid number.");
                }
            } else {
                JOptionPane.showMessageDialog(AuthorPanel.this, "Please fill in all author fields.");
            }
        } else {
            JOptionPane.showMessageDialog(AuthorPanel.this, "Please select an author to update.");
        }
    }

    private void deleteAuthor() {
        int selectedRowIndex = authorTable.getSelectedRow();

        if (selectedRowIndex != -1) {
            String authorName = authorNameField.getText();
            int option = JOptionPane.showConfirmDialog(AuthorPanel.this,
                    "Are you sure you want to delete the author: " + authorName + "?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                authorAction.deleteAuthor(selectedAuthorId);
                updateAuthorTable();
                clearAuthorFields();
            }
        } else {
            JOptionPane.showMessageDialog(AuthorPanel.this, "Please select an author to delete.");
        }
    }
}
