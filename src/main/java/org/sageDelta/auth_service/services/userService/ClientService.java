package org.sageDelta.auth_service.services.userService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    public boolean isSessionValid(String session){
        return false;
    }

    public void authorize(){

    }
}
