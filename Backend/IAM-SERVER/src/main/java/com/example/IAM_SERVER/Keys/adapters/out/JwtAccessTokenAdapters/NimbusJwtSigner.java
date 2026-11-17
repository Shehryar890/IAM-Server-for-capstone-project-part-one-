package com.example.IAM_SERVER.Keys.adapters.out.JwtAccessTokenAdapters;
import com.example.IAM_SERVER.Keys.domain.ports.out.JwtSigner;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;
import com.example.IAM_SERVER.Keys.domain.model.ActiveKey;
import org.springframework.stereotype.Component;

@Component
public class NimbusJwtSigner implements JwtSigner<SignedJWT> {

    private final ActiveKey activeKey;

    public NimbusJwtSigner(ActiveKey activeKey) {
        this.activeKey = activeKey;
    }

    @Override
    public String sign(SignedJWT token) {

        if (activeKey == null) {
            throw new IllegalStateException(
                    "Active JWT key is not available"
            );
        }

        if (activeKey.privatekey() == null) {
            throw new IllegalStateException(
                    "Active JWT private key is not available"
            );
        }

        if (token == null) {
            throw new IllegalArgumentException(
                    "JWT token cannot be null"
            );
        }

        try {

            RSASSASigner signer =
                    new RSASSASigner(activeKey.privatekey());

            token.sign(signer);

            return token.serialize();

        } catch (JOSEException exception) {

            throw new IllegalStateException(
                    "Failed to sign JWT using the active RSA private key",
                    exception
            );
        }
    }
}
