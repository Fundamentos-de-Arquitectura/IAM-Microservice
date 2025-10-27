package com.go5u.foodflowplatform.foodflowplatform.iam.interfaces.rest.resources;

//TODO: In the future I will add the token here
public record AuthenticatedAccountResource(Long id, String role, String token) {
}
