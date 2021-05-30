package com.elevate.api.aggregator.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {

    private Long id;
    private String billingId;
    private String name;

}
