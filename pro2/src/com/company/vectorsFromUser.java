package com.company;

import java.util.Scanner;

public class vectorsFromUser {
    public vectorsFromUser(String trainingFile, String testFile){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter vectors");
        String lineFromUser = sc.nextLine();
        new DataReader(trainingFile, testFile, lineFromUser);
    }
}
