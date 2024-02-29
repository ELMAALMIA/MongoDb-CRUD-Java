package tests;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBExample {
    public static void main(String[] args) {
        // Se connecter à MongoDB

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Sélectionner une base de données
            MongoDatabase database = mongoClient.getDatabase("myDb");

            // Sélectionner une collection
            MongoCollection<Document> collection = database.getCollection("maCollection");

            // Créer un document à insérer
            Document doc = new Document("nom", "John Doe")
                    .append("age", 30)
                    .append("profession", "Développeur");

            // Insérer le document dans la collection
            collection.insertOne(doc);
        }
    }
}
