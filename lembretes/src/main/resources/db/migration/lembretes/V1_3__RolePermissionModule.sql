INSERT INTO lembretes."role_permission_module" (role_id , module_id, permission_id ) VALUES
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='USER'), (select id from lembretes.catalog_admin where name='read')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='USER'), (select id from lembretes.catalog_admin where name='write')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='USER'), (select id from lembretes.catalog_admin where name='find')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='USER'), (select id from lembretes.catalog_admin where name='delete')),

((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='LEMBRETE'), (select id from lembretes.catalog_admin where name='read')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='LEMBRETE'), (select id from lembretes.catalog_admin where name='write')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='LEMBRETE'), (select id from lembretes.catalog_admin where name='find')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where name='LEMBRETE'), (select id from lembretes.catalog_admin where name='delete')),

((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where name='MODERADOR'), (select id from lembretes.catalog_admin where name='read')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where name='MODERADOR'), (select id from lembretes.catalog_admin where name='write')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where name='MODERADOR'), (select id from lembretes.catalog_admin where name='find')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where name='MODERADOR'), (select id from lembretes.catalog_admin where name='delete')),

((select id from lembretes."role" where name='GERENTE'),(select id from lembretes.catalog_admin where name='MODERADOR'), (select id from lembretes.catalog_admin where name='read')),
((select id from lembretes."role" where name='GERENTE'),(select id from lembretes.catalog_admin where name='MODERADOR'), (select id from lembretes.catalog_admin where name='find'))
;

