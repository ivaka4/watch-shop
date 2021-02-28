package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class AuthorityServiceModel{
	@Enumerated(EnumType.STRING)
	private String authority;

	
}
