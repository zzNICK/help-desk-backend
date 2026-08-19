CREATE TABLE IF NOT EXISTS chamado (
    id_chamado BIGSERIAL PRIMARY KEY,
    id_user BIGINT NOT NULL,
    ds_descricao VARCHAR(500) NOT NULL,
    nm_status VARCHAR(20) NOT NULL DEFAULT 'ABERTO',
    dt_inclusao TIMESTAMP NOT NULL DEFAULT NOW(),
    dt_alteracao TIMESTAMP NULL,
    CONSTRAINT FK01_user_x_chamado FOREIGN KEY(id_user) REFERENCES users(id_user)
);