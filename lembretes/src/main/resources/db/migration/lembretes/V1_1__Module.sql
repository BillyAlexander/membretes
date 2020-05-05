
INSERT INTO lembretes.catalog_admin (name, code, other, parent) VALUES
('Listado de Modulos', 'MODULES', NULL, NULL); 

INSERT INTO lembretes.catalog_admin(name, code, parent) VALUES
('USER', 'USERMOD', (SELECT id FROM lembretes.catalog_admin WHERE code='MODULES')),
('MODERADOR', 'MODERADORMOD', (SELECT id FROM lembretes.catalog_admin WHERE code='MODULES')),
('LEMBRETE', 'LEMBRETEMOD', (SELECT id FROM lembretes.catalog_admin WHERE code='MODULES'))
;





