import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexagonTile extends Polygon {
    // Horizontal orintation Hesagon equations by me
    // https://www.desmos.com/calculator/f5ht6dvrcp

    private double x, y;
    private double circumRadius,
            inRadius;
    private double extendQ,
            extendS,
            extendR;
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

    public HexagonTile(double xPosition, double yPosition, double circumRadius) {
        this(xPosition, yPosition, circumRadius, 'o', 0);
    }

    public HexagonTile(double xPosition, double yPosition, double circumRadius, char shape, double extendQ) {
        this.x = xPosition;
        this.y = yPosition;
        this.circumRadius = circumRadius;
        this.inRadius = Math.sqrt(circumRadius * circumRadius * .75);
        this.boarderDeform = shape;

        if ("omnxrcyz".indexOf(shape) != -1) {
            getPoints().addAll(x - .5 * circumRadius - extendQ - .5 * extendS + .5 * circumRadius * extendR,
                    y + inRadius + inRadius * extendS + inRadius * extendR);// add vertex 1
        }
        if ("mxf".indexOf(shape) != -1) {
            getPoints().addAll();// add vertex d
        }
        if ("mwf".indexOf(shape) != -1) {
            getPoints().addAll();// add vetex e
        }
        if (shape == 'l') {
            getPoints().addAll();// add vetex b
        }
        if (shape == 'r') {
            getPoints().addAll();// add vertices acg
        }
        if ("omnwlcyz".indexOf(shape) != -1) {
            getPoints().addAll(x + .5 * circumRadius + extendQ - .5 * extendS + .5 * circumRadius * extendR,
                    y + inRadius + inRadius * extendS + inRadius * extendR);// add vertex 2
        }
        if ("omnxwlyz".indexOf(shape) != -1) {
            getPoints().addAll(x + circumRadius + extendQ + .5 * circumRadius * extendS + .5 * circumRadius * extendR,
                    y - inRadius * extendS + inRadius * extendR);// add vertex 3
        }
        if ("omnxwlfy".indexOf(shape) != -1) {
            getPoints().addAll(x + .5 * circumRadius + extendQ + .5 * extendS - .5 * circumRadius * extendR,
                    y - inRadius - inRadius * extendS - inRadius * extendR);// add vertex 4
        }
        if (shape == 'l') {
            getPoints().addAll();// add vertices hf
        }
        if ("ncy".indexOf(shape) != -1) {
            getPoints().addAll();// add vertex e
        }
        if ("ncz".indexOf(shape) != -1) {
            getPoints().addAll();// add vertex d
        }
        if ("omnxwrfz".indexOf(shape) != -1) {
            getPoints().addAll(x - .5 * circumRadius - extendQ + .5 * extendS - .5 * circumRadius * extendR,
                    y - inRadius - inRadius * extendS - inRadius * extendR);// add vertex 5
        }
        if ("omnxwryz".indexOf(shape) != -1) {
            getPoints().addAll(x - circumRadius - extendQ - .5 * circumRadius * extendS - .5 * circumRadius * extendR,
                    y + inRadius * extendS - inRadius * extendR);// add vertex 6
        }
        setFill(Color.ANTIQUEWHITE);

        setStrokeWidth(1);
        setStroke(Color.BLACK);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public double[] getCenter(){
        return new double[]{x,y};
    }
}
