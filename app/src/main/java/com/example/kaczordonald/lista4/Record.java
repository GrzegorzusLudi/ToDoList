package com.example.kaczordonald.lista4;

/**
 * Created by KaczorDonald on 2017-04-19.
 */

public class Record {
    public String shortv;
    public String longv;
    Record(String s){
        if(s.length()>30)
            shortv = s.substring(0,27)+"...";
        else
            shortv = s;
        shortv=shortv.replaceAll(System.getProperty("line.separator"), " ");
        longv = s;
    }
}
