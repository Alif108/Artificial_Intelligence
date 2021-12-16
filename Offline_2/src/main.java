public class Main {

    public static void main(String[] args) {
        Board board = new Board();

        Player p1 = new Player(1, 1, 5, 3);
        Player p2 = new Player(2, 1, 10, 3);

        long start_time = System.nanoTime();
        board.play_game(p1, p2);
        long end_time = System.nanoTime();

        System.out.println("\nExecution Time: " + (end_time-start_time) + " nanosecond");
    }
}
