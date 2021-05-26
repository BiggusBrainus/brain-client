package at.htlkaindorf.bigbrain.bl;

public enum ButtonSize {
    DEFAULT_BUTTONS(170, 120),
    THREE_BUTTONS(350, 120),
    TWO_BUTTONS(170, 200);

    private int width;
    private int height;
    private double horizontal_bias;
    private double vertical_bias;

    ButtonSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
