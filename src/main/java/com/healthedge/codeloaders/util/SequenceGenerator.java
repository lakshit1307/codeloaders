package com.healthedge.codeloaders.util;

import java.util.Random;

public class SequenceGenerator {

    //TODO: Its a hack, need to use sequence for all tables
    public static int getNextSeq (String tableName) {
        Random random = new Random();
        return random.nextInt(1000);
    }
}
