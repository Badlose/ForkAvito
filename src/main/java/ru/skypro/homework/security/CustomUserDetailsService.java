package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь %s не найден", username)));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                getAuthority(userEntity)
        );

    }

    private Collection<? extends GrantedAuthority> getAuthority(UserEntity userEntity) {

        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole().name());

        return List.of(authority);
    }
}
