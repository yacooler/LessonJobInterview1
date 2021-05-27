package ru.vyazankin.shapes;

public class Triangle extends BaseShape{
    private float sideASize;
    private float sideBSize;
    private float sideCSize;

    public Triangle(float x, float y, float sideASize, float sideBSize, float sideCSize) {
        this.x = x;
        this.y = y;
        this.sideASize = sideASize;
        this.sideBSize = sideBSize;
        this.sideCSize = sideCSize;
    }

    @Override
    public float calcSquare() {
        //Формула Герона, площадь по трем сторонам через полупериметр
        float halfPerimeter = calcPerimeter() / 2F;
        return (float) Math.sqrt(halfPerimeter * (halfPerimeter - sideASize) * (halfPerimeter - sideBSize) * (halfPerimeter * sideCSize));
    }

    @Override
    public float calcPerimeter() {
        return sideASize + sideBSize + sideCSize;
    }
}
