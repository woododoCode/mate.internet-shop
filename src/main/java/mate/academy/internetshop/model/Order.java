package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private Long userId;
    private List<Product> products = new ArrayList<>();

    public Order(Long userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    public Long getId() {
        return orderId;
    }

    public void setId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderID=" + orderId
                + ", userId=" + userId
                + ", products=" + products
                + '}';
    }
}
