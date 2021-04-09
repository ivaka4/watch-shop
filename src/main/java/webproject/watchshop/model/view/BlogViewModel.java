package webproject.watchshop.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.model.service.UserServiceModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BlogViewModel {
    private Long id;
    private String title;
    private String description;
    private LocalDate addedOn;
    private String imgUrl;
//    private MultipartFile photo;
    private String category;
    private String author;
}
