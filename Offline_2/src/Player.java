import java.util.ArrayList;
import java.util.Scanner;

/*
    PLAYER TYPES
    -------------------
    HUMAN = 0
    MINIMAX = 1
*/
//class Optimal_Move
//{
//    public int score;
//    public int move;
//
//    Optimal_Move(int score, int move)
//    {
//        this.score = score;
//        this.move = move;
//    }
//}

public class Player {

    int player_num;
    int opponent_num;
    int player_type;
    int depth_limit;
    Player opponent;
    int heuristic_choice;

    Player(int num, int type, int depth_limit, int heuristic_choice)
    {
        this.player_num = num;
        this.opponent_num = 2 - num + 1;
        this.player_type = type;
        this.depth_limit = depth_limit;
        this.heuristic_choice = heuristic_choice;
    }

    public int get_player_num()
    {
        return player_num;
    }

    public int choose_move(Board board)
    {
        if(player_type == 0)
        {
            Scanner sc = new Scanner(System.in);
            int move = sc.nextInt();
            return move;
        }
        else if(player_type == 1)
        {
            int optimal_move = minimax_move(board, depth_limit);                        // simulates with minimax without pruning
//            int optimal_move = alpha_beta_move(board, depth_limit);                     // simulate with alpha beta pruning

            System.out.println("Player Move: " + optimal_move);
            return optimal_move;
        }
        return 0;
    }

//    public Optimal_Move minimax_move(Board board, int depth)
//    {
//        int move = -1;
//        int score = Integer.MIN_VALUE;
//        Player turn = this;
//
//        ArrayList<Integer>possible_moves = board.possible_moves(this);                          // getting all the possible moves from the current state of board
//
//        for(int i=0; i<possible_moves.size(); i++)
//        {
//            if(depth == 0)
//            {
//                return new Optimal_Move(h_probability(board), possible_moves.get(i));               // if we reach depth 0, call the heuristic function and return
//            }
//            if(board.game_over())
//            {
//                return new Optimal_Move(-1,-1);                                         // can't make a move, game is over
//            }
//
//            Board new_board = new Board(board);                                                     // making a copy of board
//            new_board.single_move(this, possible_moves.get(i));                                 // making a move on this board
//
//            opponent = new Player(opponent_num, player_type, depth_limit);
//            int temp_score = opponent.min_value(new_board, depth-1, turn);
//            if(temp_score > score)
//            {
//                move = possible_moves.get(i);
//                score = temp_score;
//            }
//        }
//        return new Optimal_Move(score, move);
//    }
//
//    public int max_value(Board board, int depth, Player turn)
//    {
//        if(board.game_over())
//            return turn.h_probability(board);
//
//        int score = Integer.MIN_VALUE;
//
//        ArrayList<Integer>possible_moves = board.possible_moves(this);
//
//        for(int i=0; i<possible_moves.size(); i++)
//        {
//            if(depth == 0)
//                return turn.h_probability(board);
//
//            Board new_board = new Board(board);
//            new_board.single_move(this, possible_moves.get(i));
//
//            opponent = new Player(opponent_num, player_type, depth_limit);
//            int temp_max = opponent.min_value(new_board, depth-1, turn);
//            if(temp_max > score)
//            {
//                score = temp_max;
//            }
//        }
//        return score;
//    }
//
//    public int min_value(Board board, int depth, Player turn)
//    {
//        if(board.game_over())
//            return turn.h_probability(board);
//
//        int score = Integer.MAX_VALUE;
//
//        ArrayList<Integer>possible_moves = board.possible_moves(this);
//
//        for(int i=0; i<possible_moves.size(); i++)
//        {
//            if(depth == 0)
//                return turn.h_probability(board);
//
//            Board new_board = new Board(board);
//            new_board.single_move(this, possible_moves.get(i));
//
//            opponent = new Player(opponent_num, player_type, depth_limit);
//            int temp_min = opponent.max_value(new_board, depth-1, turn);
//            if(temp_min < score)
//            {
//                score = temp_min;
//            }
//        }
//        return score;
//    }

    //// ---------------------------------- MINIMAX ALGORITHM ------------------------- ////

    public int minimax_move(Board board, int depth)
    {
        int move = -1;
        int score = Integer.MIN_VALUE;
        Player turn = this;

        ArrayList<Integer>possible_moves = board.possible_moves(this);                  // getting all the possible moves of the board

        for(int i=0; i<possible_moves.size(); i++)                                          // for each move
        {
            Board new_board = new Board(board);                                             // making a new board for running predictions

            new_board.single_move(this, possible_moves.get(i));                         // making the move on the board

            opponent = new Player(opponent_num, player_type, depth_limit, heuristic_choice);

            int temp_score = opponent.min_value(new_board, depth - 1, turn);          // opponent runs min_value
            if(temp_score > score)                                                          // player1 takes the max of the result
            {
                score = temp_score;
                move = possible_moves.get(i);
            }
        }
        System.out.println("Score for Player " + player_num + ": " + score);
        return move;
    }

//    public int minimax_move(Board board, int depth)
//    {
//        int move = -1;
//        int score;
//
//        if(player_num == 1)
//            score = Integer.MIN_VALUE;                                          // if player1 aka max_player, initial score = -infinity
//        else
//            score = Integer.MAX_VALUE;                                          // if player2 aka min_player, initial score = infinity
//
//        ArrayList<Integer>possible_moves = board.possible_moves(this);      // getting all the possible moves of the board
//
//        for(int i=0; i<possible_moves.size(); i++)                              // for each move
//        {
//            Board new_board = new Board(board);                                 // making a new board for running predictions
//
//            new_board.single_move(this, possible_moves.get(i));             // making the move on the board
//
//            opponent = new Player(opponent_num, player_type, depth_limit);
//
//            if(player_num == 1)                                                         // if player 1
//            {
//                int temp_score = opponent.min_value(new_board, depth - 1);          // opponent runs min_value
//                if(temp_score > score)                                                  // player1 takes the max of the result
//                {
//                    score = temp_score;
//                    move = possible_moves.get(i);
//                }
//            }
//            else                                                                        // else if player 2
//            {
//                int temp_score = opponent.max_value(new_board, depth - 1);          // opponent runs max_value
//                if(temp_score < score)                                                  // player2 takes the min of the result
//                {
//                    score = temp_score;
//                    move = possible_moves.get(i);
//                }
//            }
//        }
//        System.out.println("Score for Player " + player_num + ": " + score);
//        return move;
//    }

    public int max_value(Board board, int depth, Player turn)
    {
        if(board.game_over() || depth == 0)
        {
            if(heuristic_choice == 1)
            {
                return turn.h_probability(board);
            }
            else if(heuristic_choice == 2)
            {
                return turn.score_stones_diff(board);
            }
            else if(heuristic_choice == 3)
            {
                return turn.stones_diff(board);
            }
            else if(heuristic_choice == 4)
            {
                return turn.close_to_victory(board);
            }
            else if(heuristic_choice == 5)
            {
                return turn.close_to_storage(board);
            }
            else
                return -1;
        }
        else
        {
            int score = Integer.MIN_VALUE;                                                                  // score set to -INFINITY

            ArrayList<Integer>possible_moves = board.possible_moves(this);                              // taking all the possible moves

            for(int i=0; i<possible_moves.size(); i++)                                                      // for each move in possible moves
            {
                Board new_board = new Board(board);                                                         // generating a copy of the board for prediction
                new_board.single_move(this, possible_moves.get(i));                                     // giving the move on the copied board

                opponent = new Player(opponent_num, player_type, depth_limit, heuristic_choice);                              // taking the opponent player object
                int temp_score = opponent.min_value(new_board, depth-1, turn);                          // opponent gives the min move
                score = Math.max(score, temp_score);                                                        // player is selecting the max score
            }
            return score;
        }
    }

    public int min_value(Board board, int depth, Player turn)
    {
        if(board.game_over() || depth == 0)
        {
            if(heuristic_choice == 1)
            {
                return turn.h_probability(board);
            }
            else if(heuristic_choice == 2)
            {
                return turn.score_stones_diff(board);
            }
            else if(heuristic_choice == 3)
            {
                return turn.stones_diff(board);
            }
            else if(heuristic_choice == 4)
            {
                return turn.close_to_victory(board);
            }
            else if(heuristic_choice == 5)
            {
                return turn.close_to_storage(board);
            }
            else
                return -1;
        }
        else
        {
            int score = Integer.MAX_VALUE;                                                      // score set to +INFINITY

            ArrayList<Integer>possible_moves = board.possible_moves(this);                  // taking all the possible moves

            for(int i=0; i<possible_moves.size(); i++)                                          // for each move in possible moves
            {
                Board new_board = new Board(board);                                             // copying the current board to a new board
                new_board.single_move(this, possible_moves.get(i));                         // giving the move on the new board

                opponent = new Player(opponent_num, player_type, depth_limit, heuristic_choice);                  // taking the opponent player object
                int temp_score = opponent.max_value(new_board, depth-1, turn);              // opponent gives max move
                score = Math.min(score, temp_score);                                            // player selects the minimum of the scores
            }
            return score;
        }
    }



    //// ------------------------ ALPHA BETA PRUNING ------------------------- ////

    public int alpha_beta_move(Board board, int depth)
    {
        int move = -1;
        Player turn = this;
        int score = Integer.MIN_VALUE;                                                      // score set to -INFINITY

        int alpha = Integer.MAX_VALUE;                                                      // alpha set to INFINITY
        int beta = Integer.MIN_VALUE;                                                       // beta set to -INFINITY

        ArrayList<Integer>possible_moves = board.possible_moves(this);                  // getting all the possible moves of the board

        for(int i=0; i<possible_moves.size(); i++)                                          // for each move
        {
            Board new_board = new Board(board);                                             // making a new board for running predictions

            new_board.single_move(this, possible_moves.get(i));                         // making the move on the board

            opponent = new Player(opponent_num, player_type, depth_limit, heuristic_choice);

            int temp_score = opponent.ab_min_value(new_board, depth - 1, turn, alpha, beta);          // opponent runs min_value
            if(temp_score > score)                                                          // player1 takes the max of the result
            {
                score = temp_score;
                move = possible_moves.get(i);
            }
            alpha = Math.max(score, alpha);
        }
        System.out.println("Score for Player " + player_num + ": " + score);
        return move;
    }

    public int ab_max_value(Board board, int depth, Player turn, int alpha, int beta)
    {
        if(board.game_over() || depth == 0)
        {
            if(heuristic_choice == 1)
            {
                return turn.h_probability(board);
            }
            else if(heuristic_choice == 2)
            {
                return turn.score_stones_diff(board);
            }
            else if(heuristic_choice == 3)
            {
                return turn.stones_diff(board);
            }
            else if(heuristic_choice == 4)
            {
                return turn.close_to_victory(board);
            }
            else if(heuristic_choice == 5)
            {
                return turn.close_to_storage(board);
            }
            else
                return -1;
        }
        else
        {
            int score = Integer.MIN_VALUE;

            ArrayList<Integer>possible_moves = board.possible_moves(this);

            for(int i=0; i<possible_moves.size(); i++)
            {
                Board new_board = new Board(board);
                new_board.single_move(this, possible_moves.get(i));

                opponent = new Player(opponent_num, player_type, depth_limit, heuristic_choice);
                int temp_score = opponent.ab_min_value(new_board, depth-1, turn, alpha, beta);
                score = Math.max(score, temp_score);

                // alpha beta pruning -- //
                if(score >= beta)                                           // if we get a value bigger than beta, max will choose it, and min will not take it from this tree
                    return score;                                           // hence prune the rest of the tree

                alpha = Math.max(alpha, score);
            }
            return score;
        }
    }

    public int ab_min_value(Board board, int depth, Player turn, int alpha, int beta)
    {
        if(board.game_over() || depth == 0)
        {
            if(heuristic_choice == 1)
            {
                return turn.h_probability(board);
            }
            else if(heuristic_choice == 2)
            {
                return turn.score_stones_diff(board);
            }
            else if(heuristic_choice == 3)
            {
                return turn.stones_diff(board);
            }
            else if(heuristic_choice == 4)
            {
                return turn.close_to_victory(board);
            }
            else if(heuristic_choice == 5)
            {
                return turn.close_to_storage(board);
            }
            else
                return -1;

        }
        else
        {
            int score = Integer.MAX_VALUE;

            ArrayList<Integer>possible_moves = board.possible_moves(this);

            for(int i=0; i<possible_moves.size(); i++)
            {
                Board new_board = new Board(board);
                new_board.single_move(this, possible_moves.get(i));

                opponent = new Player(opponent_num, player_type, depth_limit, heuristic_choice);
                int temp_score = opponent.ab_max_value(new_board, depth-1, turn, alpha, beta);
                score = Math.min(score, temp_score);

                // -- alpha beta pruning -- //
                if(score <= alpha)                              // if we get a value smaller than alpha, min will choose it, and max will not take it from this tree
                    return score;                               // hence prune the rest of the tree

                beta = Math.min(score, beta);
            }
            return score;
        }
    }

    //// -------------------------------- HEURISTICS ------------------------- ////

    public int h_probability(Board board)
    {
        if(board.has_won(player_num))
            return 100;
        else if(board.has_won(opponent_num))
            return 0;
        else
            return 50;
    }

    public int score_stones_diff(Board board)
    {
        return (board.score_bins[player_num-1] - board.score_bins[opponent_num-1]);
    }

    public int stones_diff(Board board)
    {
        int score_diff = (board.score_bins[player_num-1] - board.score_bins[opponent_num-1]);

        int self_storage = 0;
        int opponent_storage = 0;

        if(player_num == 1)
        {
            for(int i=0; i<board.no_of_bins; i++)
            {
                self_storage += board.p1_bins[i];
                opponent_storage += board.p2_bins[i];
            }
        }
        else
        {
            for(int i=0; i<board.no_of_bins; i++)
            {
                self_storage += board.p2_bins[i];
                opponent_storage += board.p1_bins[i];
            }
        }

        int storage_diff = self_storage - opponent_storage;

        return 100 * score_diff + 50 * storage_diff;
    }

    public int close_to_victory(Board board)
    {
        // measures how close a player is to victory depending on
        // if his storage is already close to containing half of total number of stones

        int no_of_bins = board.no_of_bins;                                      // e.g. 6
        int stones_each_bin = board.stones_each_bin;                            // e.g. 4

        int total_stones = no_of_bins * stones_each_bin * 2;                    // e.g. 6 * 4 * 2 = 48
        int self_stones = 0;

        if(player_num == 1)
        {
            self_stones = board.score_bins[0];
        }
        else
            self_stones = board.score_bins[1];

        // if goal contains more than half of total stones
        if(self_stones > total_stones/2)
            return 100;

        // if goal contains stones in range [total_stones/2-stones_each_bin, total_stones]
        // e.g. [20, 48]
        else if((self_stones>(total_stones/2-stones_each_bin)) && (self_stones<(total_stones/2)) )
            return 50;

        else
            return 0;
    }

    public int close_to_storage(Board board)
    {
        int stones;
        int stones_close_to_storage = 0;
        int[] self_bins;

        if(player_num == 1) {
            self_bins = board.p1_bins;
        }
        else {
            self_bins = board.p2_bins;
        }
        for(int i=0; i<self_bins.length; i++)
        {
            stones = self_bins[i];
            int self_bins_limit = (board.no_of_bins)-i;             // limit for a bin to overflow      // i.e. bin 2 can hold 5 bins that won't overflow  // 6-(2-1) = 5

            if(stones < self_bins_limit)
            {
                stones_close_to_storage += stones;
            }
            else
            {
                int stones_left = stones;

                stones_left -= self_bins_limit;
                stones_close_to_storage += self_bins_limit;

                int iteration = 0;

                while (stones_left > 0)
                {
                    if(iteration%2==0)
                    {
                        stones_left -= board.no_of_bins;
                    }
                    else
                    {
                        if(stones_left > board.no_of_bins+1)
                        {
                            stones_left -= (board.no_of_bins + 1);
                            stones_close_to_storage += (board.no_of_bins+1);
                        }
                        else
                        {
                            stones_close_to_storage += stones_left;
                            stones_left = 0;
                        }
                    }
                    iteration++;
                }
            }
        }
        return stones_close_to_storage;
    }
}
