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
    @Getter
    private ProfileId profileId;

    public Account(SignUpCommand command) {
        // Ahora sólo se permite el rol ADMIN
        if (Objects.equals(command.role(), Roles.ADMIN.toString())) {
            this.userName = command.username();
            this.password = command.password();
            this.role = Roles.valueOf(command.role());
        } else {
            throw new IllegalArgumentException("Invalid role: only ADMIN is allowed");
        }
    }

    public Account(SignUpCommand command, String hashedPassword) {
        // Ahora sólo se permite el rol ADMIN
        if (Objects.equals(command.role(), Roles.ADMIN.toString())) {
            this.userName = command.username();
            this.password = hashedPassword;
            this.role = Roles.valueOf(command.role());
        } else {
            throw new IllegalArgumentException("Invalid role: only ADMIN is allowed");
        }
    }

    public String getRoleInString() {
        return this.role.toString();
    }

    public Set<String> getAllRoles() {
        // Devolver únicamente ADMIN
        return Set.of(Roles.ADMIN.toString());
    }
}
