package DAO;

import models.Author;
import java.util.List;

public interface AuthorDao extends GenericDao<Author,String> {
        List<Author> findAuthorsByNationality(String nationality);
}