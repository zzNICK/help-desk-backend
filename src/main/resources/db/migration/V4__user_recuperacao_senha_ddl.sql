CREATE TABLE IF NOT EXISTS user_recuperacao_senha (
    id_user_recuperacao_senha BIGSERIAL PRIMARY KEY,
    id_user BIGINT NOT NULL,
    cd_verificacao VARCHAR(6) NOT NULL,
    nm_tipo_codigo VARCHAR(30) NULL,
    dt_expiracao TIMESTAMP NOT NULL,
    dt_utilizacao TIMESTAMP,
    in_utilizado BOOLEAN NOT NULL DEFAULT FALSE,
    dt_inclusao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_codigo_usuario FOREIGN KEY(id_user) REFERENCES users(id_user)
);
