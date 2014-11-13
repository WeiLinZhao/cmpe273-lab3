package edu.sjsu.cmpe.cache.client;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

 private final int numberOfReplicas;
 private final SortedMap<String, T> circle = new TreeMap<String, T>();

 public ConsistentHash(int numberOfReplicas,
     Collection<T> nodes) {
   this.numberOfReplicas = numberOfReplicas;

   for (T node : nodes) {
     add(node);
   }
 }

 public void add(T node) {
   for (int i = 0; i < numberOfReplicas; i++) {
    try {
           circle.put(AeSimpleMD5.MD5(node.toString() + i), node);
         } catch (Exception e) {
	System.out.println(e);
         } 


   }
 }

 public void remove(T node) {
   for (int i = 0; i < numberOfReplicas; i++) {
    try {
           circle.put(AeSimpleMD5.MD5(node.toString() + i), node);
         } catch (Exception e) {
	System.out.println(e);
          
         } 

   }
 }

 public T get(Object key) {
   if (circle.isEmpty()) {
     return null;
   }
   String hash = "";
    try {
           hash = AeSimpleMD5.MD5(key.toString());
         } catch (Exception e) {
          
	System.out.println(e);
         } 

   if (!circle.containsKey(hash)) {
     SortedMap<String, T> tailMap = circle.tailMap(hash);
     hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
   }
   return circle.get(hash);
 }

}
