package com.digitalparadise.model.repositories;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;
import com.digitalparadise.controller.exceptions.repository.UserRepositoryException;
import com.digitalparadise.model.fillers.DataFiller;
import com.digitalparadise.model.fillers.StaticUserFiller;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.digitalparadise.model.entities.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@NoArgsConstructor
public class UserRepository implements Repository<User, UUID> { // todo write methods getBy...

    private List<User> people;

    @Override
    public User getResourceByUUID(UUID uuid) throws RepositoryException {
        for (User u :
                people) {
            if (u.getUuid().equals(uuid)) {
                return u;
            }

        }
        throw new UserRepositoryException(UserRepositoryException.NOT_EXIST_USER);
    }

    @Override
    public void update(UUID id, User element) throws RepositoryException {
        synchronized (this.people) {
            boolean exists = false;
//            for (User user : people) { // todo ?????????????
//                if (user.isEmailEquals(element.getEmail()) && !user.equals(element))
//                    throw new UserRepositoryException("This email is already taken");
//            }
            for (int i = 0; i < people.size(); i++) {
                if (id.equals(people.get(i).getUuid())) {
                    this.people.set(i, element);
                    exists = true;
                } else if ((i == (people.size() - 1)) && !exists) {
                    throw new UserRepositoryException(UserRepositoryException.EXIST_USER);
                }
            }
        }
    }

    @Override
    public void add(User element) throws RepositoryException {
        synchronized (this.people) {
            element.setUuid(UUID.randomUUID());
            for (User user : people) {
                if (user.equals(element))
                    throw new UserRepositoryException(UserRepositoryException.EXIST_USER);
                if (user.isEmailEquals(element.getEmail()))
                    throw new UserRepositoryException("This email is already taken");
            }
            this.people.add(element);
        }
    }

    @Override
    public void remove(User element) throws RepositoryException {
        synchronized (this.people) {
            for (User user : people) {
                if (user.equals(element)) {
                    this.people.remove(element);
                    return;
                }
            }
            throw new UserRepositoryException(UserRepositoryException.NOT_EXIST_USER);
        }
    }

    @Override
    public List<User> getAll() {
        synchronized (this.people) {
            return new CopyOnWriteArrayList<>(people);
        }
    }


    @PostConstruct
    public void initPeople() {
        DataFiller dataFiller = new StaticUserFiller();
        this.people = dataFiller.Fill();
    }

}
