package com.sageDelta.auth_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name="contact_details")
public class ContactDetails {

    @Id
    @Column(name="entity_id")
    private long entityId;

    @Column(name="phone_number")
    private long phoneNumber;

    private String email;

    private String prefecture;
    private String city;
    private String zipcode;
    private String level1;
    private String level2;

    @OneToOne(mappedBy = "contactDetails")
    private Users user;
}
