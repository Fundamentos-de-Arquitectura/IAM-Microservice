package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.transform;


import com.go5u.foodflowplatform.foodflowplatform.iam.domain.model.aggregate.Account;
import com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources.AccountResource;

public class AccountResourceFromEntityAssembler {
    public static AccountResource toResourceFromEntity(Account entity) {
        return new AccountResource(
                entity.getId(), 
                entity.getUserName(), 
                entity.getRoleInString(),
                entity.getSubscriptionId()
        );
    }
}
