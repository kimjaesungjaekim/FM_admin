package com.developer.fillme.config.security;

import com.developer.fillme.entity.UserEntity;
import com.developer.fillme.model.UserInfoDetail;
import com.developer.fillme.repository.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserDetailsService {
    private final IUserRepo authService;
    private final ModelMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userInfo = authService.findByUsername(username);
        UserInfoDetail userInfoDetailDto = mapper.map(userInfo, UserInfoDetail.class);

        if (userInfo == null) {
            throw new UsernameNotFoundException(username);
        }
        return userInfoDetailDto;
    }
}
