package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.interfaces.ProductDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getLong(1));
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + product, e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
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
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
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
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + product, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete product with ID " + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Double price = resultSet.getDouble("price");
        Product product = new Product(name, price);
        product.setId(id);
        return product;
    }
}
