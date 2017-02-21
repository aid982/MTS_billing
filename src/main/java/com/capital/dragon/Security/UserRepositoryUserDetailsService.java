/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.capital.dragon.Security;

import com.capital.dragon.REPO.EmploeeRepo;
import com.capital.dragon.model.Emploee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**

 *
 */
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private final EmploeeRepo emploeeRepo;

    @Autowired
    public UserRepositoryUserDetailsService(EmploeeRepo emploeeRepo) {
        this.emploeeRepo  = emploeeRepo;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Emploee user = emploeeRepo.findByEmail(login);
        if(user == null) {
            throw new UsernameNotFoundException("Could not find user " + login);
        }

        if ((user.getAdmin() == null) || (user.getAdmin() == false)) {
            throw new UsernameNotFoundException("Could not find user " + login);
        }



        // Retrive all roles 
        //user.getRoles().size();
        //System.out.println(user.getRoles());
        return new CustomUserDetails(user);
    }

    private final static class CustomUserDetails extends Emploee implements UserDetails {

        private static final long serialVersionUID = 5639683223516504866L;

        private CustomUserDetails(Emploee user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        	/*String[] arrayRoles = getRolesAsStrings();
            return AuthorityUtils.createAuthorityList(arrayRoles);*/
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }

        @Override
        public String getUsername() {
            return getLogin();
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
}
