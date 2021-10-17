package org.SpecikMan.DAL;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> getAll();

    Optional<T> get(String id);

    void add(T t);

    void update(T t);

    void delete(T t);
}
