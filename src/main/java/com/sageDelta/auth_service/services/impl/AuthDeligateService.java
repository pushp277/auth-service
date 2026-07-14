package com.sageDelta.auth_service.services.impl;

import com.sageDelta.auth_service.entity.ContactDetailsEntity;
import com.sageDelta.auth_service.entity.UsersEntity;
import com.sageDelta.auth_service.exceptions.authorize.UserDoesNotExist;
import com.sageDelta.auth_service.exceptions.authorize.UserPasswordIsWrong;
import com.sageDelta.auth_service.models.createToken.CreateTokenRequest;
import com.sageDelta.auth_service.models.createToken.CreateTokenResponse;
import com.sageDelta.auth_service.models.createUser.CreateUserRequest;
import com.sageDelta.auth_service.models.createUser.CreateUserResponse;
import com.sageDelta.auth_service.models.loginUser.LoginUserRequest;
import com.sageDelta.auth_service.models.logoutUser.LogoutUserResponse;
import com.sageDelta.auth_service.models.refresh.RefreshResponse;
import com.sageDelta.auth_service.repositories.ContactDetailsRepository;
import com.sageDelta.auth_service.repositories.UserRepository;
import com.sageDelta.auth_service.services.AuthDeligate;
import com.sageDelta.auth_service.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthDeligateService implements AuthDeligate {

    private final ContactDetailsRepository contactDetailsRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CreateUserResponse create(CreateUserRequest createUserRequest) {

        CreateUserRequest.ContactDetails contactDetails = createUserRequest.contactDetails();
        CreateUserRequest.ContactDetails.Address address = null;

        if(contactDetails != null){
            address = contactDetails.address();
        }

        String salt = Utils.generateSalt();
        String passwordHash = Utils.getHash(createUserRequest.password(), salt);

        ContactDetailsEntity contactDetailsEntity = ContactDetailsEntity.builder()
                .phoneNumber(Long.parseLong(contactDetails !=null
                        ?  contactDetails.phoneNumber() : ""))
                .email(contactDetails.email())
                .city(address!=null ?  address.city() : "")
                .prefecture(address!=null ? address.prefecture() : "")
                .level1(address!=null ?  address.line1() : "")
                .level2(address != null ? address.line2(): "")
                .zipcode(address != null ? address.postalCode() : 0L)
                .build();

        ContactDetailsEntity contactDetail = contactDetailsRepository.save(contactDetailsEntity);

        UsersEntity usersEntity = UsersEntity.builder()
                .username(createUserRequest.username())
                .firstName(createUserRequest.firstName())
                .lastName(createUserRequest.lastName())
                .dataOfBirth(createUserRequest.dateOfBirth())
                .salt(salt)
                .password(passwordHash)
                .contactDetails(contactDetail)
                .build();

        userRepository.save(usersEntity);
        return new CreateUserResponse();
    }

    @Override
    @Transactional
    public String authorize(LoginUserRequest loginUserRequest){
        boolean doesUserExist = userRepository.existsByUsername(loginUserRequest.name());

        if(!doesUserExist)
            throw new UserDoesNotExist("User doesn't exist");

        UsersEntity user = userRepository.findByUsername(loginUserRequest.name());
        String salt = user.getSalt();
        String passwordHash = user.getPassword();
        String passwordHashProvided = Utils.getHash(loginUserRequest.password(), salt);

        if(!passwordHash.equals(passwordHashProvided)){
            return "url";
        }

        throw new UserPasswordIsWrong("incorrect password");
    }

    @Override
    public CreateTokenResponse token(CreateTokenRequest createTokenRequest){
        return null;
    }

    @Override
    public LogoutUserResponse logout(){
        return null;
    }

    @Override
    public RefreshResponse refresh(){
        return null;
    }

    @Override
    public String login(LoginUserRequest loginUserRequest){
        return null;
    }
}
