package dev.internetshop.dao.jdbc;

import dev.internetshop.dao.interfaces.ProductDao;
import dev.internetshop.exceptions.DataProcessingException;
import dev.internetshop.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query,
                         PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getLong(1));
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + product, e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                return Optional.of(product);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get product with ID " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        List<Product> allProducts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all products", e);
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + product, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete product with ID " + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");
        Product product = new Product(name, price);
        product.setId(id);
        return product;
    }
}
