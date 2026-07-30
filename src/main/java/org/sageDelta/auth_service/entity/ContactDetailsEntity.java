package org.sageDelta.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contact_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailsEntity {

    @Id
    @Column(name="entity_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generate_id")
    @SequenceGenerator(
            name = "generate_id",
            sequenceName = "generate_id",
            allocationSize = 1
    )
    private long entityId;

    @Column(name="phone_number")
    private long phoneNumber;

    private String email;

    private String prefecture;
    private String city;
    private long zipcode;
    private String level1;
    private String level2;

    @OneToOne(mappedBy = "contactDetails")
    private UsersEntity user;
}
