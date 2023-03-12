package main;

import java.awt.*;

public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;

    public EventRect x(int x) {
        this.x = x;
        return this;
    }

    public EventRect y(int y) {
        this.y = y;
        return this;
    }

    public EventRect width(int width) {
        this.width = width;
        return this;
    }

    public EventRect height(int height) {
        this.height = height;
        return this;
    }

}
