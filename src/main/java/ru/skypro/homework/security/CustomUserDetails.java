package ru.skypro.homework.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Collection<? extends GrantedAuthority> role;
    private String image;
//    private  boolean notExpired;
//    private  boolean notLocked;
//    private  boolean credentialsNonExpired;
//    private  boolean enabled;


    public CustomUserDetails() {
    }

    public CustomUserDetails(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.phone = userEntity.getPhone();
        this.role = getCustomAuthorities(userEntity.getRole().toString());
        this.image = userEntity.getImage();
    }

    public CustomUserDetails(
            String username,
            String password,
            String firstName,
            String lastName,
            String phone,
            String role,
            String image

//            boolean notExpired,
//            boolean notLocked,
//            boolean credentialsNonExpired,
//            boolean enabled
    ) {

//        this.notExpired = notExpired;
//        this.notLocked = notLocked;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.enabled = enabled;
    }

    public CustomUserDetails(
            String username,
            String password,
            String firstName,
            String lastName,
            String phone,
            String role
//            boolean notExpired,
//            boolean notLocked,
//            boolean credentialsNonExpired,
//            boolean enabled
    ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = getCustomAuthorities(role);
//        this.notExpired = notExpired;
//        this.notLocked = notLocked;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.enabled = enabled;
    }

    private Collection<? extends GrantedAuthority> getCustomAuthorities(String role) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return Collections.singletonList(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
//        Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //логика проверки на истекший акк
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //логика приверки на блок акка
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //логика приверки на устаревшие креды
        return true;
    }

    @Override
    public boolean isEnabled() {
        //логика приверки на активированный акк
        return true;
    }
}
