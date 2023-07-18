package com.amigoscode.cohort2d.onlinebookstore.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_id_seq",
            sequenceName = "address_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_id_seq"
    )
    private Long id;

    @Column(name="street")
    @NotNull
    private String street;

    @Column(name="second_line")
    private String secondLine;

    @Column(name="city")
    @NotNull
    private String city;

    @Column(name="zip_code")
    @NotNull
    private String zipCode;

    @Column(name="country")
    @NotNull
    private String country;
}
