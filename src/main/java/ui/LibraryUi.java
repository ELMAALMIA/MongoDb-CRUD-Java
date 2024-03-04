package ui;

import DAO.AuthorDao;
import actions.AuthorAction;
import actions.BookAction;
import DAO.AuthorDefault;
import DAO.BookDefault;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import config.MongoDbConfig;
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




    public LibraryUi(AuthorAction authorAction, BookAction bookAction) {
        this.authorAction = authorAction;
        this.bookAction = bookAction;


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
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                closeMongoClient();
//            }
//        });
    }


    public static void main(String[] args) {
        try {
            AuthorAction authorAction = new AuthorAction();
            BookAction bookAction = new BookAction();
            new LibraryUi(authorAction, bookAction);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log or show an error message)
            e.printStackTrace();
        }
    }

}
