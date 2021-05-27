package ru.vyazankin.shapes;

public class Square extends BaseShape{
    private float sideSize;

    public Square(float x, float y, float sideSize) {
        this.x = x;
        this.y = y;
        this.sideSize = sideSize;
    }

    @Override
    public float calcSquare() {
        return sideSize * sideSize;
    }

    @Override
    public float calcPerimeter() {
        return sideSize * 4F;
    }
}
