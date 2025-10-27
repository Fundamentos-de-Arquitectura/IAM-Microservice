package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest;


import com.go5u.foodflowplatform.foodflowplatform.iam.domain.service.AccountCommandService;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.AccountResource;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.AuthenticatedAccountResource;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.SignInResource;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.SignUpResource;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform.AccountResourceFromEntityAssembler;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform.AuthenticatedAccountResourceFromEntityAssembler;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final AccountCommandService accountCommandService;

    public AuthenticationController(AccountCommandService accountCommandService){
        this.accountCommandService = accountCommandService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedAccountResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedAccountResult = accountCommandService.handle(signInCommand);

        if(authenticatedAccountResult.isEmpty()) return ResponseEntity.notFound().build();
        var authenticatedAccount = authenticatedAccountResult.get();
        var authenticatedAccountResource =
                AuthenticatedAccountResourceFromEntityAssembler.toResourceFromEntity(authenticatedAccount.left, authenticatedAccount.right);
        return ResponseEntity.ok(authenticatedAccountResource);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AccountResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var account = accountCommandService.handle(signUpCommand);

        if(account.isEmpty()) return ResponseEntity.badRequest().build();
        var accountEntity = account.get();
        var accountResource = AccountResourceFromEntityAssembler.toResourceFromEntity(accountEntity);
        return new ResponseEntity<>(accountResource, HttpStatus.CREATED);
    }


}
