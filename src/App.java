import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class App extends Application {

    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 600;

    private final static double n = 50; // the inner radius from hexagon center to outer corner
    private final static double r = Math.sqrt(n * n * 0.75); // the inner radius from hexagon center to middle of the
                                                             // axis
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        AnchorPane tileMap = new AnchorPane();
        Scene content = new Scene(tileMap, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(content);

        int rowCount = 4; // how many rows of tiles should be created
        int columnCount = 4; // how many columns of tiles should be created
        int tilesPerRow = 6; // the amount of tiles that are contained in each row
        int tilesPerColumn = 6; // the amount of tiles that are contained in each column
        int xStartOffset = 40; // offsets the entire field to the right
        int yStartOffset = 40; // offsets the entire fields downwards

        // Vertical alignment grid
        // for (int x = 0; x < tilesPerRow; x++) {
        // for (int y = 0; y < rowCount; y++) {
        // double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
        // double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

        // Polygon tile = new VerticalTile(xCoord, yCoord);
        // tileMap.getChildren().add(tile);
        // }
        // }

        // Horizontal alignment grid
        for (int y = 0; y < tilesPerColumn; y++) {
            for (int x = 0; x < rowCount; x++) {
                double yCoord = y * TILE_HEIGHT + (x % 2) * r + yStartOffset;
                double xCoord = x * TILE_WIDTH * 0.75 + xStartOffset;

                Polygon tile = new HorizontalTile(xCoord, yCoord);
                tileMap.getChildren().add(tile);
            }
        }
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
                    x + TILE_WIDTH, y + r,
                    x + TILE_WIDTH, y,
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

        HorizontalTile(double x, double y) {
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
}