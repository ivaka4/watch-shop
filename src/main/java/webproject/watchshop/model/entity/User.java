package webproject.watchshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.actuate.autoconfigure.env.EnvironmentEndpointAutoConfiguration;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(name = "register_on", nullable = false)
    private LocalDateTime registerOn;
    @Column(name = "is_enabled")
    private Boolean isEnabled;
    @Column(name = "phone", nullable = false)
    private Integer phone;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> cart;
    @OneToMany()
    private List<Blog> blogs;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Authority> authorities;


}
