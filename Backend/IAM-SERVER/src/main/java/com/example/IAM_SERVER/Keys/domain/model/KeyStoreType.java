package com.example.IAM_SERVER.Keys.domain.model;





    public enum KeyStoreType {

        PKCS12("PKCS12"),
        JKS("JKS");

        private final String jcaName;

        KeyStoreType(String jcaName) {
            this.jcaName = jcaName;
        }

        public String getJcaName() {
            return jcaName;
        }
    }
