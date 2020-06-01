package dev.internetshop.dao.interfaces;

import dev.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    ShoppingCart getByUserId(Long userId);
}
