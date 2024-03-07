package config;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {
    private static final String CONNECTION_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "myDb";

    private static MongoDatabase databaseInstance;

    private MongoDbConnection() {} 

    public static MongoDatabase getDatabase() {
        if (databaseInstance == null) {
            MongoClient mongoClient = MongoClients.create(CONNECTION_URL);
            databaseInstance = mongoClient.getDatabase(DATABASE_NAME);
        }
        return databaseInstance;
    }
}