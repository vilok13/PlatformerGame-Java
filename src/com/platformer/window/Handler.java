package com.platformer.window;

import com.platformer.framework.GameObject;
import com.platformer.framework.ObjectId;
import com.platformer.objects.Block;
import com.platformer.objects.Flag;
import com.platformer.objects.Player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Handler
{

    public LinkedList<GameObject> object = new LinkedList<GameObject>();

    private GameObject tempObject;

    private Camera cam;
    private BufferedImage level2 = null;

    public Handler(Camera cam) throws IOException {

        this.cam = cam;

        BufferedImageLoader loader = new BufferedImageLoader();
        level2 = loader.loadImage("/Level2.png");

    }

    public void tick()
    {
        for(int i = 0; i < object.size(); i++)
        {
            tempObject = object.get(i);

            tempObject.tick(object);
        }
    }

    public void render(Graphics g)
    {
        for(int i = 0; i < object.size(); i++)
        {
            tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void LoadImageLevel(BufferedImage image)
    {

        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println("width, height: " + w + " " + h);

        for(int xx = 0; xx < h; xx++)
        {
            for(int yy = 0; yy < w; yy++)
            {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255 && green == 255 && blue == 255) addObject(new Block(xx * 32, yy * 32, 1, ObjectId.Block));
                if(red == 128 && green == 128 && blue == 128) addObject(new Block(xx * 32, yy * 32, 0, ObjectId.Block));
                if(red == 0 && green == 0 && blue == 255) addObject(new Player(xx * 32, yy * 32, this, cam, ObjectId.Player));
                if(red == 255 && green == 255 && blue == 0) addObject(new Flag(xx * 32, yy * 32, ObjectId.Flag));

            }
        }
    }

    public void switchLevel() {
        clearLevel();
        Game.LEVEL++;
        cam.setX(0);

        switch (Game.LEVEL){

            case 1:
                LoadImageLevel(level2);
                break;
        }

    }

    private void clearLevel() {
        object.clear();
    }

    public void addObject(GameObject object)
    {
        this.object.add(object);
    }

    public void removeObject(GameObject object)
    {
        this.object.remove(object);
    }

}
