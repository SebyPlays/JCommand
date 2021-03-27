package com.github.sebyplays.jcommand.utils;

public class PreConditions {

    public static boolean notNull(Object o){
        return (o.equals("") || o.equals(null) || o == null || o == "" || o.equals(" ") || o == " ");
    }

}
