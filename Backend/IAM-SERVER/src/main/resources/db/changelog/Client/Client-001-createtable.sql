CREATE TABLE  Client
(
    id          UUID PRIMARY KEY,
    client_id   VARCHAR(100) NOT NULL,
    name        VARCHAR(150) NOT NULL,
    client_type VARCHAR(20) NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_oauth_client_client_id
        UNIQUE (client_id),

    CONSTRAINT chk_oauth_client_type
        CHECK (client_type IN ('PUBLIC', 'PRIVATE'))
);