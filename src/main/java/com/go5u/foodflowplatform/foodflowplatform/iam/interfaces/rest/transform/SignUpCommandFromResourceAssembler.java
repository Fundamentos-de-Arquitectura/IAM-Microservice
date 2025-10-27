package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform;

import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignUpCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand from(SignUpResource r) {
        return new SignUpCommand(r.username(), r.password(), r.role());
    }
}
