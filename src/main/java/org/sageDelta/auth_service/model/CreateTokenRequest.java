package org.sageDelta.auth_service.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateTokenRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-23T01:11:39.858282626Z[Etc/UTC]", comments = "Generator version: 7.25.0-SNAPSHOT")
public class CreateTokenRequest {

  private String authorizationCode;

  private String clientId;

  private String clientSecret;

  public CreateTokenRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateTokenRequest(String authorizationCode, String clientId, String clientSecret) {
    this.authorizationCode = authorizationCode;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public CreateTokenRequest authorizationCode(String authorizationCode) {
    this.authorizationCode = authorizationCode;
    return this;
  }

  /**
   * Get authorizationCode
   * @return authorizationCode
   */
  @NotNull 
  @Schema(name = "authorization-code", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("authorization-code")
  public String getAuthorizationCode() {
    return authorizationCode;
  }

  @JsonProperty("authorization-code")
  public void setAuthorizationCode(String authorizationCode) {
    this.authorizationCode = authorizationCode;
  }

  public CreateTokenRequest clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Get clientId
   * @return clientId
   */
  @NotNull 
  @Schema(name = "client-id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("client-id")
  public String getClientId() {
    return clientId;
  }

  @JsonProperty("client-id")
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public CreateTokenRequest clientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
    return this;
  }

  /**
   * Get clientSecret
   * @return clientSecret
   */
  @NotNull 
  @Schema(name = "client-secret", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("client-secret")
  public String getClientSecret() {
    return clientSecret;
  }

  @JsonProperty("client-secret")
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTokenRequest createTokenRequest = (CreateTokenRequest) o;
    return Objects.equals(this.authorizationCode, createTokenRequest.authorizationCode) &&
        Objects.equals(this.clientId, createTokenRequest.clientId) &&
        Objects.equals(this.clientSecret, createTokenRequest.clientSecret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorizationCode, clientId, clientSecret);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTokenRequest {\n");
    sb.append("    authorizationCode: ").append(toIndentedString(authorizationCode)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    clientSecret: ").append(toIndentedString(clientSecret)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }
}

