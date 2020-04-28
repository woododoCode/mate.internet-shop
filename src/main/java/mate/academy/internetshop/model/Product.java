package mate.academy.internetshop.model;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer amount;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, Integer amount, Double price) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ",name='" + name
                + ",price=" + price
                + '}';
    }
}
