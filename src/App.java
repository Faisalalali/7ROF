import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.dsig.SignatureProperty;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;

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
        private int state = 0;
        private final int DEFAULT_COLOR = 0;
        private final int SELECTED_COLOR = 1;
        private final int GREEN_COLOR = 2;
        private final int RED_COLOR = 3;

        HorizontalTile(double x, double y, double n, double r) {
            super(x, y, n, r, 0);
        }

        HorizontalTile(double x, double y, double n, double r, double ExtraX) {
            // creates the polygon using the corner coordinates
            getPoints().addAll(
                    x, y,
                    x + n + n * 1.5 * (ExtraX - 1), y,
                    x + n * 1.5 * ExtraX, y + r,
                    x + n + n * 1.5 * (ExtraX - 1), y + 2 * r,
                    x, y + 2 * r,
                    x - n * 0.5, y + r);

            this.x = x;
            this.y = y;

            double xAverage = x + (n + n * 1.5 * (ExtraX - 1)) / 2;
            double yAverage = y + r;
            this.center = new double[] { xAverage, yAverage };
            // set up the visuals and a click listener for the tile
            setFill(Color.ANTIQUEWHITE);

            setStrokeWidth(1);
            setStroke(Color.BLACK);
            setOnMouseClicked(e -> setState((getState() + 1) % 4));

        }

        public void setState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

        private void updateState() {
            if (state == DEFAULT_COLOR)
                setFill(Color.ANTIQUEWHITE);
            else if (state == SELECTED_COLOR)
                setFill(Color.YELLOW);
            else if (state == GREEN_COLOR)
                setFill(Color.GREEN);
            else if (state == RED_COLOR)
                setFill(Color.RED);
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
            return "(" + (int) (getX() / 2 / n / .75) + ", " + getY() + ")";
        }
    }

    private class HorizontalBoarder extends Polygon {

        private double x;
        private double y;
        private char orintation;

        HorizontalBoarder(double x, double y, double n, double r, char orintation) {
            super(x, y, n, r, orintation, 1);
        }

        HorizontalBoarder(double x, double y, double n, double r, char orintation, double ExtraX) {
            // creates the polygon using the corner coordinates
            if (orintation == 'r' || orintation == 'R') {
                getPoints().addAll(
                        x, y,
                        x + n + n * 1.5 * (ExtraX - 1), y,
                        x - n * 1.5 * ExtraX, y + r,
                        x + n + n * 1.5 * (ExtraX - 1), y + 2 * r,
                        x, y + 2 * r,
                        x - n * 0.5, y + r);
            } else if (orintation == 'l' || orintation == 'L') {
                getPoints().addAll(
                        x, y,
                        x + n + n * 1.5 * (ExtraX - 1), y,
                        x + n * 1.5 * ExtraX, y + r,
                        x + n + n * 1.5 * (ExtraX - 1), y + 2 * r,
                        x, y + 2 * r,
                        x + n * 0.5, y + r);
            } else if (orintation == 't' || orintation == 'T' || orintation == 'u' || orintation == 'U') {

            } else if (orintation == 'b' || orintation == 'B' || orintation == 'd' || orintation == 'D') {

            }

            this.x = x;
            this.y = y;

            // set up the visuals and a click listener for the tile
            setFill(Color.ANTIQUEWHITE);

            setStrokeWidth(1);
            setStroke(Color.BLACK);
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
        double xBoarderOffset = 0;
        double yStartOffset = 0; // offsets the entire fields downwards
        double yBoarderOffset = 0;

        HorizontalBoarder[] side;
        HorizontalTile[] grid;
        Text[] textGrid;

        HorizontalHexagonGrid(double scale, int columnCount, int tilesPerColumn) {
            this.n = scale;
            this.r = Math.sqrt(scale * scale * 0.75);
            this.columnCount = columnCount;
            this.tilesPerColumn = tilesPerColumn;
            this.xStartOffset = .5 * n;
            double extraWidth = 1.4;

            this.xBoarderOffset = n;
            this.yBoarderOffset = r;
            // Horizontal alignment grid
            grid = new HorizontalTile[columnCount * tilesPerColumn];
            textGrid = new Text[columnCount * tilesPerColumn];
            side = new HorizontalBoarder[columnCount * 2];
            // topDown = new HorizontalBoarder[tilesPerColumn*2];

            // TODO fix boarder adding (right-left for now)
            for (int y = -1; y < tilesPerColumn + 1; y++) {
                double yCoordLeft = y * 2 * r + yStartOffset + yBoarderOffset;
                double xCoordLeft = xStartOffset + xBoarderOffset;

                double yCoordRight = y * 2 * r + (columnCount % 2) * r + yStartOffset + yBoarderOffset;
                double xCoordRight = columnCount * 2 * n * 0.75 * extraWidth + xStartOffset + xBoarderOffset;

                side[y] = new HorizontalBoarder(xCoordLeft, yCoordLeft, n, r, 'l', extraWidth);
                side[y + 1] = new HorizontalBoarder(xCoordRight, yCoordRight, n, r, 'r', extraWidth);
                getChildren().addAll(side[y], side[y + 1]);
                setStyle("-fx-background-color: #fA15A1");
            }
            for (int y = 0; y < tilesPerColumn; y++) {
                for (int x = 0; x < columnCount; x++) {
                    double yCoord = y * 2 * r + (x % 2) * r + yStartOffset + yBoarderOffset;
                    double xCoord = x * 2 * n * 0.75 * extraWidth + xStartOffset + xBoarderOffset;

                    grid[y * columnCount + x] = new HorizontalTile(xCoord, yCoord, n, r, extraWidth);
                    getChildren().add(grid[y * columnCount + x]);
                    setStyle("-fx-background-color: #fA15A1");
                }
            }
            // Why the hell won't the text show.
            // Grid contents
            java.util.Random random = new java.util.Random();
            List<String> list = new ArrayList<>(Arrays.asList("أ", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
                    "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", "ن", "هـ", "و", "ي",
                    "1", "2", "3", "4", "5", "6", "7", "8", "9"));
            Collections.shuffle(list, random);
            for (int i = 0; i < grid.length; i++) {
                double x = grid[i].getCenter()[0], y = grid[i].getCenter()[1];
                textGrid[i] = new Text(list.get(i));
                Font font = Font.loadFont("file:resources/Fonts/Reem_Kufi/static/ReemKufi-Bold.ttf", 52);
                textGrid[i].setFont(font);
                textGrid[i].setTextAlignment(TextAlignment.CENTER);
                textGrid[i].setFill(Color.BLUE);
                Lighting ds = new Lighting();

                Light.Distant light = new Light.Distant();
                light.setAzimuth(0);

                Lighting lighting = new Lighting(light);
                lighting.setSurfaceScale(5.0);
                SimpleDoubleProperty azimuth = new SimpleDoubleProperty(0);

                light.setAzimuth(azimuth.get());
                lighting.setLight(light);
                textGrid[i].setEffect(lighting);

                textGrid[i].setEffect(ds);
                // textGrid[i]
                textGrid[i].xProperty().set(x - textGrid[i].getBoundsInLocal().getWidth() * .5);

                textGrid[i].yProperty().set(y + textGrid[i].getBoundsInLocal().getHeight() * .25);
                getChildren().add(textGrid[i]);
                // textGrid[i].toFront(); // no need due to insertion order
            }
        }
    }
}