package n_Puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class util {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size >");

        int size = sc.nextInt();
        String[][] puzzle1 = new String[size][size];
        String[][] puzzle2 = new String[size][size];

        System.out.println("Enter the initial state: ");
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                puzzle1[i][j] = sc.next();
            }
        }

        System.out.println("Enter the goal state: ");
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                puzzle2[i][j] = sc.next();
            }
        }

//        // redirecting output to file
//        File file = new File("E:\\sample.txt");
//        PrintStream stream = new PrintStream(file);
//        System.setOut(stream);

        System.out.println("Initial State: ");
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                System.out.print(puzzle1[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.println("Goal State: ");
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                System.out.print(puzzle2[i][j] + " ");
            }
            System.out.print("\n");
        }

        Node init = new Node(puzzle1, 0, 0, size, null);
        Node goal = new Node(puzzle2, 0, 0, size, null);

        Puzzle p = new Puzzle(size, init, goal);

//        System.out.println("###################\n");
        System.out.println("Hamming: ");
        p.simulate_hamming();

//        System.out.println("\n");
//
//        System.out.println("Manhattan: ");
//        p.simulate_manhattan();
//
//        System.out.println("\n");
//
//        System.out.println("Linear Conflict");
//        p.simulate_lc();
    }
}
