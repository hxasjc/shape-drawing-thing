public enum PointShape { //defines the point shapes
    SQUARE, CIRCLE, TRIANGLE;

    public PointShape next() {
        return values()[(ordinal() + 1) % values().length];
    }
}
