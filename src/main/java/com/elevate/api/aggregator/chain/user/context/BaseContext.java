package com.elevate.api.aggregator.chain.user.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class BaseContext<REQUEST, RESPONSE> {

    private final REQUEST request;
    private RESPONSE response;

    public abstract RESPONSE finalizeChain();

}
