package com.digitalparadise.model.fillers;

import com.digitalparadise.controller.exceptions.user.*;
import com.digitalparadise.model.clients.Administrator;
import com.digitalparadise.model.clients.Client;
import com.digitalparadise.model.clients.Employee;
import com.digitalparadise.model.entities.Address;
import com.digitalparadise.model.entities.User;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class StaticUserFiller implements DataFiller { // todo rename to people filler
    @Override
    public List<User> Fill() {
        List<User> people = new CopyOnWriteArrayList<>();
        Address address = null;
        try {
            address = new Address("High Street", "32");
        } catch (AddressException e) {
            e.printStackTrace();
        }
        try {

            User temp = new Client("Tola@gmail.com", "Tola", address,"672817289","123");
//            temp.setUuid(UUID.nameUUIDFromBytes(new String("1234567890_person").getBytes()));
            temp.setUuid(UUID.fromString("1d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);

           User temp2 = new Client("Lolek@gmail.com", "Lolek", address,"672817289","123");
//            temp2.setUuid(UUID.nameUUIDFromBytes(new String("2234567890_person").getBytes()));
            temp2.setUuid(UUID.fromString("2d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp2);

            temp = new Client("Bolek@gmail.com", "Bolek", address,"672817289","123");
//            temp.setUuid(UUID.nameUUIDFromBytes(new String("3234567890_person").getBytes()));
            temp.setUuid(UUID.fromString("3d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);

            temp = new Client("Zbigniew@gmail.com", "Zbigniew", address,"672817289","123");
//            temp.setUuid(UUID.nameUUIDFromBytes(new String("3234567890_person").getBytes()));
            temp.setUuid(UUID.fromString("106b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            temp.changeActivity();
            people.add(temp);
//
            
            temp = new Employee("TolaEmployee@gmail.com", "TolaEmployee", address, (float) 2800,"123");
            temp.setUuid(UUID.fromString("4d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);
            
            temp = new Employee("LolekEmployee@gmail.com", "LolekEmployee", address, (float) 3000,"123");
            temp.setUuid(UUID.fromString("5d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);
            
            temp = new Employee("BolekEmployee@gmail.com", "BolekEmployee", address, (float) 5000,"123");
            temp.setUuid(UUID.fromString("6d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);

//

            
            temp = new Administrator("TolaAdministrator@gmail.com", "TolaAdministrator", address,true,"123");
            temp.setUuid(UUID.fromString("7d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);
            
            temp = new Administrator("LolekAdministrator@gmail.com", "LolekAdministrator", address, false,"123");
            temp.setUuid(UUID.fromString("8d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);
            
            temp = new Administrator("BolekAdministrator@gmail.com", "BolekAdministrator", address, false,"123");
            temp.setUuid(UUID.fromString("9d6b6bd5-be82-3a41-87ac-5cd1b3b24756"));
            people.add(temp);


        } catch (UserException e) {
            e.printStackTrace();
        }
        return people;
    }
}
