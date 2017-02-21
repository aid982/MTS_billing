package com.capital.dragon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLDAP {
    @Autowired
    private LdapTemplate ldapTemplate;

    @Test
    public void contextLoads() throws Exception {
        List<String> members = this.ldapTemplate.list("ou=dragon-capital");
        System.out.println(members);

		/*members = ldapTemplate.search(
                query().where("objectclass").is("person"),
				new AttributesMapper<String>() {
					public String mapFromAttributes(Attributes attrs)
							throws NamingException {
						return (String) attrs.get("cn").get();
					}
				});
		System.out.println(members);*/


        LdapQuery query = query()
                .base("ou=dragon-capital")
                .attributes("cn", "sn", "mail", "uid", "dn")
                .where("objectclass").is("person")
                .and("otherMobile").is("0952707419");
        //0503130533 0952707419
        System.out.println(members);


        members = ldapTemplate.search(query,
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {

                        System.out.println((String) attrs.get("cn").get());
                        System.out.println((String) attrs.get("sn").get());
                        System.out.println((String) attrs.get("mail").get());
                        return (String) attrs.get("cn").get();
                    }
                });
        //System.out.println(members);

        ldapTemplate.authenticate(query().where("mail").is("osetskiy@dragon-capital.com"), "Yfcnz20131");


    }
}
