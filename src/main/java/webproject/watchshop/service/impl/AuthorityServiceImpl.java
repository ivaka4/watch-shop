package webproject.watchshop.service.impl;

import org.springframework.stereotype.Service;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Authority;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void seedAuthorities() {
        Authority admin = new Authority();
        admin.setAuthority(RoleEnum.ADMIN);
        Authority user = new Authority();
        user.setAuthority(RoleEnum.USER);
        authorityRepository.save(admin);
        authorityRepository.save(user);
    }
}
