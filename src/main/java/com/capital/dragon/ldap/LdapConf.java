package com.capital.dragon.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Created by osetskiy on 07.02.2017.
 */
@Configuration
class LdapConf {

    @Bean
    ContextSource contextSource() {

        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://192.168.2.70:389");
        ldapContextSource.setBase("DC=dragon,DC=local");
        ldapContextSource.setUserDn("1c_robot");
        ldapContextSource.setPassword("XepecH7j");
        ldapContextSource.setReferral("follow");

        return ldapContextSource;
    }

    @Bean
    LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}