package ru.vyazankin.shapes;


public class Circle extends BaseShape{
    private float radius;

    public Circle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public float calcSquare() {
        return (float) Math.PI * radius * radius;
    }

    @Override
    public float calcPerimeter() {
        return (float) Math.PI * radius * 2;
    }
}
