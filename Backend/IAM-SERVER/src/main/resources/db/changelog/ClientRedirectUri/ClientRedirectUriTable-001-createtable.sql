--liquibase formatted sql

--changeset iam:create-client-redirect-uri

CREATE TABLE Client_Redirect_URI
(
    id          UUID PRIMARY KEY,
    client_id   UUID NOT NULL,
    redirect_uri TEXT NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_client_redirect_uri_client
        FOREIGN KEY (client_id)
        REFERENCES Client(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_client_redirect_uri
        UNIQUE (client_id, redirect_uri)
);

--rollback DROP TABLE Client_Redirect_URI;