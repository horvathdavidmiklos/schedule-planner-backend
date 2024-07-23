package com.scheduleplanner.common;

import java.util.function.Supplier;

public abstract class ReturnedBusinessLogic<T> {

    //TODO hibakezelés megírása

    protected T execute(Supplier<T> supplier){
        return supplier.get();
    }
}
