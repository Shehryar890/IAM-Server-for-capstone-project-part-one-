


CREATE TABLE  PrivateClient_authentication_method

    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code        VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
   created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_oauth_client_auth_method_code UNIQUE (code)
);



INSERT INTO  PrivateClient_authentication_method(code, description)
VALUES
    ('client_secret_basic',
     'Client authenticates using HTTP Basic authentication with a client secret'),

    ('private_key_jwt',
     'Client authenticates using a JWT assertion signed with its private key'),

    ('tls_client_auth',
     'Client authenticates using mutual TLS client certificate authentication');
