import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 600;

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
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        // primaryStage.initStyle(StageStyle.TRANSPARENT);
        // primaryStage.setOpacity(1);
        // primaryStage.initStyle(StageStyle.TRANSPARENT);
        // primaryStage.setAlwaysOnTop(true);

        // Grid contents
        String[] contentList = { "أ", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز", "س", "ش",
                "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", "ن", "هـ", "و", "ي",
                "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        java.util.Random random = new java.util.Random();
        // Horizontal alignment grid
        HorizontalHexagonGrid board = new HorizontalHexagonGrid(50, 5, 5);
        tileMap.getChildren().add(board);
        primaryStage.show();

    }

    // https://www.desmos.com/calculator/rn1ilpugiv
    private class VerticalTile extends Polygon {

        private double x;
        private double y;

        VerticalTile(double x, double y) {
            // creates the polygon using the corner coordinates
            getPoints().addAll(
                    x, y,
                    x, y + r,
                    x + n, y + r * 1.5,
                    x + 2 * n, y + r,
                    x + 2 * n, y,
                    x + n, y - r * 0.5);

            this.x = x;
            this.y = y;

            // set up the visuals and a click listener for the tile
            setFill(Color.ANTIQUEWHITE);
            setStrokeWidth(1);
            setStroke(Color.BLACK);
            setOnMouseClicked(e -> System.out.println("Clicked: " + this));
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "(" + getX() + ", " + getY() + ")";
        }
    }

    // https://www.desmos.com/calculator/i4ixjjbmcm
    private class HorizontalTile extends Polygon {

        private double x;
        private double y;
        private double[] center;

        HorizontalTile(double x, double y, double n, double r) {
            // creates the polygon using the corner coordinates
            getPoints().addAll(
                    x, y,
                    x + n, y,
                    x + n * 1.5, y + r,
                    x + n, y + 2 * r,
                    x, y + 2 * r,
                    x - n * 0.5, y + r);

            this.x = x;
            this.y = y;
            this.center = new double[] { x + .5 * n, y + r };
            // set up the visuals and a click listener for the tile
            setFill(Color.ANTIQUEWHITE);

            setStrokeWidth(1);
            setStroke(Color.BLACK);
            setOnMouseClicked(e -> System.out.println("Clicked: " + this));
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double[] getCenter() {
            return center;
        }

        @Override
        public String toString() {
            return "(" + center[0] + ", " + center[1] + ")";
        }
    }

    private class TextHorizontalTile extends StackPane {
        private HorizontalTile tile;
        private Text text;

        TextHorizontalTile(double x, double y, String content) {
            this.text = new Text(x, y, content);
            tile = new HorizontalTile(x, y, n, r);
            getChildren().addAll(tile, this.text);
            // setOnMouseClicked(e -> System.out.println("Clicked: " + this)); // causes
            // inaccurate clicks

        }

    }

    private class HorizontalHexagonGrid extends AnchorPane {
        private double r, // the inner radius from hexagon center to middle of the axis
                n; // the inner radius from hexagon center to outer corner
        private int columnCount, // how many columns of tiles should be created
                tilesPerColumn; // the amount of tiles that are contained in each column

        double xStartOffset = 0; // offsets the entire field to the right
        double yStartOffset = 0; // offsets the entire fields downwards

        HorizontalTile[] grid;
        Text[] textGrid;
        StackPane[] stackGrid;

        HorizontalHexagonGrid(double scale, int columnCount, int tilesPerColumn) {
            this.n = scale;
            this.r = Math.sqrt(scale * scale * 0.75);
            this.columnCount = columnCount;
            this.tilesPerColumn = tilesPerColumn;
            this.xStartOffset = .5 * n;

            // Horizontal alignment grid
            grid = new HorizontalTile[columnCount * tilesPerColumn];
            textGrid = new Text[columnCount * tilesPerColumn];
            stackGrid = new StackPane[columnCount * tilesPerColumn];

            for (int y = 0; y < tilesPerColumn; y++) {
                for (int x = 0; x < columnCount; x++) {
                    double yCoord = y * 2 * r + (x % 2) * r + yStartOffset;
                    double xCoord = x * 2 * n * 0.75 + xStartOffset;

                    HorizontalTile tile = new HorizontalTile(xCoord, yCoord, n, r);
                    grid[y * columnCount + x] = tile;
                    getChildren().add(tile);
                    setStyle("-fx-background-color: #fA15A1");
                }
            }
            // Why the hell won't the text show
            
            // for (int i = 0; i < grid.length; i++) {
            //     double x = grid[i].getCenter()[0], y = grid[i].getCenter()[1];
            //     textGrid[i] = new Text(x, y, "t");
            //     textGrid[i].toFront();
            //     textGrid[i].setStyle("-fx-background-color: #fA15A1");
            //     stackGrid[i] = new StackPane();
            //     stackGrid[i].getChildren().addAll(grid[i], textGrid[i]);
            // }
        }
    }
}