package com.example.richard.dev01;

/**
 * Created by Richard on 29/04/2018.
 */

public class Tuple<X, Y> {
    public final X x;
    public final X y;

    public Tuple(X x, X y) {
        this.x = x;
        this.y = y;
    }
    public X get(int z) {
        if (z == 0){
            return(x);
        }
        else{
            return(y);
        }
    }
}