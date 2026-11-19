--liquibase formatted sql

--changeset iam:create-client-scope

CREATE TABLE Client_Scope
(
    id         UUID PRIMARY KEY,
    client_id  UUID NOT NULL,
    scope_id   BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_client_scope_client
        FOREIGN KEY (client_id)
        REFERENCES Client(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_client_scope_scope
        FOREIGN KEY (scope_id)
        REFERENCES Oauth_Scope(id),

    CONSTRAINT uq_client_scope
        UNIQUE (client_id, scope_id)
);

--rollback DROP TABLE Client_Scope;