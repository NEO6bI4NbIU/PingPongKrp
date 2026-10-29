package com.example.pong;

import javafx.scene.shape.Shape;
import java.util.List;

public interface Arena {
    List<Shape> getObstacles();      // препятствия
    List<Zone> getZones();           // зоны (ускорения, замедления)
    void update(double deltaTime);   // обновление движущихся объектов
    void reset();
}
