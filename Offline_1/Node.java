package n_Puzzle;

import java.util.ArrayList;

class Position
{
    public int x;
    public int y;

    Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

public class Node {

   private char[][] matrix;
   private int depth;
   private int f_val;
   private int n;
   private Node parent;

   Node(char[][] matrix, int depth, int f_val, int n, Node parent)
   {
       this.matrix = matrix;
       this.depth = depth;
       this.f_val = f_val;
       this.n = n;
       this.parent = parent;
   }

   public char[][] get_matrix()
   {
       return matrix;
   }

   public int get_depth()
   {
       return depth;
   }

   public int get_fval()
   {
       return f_val;
   }

   public void set_fval(int f_val)
   {
       this.f_val = f_val;
   }

   public Position find(char[][] puzzle, char x)
   {
       for(int i=0; i<n; i++)
       {
           for(int j=0; j<n; j++)
           {
               if (Character.compare(puzzle[i][j], x) == 0)
                   return (new Position(i, j));
           }
       }
       return null;
   }

   public char[][] shuffle(char[][] puzzle, int blank_x, int blank_y, int x2, int y2)
   {
       if((x2>=0 && x2<n) && (y2>=0 && y2<n))
       {
           char[][] temp = copy_puzzle(puzzle);             // copying the current state of the puzzle
           char blank = temp[blank_x][blank_y];             // keeping blank in a temp
           temp[blank_x][blank_y] = temp[x2][y2];           // putting the other number in blank's spot
           temp[x2][y2] = blank;                            // putting blank in the other number's spot

           return temp;
       }
       return null;
   }

   public char[][] copy_puzzle(char[][] puzzle)
   {
       char[][] clone = new char[n][n];

       for(int i=0; i<n; i++)
       {
           for(int j=0; j<n; j++)
           {
               clone[i][j] = puzzle[i][j];
           }
       }
       return clone;
   }

   public ArrayList<Node> generate_children()
   {
       ArrayList<Node> children = new ArrayList<Node>();

       Position blank = find(matrix, '*');
       int blank_x = blank.x;
       int blank_y = blank.y;

       ArrayList<Position> moves = new ArrayList<Position>();
       moves.add(new Position(blank_x, blank_y+1));
       moves.add(new Position(blank_x, blank_y-1));
       moves.add(new Position(blank_x-1, blank_y));
       moves.add(new Position(blank_x+1, blank_y));

       moves.forEach(position ->
       {
           char[][] child = shuffle(matrix, blank_x, blank_y, position.x, position.y);
           if(child != null)
           {
               Node child_node = new Node(child, depth + 1, 0, n, this);

               if(this.parent!=null)
               {
                   if (!is_same_node(this.parent, child_node))              // if the parent and child are the same node, no need to add it on the list
                       children.add(child_node);
               }
               else
                   children.add(child_node);
           }
       });
       return children;
   }

   public void print()
   {
       for(int i=0; i<n; i++)
       {
           for(int j=0; j<n; j++)
           {
               System.out.print(matrix[i][j] + " ");
           }
           System.out.print("\n");
       }
   }

   public boolean is_same_node(Node x, Node y)
   {
       char[][] x_matrix = x.get_matrix();
       char[][] y_matrix = y.get_matrix();

       for(int i=0; i<n; i++)
       {
           for(int j=0; j<n; j++)
           {
               if(Character.compare(x_matrix[i][j], y_matrix[i][j]) != 0)       // if the characters are not same
                   return false;
           }
       }
       return true;
   }
}