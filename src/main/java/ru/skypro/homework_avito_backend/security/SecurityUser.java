package ru.skypro.homework_avito_backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework_avito_backend.model.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Адаптер сущности {@link User} к интерфейсу {@link UserDetails} Spring Security.
 * Обеспечивает интеграцию пользовательских данных из базы данных с механизмами
 * аутентификации и авторизации Spring Security.
 * <p>
 * Роль пользователя преобразуется в формат {@code ROLE_<ROLE>} (например, {@code ROLE_ADMIN}),
 * а email используется в качестве username для аутентификации.
 */
public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = user.getRole() != null ? user.getRole().toUpperCase() : "USER";
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
