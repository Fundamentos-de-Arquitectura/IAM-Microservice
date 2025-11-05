package com.go5u.foodflowplatform.foodflowplatform.iam.application.internal.queryservices;

import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.aggregate.Account;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.queries.GetAccountByIdQuery;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.service.AccountQueryService;
import com.go5u.foodflowplatform.foodflowplatform.iam.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {
    private final AccountRepository accountRepository;

    public AccountQueryServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> handle(GetAccountByIdQuery query) {
        return accountRepository.findById(query.id());
    }
}
