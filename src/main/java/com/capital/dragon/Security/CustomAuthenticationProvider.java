package com.capital.dragon.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Created by osetskiy on 08.02.2017.
 */
@Component
public class CustomAuthenticationProvider
        implements AuthenticationProvider {

    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private UserRepositoryUserDetailsService userRepositoryUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        try {
            ldapTemplate.authenticate(query().where("mail").is(name), password);

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (AuthenticationException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        UserDetails userDetails = userRepositoryUserDetailsService.loadUserByUsername(name);


        // use the credentials
        // and authenticate against the third-party system
        return new UsernamePasswordAuthenticationToken(
                userDetails, password, new ArrayList<>());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}