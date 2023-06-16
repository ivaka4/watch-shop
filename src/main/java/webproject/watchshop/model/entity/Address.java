package webproject.watchshop.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.constants.SCHEMA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses", schema = SCHEMA.NAME)
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseEntity {

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String address1;
    @Column(nullable = false)
    private String address2;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String postCode;
}
