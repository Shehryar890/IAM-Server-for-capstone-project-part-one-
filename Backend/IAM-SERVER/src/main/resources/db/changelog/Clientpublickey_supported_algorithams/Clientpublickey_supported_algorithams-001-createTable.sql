CREATE TABLE Clientpublickey_supported_algorithams

(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code        VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,

    CONSTRAINT uq_oauth_supported_client_key_algorithm_code
        UNIQUE (code)
);