package org.recap.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.recap.ScsbConstants;
import org.recap.model.security.AppUserDetails;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sheiks on 17/01/17.
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {



    private Set<String> admins;

    /**
     * Instantiates a new CustomUserDetailsService.
     */
    public CustomUserDetailsService() {
        super();
    }

    /**
     * Instantiates a new CustomUserDetailsService.
     *
     * @param admins the admins
     */
    public CustomUserDetailsService(Set<String> admins) {
        super();
        this.admins = admins;
    }

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        String login = token.getPrincipal().toString();
        String lowercaseLogin = login.toLowerCase();

        log.debug("Authenticating '{}'", login);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (admins != null && admins.contains(lowercaseLogin)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(ScsbConstants.ADMIN));
        } else {
            grantedAuthorities.add(new GrantedAuthority() {
                private static final long serialVersionUID = 1L;

                @Override
                public String getAuthority() {
                    return ScsbConstants.USER;
                }
            });
        }

        return new AppUserDetails(lowercaseLogin, grantedAuthorities);
    }
}
