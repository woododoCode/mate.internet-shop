package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> get(Long shoppingCartID);

    ShoppingCart update(ShoppingCart shoppingCart);

    boolean delete(Long cartId);
}
