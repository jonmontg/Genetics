public class DisplayBox {
    private float width, height, x, y;
    public DisplayBox(float x, float y, float w, float h) {
        width = w;
        height = h;
        this.x = x;
        this.y = y;
    }

    public float[] getDim() {
        return new float[] {x, y, width, height};
    }
}
