public class Main {

    public static void main(String[] args) {

        Board board = new Board(6,4);

        Player p1 = new Player(1, 1, 5, 5);
        Player p2 = new Player(2, 1, 10, 5);

        long start_time = System.nanoTime();
        board.play_game(p1, p2);
        long end_time = System.nanoTime();

        System.out.println("\nExecution Time: " + (end_time-start_time) + " nanosecond");
    }
}
