import javafx.scene.layout.AnchorPane;

public class Board extends AnchorPane {
        private boolean offset;
        private int columnCount,
                        rowCount;
        private double xPadding = 0,
                        yPadding = 0;
        private HexagonTile[][] tiles;
        private boolean boardered = true;

        Board(int width, int hieght, double circumRadius, boolean offset, double extendQ, double extendS,
                        double extendR) {
                double inRadius = Math.sqrt(circumRadius * circumRadius * .75);
                tiles = new HexagonTile[hieght + (boardered ? 2 : 0)][width + (boardered ? 2 : 0)];

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
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'w', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                                                'y', extendQ);
                        } else if (x == lastRow - 1) {
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'x', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                                                'z', extendQ);
                        } else {
                                tiles[0][x] = new HexagonTile(xCoordTop, yCoordTop, circumRadius,
                                                'o', extendQ);
                                tiles[lastRow][x] = new HexagonTile(xCoordBot, yCoordBot, circumRadius,
                                                'o', extendQ);
                        }
                        getChildren().addAll(tiles[0][x], tiles[lastRow][x]);
                }
                for (int y = 0; y < hieght; y++) {
                        for (int x = 0; x < width; x++) {
                                double xCoord = x * 2 * (circumRadius * 0.75 + extendQ)
                                                + (width / 2) * (2 * (circumRadius * 0.75 + extendQ));
                                double yCoord = y * 2 * inRadius + ((x + (offset ? 1 : 0)) % 2) * inRadius
                                                + (hieght / 2) * (2 * inRadius);

                                tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)] = new HexagonTile(xCoord,
                                                yCoord, circumRadius, 'o', extendQ);
                                getChildren().add(tiles[y + (boardered ? 1 : 0)][x + (boardered ? 1 : 0)]);
                                setStyle("-fx-background-color: #FA15A1");
                        }
                }

        }
}
