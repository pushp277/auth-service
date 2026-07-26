package org.sageDelta.auth_service.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.sageDelta.auth_service.model.Address;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ContactDetails
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-26T12:46:18.956321047Z[Etc/UTC]", comments = "Generator version: 7.25.0-SNAPSHOT")
public class ContactDetails {

  private String email;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable String phoneNumber;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private @Nullable Address address;

  public ContactDetails() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ContactDetails(String email) {
    this.email = email;
  }

  public ContactDetails email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @NotNull 
  @Schema(name = "email", example = "pushp.raj@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonProperty("email")
  public void setEmail(String email) {
    this.email = email;
  }

  public ContactDetails phoneNumber(@Nullable String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   */
  @Pattern(regexp = "\\d{10,11}") 
  @Schema(name = "phoneNumber", example = "08043325354", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public @Nullable String getPhoneNumber() {
    return phoneNumber;
  }

  @JsonProperty("phoneNumber")
  public void setPhoneNumber(@Nullable String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public ContactDetails address(@Nullable Address address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   */
  @Valid 
  @Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public @Nullable Address getAddress() {
    return address;
  }

  @JsonProperty("address")
  public void setAddress(@Nullable Address address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactDetails contactDetails = (ContactDetails) o;
    return Objects.equals(this.email, contactDetails.email) &&
        Objects.equals(this.phoneNumber, contactDetails.phoneNumber) &&
        Objects.equals(this.address, contactDetails.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, phoneNumber, address);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContactDetails {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
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

