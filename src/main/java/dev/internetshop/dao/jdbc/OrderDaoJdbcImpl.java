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
    public Order create(Order element) {
        String insertOrderQuery = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(insertOrderQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
        insertOrdersProducts(element);
        return element;
    }

    @Override
    public Optional<Order> get(Long id) {
        String selectOrderQuery = "SELECT * FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(selectOrderQuery);
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
        String selectAllOrdersQuery = "SELECT * FROM orders;";
        List<Order> allOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(selectAllOrdersQuery);
            ResultSet resultSet = statement.executeQuery();
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
    public Order update(Order element) {
        String updateOrderQuery = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ?;";
        deleteOrderFromOrdersProducts(element.getId());
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(updateOrderQuery);
            statement.setLong(1, element.getUserId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
        insertOrdersProducts(element);
        return element;
    }

    @Override
    public boolean delete(Long id) {
        String deleteOrderQuery = "DELETE FROM orders WHERE order_id = ?;";
        deleteOrderFromOrdersProducts(id);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteOrderQuery);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete order with ID " + id, e);
        }
    }

    private void insertOrdersProducts(Order order) {
        String insertOrdersProductsQuery = "INSERT INTO orders_products (order_id, products_id) "
                + "VALUES (?, ?);";
        try {
            try (Connection connection = ConnectionUtil.getConnection()) {
                for (Product product : order.getProducts()) {
                    PreparedStatement insertStatement =
                            connection.prepareStatement(insertOrdersProductsQuery);
                    insertStatement.setLong(1, order.getId());
                    insertStatement.setLong(2, product.getId());
                    insertStatement.executeUpdate();
                }
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
        String selectProductIdQuery = "SELECT * FROM orders_products op JOIN products p "
                + "ON p.product_id = op.products_id WHERE op.order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectProductIdQuery);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
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
        String deleteOrderQuery = "DELETE FROM orders_products WHERE order_id IN"
                + " (SELECT order_id FROM orders WHERE user_id = ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteOrderQuery);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteOrderFromOrdersProducts", e);
        }
    }
}
