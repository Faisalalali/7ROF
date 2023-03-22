import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;

// import com.github.kwhat.jnativehook.GlobalScreen;
// import com.github.kwhat.jnativehook.NativeHookException;
// import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
// import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class App extends Application {

    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 700;

    private final static double n = 50; // the inner radius from hexagon center to outer corner
    private final static double r = Math.sqrt(n * n * 0.75); // the inner radius from hexagon center to middle of the
                                                             // axis

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        AnchorPane tileMap = new AnchorPane();
        tileMap.setBackground(Background.EMPTY);
        Scene content = new Scene(tileMap, WINDOW_WIDTH, WINDOW_HEIGHT);
        content.setFill(Color.TRANSPARENT);
        primaryStage.setScene(content);

        // Horizontal alignment grid
        HorizontalHexagonGrid board = new HorizontalHexagonGrid(50, 5, 5);
        tileMap.getChildren().add(board);
        primaryStage.show();

        /* KeyboardListener listener = new KeyboardListener();
        listener.register(); */

    }

    private class HorizontalBoarder extends Polygon {

        private double x;
        private double y;
        private char orientation;

        HorizontalBoarder(double x, double y, double n, double r, char orientation) {
            super(x, y, n, r, orientation, 1);
        }

        HorizontalBoarder(double x, double y, double n, double r, char orientation, double ExtraX) {
            // creates the polygon using the corner coordinates
            if (orientation == 'r' || orientation == 'R') {
                getPoints().addAll(
                        x, y,
                        x + n + n * 1.5 * (ExtraX - 1), y,
                        x + n * .5 * ExtraX, y + r,
                        x + n + n * 1.5 * (ExtraX - 1), y + 2 * r,
                        x, y + 2 * r,
                        x - n * 0.5, y + r);
                setFill(Color.RED);

            } else if (orientation == 'l' || orientation == 'L') {
                getPoints().addAll( // dunno how, but it works!
                        x + n * (ExtraX - 1) * 1.5, y,
                        x + n * (ExtraX - 1) * 1.5 + n, y,
                        x + n * (ExtraX - 1) * 1.5 + n * 1.5, y + r,
                        x + n * (ExtraX - 1) * 1.5 + n, y + 2 * r,
                        x + n * (ExtraX - 1) * 1.5, y + 2 * r,
                        x + n * (ExtraX - 1) * 1.5 + n * 0.5, y + r);
                setFill(Color.RED);
            } else if (orientation == 't' || orientation == 'T' || orientation == 'u' || orientation == 'U') {
                // TODO: custom shapes for top & bottom boarders
                // top left (connected), top left (single, aka middle)
                // top middle (odd), top middle (even)
                // top right (odd), top left (even)
                getPoints().addAll(
                        x + n * 0.5 + n * 1.5 * (ExtraX - 1), y + r,
                        x + n + n * 1.5 * (ExtraX - 1), y,
                        x + n * 1.5 * ExtraX, y + r,
                        x + n + n * 1.5 * (ExtraX - 1), y + 2 * r,
                        x, y + 2 * r,
                        x - n * 0.5, y + r);
                setFill(Color.GREEN);
            } else if (orientation == 'b' || orientation == 'B' || orientation == 'd' || orientation == 'D') {
                // TODO: same as prevoise but Bottom parts.
                getPoints().addAll(
                        x, y,
                        x + n + n * 1.5 * (ExtraX - 1), y,
                        x + n * 1.5 * ExtraX, y + r,
                        x + n + n * 1.5 * (ExtraX - 1), y + 2 * r,
                        x, y + 2 * r,
                        x - n * 0.5, y + r);
                setFill(Color.GREEN);
            }

            this.x = x;
            this.y = y;

            // set up the visuals and a click listener for the tile
            // setFill(Color.ANTIQUEWHITE);

            setStrokeWidth(1);
            setStroke(Color.BLACK);
        }

    }

    private class HorizontalHexagonGrid extends AnchorPane {
        private double r, // the inner radius from hexagon center to middle of the axis
                n; // the inner radius from hexagon center to outer corner
        private int columnCount, // how many columns of tiles should be created
                tilesPerColumn; // the amount of tiles that are contained in each column

        double xStartOffset = 0; // offsets the entire field to the right
        double xBoarderOffset = 0;
        double yStartOffset = 0; // offsets the entire fields downwards
        double yBoarderOffset = 0;

        HorizontalBoarder[] side;
        HorizontalBoarder[] topDown;
        HexagonTile[] grid;
        Text[] textGrid;

        Board board;

        HorizontalHexagonGrid(double scale, int columnCount, int tilesPerColumn) {
            this.n = scale;
            this.r = Math.sqrt(scale * scale * 0.75);
            this.columnCount = columnCount;
            this.tilesPerColumn = tilesPerColumn;
            if (xStartOffset < n)
                this.xStartOffset = n;

            this.xBoarderOffset = n;
            this.yBoarderOffset = r;

            this.board = new Board(columnCount, tilesPerColumn, scale, true, 10, 0, 0);
            board.shuffle();
            getChildren().add(board);
            ResetButton resetButton = new ResetButton(10, 10, 100, 50, this.board);
            getChildren().add(resetButton);
            DeselectButton deselectButton = new DeselectButton(170, 10, 100, 50, this.board);
            getChildren().add(deselectButton);
        }
    }

    private class ResetButton extends Button {
        public ResetButton(double x, double y, double width, double height, Board board) {
            super("Reset");
            setLayoutX(x);
            setLayoutY(y);
            setPrefSize(width, height);
            setOnAction(e -> {
                board.shuffle();
                board.resetColors();
            });
        }
    }

    private class DeselectButton extends Button {
        public DeselectButton(double x, double y, double width, double height, Board board) {
            super("Deselect");
            setLayoutX(x);
            setLayoutY(y);
            setPrefSize(width, height);
            setOnAction(e -> {
                board.deselect();
            });
        }
    }
}

/* class KeyboardListener implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Handle key press event
        // System.out.println("Key pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        String deviceName = e.getSource().hashCode() + "";
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        System.out.println("Input from " + deviceName + ": " + keyText);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // Handle key release event
        // System.out.println("Key released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Handle key typed event
        // System.out.println("Key typed: " + e.getKeyChar());
    }

    public void register() {
        try {
            // Register keyboard listener
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new KeyboardListener());
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook: " + ex.getMessage());
        }
    }
} */