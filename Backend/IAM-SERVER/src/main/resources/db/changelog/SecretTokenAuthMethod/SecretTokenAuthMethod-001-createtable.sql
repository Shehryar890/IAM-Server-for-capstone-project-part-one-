

CREATE TABLE SecretTokenAuthMethod
(
    id                       UUID PRIMARY KEY,
    client_authentication_id UUID NOT NULL,
    secret_hash              VARCHAR(255) NOT NULL,
    secret_prefix            VARCHAR(20) NOT NULL,
    created_at               TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_oauth_client_secret_authentication
        FOREIGN KEY (client_authentication_id)
        REFERENCES PrivateClient_Authentication(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_oauth_client_secret_authentication
        UNIQUE (client_authentication_id)
);


