package com.example.IAM_SERVER.platformAdmin.domain.model;


    public final class PasswordHash {

        private final String value;

        private PasswordHash(String value) {
            this.value = value;
        }

        public static PasswordHash from(String value) {

            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(
                        "Password hash cannot be empty"
                );
            }

            return new PasswordHash(value);
        }

        public String value() {
            return value;
        }


    }

