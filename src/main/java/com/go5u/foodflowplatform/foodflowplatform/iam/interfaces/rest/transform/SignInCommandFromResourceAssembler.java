package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform;


import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignInCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}
