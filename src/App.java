import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

// import com.github.kwhat.jnativehook.GlobalScreen;
// import com.github.kwhat.jnativehook.NativeHookException;
// import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
// import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class App extends Application {

    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 700;

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
        Game gameBoard = new Game(50, 5, 5);
        tileMap.getChildren().add(gameBoard);
        primaryStage.show();

        // KeyboardListener listener = new KeyboardListener();
        // listener.register();

    }

    private class Game extends AnchorPane {
        Board board;

        Game(double scale, int columnCount, int tilesPerColumn) {
            // create the board
            this.board = new Board(columnCount, tilesPerColumn, scale, true, 10, 0, 0);
            // board.shuffle();
            getChildren().add(board);
            ResetButton resetButton = new ResetButton(10, 10, 100, 50, this.board);
            getChildren().add(resetButton);
            DeselectButton deselectButton = new DeselectButton(170, 10, 100, 50, this.board);
            getChildren().add(deselectButton);
            BlinkGreenButton blinkGreenButton = new BlinkGreenButton(330, 10, 100, 50, this.board);
            getChildren().add(blinkGreenButton);
            BlinkRedButton blinkRedButton = new BlinkRedButton(490, 10, 100, 50, this.board);
            getChildren().add(blinkRedButton);
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
                board.deselectAll();
            });
        }
    }

    private class BlinkGreenButton extends Button {
        public BlinkGreenButton(double x, double y, double width, double height, Board board) {
            super("Blink Green");
            setLayoutX(x);
            setLayoutY(y);
            setPrefSize(width, height);
            setOnAction(e -> {
                board.blinkGreen(6);
            });
        }
    }

    private class BlinkRedButton extends Button {
        public BlinkRedButton(double x, double y, double width, double height, Board board) {
            super("Blink Red");
            setLayoutX(x);
            setLayoutY(y);
            setPrefSize(width, height);
            setOnAction(e -> {
                board.blinkRed(6);
            });
        }
    }
}

// class KeyboardListener implements NativeKeyListener {
//     public void nativeKeyPressed(NativeKeyEvent e) {
//         // Handle key press event
//         // System.out.println("Key pressed: " +
//         // NativeKeyEvent.getKeyText(e.getKeyCode()));
//         String deviceName = e.getSource().hashCode() + "";
//         String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
//         System.out.println("Input from " + deviceName + ": " + keyText);
//     }

//     public void nativeKeyReleased(NativeKeyEvent e) {
//         // Handle key release event
//         // System.out.println("Key released: " +
//         // NativeKeyEvent.getKeyText(e.getKeyCode()));
//     }

//     public void nativeKeyTyped(NativeKeyEvent e) {
//         // Handle key typed event
//         // System.out.println("Key typed: " + e.getKeyChar());
//     }

//     public void register() {
//         try {
//             // Register keyboard listener
//             GlobalScreen.registerNativeHook();
//             GlobalScreen.addNativeKeyListener(new KeyboardListener());
//         } catch (NativeHookException ex) {
//             System.err.println("Failed to register native hook: " + ex.getMessage());
//         }
//     }
// }