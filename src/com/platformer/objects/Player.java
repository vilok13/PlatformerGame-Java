package com.platformer.objects;

import com.platformer.framework.GameObject;
import com.platformer.framework.ObjectId;
import com.platformer.framework.Texture;
import com.platformer.window.Animation;
import com.platformer.window.Camera;
import com.platformer.window.Game;
import com.platformer.window.Handler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

import java.util.LinkedList;

public class Player extends GameObject {

    private float width = 32, height = 32;

    private boolean falling = true;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;

    private Handler handler;
    private Camera cam;

    Texture tex = Game.getInstance();

    private Animation playerWalk, playerWalkLeft;


    public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        this.cam = cam;

        playerWalk = new Animation(10, tex.player[0], tex.player[1], tex.player[2]);
        playerWalkLeft = new Animation(10, tex.player[3], tex.player[4], tex.player[5]);
    }

    public void tick(LinkedList<GameObject> object)
    {
        x += velX;
        y += velY;

        if (velX < 0) facing = -1;
        else if (velX > 0) facing = 1;

        if(falling || jumping)
        {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }
        Collision(object);

        playerWalk.runAnimation();
        playerWalkLeft.runAnimation();
    }

    private void Collision(LinkedList<GameObject> object)
    {

        for(int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block) {

                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + 32;
                    velY = 0;
                }


                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }

                //Right
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                }

                //Left
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + width;
                }
            } else if(tempObject.getId() == ObjectId.Flag) {

                //switch levels
                if(getBounds().intersects(tempObject.getBounds())) {

                    handler.switchLevel();

                }
            }
        }
    }

    public void render(Graphics g)
    {
        g.setColor(Color.blue);
        if (velX != 0)
        {
            if(facing == 1)
                playerWalk.drawAnimation(g, (int)x, (int)y);
             else
                playerWalkLeft.drawAnimation(g, (int)x, (int)y);

        } else {
            if (facing == 1)
                g.drawImage(tex.player[0], (int)x, (int)y, null);
            else
                g.drawImage(tex.player[3], (int)x, (int)y, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)((int)x + (width/2) - ((width/2)/2)), (int)((int)y + (height/2)), (int)width/2, (int)height/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int)((int)x + (width/2) - ((width/2)/2)), (int)y, (int)width/2, (int)height/2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int)((int)x + width - 5), (int)y + 5, (int)5, (int)height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y + 5, (int)5, (int)height - 10);
    }

}
