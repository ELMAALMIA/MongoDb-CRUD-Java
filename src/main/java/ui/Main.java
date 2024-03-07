package ui;

import actions.AuthorAction;
import actions.BookAction;

public class Main {
    public static void main(String[] args) {
        try {
            AuthorAction authorAction = new AuthorAction();
            BookAction bookAction = new BookAction();
            new LibraryUi(authorAction, bookAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
