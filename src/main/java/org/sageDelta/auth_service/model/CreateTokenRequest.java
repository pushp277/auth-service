package org.sageDelta.auth_service.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-28T13:27:11.515124430Z[Etc/UTC]", comments = "Generator version: 7.25.0-SNAPSHOT")
public class CreateTokenRequest {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String accessCode;

  public CreateTokenRequest accessCode(@Nullable String accessCode) {
    this.accessCode = accessCode;
    return this;
  }

  /**
   * Get accessCode
   * @return accessCode
   */
  
  @Schema(name = "accessCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("accessCode")
  public @Nullable String getAccessCode() {
    return accessCode;
  }

  @JsonProperty("accessCode")
  public void setAccessCode(@Nullable String accessCode) {
    this.accessCode = accessCode;
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
    return Objects.equals(this.accessCode, createTokenRequest.accessCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTokenRequest {\n");
    sb.append("    accessCode: ").append(toIndentedString(accessCode)).append("\n");
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

