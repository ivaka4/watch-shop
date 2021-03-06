package webproject.watchshop.web.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.util.annotation.PageTitle;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopRestController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ShopRestController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @PageTitle(name = "AJAX")
    @GetMapping("/api")
    public ResponseEntity<List<ProductViewModel>> findAll() {
        return ResponseEntity
                .ok()
                .body(this.modelMapper.map(productService.getAllProducts(),
                        new TypeToken<List<ProductViewModel>>(){}.getType()));
    }

    @PageTitle(name = "Product Sorted")
    @GetMapping("/sorted")
    public ResponseEntity<List<ProductViewModel>> findAllOrderedByPrice(){
        return ResponseEntity
                .ok()
                .body(this.modelMapper.map(productService.getAllProductOrderedByPrice(),
                        new TypeToken<List<ProductViewModel>>(){}.getType()));
    }
}
