package webproject.watchshop.model.entity;

import lombok.Getter;
import lombok.Setter;
import webproject.watchshop.constants.SCHEMA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "log_19118028", schema = SCHEMA.NAME)
public class LogTriggers extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime dateTime;

}