package com.platformer.framework;

import com.platformer.window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture
{
    SpriteSheet bs, ps;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;

    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[6];

    public Texture()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        try
        {
            block_sheet = loader.loadImage("/Overworld.png");
            player_sheet = loader.loadImage("/Player.png");
        } catch (Exception e){
            e.printStackTrace();
        }

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);

        getTextures();
    }

    private void getTextures()
    {
        block[0] = bs.grabImage(2, 2, 32, 32);
        block[1] = bs.grabImage(4, 2, 32, 32);

        //Right
        player[0] = ps.grabImage(1, 3, 32, 32);
        player[1] = ps.grabImage(2, 3, 32, 32);
        player[2] = ps.grabImage(3, 3, 32, 32);

        //Left
        player[3] = ps.grabImage(1, 2, 32, 32);
        player[4] = ps.grabImage(2, 2, 32, 32);
        player[5] = ps.grabImage(3, 2, 32, 32);
    }
}
