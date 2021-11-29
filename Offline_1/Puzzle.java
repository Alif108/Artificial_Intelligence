package n_Puzzle;

import java.util.*;

public class Puzzle {
    private int size;

    private Node init_state;
    private Node goal_state;

    Puzzle(int size, Node init_state, Node goal_state)
    {
        this.size = size;
        this.init_state = init_state;
        this.goal_state = goal_state;
    }

    // calculate hamming distance of two nodes
    public int hamming_distance(Node target, Node goal)
    {
        int sum = 0;
        String[][] target_matrix = target.get_matrix();
        String[][] goal_matrix = goal.get_matrix();

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                // if the entry is not blank and not same as the goal entry
                if(!target_matrix[i][j].equalsIgnoreCase(goal_matrix[i][j]) && !target_matrix[i][j].equalsIgnoreCase("*"))
                    sum += 1;
            }
        }
        return sum;
    }

    // calculate f_n using hamming distance
    public int hamming_f_n(Node target, Node goal)
    {
        return (hamming_distance(target, goal) + target.get_depth());           // f(n) = h(n) + g(n); here g(n) is depth
    }

    // calculate manhattan distance in two nodes
    public int manhattan_distance(Node target, Node goal)
    {
        int sum = 0;

        String[][] target_matrix = target.get_matrix();

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                String num = target_matrix[i][j];                                         // taking a number of the puzzle
                if(!num.equalsIgnoreCase("*"))                                   // if the number is not a blank
                {
                    Position pos = goal.find(num);                                      // getting the position of the number in goal matrix
                    int goal_x = pos.x;                                                 // getting the x value
                    int goal_y = pos.y;                                                 // getting the y value

                    sum += (Math.abs( i - goal_x ) + Math.abs( j - goal_y ));           // adding to the manhattan sum
                }
            }
        }
        return sum;
    }

    // calculate f_n using manhattan distance
    public int manhattan_f_n(Node target, Node goal)
    {
        return manhattan_distance(target, goal) + target.get_depth();
    }

    // calculate linear conflict
    public int linear_conflict(Node init, Node goal)
    {
        int conflict = 0;
        String[][] init_matrix = init.get_matrix();

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                Position goal_pos = goal.find(init_matrix[i][j]);                           // getting the position of a number in goal matrix
                if (goal_pos != null)
                {
                    if (goal_pos.x == i && goal_pos.y != j)                                 // if goal position is the same row, but not in the same column
                    {
                        for (int k = j + 1; k < size; k++)                                  // iterate over the next columns
                        {
                            Position goal_pos2 = goal.find(init_matrix[i][k]);              // getting the next number position
                            if(goal_pos2 != null)
                            {
                                if (goal_pos2.x == i && goal_pos2.y != k)                   // if the row matches, but not the column
                                    conflict += 1;
                            }
                        }
                    }
                }

            }
        }
        return conflict;
    }

    // calculate f_n using linear conflict + manhattan
    public int lc_f_n(Node init, Node goal)
    {
        return manhattan_distance(init, goal) + 2 * linear_conflict(init, goal) + init.get_depth();
    }

//    // returns the index of the Node having minimum f_val
//    public int get_min_index(ArrayList<Node> set)
//    {
//        int idx = -1;
//        int min = Integer.MAX_VALUE;
//
//        for(int i=0; i<set.size(); i++)
//        {
//            if(set.get(i).get_fval() < min)
//            {
//                min = set.get(i).get_fval();
//                idx = i;
//            }
//        }
//        return idx;
//    }

    // solves using hamming distance
    public void simulate_hamming()
    {
        int moves = 0;
        PriorityQueue<Node> open_set = new PriorityQueue<Node>(Comparator.comparing(Node::get_fval));
        PriorityQueue<Node> closed_set = new PriorityQueue<Node>(Comparator.comparing(Node::get_fval));

        init_state.set_fval(hamming_f_n(init_state, goal_state));               // calculating f_n for initial state
        open_set.add(init_state);                                               // adding initial state to open state

        while (true)
        {
            Node target = open_set.poll();                                      // popping the element with the lowest f_n

            target.print();
            System.out.print("\n");

            if(hamming_distance(target, goal_state) == 0)                       // if the hamming distance is 0, aka, the goal state has been reached
                break;

            ArrayList<Node> children = target.generate_children();              // generating the child nodes

            children.forEach(child ->                                           // for each child node
            {
                child.set_fval(hamming_f_n(child, goal_state));                 // set the value of f_n
                open_set.add(child);                                            // add it to the open list
            });
            closed_set.add(target);                                             // add the popped node in the closed set

            moves += 1;
        }

        System.out.println("Number of moves using Hamming: " + moves);
    }

    // solves using manhattan distance
    public void simulate_manhattan()
    {
        int moves = 0;
        PriorityQueue<Node> open_set = new PriorityQueue<Node>(Comparator.comparing(Node::get_fval));
        PriorityQueue<Node> closed_set = new PriorityQueue<Node>(Comparator.comparing(Node::get_fval));

        init_state.set_fval(manhattan_f_n(init_state, goal_state));                 // calculating f_n for initial state
        open_set.add(init_state);                                                   // adding initial state to open state

        while (true)
        {
            Node target = open_set.poll();                                          // popping the node with minimum f_val

            target.print();
            System.out.print("\n");

            if(manhattan_distance(target, goal_state) == 0)                         // if manhattan distance is 0 aka, reached the goal state
                break;

            ArrayList<Node> children = target.generate_children();                  // generating the children

            children.forEach(child ->                                               // for each child
            {
                child.set_fval(manhattan_f_n(child, goal_state));                   // set the f_val
                open_set.add(child);                                                // add the child in the open list
            });
            closed_set.add(target);                                                 // add the popped node in the closed set

            moves += 1;
        }
        System.out.println("Number of moves using Manhattan: " + moves);
    }

    // solves using linear conflict
    public void simulate_lc()
    {
//        System.out.println(linear_conflict(init_state, goal_state));

        int moves = 0;
        PriorityQueue<Node> open_set = new PriorityQueue<Node>(Comparator.comparing(Node::get_fval));
        PriorityQueue<Node> closed_set = new PriorityQueue<Node>(Comparator.comparing(Node::get_fval));

        init_state.set_fval(hamming_f_n(init_state, goal_state));               // calculating f_n for initial state
        open_set.add(init_state);                                               // adding initial state to open state

        while (true)
        {
            Node target = open_set.poll();                                      // popping the element with the lowest f_n

            target.print();
            System.out.print("\n");

            if(hamming_distance(target, goal_state) == 0)                       // if the hamming distance is 0, aka, the goal state has been reached
                break;

            ArrayList<Node> children = target.generate_children();              // generating the child nodes

            children.forEach(child ->                                           // for each child node
            {
                child.set_fval(hamming_f_n(child, goal_state));                 // set the value of f_n
                open_set.add(child);                                            // add it to the open list
            });
            closed_set.add(target);                                             // add the popped node in the closed set

            moves += 1;
        }

        System.out.println("Number of moves using Linear Conflict: " + moves);
    }
}
