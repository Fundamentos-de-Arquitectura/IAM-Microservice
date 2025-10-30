package com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.aggregate;

import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignUpCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.valueobjects.ProfileId;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.valueobjects.Roles;
import com.go5u.foodflowplatform.foodflowplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Account extends AuditableAbstractAggregateRoot<Account> {
    @NotBlank
    @Getter
    @Size(max=20)
    @Column(unique = true)
    private String userName;

    @NotBlank
    @Getter
    private String password;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Roles role;

    @Embedded
    private ProfileId profileId;

    public Account(SignUpCommand command) {
        System.out.println("role from command %s".formatted(command.role()));
        System.out.println("role from enum %s".formatted(Roles.ROLE_PROFESSIONAL.toString()));

        if(Objects.equals(command.role(), Roles.ROLE_PROFESSIONAL.toString())
                || Objects.equals(command.role(), Roles.ROLE_PATIENT.toString())) {
            this.userName = command.username();
            this.password = command.password();
            this.role = Roles.valueOf(command.role());
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
    }

    public Account(SignUpCommand command, String hashedPassword) {
        System.out.println("role from command %s".formatted(command.role()));
        System.out.println("role from enum %s".formatted(Roles.ROLE_PROFESSIONAL.toString()));

        if(Objects.equals(command.role(), Roles.ROLE_PROFESSIONAL.toString())
                || Objects.equals(command.role(), Roles.ROLE_PATIENT.toString())) {
            this.userName = command.username();
            this.password = hashedPassword;
            this.role = Roles.valueOf(command.role());
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
    }

    public String getRoleInString() {
        return this.role.toString();
    }

    public Set<String> getAllRoles() {
        return Set.of(Roles.ROLE_PATIENT.toString(), Roles.ROLE_PROFESSIONAL.toString());
    }

    // Getters explícitos para evitar dependencia de Lombok
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public Roles getRole() { return role; }
}
