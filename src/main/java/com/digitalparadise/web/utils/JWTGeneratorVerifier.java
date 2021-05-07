package com.digitalparadise.web.utils;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.text.ParseException;
import java.util.Date;

public class JWTGeneratorVerifier {

    private static final String SECRET = "8YuS04LvRqjpnGnet02bvcdoLIubmcXEt597Gj1rU6bW2MXHvQM90jNnascqF71jsmbp-co91xqE1hie-" +
            "xKz68BwqAfukX8pGCpXtlzXxrXF_fz46kTcC1HsbvwDzLpxaoAoRKAtEt0onytN4wflPcNvzWjZvAYVcfhb6ydUofU";
    private static final long JWT_TIMEOUT_MS = 1 * 60 * 1000;

    public static String generateJWTString(CredentialValidationResult credential) {
        try {
            JWSSigner signer = new MACSigner(SECRET);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(credential.getCallerPrincipal().getName())
                    .claim("auth", String.join(",", credential.getCallerGroups()))
                    .issuer("Digital paradise store")
                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT_MS))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return "JWT failure";
        }

    }

    public static String generateJWTStringBasedOnJWTString(String jwtSerializedToken) {
        SignedJWT oldJwtToken = null;
        try {
            JWSSigner signer = new MACSigner(SECRET);
            oldJwtToken = SignedJWT.parse(jwtSerializedToken);
            JWTClaimsSet oldJWTClaimsSet = oldJwtToken.getJWTClaimsSet();
            JWTClaimsSet newJWTClaimsSet = new JWTClaimsSet.Builder()
                    .subject(oldJWTClaimsSet.getSubject())
                    .claim("auth", oldJWTClaimsSet.getClaim("auth"))
                    .issuer(oldJWTClaimsSet.getIssuer())
                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT_MS))
                    .build();

            SignedJWT newSignedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), newJWTClaimsSet);
            newSignedJWT.sign(signer);
            return newSignedJWT.serialize();
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            return "JWT failure";
        }
    }

    public static boolean validateJWTSignature(String jwtSerializedToken) {
        try {
            JWSObject jwsObject = JWSObject.parse(jwtSerializedToken);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return jwsObject.verify(verifier);
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
