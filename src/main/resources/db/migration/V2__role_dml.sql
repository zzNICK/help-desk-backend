INSERT INTO role (nm_role, ds_role)
VALUES ('ADMIN', 'Usada para controle administrativo do sistema') ON CONFLICT (nm_role) DO NOTHING;

INSERT INTO role (nm_role, ds_role)
VALUES ('USER', 'Usada para usuarios que vão consumir o sistema') ON CONFLICT (nm_role) DO NOTHING;