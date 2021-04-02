package webproject.watchshop.service;

import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.view.ProductViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductServiceModel uploadProduct(ProductServiceModel productServiceModel) throws IOException;

    List<ProductServiceModel> getAllProducts();

   ProductServiceModel getProductBy(Long id);

    boolean removeProduct(Long id);

    ProductServiceModel editProduct(ProductServiceModel psm) throws IOException;

    List<ProductViewModel> getLastThreeProduct();

    List<ProductViewModel> getAllProductOrderedByPrice();
}
