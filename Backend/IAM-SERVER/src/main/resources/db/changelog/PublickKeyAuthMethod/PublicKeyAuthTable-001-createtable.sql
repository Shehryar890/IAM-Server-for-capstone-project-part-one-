CREATE TABLE ClientPublickey_Authentication
(
    id                       UUID PRIMARY KEY,
    client_authentication_id UUID NOT NULL,
    key_id                   VARCHAR(100) NOT NULL,
    supported_algorithm_id   BIGINT NOT NULL,
    public_key               TEXT NOT NULL,
    created_at               TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_oauth_client_private_key_jwt_authentication
        FOREIGN KEY (client_authentication_id)
        REFERENCES PrivateClient_Authentication(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_oauth_client_private_key_jwt_algorithm
        FOREIGN KEY (supported_algorithm_id)
        REFERENCES oauth_client_public_key_supported_algorithm(id),

    CONSTRAINT uq_oauth_client_private_key_jwt_authentication
        UNIQUE (client_authentication_id)
);