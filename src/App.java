import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        Game gameBoard = new Game(50, 5, 5);
        tileMap.getChildren().add(gameBoard);
        primaryStage.show();
    }

    private class Game extends AnchorPane {
        private Board board;

        Game(double scale, int columnCount, int tilesPerColumn) {
            this.board = new Board(columnCount, tilesPerColumn, scale, true, 10, 0, 0);
            getChildren().add(board);
            addButton("Reset", 10, 10, 100, 50, e -> {
                board.shuffle();
                board.resetColors();
            });
            addButton("Deselect", 170, 10, 100, 50, e -> board.deselectAll());
            addButton("Blink Green", 330, 10, 100, 50, e -> board.blinkGreen(6));
            addButton("Blink Red", 490, 10, 100, 50, e -> board.blinkRed(6));
        }

        private void addButton(String text, double x, double y, double width, double height, EventHandler<ActionEvent> action) {
            Button button = new Button(text);
            button.setLayoutX(x);
            button.setLayoutY(y);
            button.setPrefSize(width, height);
            button.setOnAction(action);
            getChildren().add(button);
        }
    }
}
