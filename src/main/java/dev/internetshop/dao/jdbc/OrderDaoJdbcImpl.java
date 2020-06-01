package dev.internetshop.dao.jdbc;

import dev.internetshop.dao.interfaces.OrderDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Dao;
import dev.internetshop.model.Order;
import dev.internetshop.model.Product;
import dev.internetshop.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        String orderQuery = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(orderQuery,
                         PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            resultSet.next();
            order.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + order, e);
        }
        insertOrdersProducts(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var order = getOrderFromResultSet(resultSet);
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get order with ID " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders;";
        List<Order> allOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var order = getOrderFromResultSet(resultSet);
                allOrders.add(order);
            }
            return allOrders;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all orders", e);
        }
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ?;";
        deleteOrderFromOrdersProducts(order.getId());
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + order, e);
        }
        insertOrdersProducts(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM orders WHERE order_id = ?;";
        deleteOrderFromOrdersProducts(id);
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete order with ID " + id, e);
        }
    }

    private void insertOrdersProducts(Order order) {
        String query = "INSERT INTO orders_products (order_id, products_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            for (Product product : order.getProducts()) {
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error in insertOrdersProducts", e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        Order order = new Order(userId, getProductsFromOrderId(id));
        order.setId(id);
        return order;
    }

    private List<Product> getProductsFromOrderId(Long orderId) throws SQLException {
        String query = "SELECT * FROM orders_products op JOIN products p "
                + "ON p.product_id = op.products_id WHERE op.order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            var resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                Product product = new Product(name, price);
                product.setId(id);
                products.add(product);
            }
            return products;
        }
    }

    private void deleteOrderFromOrdersProducts(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id IN"
                + " (SELECT order_id FROM orders WHERE user_id = ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteOrderFromOrdersProducts", e);
        }
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
            String query = "SELECT * FROM orders WHERE user_id = ?;";
            List<Order> userOrders = new ArrayList<>();
            try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);
                var resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    var order = getOrderFromResultSet(resultSet);
                    userOrders.add(order);
                }
                return userOrders;
            } catch (SQLException e) {
                throw new DataProcessingException("Unable to get cart with ID " + userId, e);
            }
    }
}
