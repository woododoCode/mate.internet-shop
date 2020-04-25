package mate.academy.internetshop.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, E> {
    T create(T t);

    Optional<T> get(E id);

    List<T> getAll();

    T update(T t);

    boolean delete(E id);
}
