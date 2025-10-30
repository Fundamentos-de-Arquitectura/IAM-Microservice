package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform;

import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignUpCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand from(SignUpResource r) {
        return new SignUpCommand(r.username(), r.password(), r.role());
    }
    // Añadido para alinear el nombre del método con otros ensambladores y evitar el error en AuthenticationController
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return from(resource);
    }
}
