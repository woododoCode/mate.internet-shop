package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.interfaces.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String selectUserQuery = "SELECT * FROM users WHERE login = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(selectUserQuery);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get user with login " + login, e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, login, password, salt) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setUserId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + user, e);
        }
        setRoleForUser(user);
        return user;
    }

    private void setRoleForUser(User user) {
        String selectRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String insertUsersRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Role role : user.getRoles()) {
                var selectRoleStatement = connection.prepareStatement(selectRoleIdQuery);
                selectRoleStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = selectRoleStatement.executeQuery();
                resultSet.next();
                var insertRoleStatement = connection.prepareStatement(insertUsersRolesQuery);
                insertRoleStatement.setLong(1, user.getId());
                insertRoleStatement.setLong(2, resultSet.getLong("role_id"));
                insertRoleStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error in setRoleForUser ", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String selectUserQuery = "SELECT * FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(selectUserQuery);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(id));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get user with ID " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String selectAllUsersQuery = "SELECT * FROM users;";
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(selectAllUsersQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId()));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all users", e);
        }
    }

    @Override
    public User update(User user) {
        String updateUserQuery = "UPDATE users SET name = ?, login = ?, password = ? , salt = ?"
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(updateUserQuery);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            setRoleForUser(user);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + user, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        deleteUserShoppingCartProducts(id);
        deleteUsersRoles(id);
        deleteOrdersProducts(id);
        deleteUserOrders(id);
        deleteUserShoppingCart(id);
        String deleteUserQuery = "DELETE FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteUserQuery);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete user with ID " + id, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(name, login, password);
        user.setUserId(id);
        user.setSalt(salt);
        return user;
    }

    private Set<Role> getRolesFromUserId(Long userId) throws SQLException {
        String selectRoleNameQuery = "SELECT role_name FROM users_roles "
                + "JOIN roles USING (role_id) WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            var statement = connection.prepareStatement(selectRoleNameQuery);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
            return roles;
        }
    }

    private void deleteUsersRoles(Long userId) {
        String deleteUserQuery = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteUserQuery);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteUsersRoles", e);
        }
    }

    private void deleteOrdersProducts(Long userId) {
        String query = "DELETE FROM orders_products WHERE order_id IN"
                + " (SELECT order_id FROM orders WHERE user_id = ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteOrdersProducts", e);
        }
    }

    private void deleteUserOrders(Long userId) {
        String query = "DELETE FROM orders WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteUserOrders", e);
        }
    }

    private void deleteUserShoppingCartProducts(Long userId) {
        String query = "DELETE FROM carts_products WHERE cart_id IN"
                + " (SELECT cart_id FROM carts WHERE user_id = ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteUserShoppingCartProducts", e);
        }
    }

    private void deleteUserShoppingCart(Long userId) {
        String query = "DELETE FROM carts WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Error in deleteUserShoppingCart", e);
        }
    }
}
