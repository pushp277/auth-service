package org.sageDelta.auth_service.clients;

import java.util.List;
import java.util.Set;


public interface BasicClient {

    String clientId();
    List<String> redirectUrls();
    String clientSecret();

}
