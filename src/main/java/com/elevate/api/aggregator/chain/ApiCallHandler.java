package com.elevate.api.aggregator.chain;

import com.elevate.api.aggregator.chain.user.context.BaseContext;

public abstract class ApiCallHandler<CONTEXT extends BaseContext> {

    private final ApiCallHandler<CONTEXT> next;

    public ApiCallHandler(ApiCallHandler<CONTEXT> next) {
        this.next = next;
    }

    public ApiCallHandler() {
        next = null;
    }

    public abstract void handle(CONTEXT context);

    public void runChain(CONTEXT context) {
        handle(context);
        if (next != null) {
            next.runChain(context);
        }
    }

}
