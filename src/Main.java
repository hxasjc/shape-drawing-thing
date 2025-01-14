import javadraw.*;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Main extends Window {
    private static final double POINT_SIZE = 5; //size of points

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
                    case SQUARE -> point = new Rectangle(screen, location, POINT_SIZE, POINT_SIZE); //create shape
                    case CIRCLE -> point = new Oval(screen, location, POINT_SIZE, POINT_SIZE);
                    case TRIANGLE -> point = new Triangle(screen, location, POINT_SIZE, POINT_SIZE);
                }

                point.center(location); //center shape on mouse position
                point.color(Color.BLACK);
                points.add(point); //add to point stack
                lastType.add(LastType.POINT); //add point to lastType stack

                if (lineModeEnabled && lastPoint != null) { //draw line
                    Line line = new Line(screen, point.center(), lastPoint.center()); //create line
                    lines.add(line); //add to stack
                    lastType.add(LastType.LINE); //add line to lastType stack
                }
            }
            case 2 -> { //middle click (cycle through point shapes)
                //pointShape = nextEnum(PointShape.class, pointShape); //get next enum
                pointShape = pointShape.next();
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
                points.forEach(Renderable::remove); //remove all points from screen
                points.clear();
                lastType.removeIf((lt) -> lt.equals(LastType.POINT)); //remove all points from lastType stack
                System.out.println(lastType); //debug
            }
            case "L" -> { //toggle line mode
                lineModeEnabled = !lineModeEnabled;
                updateLineLabel();
            }
            case "C" -> { //delete all lines
                lines.forEach(Line::remove); //remove all lines from screen
                lines.clear();
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

    public void updateLineLabel() {
        lineLabel.text("Line Mode: " + lineModeEnabled);
    }

}
