package com.cmput301w21t36.phenocount;

import java.io.Serializable;

public class Count extends Trial implements Serializable {

    private int count;

    /**
     * constructor for new Trial object
     *
     * @param owner This is an object of type User that stores the owner of the trial
     */
    public Count(User owner) {
        super(owner);
        count = 0;
    }

    void isCount(){this.count++;}



    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }
}
