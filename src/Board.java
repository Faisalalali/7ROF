import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;

public class Board extends AnchorPane {
    private boolean offset;
    private int columnCount,
            rowCount;
    private final double xPadding = 0, yPadding = 0;
    private HexagonTile[][] tiles;
    private HexagonTile[] borderTilesTopDown;
    private HexagonTile[] borderTilesSides;
    private Text[][] textTiles;
    private boolean bordered = true;

    private double extendQ = 0, extendS = 0, extendR = 0;
    private double circumRadius = 0;
    private int width = 0, height = 0;
    private double inRadius = 0;
    private double v = 0;

    Random random = new Random();
    List<String> list = new ArrayList<>(Arrays.asList("أ", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
            "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", "ن", "هـ", "و", "ي",
            "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    Board(int width, int height, double circumRadius, boolean offset, double extendQ, double extendS,
            double extendR) {
        this.extendQ = extendQ;
        this.extendS = extendS;
        this.extendR = extendR;
        this.circumRadius = circumRadius;
        this.width = width;
        this.height = height;

        this.offset = offset;
        this.columnCount = width;
        this.rowCount = height;
        double inRadius = Math.sqrt(circumRadius * circumRadius * .75);
        this.inRadius = inRadius;
        tiles = new HexagonTile[height + (bordered ? 2 : 0)][width + (bordered ? 2 : 0)];
        textTiles = new Text[height + (bordered ? 2 : 0)][width + (bordered ? 2 : 0)];

        // Side (Left and right) boarders
        borderTilesSides = new HexagonTile[(height + (bordered ? 1 : 0)) * 2];
        for (int y = 1; y < tiles.length; y++) {
            double xCordFront = (1) * (2 * (circumRadius * 0.75 + extendQ))
                    + (width / 2. - 2) * (2 * (circumRadius * 0.75 + extendQ));
            double yCordFront = y * 2 * inRadius + ((offset ? 0 : 1) % 2) * inRadius
                    + (height / 2. - 1) * (2 * inRadius);

            int lastCol = tiles[0].length - 1;
            double xCordBack = lastCol * 2 * (circumRadius * 0.75 + extendQ)
                    + (width / 2. - 1) * (2 * (circumRadius * 0.75 + extendQ));
            double yCordBack = y * 2 * inRadius + ((lastCol + (offset ? 0 : 1)) % 2) *
                    inRadius + (height / 2. - 1) * (2 * inRadius);
            tiles[y][0] = new HexagonTile(xCordFront, yCordFront, circumRadius, 'l',
                    extendQ);
            tiles[y][lastCol] = new HexagonTile(xCordBack, yCordBack, circumRadius,
                    'r', extendQ);
            borderTilesSides[y - 1] = tiles[y][0];
            borderTilesSides[y + lastCol - 1] = tiles[y][lastCol];
            getChildren().addAll(tiles[y][0], tiles[y][lastCol]);
        }

        // Top and bottom boarders
        borderTilesTopDown = new HexagonTile[(width + (bordered ? 1 : 0)) * 2];

        double v = (width / 2.) * (2 * (circumRadius * 0.75 + extendQ));
        this.v = v;
        for (int x = 1; x < tiles[0].length - 1; x++) {
            double xCoordTop = (x - 1) * 2 * (circumRadius * 0.75 + extendQ)
                    + v;
            double v1 = ((x + (offset ? 0 : 1)) % 2) * inRadius;
            double yCoordTop = (-1) * 2 * inRadius + v1
                    + (height / 2.) * (2 * inRadius);

            int lastRow = tiles.length - 1;
            double yCordBot = (lastRow - 1) * 2 * inRadius + v1
                    + (height / 2.) * (2 * inRadius);

            HexagonTile topTile;
            HexagonTile botTile;

            if (x == 1) {
                // First Column
                topTile = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'w', extendQ);
                botTile = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                        'f', extendQ);
            } else if (x == lastRow - 1) {
                // Last Column
                topTile = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'x', extendQ);
                botTile = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                        'f', extendQ);
            } else if (x % 2 == 0) {
                // Even Columns
                topTile = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'c', extendQ);
                botTile = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                        'n', extendQ);
            } else {
                // Odd Columns
                topTile = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'm', extendQ);
                botTile = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                        'f', extendQ);
            }

            tiles[0][x] = topTile;
            tiles[lastRow][x] = botTile;

            borderTilesTopDown[x - 1] = topTile;
            borderTilesTopDown[x + lastRow - 1] = botTile;
            getChildren().addAll(tiles[0][x], tiles[lastRow][x]);
        }

        // Center board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double xCord = x * 2 * (circumRadius * 0.75 + extendQ)
                        + v;
                double yCord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (height / 2.) * (2 * inRadius);

                tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)] = new HexagonTile(xCord,
                        yCord, circumRadius, 'o', extendQ);
                getChildren().add(tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)]);
                // setStyle("-fx-background-color: #FA15A1");
            }
        }

        // Letters
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // calculate center of tile
                double CenterX = x * 2 * (circumRadius * 0.75 + extendQ) + v;
                double CenterY = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (height / 2.) * (2 * inRadius);

                // add text to tiles
                Text text = new Text(
                        // x + "," + y);
                        list.get(0));
                textTiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)] = text;
                list.remove(0);

                // apply font
                Font font = Font.font(
                        // "file:resources/Fonts/Noto/NotoKufiArabic-VariableFont_wght.ttf",
                        "file:resources/Fonts/El_Messiri/ElMessiri-VariableFont_wght.ttf",
                        FontWeight.BOLD,
                        50);
                text.setFont(font);

                // apply custom styles
                text.setFill(Color.BLUE);
                Lighting ds = new Lighting();
                Light.Distant light = new Light.Distant();
                light.setAzimuth(0);
                Lighting lighting = new Lighting(light);
                lighting.setSurfaceScale(5.0);
                SimpleDoubleProperty azimuth = new SimpleDoubleProperty(0);
                light.setAzimuth(azimuth.get());
                lighting.setLight(light);
                text.setEffect(lighting);
                text.setEffect(ds);

                // set text position in the middle of the hexagon
                double fixedX = CenterX - text.getLayoutBounds().getWidth() / 2;
                double fixedY = CenterY + text.getLayoutBounds().getHeight() / 4;
                text.setX(fixedX);
                text.setY(fixedY);

                getChildren().add(text);
                // setStyle("-fx-background-color: #FA15A1");
            }
        }

        // Color Overlay
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double xCord = x * 2 * (circumRadius * 0.75 + extendQ)
                        + v;
                double yCord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (height / 2.) * (2 * inRadius);

                tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)] = new HexagonTile(xCord,
                        yCord, circumRadius, 'o', extendQ);
                tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)].setOverlay();
                getChildren().add(tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)]);
                // setStyle("-fx-background-color: #FA15A1");
            }
        }

    }

    public void shuffle() {
        list = new ArrayList<>(Arrays.asList("أ", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
                "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", "ن", "هـ", "و", "ي",
                "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        Collections.shuffle(list, random);
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                double CenterX = x * 2 * (circumRadius * 0.75 + extendQ) + v;
                double CenterY = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (height / 2.) * (2 * inRadius);
                Text text = textTiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)];
                text.setText(
                        // x + "," + y
                        list.get(0));
                // set text position in the middle of the hexagon
                double fixedX = CenterX - text.getLayoutBounds().getWidth() / 2;
                double fixedY = CenterY + text.getLayoutBounds().getHeight() / 4;
                text.setX(fixedX);
                text.setY(fixedY);
                list.remove(0);
            }
        }
    }

    public void resetColors() {
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)].resetColor();
            }
        }
    }

    public void deselectAll() {
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)].resetColor();
            }
        }
    }

    public Text getTileText(int x, int y) {
        return textTiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)];
    }

    public void blinkGreen(double duration) {
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                HexagonTile tile = tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)];
                if (tile != null) {
                    tile.blinkGreen(duration);
                }
            }
        }
        for (int y = 0; y < borderTilesTopDown.length; y++) {
            if (borderTilesTopDown[y] != null) {
                borderTilesTopDown[y].blinkGreen(duration);
            }
        }
    }

    public void blinkRed(double duration) {
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                tiles[y + (bordered ? 1 : 0)][x + (bordered ? 1 : 0)].blinkRed(duration);
            }
        }
        for (int y = 0; y < borderTilesSides.length; y++) {
            HexagonTile tile = borderTilesSides[y];
            if (tile != null) {
                tile.blinkRed(duration);
            }
        }
    }
}
