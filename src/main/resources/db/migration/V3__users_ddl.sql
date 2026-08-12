CREATE TABLE IF NOT EXISTS users (
    id_user BIGSERIAL PRIMARY KEY,
    id_role SMALLINT NOT NULL,
    nm_user VARCHAR(45) NOT NULL,
    ps_hash VARCHAR(255) NOT NULL,
    nm_email VARCHAR(100) NOT NULL UNIQUE,
    in_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    dt_ultimo_login TIMESTAMP,
    dt_inclusao TIMESTAMP NOT NULL DEFAULT NOW(),
    dt_alteracao TIMESTAMP NULL,
    CONSTRAINT FK01_role_x_users FOREIGN KEY(id_role) REFERENCES role(id_role)

);