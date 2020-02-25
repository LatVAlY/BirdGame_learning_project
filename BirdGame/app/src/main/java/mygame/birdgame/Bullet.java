package mygame.birdgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static mygame.birdgame.GameView2.screenRetioX;
import static mygame.birdgame.GameView2.screenRetioY;

public class Bullet {
    int x,y,width,hight;
    Bitmap bullet;
    Bullet (Resources res){
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        hight = bullet.getHeight();
        width /= 4;
        hight /=4;
        width *= (int) screenRetioX;
        hight *= (int) screenRetioY;
        bullet = Bitmap.createScaledBitmap(bullet, width, hight, false);
    }

    Rect getCollisionShaps(){
        return new Rect(x, y, x + width, y + hight);
    }
}
