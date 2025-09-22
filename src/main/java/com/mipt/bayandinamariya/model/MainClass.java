package com.mipt.bayandinamariya.model;

public class MainClass {

    private int number;
    private String text;

    protected static double staticDouble;

    public final long TIMESTAMP = 1700000000000L;

    public static void main(String[] args) {
        for (int i = 0; i <= 15; i++) {
            System.out.println("Iter: " + i);
        }
    }
}