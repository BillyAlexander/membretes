INSERT INTO lembretes."role_permission_module" (role_id , module_id, permission_id ) VALUES
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='USER'), (select id from lembretes.permission where action='read')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='USER'), (select id from lembretes.permission where action='write')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='USER'), (select id from lembretes.permission where action='find')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='USER'), (select id from lembretes.permission where action='delete')),

((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='LEMBRETE'), (select id from lembretes.permission where action='read')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='LEMBRETE'), (select id from lembretes.permission where action='write')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='LEMBRETE'), (select id from lembretes.permission where action='find')),
((select id from lembretes."role" where name='PUBLIC'),(select id from lembretes.module where name='LEMBRETE'), (select id from lembretes.permission where action='delete')),

((select id from lembretes."role" where name='MASTER'),(select id from lembretes.module where name='MODERADOR'), (select id from lembretes.permission where action='read')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.module where name='MODERADOR'), (select id from lembretes.permission where action='write')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.module where name='MODERADOR'), (select id from lembretes.permission where action='find')),
((select id from lembretes."role" where name='MASTER'),(select id from lembretes.module where name='MODERADOR'), (select id from lembretes.permission where action='delete')),

((select id from lembretes."role" where name='GERENTE'),(select id from lembretes.module where name='MODERADOR'), (select id from lembretes.permission where action='read')),
((select id from lembretes."role" where name='GERENTE'),(select id from lembretes.module where name='MODERADOR'), (select id from lembretes.permission where action='find'))
;

