INSERT INTO lembretes."role_permission" (role_id, permission_id) VALUES
((SELECT id FROM lembretes.role where name='MASTER'),(SELECT id FROM lembretes.permission where action='read')),
((SELECT id FROM lembretes.role where name='MASTER'), (SELECT id FROM lembretes.permission where action='write')),
((SELECT id FROM lembretes.role where name='MASTER'), (SELECT id FROM lembretes.permission where action='find')),
((SELECT id FROM lembretes.role where name='MASTER'), (SELECT id FROM lembretes.permission where action='delete')),

((SELECT id FROM lembretes.role where name='GERENTE'),(SELECT id FROM lembretes.permission where action='read')),
((SELECT id FROM lembretes.role where name='GERENTE'), (SELECT id FROM lembretes.permission where action='write')),
((SELECT id FROM lembretes.role where name='GERENTE'), (SELECT id FROM lembretes.permission where action='find')),
((SELECT id FROM lembretes.role where name='GERENTE'), (SELECT id FROM lembretes.permission where action='delete')),

((SELECT id FROM lembretes.role where name='PUBLIC'),(SELECT id FROM lembretes.permission where action='read')),
((SELECT id FROM lembretes.role where name='PUBLIC'), (SELECT id FROM lembretes.permission where action='write')),
((SELECT id FROM lembretes.role where name='PUBLIC'), (SELECT id FROM lembretes.permission where action='find')),
((SELECT id FROM lembretes.role where name='PUBLIC'), (SELECT id FROM lembretes.permission where action='delete'));
