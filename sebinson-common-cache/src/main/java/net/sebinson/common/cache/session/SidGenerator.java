package net.sebinson.common.cache.session;

import java.util.UUID;

public class SidGenerator {
    public static String generateSid() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }
    
    public static void main(String args[]){
        System.out.print(SidGenerator.generateSid());
    } 
}
