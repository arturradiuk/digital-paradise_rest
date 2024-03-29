package com.digitalparadise.web.security;


import com.digitalparadise.controller.managers.UserManager;
import com.digitalparadise.web.utils.JWTGeneratorVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.util.logging.Logger;

@RequestScoped
@Path("authenticate")
public class LoginService {
    @Inject
    private UserManager userManager;

    public LoginService() {

    }

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response authenticate(@NotNull LoginData loginData) {

        Credential credential = new UsernamePasswordCredential(loginData.getEmail(), new Password(loginData.getPassword()));
        CredentialValidationResult result = identityStoreHandler.validate(credential);

        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            return Response.accepted()
                    .type("application/jwt")
                    .entity(JWTGeneratorVerifier.generateJWTString(result))
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/token-update")
    public Response updateToken(@Context HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(CustomJWTAuthenticationMechanism.AUTHORIZATION_HEADER);
        String jwtSerializedToken = authorizationHeader.substring((CustomJWTAuthenticationMechanism.BEARER.length())).trim();
        String userEmail = null;
        try {
            userEmail = SignedJWT.parse(jwtSerializedToken).getJWTClaimsSet().getSubject();
            if (!this.userManager.isClientActive(userEmail))
                return Response.status(403).build();
        } catch (ParseException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
        return Response.accepted()
                .type("application/jwt")
                .entity(JWTGeneratorVerifier.generateJWTStringBasedOnJWTString(jwtSerializedToken))
                .build();
    }


    public static class LoginData {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
