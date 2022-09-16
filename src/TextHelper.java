import javadraw.Color;
import javadraw.Location;
import javadraw.Screen;
import javadraw.Text;

/**
 * Easier construction of Text objects, makes it easy to change lesser used params like bold/italic without using the full constructor
 */
public class TextHelper {
    private final Screen screen; //param variables
    private final Object text;
    private final Location location;
    private Color color;
    private Color border;
    private String font;
    private int size;
    private String align;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikethrough;
    private double rotation;
    private boolean visible;

    /**
     * Accepts required params and sets other params to default values
     * @param screen screen to display on
     * @param text text to display
     * @param location location to place the object at
     */
    public TextHelper(Screen screen, Object text, Location location) {
        this.screen = screen;
        this.text = text;
        this.location = location;
        this.color = Color.BLACK;
        this.border = Color.NONE;
        this.font = "";
        this.size = 12;
        this.align = "LEFT";
        this.bold = false;
        this.italic = false;
        this.underline = false;
        this.strikethrough = false;
        this.rotation = 0;
        this.visible = true;
    }

    /**
     * sets color param
     * @param color text color
     * @return current object
     */
    public TextHelper color(Color color) {
        this.color = color;
        return this;
    }

    /**
     * sets the border color param
     * @param border border color
     * @return current object
     */
    public TextHelper border(Color border) {
        this.border = border;
        return this;
    }

    /**
     * sets the font param
     * <br/>
     * uses java.awt font names
     * @param font font to use
     * @return current object
     */
    public TextHelper font(String font) {
        this.font = font;
        return this;
    }

    /**
     * sets the text size param
     * @param size text size
     * @return current object
     */
    public TextHelper size(int size) {
        this.size = size;
        return this;
    }

    /**
     * sets the text alignment param
     * <br/>
     * LEFT, CENTER, RIGHT
     * @param align text alignment
     * @return current object
     */
    public TextHelper align(String align) {
        this.align = align;
        return this;
    }

    /**
     * sets the bold text param
     * @param bold if text is bold
     * @return current object
     */
    public TextHelper bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    /**
     * sets the italic text param
     * @param italic if text is italic
     * @return current object
     */
    public TextHelper italic(boolean italic) {
        this.italic = italic;
        return this;
    }

    /**
     * sets the text underline param
     * @param underline if text is underlined
     * @return current object
     */
    public TextHelper underline(boolean underline) {
        this.underline = underline;
        return this;
    }

    /**
     * sets the text strikethrough param
     * @param strikethrough if text is strikethrough
     * @return current object
     */
    public TextHelper strikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    /**
     * sets the object rotation param
     * @param rotation object rotation
     * @return current object
     */
    public TextHelper rotation(double rotation) {
        this.rotation = rotation;
        return this;
    }

    /**
     * sets object visibility param
     * @param visible is object visible
     * @return current object
     */
    public TextHelper visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    /**
     * takes all given params and returns a javadraw.Text object
     * @return {@code javadraw.Text} object
     */
    public Text create() {
        return new Text(screen, text, location, color, border, font, size, align, bold, italic, underline, strikethrough, rotation, visible);
    }
}
