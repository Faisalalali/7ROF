import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;

public class Board extends AnchorPane {
    private boolean offset;
    private int columnCount,
            rowCount;
    private double xPadding = 0,
            yPadding = 0;
    private HexagonTile[][] tiles;
    private Text[][] textTiles;
    private boolean boardered = true;

    Board(int width, int hieght, double circumRadius, boolean offset, double extendQ, double extendS,
            double extendR) {
        double inRadius = Math.sqrt(circumRadius * circumRadius * .75);
        tiles = new HexagonTile[hieght + (boardered ? 2 : 0)][width + (boardered ? 2 : 0)];
        textTiles = new Text[hieght + (boardered ? 2 : 0)][width + (boardered ? 2 : 0)];

        // Side (Left and right) boarders
        for (int y = 1; y < tiles.length; y++) {
            double xCoordFront = (1) * (2 * (circumRadius * 0.75 + extendQ));
            double yCoordFront = y * 2 * inRadius + ((offset ? 0 : 1) % 2) * inRadius
                    + (hieght / 2 - 1) * (2 * inRadius);

            int lastCol = tiles[0].length - 1;
            double xCoordBack = lastCol * 2 * (circumRadius * 0.75 + extendQ)
                    + (width / 2 - 1) * (2 * (circumRadius * 0.75 + extendQ));
            double yCoordBack = y * 2 * inRadius + ((lastCol + (offset ? 0 : 1)) % 2) *
                    inRadius + (hieght / 2 - 1) * (2 * inRadius);
            tiles[y][0] = new HexagonTile(xCoordFront, yCoordFront, circumRadius, 'l',
                    extendQ);
            tiles[y][lastCol] = new HexagonTile(xCoordBack, yCoordBack, circumRadius,
                    'r', extendQ);
            getChildren().addAll(tiles[y][0], tiles[y][lastCol]);
        }

        // Top and bottom boarders
        for (int x = 1; x < tiles[0].length - 1; x++) {
            double xCoordTop = (x - 1) * 2 * (circumRadius * 0.75 + extendQ)
                    + (width / 2) * (2 * (circumRadius * 0.75 + extendQ));
            double yCoordTop = (-1) * 2 * inRadius + ((x + (offset ? 0 : 1)) % 2) * inRadius
                    + (hieght / 2) * (2 * inRadius);

            int lastRow = tiles.length - 1;
            double xCoordBot = (x - 1) * 2 * (circumRadius * 0.75 + extendQ)
                    + (width / 2) * (2 * (circumRadius * 0.75 + extendQ));
            double yCoordBot = (lastRow - 1) * 2 * inRadius + ((x + (offset ? 0 : 1)) % 2) * inRadius
                    + (hieght / 2) * (2 * inRadius);

            if (x == 1) {
                // First Column
                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'w', extendQ);
                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                        'f', extendQ);
            } else if (x == lastRow - 1) {
                // Last Column
                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'x', extendQ);
                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                        'f', extendQ);
            } else if (x % 2 == 0) {
                // Even Columns
                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'c', extendQ);
                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                        'n', extendQ);
            } else if (x % 2 == 1) {
                // Odd Columns
                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                        'm', extendQ);
                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                        'f', extendQ);
            }
            // if (x == 1) {
            // tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
            // 'w', extendQ);
            // tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
            // 'y', extendQ);
            // } else if (x == lastRow - 1) {
            // tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
            // 'x', extendQ);
            // tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
            // 'z', extendQ);
            // } else {
            // tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
            // 'c', extendQ);
            // tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
            // 'f', extendQ);
            // }
            getChildren().addAll(tiles[0][x], tiles[lastRow][x]);
        }

        // Center board
        for (int y = 0; y < hieght; y++) {
            for (int x = 0; x < width; x++) {
                double xCoord = x * 2 * (circumRadius * 0.75 + extendQ)
                        + (width / 2) * (2 * (circumRadius * 0.75 + extendQ));
                double yCoord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (hieght / 2) * (2 * inRadius);

                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new HexagonTile(xCoord,
                        yCoord, circumRadius, 'o', extendQ);
                getChildren().add(tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]);
                // setStyle("-fx-background-color: #FA15A1");
            }
        }

        // Letters
        java.util.Random random = new java.util.Random();
        List<String> list = new ArrayList<>(Arrays.asList("أ", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
                "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", "ن", "هـ", "و", "ي", "1", "2", "3",
                "4", "5", "6", "7", "8", "9"));
        Collections.shuffle(list, random);
        for (int y = 0; y < hieght; y++) {
            for (int x = 0; x < width; x++) {
                double xCoord = x * 2 * (circumRadius * 0.75 + extendQ)
                        + (width / 2) * (2 * (circumRadius * 0.75 + extendQ)) - 15;
                double yCoord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (hieght / 2) * (2 * inRadius) + 15;

                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new Text(xCoord, yCoord, list.get(0));
                list.remove(0);
                Font font = Font.font("file:resources/Fonts/Noto/NotoKufiArabic-VariableFont_wght.ttf", FontWeight.BOLD,
                        50);
                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setFont(font);
                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setTextAlignment(TextAlignment.CENTER);
                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setFill(Color.BLUE);
                Lighting ds = new Lighting();
                Light.Distant light = new Light.Distant();
                light.setAzimuth(0);

                Lighting lighting = new Lighting(light);
                lighting.setSurfaceScale(5.0);
                SimpleDoubleProperty azimuth = new SimpleDoubleProperty(0);

                light.setAzimuth(azimuth.get());
                lighting.setLight(light);
                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setEffect(lighting);

                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setEffect(ds);

                getChildren().add(textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]);
                // setStyle("-fx-background-color: #FA15A1");
            }
        }

        // Color Overlay

        for (int y = 0; y < hieght; y++) {
            for (int x = 0; x < width; x++) {
                double xCoord = x * 2 * (circumRadius * 0.75 + extendQ)
                        + (width / 2) * (2 * (circumRadius * 0.75 + extendQ));
                double yCoord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                        + (hieght / 2) * (2 * inRadius);

                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new HexagonTile(xCoord,
                        yCoord, circumRadius, 'o', extendQ);
                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setOverlay();
                getChildren().add(tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]);
                // setStyle("-fx-background-color: #FA15A1");
            }
        }

    }
}
