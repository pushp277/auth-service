package org.sageDelta.auth_service.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="users")
@Data
@Builder
public class UsersEntity {
    @Id
    @Column(name="entity_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generate_id")
    @SequenceGenerator(
            name = "generate_id",
            sequenceName = "generate_id",
            allocationSize = 1
    )
    private long entityId;

    private String username;

    private String password;

    private String salt;

    @Column(name="date_of_birth")
    private Date dataOfBirth;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @OneToOne
    @JoinColumn(name="contact_details_id", unique = true)
    private ContactDetailsEntity contactDetails;

}
