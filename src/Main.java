import javadraw.*;

import java.util.EmptyStackException;
import java.util.Stack;

public class Main extends Window {
    private static final double POINT_SIZE = 5;

    int lastPoint = 0;
    PointShape pointShape = PointShape.SQUARE;
    Text shapeLabel;
    Stack<Renderable> points = new Stack<>();

    public static void main(String[] args) {
        Window window = new Window();
        window.open(800, 800, "Shape Drawer");
    }

    @Override
    public void start() {
        shapeLabel = new TextHelper(screen, "Current Shape: " + pointShape.toString(), new Location(10, 10)).create();
    }

    @Override
    public void mouseDown(Location location, int button) {
        print(location, button); //debug
        switch (button) {
            case 1 -> {
                switch (pointShape) {
                    case SQUARE -> {
                        Rectangle rectangle = new Rectangle(screen, location, POINT_SIZE, POINT_SIZE);
                        rectangle.center(location);
                        points.add(rectangle);
                    }
                    case CIRCLE -> {
                        Oval oval = new Oval(screen, location, POINT_SIZE, POINT_SIZE);
                        oval.center(location);
                        points.add(oval);
                    }
                    case TRIANGLE -> {
                        Triangle triangle = new Triangle(screen, location, POINT_SIZE, POINT_SIZE);
                        triangle.center(location);
                        points.add(triangle);
                    }
                }
            }
            case 2 -> {
                pointShape = nextEnum(PointShape.class, pointShape);
                print(pointShape); //debug
                updateShapeLabel();
            }
            case 3 -> {
                try {
                    points.pop().remove();
                } catch (EmptyStackException e) {
                    //ignore
                }
            }
        }
    }

    @Override
    public void keyDown(Key key) {
        switch (key.character()) {
            case "E" -> {
                points.forEach((p) -> p.remove());
                points.empty();
            }
        }
    }

    public void updateShapeLabel() {
        shapeLabel.text("Current Shape: " + pointShape.toString());
    }

    public static <T extends Enum<T>> T nextEnum(Class<T> clazz, T currentEnum) {
        T[] enumItems = clazz.getEnumConstants();
        int currentIndex = -1;

        for (int i = 0; i < enumItems.length; i++) {
            if (enumItems[i].equals(currentEnum)) {
                currentIndex = i;
            }
        }

        int nextIndex = currentIndex + 1;

        if (nextIndex >= enumItems.length) {
            nextIndex -= enumItems.length;
        }

        return enumItems[nextIndex];
    }

    public enum PointShape {
        SQUARE, CIRCLE, TRIANGLE
    }
}
