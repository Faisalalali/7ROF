import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexagonTile extends Polygon {
    // Horizontal orintation Hesagon equations by me
    // https://www.desmos.com/calculator/f5ht6dvrcp

    private double x, y;
    private double circumRadius,
            inRadius;
    private double Qextend,
            Sextend,
            Rextend;
    private char boarderDeform; /*
                                 * 'o': Full size (origional)
                                 * 'l': Left boarder piece
                                 * 'r': Right boarder piece
                                 * 'w': Top left boarder piece
                                 * 'x': Top right boarder piece
                                 * 'y': Bottom left boarder piece
                                 * 'z': Bottom right boarder piece
                                 * 'm': Top middle boarder piece
                                 * 'n': Bottom middle boarder piece
                                 * 'c': Top high boarder piece (ceiling)
                                 * 'f': Bottom low boarder piece (floor)
                                 */
    private double xOffset, yOffset;
    private Color color;
}
