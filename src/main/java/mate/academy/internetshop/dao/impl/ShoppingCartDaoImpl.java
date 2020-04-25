package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.interfaces.ShoppingCartDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return Storage.addCart(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> get(Long cartID) {
        return Storage.shoppingCarts.stream()
                .filter(s -> s.getId().equals(cartID))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart updatedCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(i -> Storage.shoppingCarts.get(i)
                        .getId().equals(updatedCart.getId()))
                .forEach(i -> Storage.shoppingCarts
                        .set(i, updatedCart));

        return updatedCart;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts
                .removeIf(s -> s.getId().equals(id));
    }
}
