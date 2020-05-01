 INSERT INTO lembretes.catalog_admin (name, code, other, parent) VALUES
('Listado de Permisos', 'PERMISSIONS', NULL, NULL); 

INSERT INTO lembretes.catalog_admin(name, code, parent) VALUES
('read', 'READPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS')),
('write', 'WRITEPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS')),
('find', 'FINDPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS')),
('delete', 'DELETEPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS'))
;
