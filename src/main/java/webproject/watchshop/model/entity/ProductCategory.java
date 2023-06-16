package webproject.watchshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.constants.SCHEMA;

import javax.persistence.*;

@Entity()
@Table(name = "product_categories", schema = SCHEMA.NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory extends BaseEntity {

    private String category;

    private String description;

}
