package webproject.watchshop.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "modifiedDate_19118028")
    private LocalDateTime modifiedDate;

    @PreUpdate
    @PrePersist
    public void beforeCreateAndUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

}
