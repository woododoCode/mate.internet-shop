package mate.academy.internetshop.service.impl;

import java.util.List;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public Order completeOrder(List<Product> products, User user) {
        return null;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return null;
    }

    @Override
    public Order get(Long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
