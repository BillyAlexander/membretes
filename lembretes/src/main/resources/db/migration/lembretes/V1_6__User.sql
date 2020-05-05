INSERT INTO lembretes."user"
(last_name, "name", "password",  provider,  user_name, role_id, usergroup_id)
VALUES('Villa', 'Alex', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba',  'local',  'alex22wb@gmail.com', 
(SELECT id FROM lembretes."role" WHERE name='MASTER') , (SELECT id FROM lembretes.catalog_admin WHERE code='GROUPDEFAULT'));

