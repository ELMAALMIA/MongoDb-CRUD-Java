package DAO;

import com.mongodb.client.MongoDatabase;
import config.MongoDbConnection;
import models.Author;
import org.junit.jupiter.api.*;


import com.mongodb.client.MongoDatabase;
import config.MongoDbConnection;
import models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDefaultTest {

    private MongoDatabase database;
    private AuthorDefault authorDao;

    @BeforeEach
    void init() {
        database = MongoDbConnection.getDatabase();
        database.getCollection("authors").drop();
        authorDao = new AuthorDefault();
        Author author = new Author();
        author.setName("Author test");
        author.setNationality("British");
        author.setBirthYear(2000);
        authorDao.create(author);
    }

    @Test
    void testCreateAndFindById() {
        Author author = new Author();
        author.setName("Author 1");
        author.setNationality("Morocco");
        author.setBirthYear(2000);
        authorDao.create(author);
        Author foundAuthor = authorDao.findById(author.getId().toString());
        assertNotNull(foundAuthor);
        assertEquals("Author 1", foundAuthor.getName());
        assertEquals("Morocco", foundAuthor.getNationality());
        assertEquals(2000, foundAuthor.getBirthYear());
    }

    @Test
    void testUpdateAuthor() {
        Author initialAuthor = authorDao.findAuthorsByNationality("British").get(0);
        initialAuthor.setName("Updated Author");
        initialAuthor.setBirthYear(1980);
        authorDao.update(initialAuthor);
        Author updatedAuthor = authorDao.findById(initialAuthor.getId().toString());
        assertNotNull(updatedAuthor);
        assertEquals("Updated Author", updatedAuthor.getName());
        assertEquals("British", updatedAuthor.getNationality()); // La nationalité ne doit pas changer dans ce test
        assertEquals(1980, updatedAuthor.getBirthYear());
    }

    @Test
    void testFindAuthorsByNationality() {
        Author author1 = new Author();
        author1.setName("Author French");
        author1.setNationality("French");
        author1.setBirthYear(1990);
        authorDao.create(author1);

        Author author2 = new Author();
        author2.setName("Author German");
        author2.setNationality("German");
        author2.setBirthYear(1985);
        authorDao.create(author2);
        List<Author> frenchAuthors = authorDao.findAuthorsByNationality("French");
        List<Author> germanAuthors = authorDao.findAuthorsByNationality("German");

        assertEquals(1, frenchAuthors.size());
        assertEquals(1, germanAuthors.size());
    }


    @AfterEach
    void  clearData(){
        database.getCollection("authors").drop();
    }

}