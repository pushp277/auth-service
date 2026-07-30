package org.sageDelta.auth_service.enums;

import java.util.List;

public enum ProviderEnum {
     GOOGLE,
    GITHUB,
    SAGE_DELTA;

    private ProviderEnum(){}

    public String getName(){
        return this.name();
    }

}
