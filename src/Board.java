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

public class Board extends AnchorPane {
        private boolean offset;
        private int columnCount,
                        rowCount;
        private final double xPadding = 0, yPadding = 0;
        private HexagonTile[][] tiles;
        private Text[][] textTiles;
        private boolean boardered = true;

        java.util.Random random = new java.util.Random();
        List<String> list = new ArrayList<>(Arrays.asList("أ", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز",
                        "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", "ن", "هـ", "و", "ي",
                        "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        Board(int width, int height, double circumRadius, boolean offset, double extendQ, double extendS,
                        double extendR) {
                this.offset = offset;
                this.columnCount = width;
                this.rowCount = height;
                double inRadius = Math.sqrt(circumRadius * circumRadius * .75);
                tiles = new HexagonTile[height + (boardered ? 2 : 0)][width + (boardered ? 2 : 0)];
                textTiles = new Text[height + (boardered ? 2 : 0)][width + (boardered ? 2 : 0)];

                // Side (Left and right) boarders
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
                        getChildren().addAll(tiles[y][0], tiles[y][lastCol]);
                }

                // Top and bottom boarders
                double v = (width / 2.) * (2 * (circumRadius * 0.75 + extendQ));
                for (int x = 1; x < tiles[0].length - 1; x++) {
                        double xCoordTop = (x - 1) * 2 * (circumRadius * 0.75 + extendQ)
                                        + v;
                        double v1 = ((x + (offset ? 0 : 1)) % 2) * inRadius;
                        double yCoordTop = (-1) * 2 * inRadius + v1
                                        + (height / 2.) * (2 * inRadius);

                        int lastRow = tiles.length - 1;
                        double yCordBot = (lastRow - 1) * 2 * inRadius + v1
                                        + (height / 2.) * (2 * inRadius);

                        if (x == 1) {
                                // First Column
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'w', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                                                'f', extendQ);
                        } else if (x == lastRow - 1) {
                                // Last Column
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'x', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                                                'f', extendQ);
                        } else if (x % 2 == 0) {
                                // Even Columns
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'c', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                                                'n', extendQ);
                        } else {
                                // Odd Columns
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'm', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordTop, yCordBot, circumRadius,
                                                'f', extendQ);
                        }
                        
                        getChildren().addAll(tiles[0][x], tiles[lastRow][x]);
                }

                // Center board
                for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                                double xCord = x * 2 * (circumRadius * 0.75 + extendQ)
                                                + v;
                                double yCord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                                                + (height / 2.) * (2 * inRadius);

                                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new HexagonTile(xCord,
                                                yCord, circumRadius, 'o', extendQ);
                                getChildren().add(tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]);
                                // setStyle("-fx-background-color: #FA15A1");
                        }
                }

                // Letters
                // Collections.shuffle(list, random);
                for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                                double xCord = x * 2 * (circumRadius * 0.75 + extendQ)
                                                + v - 15;
                                double yCord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                                                + (height / 2.) * (2 * inRadius) + 15;

                                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new Text(xCord, yCord,
                                                list.get(0));
                                list.remove(0);
                                Font font = Font.font("file:resources/Fonts/Noto/NotoKufiArabic-VariableFont_wght.ttf",
                                                FontWeight.BOLD,
                                                50);
                                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setFont(font);
                                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]
                                                .setTextAlignment(TextAlignment.CENTER);
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
                for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                                double xCord = x * 2 * (circumRadius * 0.75 + extendQ)
                                                + v;
                                double yCord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                                                + (height / 2.) * (2 * inRadius);

                                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new HexagonTile(xCord,
                                                yCord, circumRadius, 'o', extendQ);
                                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setOverlay();
                                getChildren().add(tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]);
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
                                textTiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].setText(list.get(0));
                                list.remove(0);
                        }
                }
        }

        public void resetColors() {
                for (int y = 0; y < rowCount; y++) {
                        for (int x = 0; x < columnCount; x++) {
                                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].resetColor();
                        }
                }
        }

        public void deselect() {
                for (int y = 0; y < rowCount; y++) {
                        for (int x = 0; x < columnCount; x++) {
                                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)].resetColor();
                        }
                }
        }
}
