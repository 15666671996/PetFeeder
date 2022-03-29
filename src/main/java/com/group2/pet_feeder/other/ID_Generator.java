package com.group2.pet_feeder.other;

import java.util.Random;
import java.util.UUID;

public class ID_Generator {
    public static String getUUID() {
//        Random random = new Random();
//        int test = random.nextInt(3);
//        System.out.println(test);
//        if(test>=1){
//            return "123";
//        }else{
        return UUID.randomUUID().toString().replaceAll("-", "");
//        }

    }
}
