
CREATE TABLE Client_Permission
(
    id            UUID PRIMARY KEY,
    client_id     UUID NOT NULL,
    permission_id BIGINT NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_client_permission_client
        FOREIGN KEY (client_id)
        REFERENCES Client(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_client_permission_permission
        FOREIGN KEY (permission_id)
        REFERENCES Permission(id),

    CONSTRAINT uq_client_permission
        UNIQUE (client_id, permission_id)
);
