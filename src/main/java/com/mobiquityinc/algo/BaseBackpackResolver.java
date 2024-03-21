package com.mobiquityinc.algo;

import java.util.List;

import com.mobiquityinc.model.Case;

public abstract class BaseBackpackResolver { 
    private boolean debug = true;


    public boolean isDebug(){
        return debug;
    }

    public void setDebug(boolean isDebug){
        this.debug = isDebug;
    }

    public abstract List<Integer> packBackpack(Case aCase);
}