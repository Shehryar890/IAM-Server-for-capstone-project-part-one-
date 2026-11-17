package com.example.IAM_SERVER.Keys.adapters.out;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public final class GeneratePassword  {
    private final static int  arrayofbytelength = 32 ;
private final static SecureRandom randombytes = new SecureRandom();


private GeneratePassword(){

}
    public static byte[] generate() {
        byte[] arrayofbytes = new byte[arrayofbytelength];

        try{



            randombytes.nextBytes(arrayofbytes);
            return arrayofbytes;
        } catch (Exception e) {
    Arrays.fill(arrayofbytes, (byte) 0);
   throw e ;
}

        }


    }
