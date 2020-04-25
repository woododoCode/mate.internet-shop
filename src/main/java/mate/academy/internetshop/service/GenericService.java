package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, E> {
    T create(T t);

    T get(E id);

    List<T> getAll();

    T update(T t);

    boolean delete(E id);
}
