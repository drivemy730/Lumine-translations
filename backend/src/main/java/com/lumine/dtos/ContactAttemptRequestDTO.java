package com.lumine.dtos;

import com.lumine.helpers.ContactMethod;

import java.util.UUID;

public record ContactAttemptRequestDTO(
        Long clientId,
        UUID fileId,
        ContactMethod contactMethod)
{}
