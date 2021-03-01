package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.enums.RoleEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class AuthorityServiceModel{

	private RoleEnum authority;

	
}
