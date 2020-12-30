package com.digitalparadise.web.security;


import com.digitalparadise.controller.exceptions.repository.UserRepositoryException;
import com.digitalparadise.model.clients.Administrator;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.clients.Employee;
import com.digitalparadise.model.entities.User;
import com.digitalparadise.model.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {
    @Inject
//    private AccountRepo accountRepo;
    private UserRepository userRepository;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        try {

            if (credential instanceof UsernamePasswordCredential) {
                UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;

//            Account account = accountRepo.findByLoginPasswordActive(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
                User user = userRepository.findByLoginPasswordActive(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());

                String group = null;
                if (user instanceof Client)
                    group = "CLIENT";//Client.class.getName();
                if (user instanceof Administrator)
                    group = "ADMIN";//Administrator.class.getName();
                if (user instanceof Employee)
                    group = "EMPLOYEE";//Employee.class.getName();


                return (null != user ? new CredentialValidationResult(user.getEmail(), new HashSet<>(Arrays.asList(new String[]{group}))) : CredentialValidationResult.INVALID_RESULT);

            }
        } catch (UserRepositoryException e) {
            e.printStackTrace();
            return CredentialValidationResult.NOT_VALIDATED_RESULT;

        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }
}
