package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Book;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Vector;

import static com.mongodb.client.model.Filters.eq;

public class BookDefault implements  BookDao{
    private MongoCollection<Document> collection;
    private  Document document;

    public BookDefault(MongoDatabase database) {
        this.collection = database.getCollection("books");
    }

    @Override
    public void create(Book book) {
        Document document = new Document("title", book.getTitle())
                .append("publicationYear", book.getPublicationYear())
                .append("authorIds", book.getAuthorIds());

        collection.insertOne(document);
        book.setId(document.getObjectId("_id"));
    }
    @Override
    public Book findById(String s) {
      Document document= collection.find(eq("_id",new ObjectId(s))).first();
      return  documentToBook(document);
    }

    @Override
    public List<Book> findAll() {
    List<Book> books = new Vector<>();
    for (Document document : collection.find()){
        books.add(documentToBook(document));
    }
    return books;
    }

    @Override
    public void update(Book book) {
collection.updateOne(eq("_id",book.getId())
        , new Document("title", book.getTitle())
                .append("publicationYear", book.getPublicationYear())
                .append("authorIds", book.getAuthorIds()));
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(eq("_id",new ObjectId(id)));
    }

    private Book documentToBook(Document doc) {
        Book book = new Book();
        book.setId(doc.getObjectId("_id"));
        book.setTitle(doc.getString("title"));
        book.setPublicationYear(doc.getInteger("publicationYear"));
        book.setAuthorIds((List<ObjectId>) doc.get("authorIds"));
        return book;
    }
}
