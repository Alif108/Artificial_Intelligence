public class Game {
    private int[] board;
    private int board_size = 14;

    public Game()
    {
        this.board = new int[board_size];

        for(int i=0; i<board_size; i++)
        {
            if(i==6 || i==13)
                continue;

            board[i] = 4;
        }
    }

    void play()
    {
        System.out.println("Before");
        Node init = new Node(board, false);
        init.print_board();

        System.out.println("After");
        Node next_board =  init.single_move(9);
        next_board.print_board();
    }
}
