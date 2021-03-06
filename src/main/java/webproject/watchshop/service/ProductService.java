package webproject.watchshop.service;

import webproject.watchshop.model.service.ProductServiceModel;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductServiceModel uploadProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> getAllProducts();

   ProductServiceModel getProductBy(Long id);
}
