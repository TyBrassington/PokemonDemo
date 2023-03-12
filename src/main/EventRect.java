package main;

import java.awt.*;

public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    int eventRect1DefaultX, eventRect1DefaultY;
    int eventRect2DefaultX, eventRect2DefaultY;
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
