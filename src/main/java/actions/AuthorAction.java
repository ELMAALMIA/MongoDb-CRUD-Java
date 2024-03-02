package actions;

import DAO.AuthorDao;
import DAO.AuthorDefault;
import com.mongodb.client.MongoDatabase;
import models.Author;

import java.util.List;

public class AuthorAction {

    private AuthorDefault authorDao;

    public AuthorAction(MongoDatabase database) {
        this.authorDao = new AuthorDefault(database);
    }
    public  void createAuthor(Author author){
        authorDao.create(author);
    }
    public List<Author> getAllAuthors(){
        return  authorDao.findAll();
    }

    public  Author findAuthorById(String id){
        return  authorDao.findById(id);
    }
    public void updateAuthor(Author author){
        authorDao.update(author);
    }


    public  void  deleteAuthor(String id){
        authorDao.delete(id);
    }
}
