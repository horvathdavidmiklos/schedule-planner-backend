package com.scheduleplanner.common;

import java.util.function.Supplier;

public abstract class VoidBusinessLogic {
    //TODO hibakezelés megírása
    protected void execute(Runnable runnable){
            runnable.run();
    }
}
