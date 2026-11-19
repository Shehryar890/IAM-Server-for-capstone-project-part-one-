

CREATE TABLE iam_permission
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code        VARCHAR(100)  NOT NULL,
    description VARCHAR(255)  NOT NULL,
       created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT uk_iam_permission_code UNIQUE (code)
);



INSERT INTO iam_permission (code, description)
VALUES
    ('IDENTITY_CREATE', 'Allows creation of new identities'),
    ('IDENTITY_READ', 'Allows reading identity information'),
    ('IDENTITY_UPDATE', 'Allows updating existing identities'),
    ('IDENTITY_READ_BY_TOKEN_ID', 'Allows retrieving identity information associated with a token ID');
