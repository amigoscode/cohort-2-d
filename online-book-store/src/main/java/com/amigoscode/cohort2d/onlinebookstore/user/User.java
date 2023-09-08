package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.address.Address;
import com.amigoscode.cohort2d.onlinebookstore.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user_obs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

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
    @NotBlank
    private String firstName;

    @Column(name = "last_name", length = 50)
    @NotBlank
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 250)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    @NotBlank
    private String password;

    @Column(name = "phone_number", nullable = false, length = 25)
    private String phoneNumber;

    @Column(name = "role", nullable = false, length = 50)
    @NotBlank
    private String role;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "user_id")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    public User(@NotBlank String firstName, @NotBlank String lastName, String email,
                @NotBlank String password, String phoneNumber,
                @NotBlank String role, List<Address> addresses, Set<Order> orders) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.addresses = addresses;
        this.orders = orders;
    }

    public void addAddress(Address address) {
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        addresses.add(address);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}