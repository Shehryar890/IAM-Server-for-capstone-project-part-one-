
--liquibase formatted sql

--changeset iam-server:20261110-create-platform-admin-refresh-token

CREATE TABLE platform_admin_refresh_token
(
    -- Unique identity of each refresh-token record
    refresh_token_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Admin who owns this refresh token
    platform_admin_id UUID NOT NULL,

    -- SHA-256 hash of the original refresh token
    token_hash VARCHAR(64) NOT NULL,

    -- Identifies the login session to which this token belongs
    family_id UUID NOT NULL,

    -- When this refresh token was created
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- When this refresh token expires
    expires_at TIMESTAMPTZ NOT NULL,

    -- Whether this refresh token has been revoked or consumed
    is_revoked BOOLEAN NOT NULL DEFAULT FALSE,

    -- ID of the new token that replaced this token
    replaced_by UUID,


    -- Each token hash must be unique
    CONSTRAINT uk_platform_admin_refresh_token_hash
        UNIQUE (token_hash),


    -- Every refresh token must belong to an existing platform admin
    CONSTRAINT fk_refresh_token_platform_admin
        FOREIGN KEY (platform_admin_id)
        REFERENCES platform_admin (platform_admin_id)
        ON DELETE CASCADE,


    -- A replacement must reference an existing refresh-token record
    CONSTRAINT fk_refresh_token_replaced_by
        FOREIGN KEY (replaced_by)
        REFERENCES platform_admin_refresh_token (refresh_token_id),


    -- SHA-256 hexadecimal hash must contain exactly 64 lowercase characters
    CONSTRAINT chk_refresh_token_hash_format
        CHECK (token_hash ~ '^[0-9a-f]{64}$'),


    -- Expiration must be later than creation
    CONSTRAINT chk_refresh_token_expiry
        CHECK (expires_at > created_at),


    -- A token cannot replace itself
    CONSTRAINT chk_refresh_token_not_self_replaced
        CHECK (
            replaced_by IS NULL
            OR replaced_by <> refresh_token_id
        ),


    -- A token must be revoked before it can have a replacement
    CONSTRAINT chk_refresh_token_replacement_revoked
        CHECK (
            replaced_by IS NULL
            OR is_revoked = TRUE
        )
);


-- Helps find all refresh tokens belonging to a platform admin
CREATE INDEX idx_refresh_token_platform_admin
    ON platform_admin_refresh_token (platform_admin_id);


-- Helps find all tokens belonging to one login session
CREATE INDEX idx_refresh_token_family
    ON platform_admin_refresh_token (family_id);
