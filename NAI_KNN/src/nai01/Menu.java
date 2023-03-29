package nai01;
import java.util.Scanner;

public class Menu {

    public Menu(){
        Scanner sc = new Scanner(System.in);
        String testFilePath = "iris_test.txt";
        String trainingFilePath = "iris_training.txt";
        KNN knn = new KNN();
        knn.readFile(testFilePath, "test");
        knn.readFile(trainingFilePath, "training");
        System.out.println("Enter the number k ");

        knn.comparing();
        System.out.println("Do you want to write a new test object? y/n");
        String answer = sc.nextLine();
        if(answer.equals("y")) {
            knn.newInstance();
            System.out.println("Do you want to write the next test object? y/n");
            String answer1 = sc.nextLine();
            while(answer1.equals("y")){
                knn.newInstance();
                System.out.println("Do you want to enter another test object? y/n");
                answer1 = sc.nextLine();
            }
        }
        System.out.println("It's over");
    }
}