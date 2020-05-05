 INSERT INTO lembretes.catalog_admin (name, code, other, parent) VALUES
('Listado de Grupos de Usuario', 'USERGROUP', NULL, NULL); 

INSERT INTO lembretes.catalog_admin(name, code, parent) VALUES
('Default', 'GROUPDEFAULT', (SELECT id FROM lembretes.catalog_admin WHERE code='USERGROUP'))
;
