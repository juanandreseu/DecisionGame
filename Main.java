import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner kb=new Scanner(System.in);
        System.out.println("Do you want to load a previous game?");
        String userResponse = kb.nextLine();
        if (userResponse.equals("yes")) {
            GuessingGame game = new GuessingGame();
            game.play();
        } else if (userResponse.equals("no")) {
            GuessingGame game = new GuessingGame("Is it a mammal?","crocodile","dog");
            game.play();
        } else
            throw new IllegalArgumentException("Only respond in yes or no!");


    }
}
