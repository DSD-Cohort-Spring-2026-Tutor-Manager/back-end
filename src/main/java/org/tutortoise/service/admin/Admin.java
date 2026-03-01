package org.tutortoise.service.admin;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="admin")
@Data
public class Admin {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="admin_id")
    private Integer adminId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @Column(name="password_encrypted")
    private String passwordEncrypted;

    public Admin() {
    }

    public Admin(Integer adminId, String firstName, String lastName, String email, String phone, String passwordEncrypted) {
        this.adminId = adminId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passwordEncrypted = passwordEncrypted;
    }
}
