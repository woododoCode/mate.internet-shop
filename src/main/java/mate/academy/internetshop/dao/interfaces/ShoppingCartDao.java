package mate.academy.internetshop.dao.interfaces;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> get(Long shoppingCartID);

    List<ShoppingCart> getAll();

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean delete(Long id);
}
