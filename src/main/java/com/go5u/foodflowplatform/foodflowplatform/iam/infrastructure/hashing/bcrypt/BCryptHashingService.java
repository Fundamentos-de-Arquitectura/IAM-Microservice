package com.go5u.foodflowplatform.foodflowplatform.iam.infrastructure.hashing.bcrypt;


import com.go5u.foodflowplatform.foodflowplatform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
