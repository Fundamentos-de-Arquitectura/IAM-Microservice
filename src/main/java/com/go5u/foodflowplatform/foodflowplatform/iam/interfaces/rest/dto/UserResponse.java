package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.dto;

/**
 * DTO compartido para respuestas de usuario
 */
public record UserResponse(
        Long id,
        String username,
        String role,
        Long subscriptionId
) {}

