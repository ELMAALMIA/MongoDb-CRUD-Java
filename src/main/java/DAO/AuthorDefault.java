package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Author;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;



import java.util.List;
import java.util.Vector;

public class AuthorDefault implements AuthorDao{
    private MongoCollection<Document> collection;

    public AuthorDefault(MongoDatabase database) {

        if (database.getCollection("authors") == null) {
            System.out.println("Creating 'authors' collection...");
            database.createCollection("authors");
            System.out.println("'authors' collection created.");
        }
        this.collection = database.getCollection("authors");

    }

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
        //insertOne(doc) ne modifie pas doc pour inclure l'_id généré.

        collection.insertOne(document);
        author.setId(document.getObjectId("_id"));
    }

    @Override
    public Author findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        return documentToAuthor(doc);
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
    public List<Author> findAuthorsByNationality(String nationality) {
        List<Author> authors = new Vector<>();
        for (Document doc : collection.find(eq("nationality", nationality))) {
            authors.add(documentToAuthor(doc));
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
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    private Author documentToAuthor(Document doc) {
        Author author = new Author();
        author.setId(doc.getObjectId("_id"));
        author.setName(doc.getString("name"));
        author.setNationality(doc.getString("nationality"));
        author.setBirthYear(doc.getInteger("birthYear", 0));
        // Convertir les String bookIds en ObjectId
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
