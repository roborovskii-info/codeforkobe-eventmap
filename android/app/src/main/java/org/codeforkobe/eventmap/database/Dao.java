package org.codeforkobe.eventmap.database;

import java.util.List;

/**
 * @author ISHIMARU Sohei on 2016/08/05.
 */
public interface Dao<E> {

    E fetchById(long id);

    List<E> fetchAll();

    long add(E e);

    int update(E e);

    boolean deleteById(long id);

    boolean deleteAll();

}
