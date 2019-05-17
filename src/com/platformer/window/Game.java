package com.platformer.window;

import com.platformer.framework.KeyInput;
import com.platformer.framework.ObjectId;
import com.platformer.framework.Texture;
import com.platformer.objects.Block;
import com.platformer.objects.Flag;
import com.platformer.objects.Player;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.IOException;

public class Game extends Canvas implements Runnable
{

    private static final long serialVersionUID = 1283590811274333136L;

    private boolean running = false;
    private Thread thread;

    public static int WIDTH, HEIGHT;

    public BufferedImage level = null, background = null;

    Handler handler;
    Camera cam;
    static Texture tex;

    public static int LEVEL = 0;

    private void init() throws IOException {

        WIDTH = getWidth();
        HEIGHT = getHeight();

        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/Level.png");
        background = loader.loadImage("/Background.png");

        cam = new Camera(0, 0);
        handler = new Handler(cam);

        handler.LoadImageLevel(level);

        //handler.addObject(new Player(100, 100, handler, ObjectId.Player));
        //handler.createLevel();

        this.addKeyListener(new KeyInput(handler));
    }

    //Garante que as Threads não vão sobrepor umas as outras
    public synchronized void start()
    {
        if(running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run()
    {
        // Game Loop
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;

            }
        }
    }

    private void tick()
    {
        handler.tick();
        for (int i = 0; i < handler.object.size(); i++)
            if(handler.object.get(i).getId() == ObjectId.Player)
            {
                cam.tick(handler.object.get(i));
            }
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        /////////////////////////////////
        //Draw Here
        g.setColor(new Color(78, 173, 245));
        g.fillRect(0, 0, getWidth(), getHeight());

        g2d.translate(cam.getX(), cam.getY());

            for(int xx = 0; xx < background.getWidth(); xx += background.getWidth())
                g.drawImage(background,xx, 0, this);

            handler.render(g);

        g2d.translate(-cam.getX(), -cam.getY());

        /////////////////////////////////

        g.dispose();
        bs.show();
    }

    public static Texture getInstance()
    {
        return tex;
    }

    public static void main(String[] args)
    {
        new Window(800, 640, "Platformer Game", new Game());
    }

}
