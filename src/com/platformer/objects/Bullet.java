package com.platformer.objects;

import com.platformer.framework.GameObject;
import com.platformer.framework.ObjectId;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.LinkedList;

public class Bullet extends GameObject {

    public Bullet(float x, float y, ObjectId id, int velX) {
        super(x, y, id);
        this.velX = velX;
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, 8, 8);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 8, 8);
    }
}
