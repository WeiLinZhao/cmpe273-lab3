package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.lang.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        char[] alphabet = "abcdefghij".toCharArray();
        ArrayList<CacheServiceInterface> array = new ArrayList<CacheServiceInterface>();
        array.add(new DistributedCacheService(
                "http://localhost:3000"));
        array.add(new DistributedCacheService(
                "http://localhost:3001"));
        array.add(new DistributedCacheService(
                "http://localhost:3002"));


        ConsistentHash consistenthash = new ConsistentHash(5, array);
        System.out.println("PUT the sequence of key-value pairs into the three cache servers...");
        CacheServiceInterface m;
        Object server;
        for (int i = 1; i<11;i++){
            server = consistenthash.get(i);
            m = (CacheServiceInterface)server;
            m.put(i,String.valueOf(alphabet[i-1]));
            System.out.println("put("+ String.valueOf(i)+" => " + String.valueOf(alphabet[i-1]) + ")");
        }

        System.out.println("Retrieve all keys...");
        for (int i = 1; i<11;i++){
            server = consistenthash.get(i);
            m = (CacheServiceInterface)server;
            System.out.println("get(" + String.valueOf(i)+ ") => " + m.get(i));
        }


        System.out.println("Existing Cache Client...");
    }

}
