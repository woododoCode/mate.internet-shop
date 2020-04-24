package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.interfaces.ProductDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        return Storage.addProduct(product);
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product updatedProduct) {
        IntStream.range(0, Storage.products.size())
                .filter(i -> Storage.products.get(i)
                        .getId().equals(updatedProduct.getId()))
                .forEach(i -> Storage.products
                        .set(i, updatedProduct));

        return updatedProduct;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products
                .removeIf(product -> product.getId().equals(id));
    }
}
