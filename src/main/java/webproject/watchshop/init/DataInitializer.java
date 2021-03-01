//package webproject.watchshop.init;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import webproject.watchshop.model.entity.ProductCategory;
//import webproject.watchshop.repository.ProductCategoryRepository;
//
//@Component
//
//public class DataInitializer implements CommandLineRunner {
//    private final ProductCategoryRepository productCategoryRepository;
//
//    public DataInitializer(ProductCategoryRepository productCategoryRepository) {
//        this.productCategoryRepository = productCategoryRepository;
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        ProductCategory sport = new ProductCategory();
//        sport.setCategory("Sport");
//        sport.setDescription("The best sports watches");
//        ProductCategory offical = new ProductCategory();
//        offical.setCategory("Offical");
//        offical.setDescription("THe best offical watch");
//        productCategoryRepository.saveAndFlush(sport);
//        productCategoryRepository.saveAndFlush(offical);
//    }
//}
