import TreePackage.BinaryTree;
import TreePackage.DecisionTreeInterface;
import TreePackage.DecisionTree;


import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
public class GuessingGame
{
    private DecisionTree<String> tree;
    private Scanner kb=new Scanner(System.in);

    public GuessingGame(String question, String noAnswer, String yesAnswer){
        DecisionTree<String> no = new DecisionTree<>(noAnswer);
        DecisionTree<String> yes = new DecisionTree<>(yesAnswer);
        tree = new DecisionTree<>(question, no, yes);
    }
    public GuessingGame() throws IOException, ClassNotFoundException {
        try {
            tree = load();
        }
        catch(FileNotFoundException e){
            System.out.println("There's no save file. New game starting with default values...\n");
            DecisionTree<String> no = new DecisionTree<>("crocodile");
            DecisionTree<String> yes = new DecisionTree<>("dog");
            tree = new DecisionTree<>("Is it a mammal?", no, yes);
        }
    }
    public void play() throws IOException {
        String userResponse;
        do {
            while (!tree.isAnswer()) {
                System.out.println(tree.getCurrentData());
                userResponse = kb.nextLine();
                if (userResponse.equals("no")) {
                    tree.advanceToNo();
                } else if (userResponse.equals("yes")) {
                    tree.advanceToYes();
                } else
                    throw new IllegalArgumentException("Only respond in yes or no!");
            }
            System.out.println("Is it a(n) " + tree.getCurrentData() + "?");
            userResponse = kb.nextLine();
            if (userResponse.equals("yes")) {
                System.out.println("I WIN!");
                tree.resetCurrentNode();
            } else if (userResponse.equals("no")) {
                learn();
            } else
                throw new IllegalArgumentException("Only respond in yes or no!");
            System.out.println("Do you want to print?");
            userResponse = kb.nextLine();
            if (userResponse.equals("yes")) {
                Iterator<String>  iterator;
                System.out.println("For preorder press 'p', for level order press 'l'");
                userResponse = kb.nextLine();
                if (userResponse.equals("p")) {
                    iterator= tree.getPreorderIterator();
                    while(iterator.hasNext()){
                        System.out.print(iterator.next() + " ");
                    }
                    System.out.println();
                } else if (userResponse.equals("l")) {
                    iterator=tree.getLevelOrderIterator();
                    while(iterator.hasNext()){
                        System.out.print(iterator.next() + " ");
                    }
                    System.out.println();
                } else
                    throw new IllegalArgumentException("Only respond in p or l!");
            } else if (userResponse.equals("no")) {
            } else
                throw new IllegalArgumentException("Only respond in yes or no!");
            System.out.println("Do you want to keep playing?");
            userResponse=kb.nextLine();
            if(userResponse.equals("no")){
                System.out.println("Do you want to save your tree?");
                userResponse = kb.nextLine();
                if (userResponse.equals("yes")) {
                    save();
                    break;
                } else if (userResponse.equals("no")) {
                    break;
                } else
                    throw new IllegalArgumentException("Only respond in yes or no!");
            }
        } while(userResponse.equals("yes"));
    }

    public void learn()
    {
        String userQuestion, userAnswer, tempAnswer;
        System.out.println("I give up, what were you thinking of?");
        userAnswer=kb.nextLine();
        System.out.println("Give me a question whose answer is yes for "+userAnswer+" and no for "+tree.getCurrentData());
        userQuestion=kb.nextLine();
        tempAnswer=tree.getCurrentData(); //Current answer
        tree.setResponses(tempAnswer,userAnswer);
        tree.setCurrentData(userQuestion);
        tree.resetCurrentNode();
    }
    public void save() throws IOException {
        FileOutputStream oStream = new FileOutputStream("MyTree.dat");
        ObjectOutputStream outputFile = new ObjectOutputStream(oStream); //Open output file.
        System.out.println("Saving your tree data...");
        outputFile.writeObject(tree); //Save object as bytes into file.
        System.out.println("\nDone.");
        outputFile.close(); // Close the file.
    }
    public DecisionTree load() throws IOException, ClassNotFoundException {
        FileInputStream inStream = new FileInputStream("MyTree.dat");
        ObjectInputStream inputFile = new ObjectInputStream(inStream); //Open object input stream.
        System.out.println("Loading your tree data...");
        tree=(DecisionTree<String>)inputFile.readObject(); //Deserialize object data
        System.out.println("\nDone.");
        // Close the file.
        inputFile.close();
        return tree;
    }


}
