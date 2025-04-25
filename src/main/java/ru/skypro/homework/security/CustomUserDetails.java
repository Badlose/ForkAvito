//package ru.skypro.homework.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import ru.skypro.homework.entity.UserEntity;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//
//    private UserEntity user;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
//
////        Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList());
//
//        return Collections.singletonList(authority); //
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        //логика проверки на истекший акк
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        //логика приверки на блок акка
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        //логика приверки на устаревшие креды
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        //логика приверки на активированный акк
//        return true;
//    }
//}
