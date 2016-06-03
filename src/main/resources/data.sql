
--insert into role(id) values ('ROLE_ADMIN');
--insert into role(id) values ('ROLE_USER');
--update emploee(id,email,password) values ('196','osetskiy@dragon-capital.com','rfnz1984');

UPDATE emploee
   SET  password='1'
 WHERE id='196';
 
--insert into  emploee_roles(emploees_id, roles_id) values ('196','ROLE_ADMIN');








