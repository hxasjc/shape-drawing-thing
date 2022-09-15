import javadraw.*;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Main extends Window {
    private static final double POINT_SIZE = 5;

    PointShape pointShape = PointShape.SQUARE;
    Text shapeLabel;
    Text lineLabel;
    Stack<Renderable> points = new Stack<>();
    Stack<Line> lines = new Stack<>();
    Stack<LastType> lastType = new Stack<>();
    boolean lineModeEnabled = false;

    public static void main(String[] args) {
        Window window = new Window();
        window.open(800, 800, "Shape Drawer");
    }

    @Override
    public void start() {
        shapeLabel = new TextHelper(screen, "Current Shape: " + pointShape.toString(), new Location(10, 10)).create();
        lineLabel = new TextHelper(screen, "Line Mode: " + lineModeEnabled, new Location(10, 25)).create();
    }

    @Override
    public void mouseDown(Location location, int button) {
        print(location, button); //debug
        switch (button) {
            case 1 -> {
                Renderable point = null;
                Renderable lastPoint = null;
                try {
                    lastPoint = points.lastElement();
                } catch (NoSuchElementException e) {
                    //ignore
                }
                switch (pointShape) {
                    case SQUARE -> {
                        point = new Rectangle(screen, location, POINT_SIZE, POINT_SIZE);
                        point.center(location);
                        points.add(point);
                        lastType.add(LastType.POINT);
                    }
                    case CIRCLE -> {
                        point = new Oval(screen, location, POINT_SIZE, POINT_SIZE);
                        point.center(location);
                        points.add(point);
                        lastType.add(LastType.POINT);
                    }
                    case TRIANGLE -> {
                        point = new Triangle(screen, location, POINT_SIZE, POINT_SIZE);
                        point.center(location);
                        points.add(point);
                        lastType.add(LastType.POINT);
                    }
                }
                if (lineModeEnabled) {
                    Line line = new Line(screen, point.center(), lastPoint.center());
                    lines.add(line);
                    lastType.add(LastType.LINE);
                }
            }
            case 2 -> {
                pointShape = nextEnum(PointShape.class, pointShape);
                print(pointShape); //debug
                updateShapeLabel();
            }
            case 3 -> {
                try {
                    switch (lastType.pop()) {
                        case POINT -> points.pop().remove();
                        case LINE -> lines.pop().remove();
                    }
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
                lastType.removeIf((lt) -> lt.equals(LastType.POINT));
                System.out.println(lastType);
            }
            case "L" -> lineModeEnabled = !lineModeEnabled;
            case "C" -> {
                lines.forEach((l) -> l.remove());
                lines.empty();
                lastType.removeIf((lt) -> lt.equals(LastType.LINE));
            }
            case "1" -> System.out.println(points);
            case "2" -> System.out.println(lines);
            case "3" -> System.out.println(lastType);
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

    public enum LastType {
        POINT, LINE
    }
}
