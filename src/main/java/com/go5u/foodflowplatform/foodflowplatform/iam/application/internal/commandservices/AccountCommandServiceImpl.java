package com.go5u.foodflowplatform.foodflowplatform.iam.application.internal.commandservices;


import com.go5u.foodflowplatform.foodflowplatform.iam.application.internal.outboundservices.hashing.HashingService;
import com.go5u.foodflowplatform.foodflowplatform.iam.application.internal.outboundservices.tokens.TokenService;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.aggregate.Account;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignInCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.commands.SignUpCommand;
import com.go5u.foodflowplatform.foodflowplatform.iam.domain.service.AccountCommandService;
import com.go5u.foodflowplatform.foodflowplatform.iam.infrastructure.config.SubscriptionClient;
import com.go5u.foodflowplatform.foodflowplatform.iam.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {
    private static final Logger logger = LoggerFactory.getLogger(AccountCommandServiceImpl.class);
    
    private final AccountRepository accountRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final SubscriptionClient subscriptionClient;

    public AccountCommandServiceImpl(AccountRepository accountRepository, 
                                     HashingService hashingService, 
                                     TokenService tokenService,
                                     SubscriptionClient subscriptionClient) {
        this.accountRepository = accountRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.subscriptionClient = subscriptionClient;
    }

    @Override
    @Transactional
    public Optional<Account> handle(SignUpCommand command) {
        if(accountRepository.existsByUserName(command.username()))
            throw new RuntimeException("User already exists");

        var account = new Account(command, hashingService.encode(command.password()));

        try{
            // Guardar el usuario primero para obtener su ID
            var accountCreated = accountRepository.save(account);
            
            // Crear suscripción automáticamente en el microservicio Subscription
            logger.info("Creating subscription for new user: {}", accountCreated.getId());
            var subscriptionResponse = subscriptionClient.createDefaultSubscription(accountCreated.getId());
            
            if (subscriptionResponse.isPresent()) {
                // Asociar el ID de la suscripción al usuario
                accountCreated.setSubscriptionId(subscriptionResponse.get().id());
                accountCreated = accountRepository.save(accountCreated);
                logger.info("Subscription {} created and associated with user {}", 
                           subscriptionResponse.get().id(), accountCreated.getId());
            } else {
                logger.warn("Failed to create subscription for user {}, but user was created", accountCreated.getId());
            }
            
            return Optional.of(accountCreated);
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving user: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public Optional<ImmutablePair<Account, String>> handle(SignInCommand command) {
        var accountExists = accountRepository.existsByUserName(command.username());
        if(accountExists) {
            var account = accountRepository.findByUserName(command.username());
            var token = tokenService.generateToken(account.get().getUserName());
            return Optional.of(ImmutablePair.of(account.get(), token));
        }
        throw new RuntimeException("User not found");
    }
}
