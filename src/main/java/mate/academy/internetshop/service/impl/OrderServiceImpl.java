package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.internetshop.dao.interfaces.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Product> products, User user) {
        shoppingCartService.clear(shoppingCartService.getByUserId(user.getId()));
        return create(new Order(user, products));
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll().stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id).get();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
