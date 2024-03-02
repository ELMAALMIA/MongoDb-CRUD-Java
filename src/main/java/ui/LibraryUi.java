package ui;

import DAO.AuthorDao;
import actions.AuthorAction;
import actions.BookAction;
import DAO.AuthorDefault;
import DAO.BookDefault;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import models.Author;
import models.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import actions.AuthorAction;
import actions.BookAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LibraryUi extends JFrame {
    private final AuthorAction authorAction;
    private final BookAction bookAction;

    private final JTabbedPane tabbedPane;
    private final AuthorPanel authorPanel;
    private final BookPanel bookPanel;

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public LibraryUi(AuthorAction authorAction, BookAction bookAction, MongoClient mongoClient, MongoDatabase database) {
        this.authorAction = authorAction;
        this.bookAction = bookAction;
        this.mongoClient = mongoClient;
        this.database = database;

        setTitle("Library Management - MongoDB");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        authorPanel = new AuthorPanel(authorAction);
        bookPanel = new BookPanel(bookAction);

        tabbedPane.addTab("Authors", authorPanel);
        tabbedPane.addTab("Books", bookPanel);

        add(tabbedPane);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        setVisible(true);

        // Add a window listener to close the MongoClient when the window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeMongoClient();
            }
        });
    }

    private void closeMongoClient() {
        if (mongoClient != null ) {
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Select a database
            MongoDatabase database = mongoClient.getDatabase("myDb");

            // Set up Author and Book actions
            AuthorAction authorAction = new AuthorAction(database);
            BookAction bookAction = new BookAction(database);

            // Create and display the Swing application

                    new LibraryUi(authorAction, bookAction, mongoClient, database);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
