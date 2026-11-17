package com.example.IAM_SERVER.Keys.domain.model;



import java.time.Instant;

public record KeystoreInitializationStatus(
        Short id,
        Instant initializedAt
) {
}