import java.util.*;

public class CapitalsGame {
    private final int rows;
    private final int cols;
    private final Property<Character>[][] grid;
    private final Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("unchecked")
    public CapitalsGame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Property[rows][cols];
        initializeGrid();
    }

    private void initializeGrid() {
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char letter = (char) ('a' + (i * cols + j));
                grid[i][j] = new Property<>(this, rand.nextBoolean() ? Character.toUpperCase(letter) : letter);
                grid[i][j].addListener((property, oldValue, newValue) -> displayGrid());
            }
        }
    }

    private void displayGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j].get() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void toggleCells(char selected) {
        selected = Character.toLowerCase(selected);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Character.toLowerCase(grid[i][j].get()) == selected) {
                    toggle(i, j);
                    if (i > 0) toggle(i - 1, j);
                    if (i < rows - 1) toggle(i + 1, j);
                    if (j > 0) toggle(i, j - 1);
                    if (j < cols - 1) toggle(i, j + 1);
                    return;
                }
            }
        }
    }

    private void toggle(int i, int j) {
        char current = grid[i][j].get();
        grid[i][j].set(Character.isUpperCase(current) ? Character.toLowerCase(current) : Character.toUpperCase(current));
    }

    public void play() {
        while (true) {
            displayGrid();
            System.out.print("Enter a letter to toggle (or 'q' to quit): ");
            char choice = scanner.next().charAt(0);
            if (choice == 'q') break;
            toggleCells(choice);
            if (isGameWon()) {
                System.out.println("Congratulations! All letters are capitalized!");
                break;
            }
        }
    }

    private boolean isGameWon() {
        for (Property<Character>[] row : grid) {
            for (Property<Character> cell : row) {
                if (Character.isLowerCase(cell.get())) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Capitals Game!");
        System.out.print("Enter the number of rows: ");
        Integer rows = scanner.nextInt();
        System.out.print("Enter the number of columns: ");
        Integer cols = scanner.nextInt();
        CapitalsGame game = new CapitalsGame(rows, cols);
        game.play();
    }
}
