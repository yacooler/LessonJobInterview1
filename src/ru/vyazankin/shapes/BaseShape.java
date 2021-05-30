package ru.vyazankin.shapes;

import java.math.BigDecimal;

public abstract class BaseShape {
    protected float x;
    protected float y;

    public abstract float calcSquare();
    public abstract float calcPerimeter();
}
