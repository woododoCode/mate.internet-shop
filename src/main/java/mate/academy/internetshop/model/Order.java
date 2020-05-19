package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return orderId.equals(order.orderId)
                && getUserId().equals(order.getUserId())
                && getProducts().equals(order.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, getUserId(), getProducts());
    }
}
