package com.mindspark.family_finances.controllers;

import lombok.Data;

@Data
public class JoinRequest {
    private String inviterEmail;
    private String requesterEmail;
}
