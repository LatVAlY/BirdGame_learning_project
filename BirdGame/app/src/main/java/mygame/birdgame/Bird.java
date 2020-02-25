package mygame.birdgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static mygame.birdgame.GameView2.screenRetioX;
import static mygame.birdgame.GameView2.screenRetioY;

public class Bird {
    public int speed = 10;
    public boolean wasShot = true;
    int x = 0,y, width, hight, BirdCounter = 1;
    Bitmap bird1, bird2, bird3, bird4;
    Bird (Resources res){
        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

         width = bird1.getWidth();
         hight = bird1.getHeight();

         width /= 6;
         hight /=6;
         width *= (int) screenRetioX;
         hight *= (int) screenRetioY;

        bird1 = Bitmap.createScaledBitmap(bird1, width, hight, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, hight, false);
        bird3 = Bitmap.createScaledBitmap(bird3, width, hight, false);
        bird4 = Bitmap.createScaledBitmap(bird4, width, hight, false);
        y -= hight;
    }
    Bitmap getBird(){
        if (BirdCounter == 1){
            BirdCounter++;
            return bird1;
        }
        if (BirdCounter == 2){
            BirdCounter++;
            return bird2;
        }
        if (BirdCounter == 3){
            BirdCounter++;
            return bird3;
        }
        BirdCounter = 1;
        return bird4;

    }
    Rect getCollisionShaps(){
        return new Rect(x, y, x + width, y + hight);
    }
}
