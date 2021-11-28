package n_Puzzle;

import java.util.*;

public class Puzzle {
    private int size;
    private ArrayList<Node> open_set;
    private ArrayList<Node> closed_set;

    private Node init_state;
    private Node goal_state;

    Puzzle(int size, Node init_state, Node goal_state)
    {
        this.size = size;
        this.init_state = init_state;
        this.goal_state = goal_state;

        this.open_set = new ArrayList<Node>();
        this.closed_set = new ArrayList<Node>();
    }

    public int hamming_distance(Node target, Node goal)
    {
        int sum = 0;
        char[][] target_matrix = target.get_matrix();
        char[][] goal_matrix = goal.get_matrix();

        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                // if the entry is not blank and not same as the goal entry
                if((Character.compare(target_matrix[i][j], goal_matrix[i][j]) != 0) && (Character.compare(target_matrix[i][j], '*') !=0 ))
                    sum += 1;
            }
        }
        return sum;
    }

    public int f_n(Node target, Node goal)
    {
        return (hamming_distance(target, goal) + target.get_depth());           // f(n) = h(n) + g(n); here g(n) is depth
    }

    public int get_min_index(ArrayList<Node> set)
    {
        int idx = -1;
        int min = Integer.MAX_VALUE;

        for(int i=0; i<set.size(); i++)
        {
            if(set.get(i).get_fval() < min)
            {
                min = set.get(i).get_fval();
                idx = i;
            }
        }
        return idx;
    }

    public void simulate_hamming()
    {
        init_state.set_fval(f_n(init_state, goal_state));
        open_set.add(init_state);

        while (true)
        {
            Node target = open_set.get(0);

            target.print();
            System.out.print("\n");

            if(hamming_distance(target, goal_state) == 0)
                break;

            ArrayList<Node> children = target.generate_children();

            children.forEach(child ->
            {
                child.set_fval(f_n(child, goal_state));
                open_set.add(child);
            });
            closed_set.add(target);
            open_set.remove(0);

            Collections.swap(open_set, 0, get_min_index(open_set));
        }
    }
}
