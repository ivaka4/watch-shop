package webproject.watchshop.service;

import webproject.watchshop.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel uploadProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> getAllProducts();
}
