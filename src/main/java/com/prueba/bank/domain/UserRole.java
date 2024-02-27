package com.prueba.bank.domain;

import com.prueba.bank.util.UserRoleKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleKey.class)
@Getter
@Setter
@NoArgsConstructor
public class UserRole {

    @Id
    @Column(nullable = false, length = 30)
    private String username;
    @Id
    @Column(nullable = false, length = 30)
    private String role;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

}
