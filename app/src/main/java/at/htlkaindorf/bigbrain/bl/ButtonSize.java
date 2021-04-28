package at.htlkaindorf.bigbrain.bl;

public enum ButtonSize {
    THREE_BUTTONS(350, 120, 0.871, 0.689),
    TWO_BUTTONS(170, 200, 0.124, 0.906);

    private int width;
    private int height;
    private double horizontal_bias;
    private double vertical_bias;

    ButtonSize(int width, int height, double horizontal_bias, double vertical_bias) {
        this.width = width;
        this.height = height;
        this.horizontal_bias = horizontal_bias;
        this.vertical_bias = vertical_bias;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getHorizontal_bias() {
        return horizontal_bias;
    }

    public double getVertical_bias() {
        return vertical_bias;
    }
}
