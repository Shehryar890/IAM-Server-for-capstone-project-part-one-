
CREATE TABLE oauth_trusted_ca_certificate
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    certificate_pem TEXT NOT NULL,
    fingerprint     VARCHAR(64) NOT NULL,
    is_active       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_oauth_trusted_ca_certificate_fingerprint
        UNIQUE (fingerprint)
);


