public class Node {
    private int board[];
    private int board_size = 14;
    private boolean turn;

    public Node(int[] board, boolean turn)
    {
        this.board = board;
        this.turn = turn;                       // true -> player 1, false -> player 2
    }

    public int[] get_board()
    {
        return board;
    }

    public boolean get_turn()
    {
        return turn;
    }

    public Node single_move(int bin)
    {
        if(board[bin] == 0)                             // if bin is empty
            return null;
        else
        {
            int temp[] = clone_board();
            int stone = board[bin];

            int bin_no = bin;                           // starting from the next bin
            temp[bin_no] = 0;                           // no stones in the selected bin

            while(stone!=0)
            {
                bin_no++;
                if(bin_no == 14)                        // wrap around
                    bin_no = 0;

                if(turn && bin_no==13)                  // if turn is player 1, opponent's goal is 13 -> don't put in opponent's goal
                    bin_no = 0;
                else if(!turn && bin_no==6)             // if turn is player 2, opponent's goal is 6 -> don't put in opponent's goal
                    bin_no = 7;

                temp[bin_no]++;                         // increasing the stones in the next bins by one
                stone--;
            }

            if(turn && bin_no==6)                                       // if player 1 ends on goal
            {
                System.out.println("Bonus Turn for Player 1");
                return new Node(temp, turn);                            // he gets a bonus turn
            }
            else if(turn && bin_no !=6)                                 // if player 1 doesn't end on goal
            {
                return new Node(temp, !turn);                           // turn changed
            }
            else if(!turn && bin_no==13)                                // if player 2 ends on goal
            {
                System.out.println("Bonus Turn for Player 2");
                return new Node(temp, !turn);                           // he gets a bonus turn
            }
            else if(!turn && bin_no!=13)                                // if player 2 does not end on goal
            {
                return new Node(temp, turn);                            // turn changed
            }
            else
                return null;
        }
    }

    // -- creates a copy of the board -- //
    public int[] clone_board()
    {
        int[] temp = new int[board_size];

        for(int i=0; i<board_size; i++)
        {
            temp[i] = board[i];
        }
        return temp;
    }

    void print_board()
    {
        System.out.println("    " + board[5] + "  " + board[4] + "  " + board[3] + "  " + board[2] + "  " + board[1] + "  " + board[0]);
        System.out.println("    " + "_  _  _  _  _  _");
        System.out.println(board[6] + "                    " + board[13]);
        System.out.println("_" + "                    " + "_");
        System.out.println("    " + board[7] + "  " + board[8] + "  " + board[9] + "  " + board[10] + "  " + board[11] + "  " + board[12]);
        System.out.println("    " + "_  _  _  _  _  _");
        System.out.print("\n");
    }
}
