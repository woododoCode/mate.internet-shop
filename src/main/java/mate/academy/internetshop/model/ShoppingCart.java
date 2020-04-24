package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products = new ArrayList<>();
    private User user;

    public ShoppingCart(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", products=" + products
                + ", user=" + user
                + '}';
    }
}
