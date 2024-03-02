package config;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {
    private static final String CONNECTION_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "myDb";

    private static MongoDatabase databaseInstance;

    private MongoDbConnection() {} // Empêche l'instanciation

    public static MongoDatabase getDatabase() {
        if (databaseInstance == null) {
            System.out.println(" CONEXION MONGODB........");
            MongoClient mongoClient = MongoClients.create(CONNECTION_URL);
            databaseInstance = mongoClient.getDatabase(DATABASE_NAME);
        }
        return databaseInstance;
    }
}
