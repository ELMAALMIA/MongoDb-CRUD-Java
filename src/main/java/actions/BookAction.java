package actions;

import DAO.BookDao;
import DAO.BookDefault;
import com.mongodb.client.MongoDatabase;
import models.Book;

import java.util.List;

public class BookAction {
    private final BookDao bookDao;

    public BookAction(MongoDatabase database) {
        this.bookDao = new BookDefault(database);
    }

    public void createBook(Book book) {
        bookDao.create(book);
    }

    public Book getBookById(String id) {
        return bookDao.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    public void updateBook(Book book) {
        bookDao.update(book);
    }

    public void deleteBook(String id) {
        bookDao.delete(id);
    }
}
