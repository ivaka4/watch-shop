package webproject.watchshop.entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import webproject.watchshop.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {
    @Enumerated(value = EnumType.STRING)
    private RoleEnum authority;

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    public Authority() {
    }
}
