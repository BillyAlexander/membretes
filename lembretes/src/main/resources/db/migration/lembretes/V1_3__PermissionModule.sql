INSERT INTO lembretes."permission_module" (module_id, permission_id) VALUES
((SELECT id FROM lembretes.module where name='MODERADOR'),(SELECT id FROM lembretes.permission where action='read')),
((SELECT id FROM lembretes.module where name='MODERADOR'), (SELECT id FROM lembretes.permission where action='write')),
((SELECT id FROM lembretes.module where name='MODERADOR'), (SELECT id FROM lembretes.permission where action='find')),
((SELECT id FROM lembretes.module where name='MODERADOR'), (SELECT id FROM lembretes.permission where action='delete')),

((SELECT id FROM lembretes.module where name='USER'),(SELECT id FROM lembretes.permission where action='read')),
((SELECT id FROM lembretes.module where name='USER'), (SELECT id FROM lembretes.permission where action='write')),
((SELECT id FROM lembretes.module where name='USER'), (SELECT id FROM lembretes.permission where action='find')),
((SELECT id FROM lembretes.module where name='USER'), (SELECT id FROM lembretes.permission where action='delete')),

((SELECT id FROM lembretes.module where name='LEMBRETE'),(SELECT id FROM lembretes.permission where action='read')),
((SELECT id FROM lembretes.module where name='LEMBRETE'), (SELECT id FROM lembretes.permission where action='write')),
((SELECT id FROM lembretes.module where name='LEMBRETE'), (SELECT id FROM lembretes.permission where action='find')),
((SELECT id FROM lembretes.module where name='LEMBRETE'), (SELECT id FROM lembretes.permission where action='delete'));
