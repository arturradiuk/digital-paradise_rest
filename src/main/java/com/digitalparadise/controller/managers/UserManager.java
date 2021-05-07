package com.digitalparadise.controller.managers;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.exceptions.repository.UserRepositoryException;
import com.digitalparadise.controller.exceptions.manager.GoodManagerException;
import com.digitalparadise.model.repositories.UserRepository;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.clients.Administrator;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.clients.Employee;
import com.digitalparadise.model.entities.Order;
import com.digitalparadise.model.entities.User;
import com.digitalparadise.model.repositories.Repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
@NoArgsConstructor
public class UserManager implements IManager<User, UUID> {

    @Inject
    private Repository<User, UUID> userRepository;

    @Override
    public void add(User user) throws RepositoryException {
        this.userRepository.add(user);
    }

    @Override
    public void remove(User user) throws RepositoryException {
        this.userRepository.remove(user);
    }

    public void remove(OrderManager orderManager, User user) throws GoodManagerException, RepositoryException {
        for (Order o : orderManager.getAll()) {
            if (o.getClient().getUuid().equals(user.getUuid()))
                throw new GoodManagerException(GoodManagerException.CANNOT_DELETE);
        }
        this.userRepository.remove(user);

    }

    @Override
    public void update(UUID uuid, User user) throws RepositoryException {
        this.userRepository.update(uuid, user);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.getAll();
    }


    public User getUserByUUID(UUID uuid) throws RepositoryException {
        return this.userRepository.getResourceByUUID(uuid);
    }


    public List<User> getAllClients() {
        return this.getAll().stream().filter(person -> person instanceof Client).collect(Collectors.toList());
    }

    public List<User> getAllEmployees() {
        return this.getAll().stream().filter(person -> person instanceof Employee).collect(Collectors.toList());
    }

    public List<User> getAllAdministrators() {
        return this.getAll().stream().filter(person -> person instanceof Administrator).collect(Collectors.toList());
    }

    public User findByEmail(String email) throws UserRepositoryException {
        return ((UserRepository) this.userRepository).getResourceByEmail(email);
    }

    public boolean isClientActive(String email) {
        for (User u : this.userRepository.getAll()) {
            if (u instanceof Client
                    && !((Client) u).getActive()
                    && ((Client) u).getEmail().equals(email))
                return false;
        }
        return true;
    }
}
