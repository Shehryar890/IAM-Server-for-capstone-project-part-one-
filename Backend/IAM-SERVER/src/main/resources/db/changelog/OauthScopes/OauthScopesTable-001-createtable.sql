
--liquibase formatted sql

--changeset iam:005-create-oauth-scope

CREATE TABLE Oauth_Scope
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code        VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_oauth_scope_code UNIQUE (code)
);

--changeset iam:006-insert-default-oauth-scopes

INSERT INTO oauth_scope (code, description)
VALUES
    (
        'openid',
        'Requests OpenID Connect authentication and an ID Token containing the authenticated user subject identifier'
    ),
    (
        'profile',
        'Requests standard user profile claims such as name, username, picture, and other supported profile attributes'
    ),
    (
        'email',
        'Requests the authenticated user email address and email verification status'
    );
