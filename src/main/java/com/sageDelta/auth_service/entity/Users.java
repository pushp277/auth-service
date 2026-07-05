package com.sageDelta.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="users")
@Data
public class Users {
    @Id
    @Column(name="entity_id")
    private long entityId;

    private String username;

    private String password;

    private String salt;

    @Column(name="data_of_birth")
    private Date dataOfBirth;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @OneToOne
    @JoinColumn(name="contact_details_id", unique = true)
    private ContactDetails contactDetails;

}
