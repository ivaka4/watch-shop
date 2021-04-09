package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BlogServiceModel {

    private String title;
    private String description;
    private LocalDate addedOn;
    private String imgUrl;
    private MultipartFile photo;
    private String category;
    private UserServiceModel author;
}
