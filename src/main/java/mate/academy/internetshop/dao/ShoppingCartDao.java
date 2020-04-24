package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart get(Long shoppingCartID);
}
