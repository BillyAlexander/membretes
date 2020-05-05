INSERT INTO lembretes."role_permission_module" (role_id , module_id, permission_id ) VALUES
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='USERMOD'), (select id from lembretes.catalog_admin where code='READPERM')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='USERMOD'), (select id from lembretes.catalog_admin where code='WRITEPERM')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='USERMOD'), (select id from lembretes.catalog_admin where code='FINDPERM')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='USERMOD'), (select id from lembretes.catalog_admin where code='DELETEPERM')),

((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='LEMBRETEMOD'), (select id from lembretes.catalog_admin where code='READPERM')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='LEMBRETEMOD'), (select id from lembretes.catalog_admin where code='WRITEPERM')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='LEMBRETEMOD'), (select id from lembretes.catalog_admin where code='FINDPERM')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.catalog_admin where code='LEMBRETEMOD'), (select id from lembretes.catalog_admin where code='DELETEPERM')),

((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where code='MODERADORMOD'), (select id from lembretes.catalog_admin where code='READPERM')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where code='MODERADORMOD'), (select id from lembretes.catalog_admin where code='WRITEPERM')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where code='MODERADORMOD'), (select id from lembretes.catalog_admin where code='FINDPERM')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.catalog_admin where code='MODERADORMOD'), (select id from lembretes.catalog_admin where code='DELETEPERM')),

((select id from lembretes."role" where name='GERENTE'),(select id from lembretes.catalog_admin where code='MODERADORMOD'), (select id from lembretes.catalog_admin where code='READPERM')),
((select id from lembretes."role" where name='GERENTE'),(select id from lembretes.catalog_admin where code='MODERADORMOD'), (select id from lembretes.catalog_admin where code='FINDPERM'))
;

