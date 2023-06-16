package webproject.watchshop.model.entity;

import org.springframework.security.core.GrantedAuthority;
import webproject.watchshop.constants.SCHEMA;
import webproject.watchshop.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = SCHEMA.NAME)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    private RoleEnum authority;

    @Override
    @Column(name = "role", nullable = false, unique = true)
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    public Authority() {
    }
}
