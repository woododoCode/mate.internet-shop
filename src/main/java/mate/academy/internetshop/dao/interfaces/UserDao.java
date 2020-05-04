package mate.academy.internetshop.dao.interfaces;

import java.util.Optional;
import mate.academy.internetshop.model.User;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> findByLogin(String login);
}
