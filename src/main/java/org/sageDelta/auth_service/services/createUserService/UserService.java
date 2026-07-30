package org.sageDelta.auth_service.services.createUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sageDelta.auth_service.entity.ContactDetailsEntity;
import org.sageDelta.auth_service.entity.UsersEntity;
import org.sageDelta.auth_service.enums.ProviderEnum;
import org.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import org.sageDelta.auth_service.exceptions.authorize.UserPasswordIsWrong;
import org.sageDelta.auth_service.exceptions.create.UserAlreadyExistsException;
import org.sageDelta.auth_service.model.Address;
import org.sageDelta.auth_service.model.ContactDetails;
import org.sageDelta.auth_service.model.User;
import org.sageDelta.auth_service.repositories.ContactDetailsRepository;
import org.sageDelta.auth_service.repositories.UserRepository;
import org.sageDelta.auth_service.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        log.info("user details: {}", user);
        String username = user.getUsername();
        if(userRepository.existsByUsername(username)){
            log.error("username: {} already exists", username);
            throw new UserAlreadyExistsException("User Already exists");
        }

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();
        String salt = Utils.generateSalt();
        String hashedPassword = Utils.getHash(password, salt);
        Optional<LocalDate> dateOfBirthOptional = Optional.ofNullable(user.getDateOfBirth());
        Date dateOfBirth = new Date();
        if(dateOfBirthOptional.isPresent()){
            LocalDate localDate = dateOfBirthOptional.get();
            dateOfBirth = Date.from(localDate.atStartOfDay(ZoneId.of("UTC")).toInstant());
        }
        UsersEntity usersEntity = UsersEntity.builder()
                .username(username)
                .password(hashedPassword)
                .salt(salt)
                .firstName(firstName)
                .lastName(lastName)
                .dataOfBirth(dateOfBirth)
                .provider(ProviderEnum.SAGE_DELTA.getName())
                .build();

        if(contactDetailsOptional.isPresent()){
            ContactDetails contactDetails = contactDetailsOptional.get();
            log.debug("Contact details: {}", contactDetails);
            Optional<Address> addressOptional = Optional.ofNullable(contactDetails.getAddress());

            String email = contactDetails.getEmail();
            if(contactDetailsRepository.existsByEmail(email)){
                log.error("user email: {} already exists", email);
                throw new UserAlreadyExistsException("User Already exists");
            }

            Long phoneNumber = Long.valueOf(Optional.ofNullable(contactDetails.getPhoneNumber()).orElse("0"));


           contactDetailsEntity = ContactDetailsEntity.builder()
                   .email(email)
                   .phoneNumber(phoneNumber)
                   .build();

           if(addressOptional.isPresent()){
               Address address = addressOptional.get();
               String line1 = address.getLine1();
               String line2 = address.getLine2();
               String city = address.getCity();
               String prefecture = address.getPrefecture();
               long postalCode = Optional.ofNullable(address.getPostalCode()).orElse(0);

               contactDetailsEntity = ContactDetailsEntity.builder()
                       .phoneNumber(phoneNumber)
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
                   .provider(ProviderEnum.SAGE_DELTA.getName())
                    .build();
        }



        userRepository.save(usersEntity);

        log.info("new User created successfully");
    }


    public String verifyUser(String username, String password){

        log.info("[Login][verifyUser] method call for user: {}", username);
        Optional<UsersEntity> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if(userOptional.isEmpty()) {
            log.error("User doesn't exist");
            throw new UserDoesNotExist("User doesn't exist");
        }

        UsersEntity user = userOptional.get();
        String passwordHash = user.getPassword();
        String salt = user.getSalt();

        if(passwordHash == null || salt == null){
            log.error("User doesn't exist");
            throw new UserPasswordIsWrong("User password is wrong");
        }

        if(Utils.getHash(password, salt).equals(passwordHash)){
            log.info("user: {} logged in", username);
            return Utils.getAuthCode();
        }

        log.error("Entered password is wrong");

        throw new UserPasswordIsWrong("User password is wrong");
    }



}
