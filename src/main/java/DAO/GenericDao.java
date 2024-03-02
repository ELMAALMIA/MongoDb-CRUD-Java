package DAO;

import java.util.List;

public interface GenericDao<T, ID> {
    void create(T item);
    T findById(ID id);
    List<T> findAll();
    void update(T item);
    void delete(ID id);
}
