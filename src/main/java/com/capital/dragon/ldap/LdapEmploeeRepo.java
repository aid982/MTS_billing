package com.capital.dragon.ldap;

import com.capital.dragon.model.Emploee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Created by osetskiy on 08.02.2017.
 */
@Component
public class LdapEmploeeRepo {
    private LdapTemplate ldapTemplate;

    @Autowired
    public LdapEmploeeRepo(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public Emploee findUserByPhoneNumber(String phone) {
        phone = phone.substring(2);
        LdapQuery query = query()
                .base("ou=dragon-capital")
                .attributes("cn", "sn", "mail", "department", "otherMobile")
                .where("objectclass").is("person")
                .and("otherMobile").is(phone);
        //0503130533 0952707419
        try {
            List<Emploee> emploeeList = ldapTemplate.search(query, new EmploeeAttributesMapper());
            if (!emploeeList.isEmpty()) return emploeeList.get(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }

        return new Emploee("Unknown user", phone, "unknownuser@dragon.capital.com", true);
    }

    private class EmploeeAttributesMapper implements AttributesMapper<Emploee> {
        public Emploee mapFromAttributes(Attributes attrs) throws NamingException {
            Emploee emploee = new Emploee();
            emploee.setName((String) attrs.get("cn").get());
            emploee.setEmail((String) attrs.get("mail").get());
            emploee.setDepartment((String) attrs.get("department").get());
            emploee.setAutoCreated(true);
            return emploee;
        }
    }
}
