package com.elevate.api.aggregator.client.model.person;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePersonRequest {

    private String name;

}
