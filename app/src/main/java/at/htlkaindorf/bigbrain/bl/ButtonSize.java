package at.htlkaindorf.bigbrain.bl;

/**
 * Used to change the buttons width and height in the GameActivity
 * @version BigBrain v1
 * @since 27.04.2021
 * @author Nico Pessnegger
 */
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
