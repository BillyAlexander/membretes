 INSERT INTO lembretes.catalog_admin (name, code, other, parent) VALUES
('Listado de Permisos', 'PERMISSIONS', NULL, NULL); 

INSERT INTO lembretes.catalog_admin(name, code, parent) VALUES
('READ', 'READPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS')),
('WRITE', 'WRITEPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS')),
('FIND', 'FINDPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS')),
('DELETE', 'DELETEPERM', (SELECT id FROM lembretes.catalog_admin WHERE code='PERMISSIONS'))
;
