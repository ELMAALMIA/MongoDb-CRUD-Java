package tests;


import DAO.AuthorDao;
import DAO.AuthorDefault;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Author;
import org.bson.Document;

public class MongoDBExample {
    public static void main(String[] args) {
        // Se connecter à MongoDB

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Sélectionner une base de données
            MongoDatabase database = mongoClient.getDatabase("myDb");

            // Sélectionner une collection
            MongoCollection<Document> collection = database.getCollection("myCollection");
            MongoCollection<Document> collectionAuthors = database.getCollection("authors");

           AuthorDefault authorDefault = new AuthorDefault();
            Author author = new Author();
            author.setName("Author test 2 ");
            author.setNationality("British");
            author.setBirthYear(2000);
            authorDefault.create(author);


        }
    }
}
