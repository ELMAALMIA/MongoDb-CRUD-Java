package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
    void init() {
        database = MongoDbConnection.getDatabase();
        // Assurez-vous que la collection est vide avant chaque test
        database.getCollection("authors").drop();

        authorDao = new AuthorDefault(database);
        Author author = new Author();
        author.setName("Author test ");
        author.setNationality("British");
        author.setBirthYear(2000);
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

    @Test
    void testDeleteAuthor(){

        assertNotNull( authorDao.findAuthorsByNationality("British"));
        System.out.println( authorDao.findAuthorsByNationality("British"));
    }

   /* @AfterEach
    void  clearData(){
        database.getCollection("authors").drop();
    }
*/
}
