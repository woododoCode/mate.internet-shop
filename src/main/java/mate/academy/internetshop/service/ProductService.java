package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Product;

@Service
public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
