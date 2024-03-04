package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConfig {
    private static final String CONNECTION_URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "myDb";

    public static MongoClient createMongoClient() {
        return MongoClients.create(CONNECTION_URL);
    }

    public static MongoDatabase getDatabase() {
        return createMongoClient().getDatabase(DATABASE_NAME);
    }
}
