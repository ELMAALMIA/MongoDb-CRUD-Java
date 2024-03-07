package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.MongoDbConnection;
import models.Author;
import org.bson.Document;
import org.bson.types.ObjectId;


import static com.mongodb.client.model.Filters.eq;
import java.util.List;
import java.util.Vector;

public class AuthorDefault implements AuthorDao{
    private MongoCollection<Document> collection;
    private  MongoDatabase mongoDatabase;
   

    public AuthorDefault() {
        this.mongoDatabase = MongoDbConnection.getDatabase();
        this.collection = mongoDatabase.getCollection("authors");
    }
    
    // le reste du code
    
    @Override
    public void create(Author author) {

        Document document = new Document("name", author.getName())
                .append("nationality", author.getNationality())
                .append("birthYear", author.getBirthYear());
        if (author.getBookIds() != null) {
            List<String> bookIds = new Vector<>();
            for (ObjectId objectId : author.getBookIds()) {
                bookIds.add(objectId.toHexString());
            }
            document.append("bookIds", bookIds);
        }

     collection.insertOne(document);
        author.setId(document.getObjectId("_id"));
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new Vector<>();

        for (Document document : collection.find()) {

            authors.add(documentToAuthor(document));
        }
        return authors;
    }

    @Override
    public Author findById(String id) {
        Document document = collection.find(eq("_id", new ObjectId(id))).first();
        return documentToAuthor(document);
    }
    @Override
    public List<Author> findAuthorsByNationality(String nationality) {
        List<Author> authors = new Vector<>();
        for (Document document : collection.find(eq("nationality", nationality))) {
            authors.add(documentToAuthor(document));
        }
        return authors;
    }

    @Override
    public void update(Author author) {
        collection.updateOne(eq("_id", author.getId()),
                new Document("$set", new Document("name", author.getName())
                        .append("nationality", author.getNationality())
                        .append("birthYear", author.getBirthYear())));
    }

    @Override
    public void delete(String id) {
        System.out.println("id : "+id);
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    private Author documentToAuthor(Document doc) {
        if (doc == null) {
            return null;
        }
        Author author = new Author();
        author.setId(doc.getObjectId("_id"));
        author.setName(doc.getString("name"));
        author.setNationality(doc.getString("nationality"));
        author.setBirthYear(doc.getInteger("birthYear", 0));
        List<ObjectId> bookIds = new Vector<>();
        List<String> books = doc.getList("bookIds", String.class);
        if (books != null) {
            for (String bookId : books) {
                bookIds.add(new ObjectId(bookId));
            }
            author.setBookIds(bookIds);
        }
        return author;
    }
}
