package com.digitalparadise.controller.managers;

import com.digitalparadise.controller.exceptions.ManagerException;
import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.exceptions.repository.UserRepositoryException;
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
    private Repository<User, UUID> userRepository;// = new PersonRepository();

    @Override
    public void add(User user) throws RepositoryException {
        this.userRepository.add(user);
    }

    @Override
    public void remove(User user) throws RepositoryException {
        this.userRepository.remove(user);
    }

    public void remove(OrderManager orderManager, User user) throws ManagerException, RepositoryException {
        for (Order o : orderManager.getAll()) {
            if (o.getClient().getUuid().equals(user.getUuid()))
                throw new ManagerException("Cannot delete the user that is a part of the order");
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
        List<User> people = this.getAll().stream().filter(person -> person instanceof Client).collect(Collectors.toList());
        return people;
    }

    public List<User> getAllEmployees() { // todo move to the repository
        List<User> people = this.getAll().stream().filter(person -> person instanceof Employee).collect(Collectors.toList());
        return people;
    }

    public List<User> getAllAdministrators() {
        List<User> people = this.getAll().stream().filter(person -> person instanceof Administrator).collect(Collectors.toList());
        return people;
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
