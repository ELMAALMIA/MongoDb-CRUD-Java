package config;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {
    private static final String CONNECTION_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "myDb";

    public static MongoDatabase getDatabase() {
        System.out.println("CONNECTION TO MONGODB........");
        MongoClient mongoClient = MongoClients.create(CONNECTION_URL);
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}

