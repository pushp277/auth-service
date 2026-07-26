package org.sageDelta.auth_service.services.createUserService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.entity.ContactDetailsEntity;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.exceptions.create.UserAlreadyExists;
import org.sageDelta.auth_service.model.Address;
import org.sageDelta.auth_service.model.ContactDetails;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.repositories.ContactDetailsRepository;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

   private final ContactDetailsRepository contactDetailsRepository;
   private final UserRepository userRepository;

    public void createUser(User user){
        Optional<ContactDetails> contactDetailsOptional = Optional.ofNullable(user.getContactDetails());
        ContactDetailsEntity contactDetailsEntity;

        String username = user.getUsername();
        if(userRepository.existsByUsername(username)){
            log.error("username: {} already exists", username);
            throw new UserAlreadyExists("User Already exists");
        }

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();
        String salt = Utils.generateSalt();
        String hashedPassword = Utils.getHash(password, salt);
        UsersEntity usersEntity = UsersEntity.builder()
                .username(username)
                .password(hashedPassword)
                .salt(salt)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        if(contactDetailsOptional.isPresent()){
            ContactDetails contactDetails = contactDetailsOptional.get();
            log.debug("Contact details: {}", contactDetails);
            Optional<Address> addressOptional = Optional.ofNullable(contactDetails.getAddress());

            String email = contactDetails.getEmail();
            if(contactDetailsRepository.existsByEmail(email)){
                log.error("user email: {} already exists", email);
                throw new UserAlreadyExists("User Already exists");
            }

            Long phoneNumer = Long.valueOf(Optional.ofNullable(contactDetails.getPhoneNumber()).orElse("0"));


           contactDetailsEntity = ContactDetailsEntity.builder()
                   .email(email)
                   .phoneNumber(phoneNumer)
                   .build();

           if(addressOptional.isPresent()){
               Address address = addressOptional.get();
               String line1 = address.getLine1();
               String line2 = address.getLine2();
               String city = address.getCity();
               String prefecture = address.getPrefecture();
               long postalCode = Optional.ofNullable(address.getPostalCode()).orElse(0);

               contactDetailsEntity = ContactDetailsEntity.builder()
                       .phoneNumber(phoneNumer)
                       .email(email)
                       .level1(line1)
                       .level2(line2)
                       .zipcode(postalCode)
                       .prefecture(prefecture)
                       .city(city)
                       .build();
           }

            contactDetailsRepository.save(contactDetailsEntity);

           usersEntity = UsersEntity.builder()
                    .username(username)
                    .password(hashedPassword)
                    .salt(salt)
                    .firstName(firstName)
                    .lastName(lastName)
                    .contactDetails(contactDetailsEntity)
                    .build();
        }



        userRepository.save(usersEntity);

        log.info("new User created successfully");
    }

}
