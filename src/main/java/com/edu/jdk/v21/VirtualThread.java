package com.edu.jdk.v21;


public class VirtualThread {
  public static void main(String[] args) throws InterruptedException {
    Thread.ofVirtual().start(() -> {
      System.out.println("hello");
    });
    Thread.sleep(1000);
  }
} 
