import javadraw.*;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Main extends Window {
    private static final double POINT_SIZE = 5; //size of pointss

    PointShape pointShape = PointShape.SQUARE; //current shape of points
    Text shapeLabel; //label to show point shape
    Text lineLabel; //label to show if lines are enabled
    Stack<Renderable> points = new Stack<>(); //stack of points
    Stack<Line> lines = new Stack<>(); //stack of lines
    Stack<LastType> lastType = new Stack<>(); //stack to store order of object types created
    boolean lineModeEnabled = false; //are lines enabled

    public static void main(String[] args) {
        Window window = new Window(); //standard setup
        window.open(800, 800, "Shape Drawer");
    }

    @Override
    public void start() {
        shapeLabel = new TextHelper(screen, "Current Shape: " + pointShape.toString(), new Location(10, 10)).create(); //create info labels
        lineLabel = new TextHelper(screen, "Line Mode: " + lineModeEnabled, new Location(10, 25)).create();
    }

    @Override
    public void mouseDown(Location location, int button) {
        print(location, button); //debug
        switch (button) {
            case 1 -> { //left click (create new point)
                Renderable point = null;
                Renderable lastPoint = null;
                try { //get last point to draw line
                    lastPoint = points.lastElement();
                } catch (NoSuchElementException e) { //for when stack is empty
                    //ignore
                }
                switch (pointShape) { //draw shape of corresponding type
                    case SQUARE -> {
                        point = new Rectangle(screen, location, POINT_SIZE, POINT_SIZE); //create shape
                        point.center(location); //center shape on mouse position
                        points.add(point); //add to point stack
                        lastType.add(LastType.POINT); //add point to lastType stack
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
                if (lineModeEnabled) { //draw line
                    Line line = new Line(screen, point.center(), lastPoint.center()); //create line
                    lines.add(line); //add to stack
                    lastType.add(LastType.LINE); //add line to lastType stack
                }
            }
            case 2 -> { //middle click (cycle through point shapes)
                pointShape = nextEnum(PointShape.class, pointShape); //get next enum
                print(pointShape); //debug
                updateShapeLabel();
            }
            case 3 -> { //right click (remove last item)
                try {
                    switch (lastType.pop()) { //get last item created
                        case POINT -> points.pop().remove(); //delete it from the correct stack and remove it from the screen
                        case LINE -> lines.pop().remove();
                    }
                } catch (EmptyStackException e) { //if a stack is empty
                    //ignore
                }
            }
        }
    }

    @Override
    public void keyDown(Key key) {
        switch (key.character()) {
            case "E" -> { //delete all points
                points.forEach((p) -> p.remove()); //remove all points from screen
                points.empty();
                lastType.removeIf((lt) -> lt.equals(LastType.POINT)); //remove all points from lastType stack
                System.out.println(lastType); //debug
            }
            case "L" -> lineModeEnabled = !lineModeEnabled; //toggle line mode
            case "C" -> { //delete all lines
                lines.forEach((l) -> l.remove()); //remove all lines from screen
                lines.empty();
                lastType.removeIf((lt) -> lt.equals(LastType.LINE)); //remove all lines from lastType stack
            }
            case "1" -> System.out.println(points); //debug (non-functional)
            case "2" -> System.out.println(lines); //debug (non-functional)
            case "3" -> System.out.println(lastType); //debug (non-functional)
        }
    }

    public void updateShapeLabel() { //updates shape label text
        shapeLabel.text("Current Shape: " + pointShape.toString());
    }

    public static <T extends Enum<T>> T nextEnum(Class<T> clazz, T currentEnum) { //generic fun
        T[] enumItems = clazz.getEnumConstants(); //get enum items of given class (because the values() method is not in the Enum class)
        int currentIndex = -1;

        for (int i = 0; i < enumItems.length; i++) { //find index of current enum item in array
            if (enumItems[i].equals(currentEnum)) {
                currentIndex = i;
            }
        }

        int nextIndex = currentIndex + 1;

        if (nextIndex >= enumItems.length) { //prevent overflow, cycle back to front of array
            nextIndex -= enumItems.length;
        }

        return enumItems[nextIndex];
    }

    public enum PointShape { //defines the point shapes
        SQUARE, CIRCLE, TRIANGLE
    }

    public enum LastType { //defines lastType
        POINT, LINE
    }
}
