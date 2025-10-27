package com.go5u.foodflowplatform.foodflowplatform.iam.domain.service;

import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.aggregate.Account;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignInCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;


import java.util.Optional;

public interface AccountCommandService {
    Optional<Account> handle (SignUpCommand command);
    Optional<ImmutablePair<Account, String>> handle(SignInCommand command);
}
