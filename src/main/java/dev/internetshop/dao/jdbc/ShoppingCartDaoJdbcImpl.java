package dev.internetshop.dao.jdbc;

import dev.internetshop.dao.interfaces.ShoppingCartDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Dao;
import dev.internetshop.model.Product;
import dev.internetshop.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart element) {
        String query = "INSERT INTO carts (user_id) VALUES (?);";

        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query,
                         PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, element.getUserId());
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
        insertCartsProducts(element);
        return element;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM carts WHERE card_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(getProductsFromShoppingCartId(id));
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get cart with ID " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM carts;";
        List<ShoppingCart> allShoppingCarts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(getProductsFromShoppingCartId(shoppingCart.getId()));
                allShoppingCarts.add(shoppingCart);
            }
            return allShoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        String query = "UPDATE carts SET user_id = ? WHERE card_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            statement.setLong(1, element.getUserId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
        deleteShoppingCartFromCartsProducts(element.getId());
        insertCartsProducts(element);
        return element;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM carts WHERE card_id = ?;";
        deleteShoppingCartFromCartsProducts(id);
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete cart with ID " + id, e);
        }
    }

    private void insertCartsProducts(ShoppingCart shoppingCart) {
        String query = "INSERT INTO carts_products (cart_id, product_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            for (Product product : shoppingCart.getProducts()) {
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error in insertCartsProducts", e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("card_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(id);
        return shoppingCart;
    }

    private List<Product> getProductsFromShoppingCartId(Long shoppingCartId) throws SQLException {
        String query = "SELECT products.* FROM carts_products "
                + "JOIN products USING (product_id) WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, shoppingCartId);
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

    private void deleteShoppingCartFromCartsProducts(Long shoppingCartId) {
        String query = "DELETE FROM carts_products WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            statement.setLong(1, shoppingCartId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteShoppingCartFromCartsProducts", e);
        }
    }
}
