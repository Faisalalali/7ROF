import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class HexagonTile extends Polygon {
    final int UNSELECTED = 0;
    final int SELECTED = 1;
    final int GREEN = 2;
    final int RED = 3;
    // Horizontal orintation Hesagon equations by me
    // https://www.desmos.com/calculator/0tpb0oirpq

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
        this.state = UNSELECTED;
        double h1 = y - inRadius - inRadius * extendS + inRadius * extendR,
                h2 = y, // Generally y ± inRadius * extendS ∓ inRadius * extendR, but no need rn.
                h3 = y + inRadius + inRadius * extendS + inRadius * extendR,
                w1 = x - circumRadius - extendQ - .5 * circumRadius * extendS - .5 * circumRadius * extendR,
                w2 = x - .5 * circumRadius - extendQ - .5 * extendS + .5 * circumRadius * extendR,
                w3 = x - extendQ - .5 * extendS + .5 * circumRadius * extendR, // TODO apply scaling effect.
                w4 = x + extendQ - .5 * extendS + .5 * circumRadius * extendR, // TODO apply scaling effect.
                w5 = x + .5 * circumRadius + extendQ - .5 * extendS + .5 * circumRadius * extendR,
                w6 = x + circumRadius + extendQ - .5 * circumRadius * extendS + .5 * circumRadius * extendR;

        switch (shape) {
            case 'o': // getPoints().addAll(h1,w2,h1,w5,h2,w6,h3,w5,h3,w2,h2,w1);break;
                getPoints().addAll(
                        w2, h1,
                        w5, h1,
                        w6, h2,
                        w5, h3,
                        w2, h3,
                        w1, h2);
                setFill(Color.ANTIQUEWHITE);
                // setStyle(
                // "-fx-stroke-type: inside; -fx-stroke-width: 10px; -fx-stroke: black;
                // -fx-border-style: solid solid none solid none solid;");
                break;
            case 'l':
                getPoints().addAll(
                        (w2 + w4) / 2, h1,
                        w5, h1,
                        w6, h2,
                        w5, h3,
                        (w2 + w4) / 2, h3,
                        (w3 + w5) / 2, h2);
                setFill(CostumeColor.LIGHT_RED);
                this.state = RED;
                break;
            case 'r':
                getPoints().addAll(
                        w2, h1,
                        (w5 + w3) / 2, h1,
                        (w4 + w2) / 2, h2,
                        (w5 + w3) / 2, h3,
                        w2, h3,
                        w1, h2);
                setFill(CostumeColor.LIGHT_RED);
                this.state = RED;
                break;
            case 'w':
                getPoints().addAll(
                        w4, h2,
                        w5, h1,
                        w6, h2,
                        w5, h3,
                        w2, h3,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'x':
                getPoints().addAll(
                        w2, h1,
                        w3, h2,
                        w6, h2,
                        w5, h3,
                        w2, h3,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'y':
                getPoints().addAll(
                        w4, h2,
                        w5, h3,
                        w6, h2,
                        w5, h1,
                        w2, h1,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'z':
                getPoints().addAll(
                        w2, h3,
                        w3, h2,
                        w6, h2,
                        w5, h1,
                        w2, h1,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'c':
                getPoints().addAll(
                        // w4, h2,
                        // w5, h1,
                        w6, h2,
                        w5, h3,
                        w2, h3,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'f':
                getPoints().addAll(
                        // w2, h3,
                        // w3, h2,
                        w6, h2,
                        w5, h1,
                        w2, h1,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'm':
                getPoints().addAll(
                        w2, h1,
                        w3, h2, // middle
                        w4, h2, // middle
                        w5, h1,
                        w6, h2,
                        w5, h3,
                        w2, h3,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;
            case 'n':
                getPoints().addAll(
                        w2, h3,
                        w3, h2,
                        w4, h2,
                        w5, h3,
                        w6, h2,
                        w5, h1,
                        w2, h1,
                        w1, h2);
                setFill(CostumeColor.LIGHT_GREEN);
                this.state = GREEN;
                break;

            // cfmn are left
        }

        setStrokeWidth(1);
        setStroke(Color.BLACK);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double[] getCenter() {
        return new double[] { x, y };
    }

    int state = 0;

    /*
     * states:
     * 0: transparent
     * 1: selected
     * 2: green
     * 3: red
     */
    Timeline timeline;

    public void resetColor() {
        state = 0;
        setFill(Color.TRANSPARENT);
        // reset timeline
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            state = SELECTED;
            setBlendMode(BlendMode.MULTIPLY);
            setFill(Color.YELLOW);
            setStroke(Color.BLACK);
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.1), evt -> setBlendMode(BlendMode.MULTIPLY)),
                    new KeyFrame(Duration.seconds(0.4), evt -> setBlendMode(BlendMode.SOFT_LIGHT)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } else {
            state = UNSELECTED;
            setFill(Color.TRANSPARENT);
            setBlendMode(null);
            timeline.stop();
        }
    }

    public void setGreen() {
        setBlendMode(null);
        timeline.stop();
        state = GREEN;
        setFill(CostumeColor.LIGHT_GREEN);
    }

    public void setRed() {
        setBlendMode(null);
        timeline.stop();
        state = RED;
        setFill(CostumeColor.LIGHT_RED);
    }

    public void setOverlay() {
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (state == UNSELECTED) {
                    setSelected(true);
                } else if (state == SELECTED) {
                    setGreen();
                } else if (state == RED || state == GREEN) {
                    setSelected(false);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (state == UNSELECTED) {
                    setSelected(true);
                } else if (state == SELECTED) {
                    setRed();
                } else if (state == RED || state == GREEN) {
                    setSelected(false);
                }
            } else if (event.getButton() == MouseButton.MIDDLE) {
                if (state == UNSELECTED) {
                    setSelected(true);
                } else if (state == SELECTED) {
                    setSelected(false);
                } else if (state == RED || state == GREEN) {
                    setSelected(false);
                }
            }
        });
    }

    public int getState() {
        return state;
    }

    public void blinkGreen(double duration) {
        if (state == GREEN) {
            setFill(CostumeColor.DARK_GREEN);
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.1), evt -> setFill(CostumeColor.DARK_GREEN)),
                    new KeyFrame(Duration.seconds(0.2), evt -> setFill(CostumeColor.LIGHT_GREEN)));
            timeline.setCycleCount((int) (duration) * 5);
            timeline.play();
        }
    }

    public void blinkRed(double duration) {
        if (state == RED) {
            setFill(CostumeColor.DARK_RED);
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.1), evt -> setFill(CostumeColor.DARK_RED)),
                    new KeyFrame(Duration.seconds(0.2), evt -> setFill(CostumeColor.LIGHT_RED)));
            timeline.setCycleCount((int) (duration) * 5);
            timeline.play();
        }
    }
}

class CostumeColor {
    public final static Color LIGHT_RED = Color.rgb(245, 24, 24);
    public final static Color LIGHT_GREEN = Color.rgb(17, 200, 17);
    public final static Color DARK_RED = Color.rgb(174, 23, 18);
    public final static Color DARK_GREEN = Color.rgb(0, 128, 0);

}