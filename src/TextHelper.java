import javadraw.Color;
import javadraw.Location;
import javadraw.Screen;
import javadraw.Text;

public class TextHelper {
    private final Screen screen;
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

    public TextHelper color(Color color) {
        this.color = color;
        return this;
    }

    public TextHelper border(Color border) {
        this.border = border;
        return this;
    }

    public TextHelper font(String font) {
        this.font = font;
        return this;
    }

    public TextHelper size(int size) {
        this.size = size;
        return this;
    }

    public TextHelper align(String align) {
        this.align = align;
        return this;
    }

    public TextHelper bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public TextHelper italic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public TextHelper underline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public TextHelper strikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public TextHelper rotation(double rotation) {
        this.rotation = rotation;
        return this;
    }

    public TextHelper visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Text create() {
        return new Text(screen, text, location, color, border, font, size, align, bold, italic, underline, strikethrough, rotation, visible);
    }
}
