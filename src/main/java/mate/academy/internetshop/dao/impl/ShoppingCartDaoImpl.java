package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.ShoppingCart;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public ShoppingCart get(Long shoppingCartID) {
        return Storage.shoppingCarts
                .stream().filter(s -> s.getId().equals(shoppingCartID))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find cart with id" + shoppingCartID));
    }
}
