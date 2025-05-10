package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        System.out.println("Would you like to buy something?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        Scanner buyOrNotScanner = new Scanner(System.in);
        if (buyOrNotScanner.nextInt() == 1) {
            System.exit(1);
        }

        System.out.println("Please enter your role.");
        System.out.println("1. Admin");
        System.out.println("2. Staff");

        Scanner input = new Scanner(System.in);
        String role = input.nextLine();

        System.out.println("What is your ID?");
        Scanner idScanner = new Scanner(System.in);
        String id = idScanner.nextLine();

    }
}
