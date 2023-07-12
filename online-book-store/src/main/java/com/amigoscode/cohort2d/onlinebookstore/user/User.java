package com.amigoscode.cohort2d.onlinebookstore.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_obs")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(
            name = "user_obs_id_seq",
            sequenceName = "user_obs_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_obs_id_seq"
    )
    private Long id;

    @Column(name = "first_name",length = 50)
    @NotNull
    private String firstName;

    @Column(name = "last_name", length = 50)
    @NotNull
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 250)
    @Email
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

}