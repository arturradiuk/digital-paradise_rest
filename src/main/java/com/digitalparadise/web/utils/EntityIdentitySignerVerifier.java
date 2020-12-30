package com.digitalparadise.web.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;
import com.digitalparadise.web.*;


public class EntityIdentitySignerVerifier {
//    rPIU2Cx
    private static final String SECRET = "8YuS04LvRqjpnGnet02bvcdoLIubmcXEt597Gj1rU6bW2MXHvQM90jNnascqF71jsmbp-co91xqE1hie-xKz68BwqAfukX8pGCpXtlzXxrXF_fz46kTcC1HsbvwDzLpxaoAoRKAtEt0onytN4wflPcNvzWjZvAYVcfhb6ydUofU";

    public static String calculateEntitySignature(SignableEntity entity){
        try {
            JWSSigner signer = new MACSigner(SECRET);
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm. HS256), new Payload(entity.getSignablePayload()));
            jwsObject.sign(signer);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return "ETag failure";
        }
    }


    public static boolean validateEntitySignature(String tagValue) {
        try{
            JWSObject jwsObject = JWSObject.parse(tagValue);
                JWSVerifier verifier = new MACVerifier(SECRET);
            boolean temp = jwsObject.verify(verifier);
            return temp;

        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyEntityIntegrity(String tagValue, SignableEntity entity) {
        try{
            final String valueFromIfMatchHeader = JWSObject.parse(tagValue).getPayload().toString();
            final String valueFromEntitySignablePayload = entity.getSignablePayload();
            boolean fist=validateEntitySignature(tagValue); // todo remove it
            boolean second = valueFromEntitySignablePayload.equals( valueFromIfMatchHeader   ); // todo remove it
            return  fist && second;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }
}
