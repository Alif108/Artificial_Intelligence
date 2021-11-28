package n_Puzzle;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class util {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size >");

        int size = sc.nextInt();
        char[][] puzzle1 = new char[size][size];
        char[][] puzzle2 = new char[size][size];

        System.out.println("Enter the initial state: ");
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                puzzle1[i][j] = sc.next().charAt(0);
            }
        }

        System.out.println("Enter the goal state: ");
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                puzzle2[i][j] = sc.next().charAt(0);
            }
        }

        Node init = new Node(puzzle1, 0, 0, size, null);
        Node goal = new Node(puzzle2, 0, 0, size, null);

        Puzzle p = new Puzzle(size, init, goal);
        p.simulate_hamming();
    }
}
