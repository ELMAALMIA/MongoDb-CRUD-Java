package DAO;

import config.MongoDbConnection;
import models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import com.mongodb.client.MongoDatabase;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDefaultTest {
    private MongoDatabase database;
    private AuthorDefault authorDao;

    @BeforeEach
    void setUp() {
        // Utilisez MongoDbConnection pour obtenir la base de données de test
        database = MongoDbConnection.getDatabase();

        // Assurez-vous que la collection est vide avant chaque test
        database.getCollection("authors").drop();

        authorDao = new AuthorDefault(database);
    }

    @Test
    void testCreateAndFindById() {
        Author author = new Author();
        author.setName("Author 1");
        author.setNationality("Morocco");
        author.setBirthYear(2000);


        authorDao.create(author);

        Author author1 = authorDao.findById(author.getId().toString());
        assertNotNull(author1);
        assertEquals("Author 1", author1.getName());
        assertEquals("Morocco", author1.getNationality());
        assertEquals(2000, author.getBirthYear());
    }


}
