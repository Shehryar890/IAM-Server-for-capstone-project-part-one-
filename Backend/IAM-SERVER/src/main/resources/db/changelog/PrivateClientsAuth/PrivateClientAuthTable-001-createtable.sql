
-
CREATE TABLE PrivateClient_Authentication
(
    id              UUID PRIMARY KEY,
    client_id       UUID NOT NULL,
    auth_method_id  BIGINT NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_oauth_client_authentication_client
        FOREIGN KEY (client_id)
        REFERENCES oauth_client(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_oauth_client_authentication_method
        FOREIGN KEY (auth_method_id)
        REFERENCES PrivateClient_authentication_method(id),

    CONSTRAINT uq_oauth_client_authentication_client
        UNIQUE (client_id)
);


