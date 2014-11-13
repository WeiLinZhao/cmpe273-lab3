package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.lang.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        char[] alphabet = "abcdefghij".toCharArray();
        ArrayList<CacheServiceInterface> array = new ArrayList<CacheServiceInterface>();
        CacheServiceInterface serverA = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface serverB = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface serverC = new DistributedCacheService(
                "http://localhost:3002");
        array.add(serverA);
        array.add(serverB);
        array.add(serverC);


        ConsistentHash consistenthash = new ConsistentHash(5, array);
        System.out.println("PUT the sequence of key-value pairs into the three cache servers...");
        CacheServiceInterface m;
        Object server;
        for (int i = 1; i<11;i++){
            server = consistenthash.get(i);
            m = (CacheServiceInterface)server;
            if (m == serverA){
                System.out.print("http://localhost:3000  ");
            }
            if (m == serverB){
                System.out.print("http://localhost:3001  ");
            }
            if (m == serverC){
                System.out.print("http://localhost:3002  ");
            }
            m.put(i,String.valueOf(alphabet[i-1]));
            System.out.println("put("+ String.valueOf(i)+" => " + String.valueOf(alphabet[i-1]) + ")");
        }

        System.out.println("Retrieve all keys...");
        for (int i = 1; i<11;i++){
            server = consistenthash.get(i);
            m = (CacheServiceInterface)server;
            if (m == serverA){
                System.out.print("http://localhost:3000  ");
            }
            if (m == serverB){
                System.out.print("http://localhost:3001  ");
            }
            if (m == serverC){
                System.out.print("http://localhost:3002  ");
            }
            
            System.out.println("get(" + String.valueOf(i)+ ") => " + m.get(i));
        }


        System.out.println("Existing Cache Client...");
    }

}
