package com.go5u.foodflowplatform.foodflowplatform.iam.domain.service;

import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.aggregate.Account;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.queries.GetAccountByIdQuery;

import java.util.Optional;

public interface AccountQueryService {
    Optional<Account> handle(GetAccountByIdQuery query);
}
