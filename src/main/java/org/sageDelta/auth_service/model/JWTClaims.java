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
 * JWTClaims
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-15T09:24:14.068569718Z[Etc/UTC]", comments = "Generator version: 7.25.0-SNAPSHOT")
public class JWTClaims {

  private @Nullable String jti;

  private @Nullable String aud;

  private @Nullable Long exp;

  private @Nullable Long iat;

  private @Nullable String role;

  private @Nullable String sub;

  private @Nullable String iss;

  public JWTClaims jti(@Nullable String jti) {
    this.jti = jti;
    return this;
  }

  /**
   * Get jti
   * @return jti
   */
  
  @Schema(name = "jti", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("jti")
  public @Nullable String getJti() {
    return jti;
  }

  @JsonProperty("jti")
  public void setJti(@Nullable String jti) {
    this.jti = jti;
  }

  public JWTClaims aud(@Nullable String aud) {
    this.aud = aud;
    return this;
  }

  /**
   * Get aud
   * @return aud
   */
  
  @Schema(name = "aud", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("aud")
  public @Nullable String getAud() {
    return aud;
  }

  @JsonProperty("aud")
  public void setAud(@Nullable String aud) {
    this.aud = aud;
  }

  public JWTClaims exp(@Nullable Long exp) {
    this.exp = exp;
    return this;
  }

  /**
   * Get exp
   * @return exp
   */
  
  @Schema(name = "exp", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("exp")
  public @Nullable Long getExp() {
    return exp;
  }

  @JsonProperty("exp")
  public void setExp(@Nullable Long exp) {
    this.exp = exp;
  }

  public JWTClaims iat(@Nullable Long iat) {
    this.iat = iat;
    return this;
  }

  /**
   * Get iat
   * @return iat
   */
  
  @Schema(name = "iat", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("iat")
  public @Nullable Long getIat() {
    return iat;
  }

  @JsonProperty("iat")
  public void setIat(@Nullable Long iat) {
    this.iat = iat;
  }

  public JWTClaims role(@Nullable String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   */
  
  @Schema(name = "role", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("role")
  public @Nullable String getRole() {
    return role;
  }

  @JsonProperty("role")
  public void setRole(@Nullable String role) {
    this.role = role;
  }

  public JWTClaims sub(@Nullable String sub) {
    this.sub = sub;
    return this;
  }

  /**
   * Get sub
   * @return sub
   */
  
  @Schema(name = "sub", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sub")
  public @Nullable String getSub() {
    return sub;
  }

  @JsonProperty("sub")
  public void setSub(@Nullable String sub) {
    this.sub = sub;
  }

  public JWTClaims iss(@Nullable String iss) {
    this.iss = iss;
    return this;
  }

  /**
   * Get iss
   * @return iss
   */
  
  @Schema(name = "iss", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("iss")
  public @Nullable String getIss() {
    return iss;
  }

  @JsonProperty("iss")
  public void setIss(@Nullable String iss) {
    this.iss = iss;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JWTClaims jwTClaims = (JWTClaims) o;
    return Objects.equals(this.jti, jwTClaims.jti) &&
        Objects.equals(this.aud, jwTClaims.aud) &&
        Objects.equals(this.exp, jwTClaims.exp) &&
        Objects.equals(this.iat, jwTClaims.iat) &&
        Objects.equals(this.role, jwTClaims.role) &&
        Objects.equals(this.sub, jwTClaims.sub) &&
        Objects.equals(this.iss, jwTClaims.iss);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jti, aud, exp, iat, role, sub, iss);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JWTClaims {\n");
    sb.append("    jti: ").append(toIndentedString(jti)).append("\n");
    sb.append("    aud: ").append(toIndentedString(aud)).append("\n");
    sb.append("    exp: ").append(toIndentedString(exp)).append("\n");
    sb.append("    iat: ").append(toIndentedString(iat)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    sub: ").append(toIndentedString(sub)).append("\n");
    sb.append("    iss: ").append(toIndentedString(iss)).append("\n");
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

