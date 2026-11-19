CREATE TABLE  Tls_AuthMethod
(
    id                       UUID PRIMARY KEY,
    client_authentication_id UUID NOT NULL,
    certificate_pem          TEXT NOT NULL,
    fingerprint              VARCHAR(64) NOT NULL,
    created_at               TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_oauth_client_tls_certificate_authentication
        FOREIGN KEY (client_authentication_id)
        REFERENCES oauth_client_authentication(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_oauth_client_tls_certificate_authentication
        UNIQUE (client_authentication_id)
);