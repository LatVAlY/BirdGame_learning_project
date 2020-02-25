package mygame.birdgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static mygame.birdgame.GameView2.screenRetioX;
import static mygame.birdgame.GameView2.screenRetioY;

public class Flight {
    public boolean isGoingUp;
    int toShoot = 0;
    int x, y, width, hight, wingCounter = 0, ShootCounter = 1;
    Bitmap flight1, flight2, shoot1, shoot2, shoot3, shoot4, shoot5, dead;
    private GameView2 gameView2;

    Flight(GameView2 gameView2, int screenY, Resources res){
        this.gameView2 = gameView2;

        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.fly2);

        width = flight1.getWidth();
        hight = flight2.getWidth();

        width /= 4;
        hight /= 4;
        width *=(int) screenRetioX;
        hight *=(int) screenRetioY;
        flight1 = Bitmap.createScaledBitmap(flight1, width, hight, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, hight, false);

        shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot5);
        dead = BitmapFactory.decodeResource(res, R.drawable.dead);

        shoot1 = Bitmap.createScaledBitmap(shoot1, width, hight, false);
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, hight, false);
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, hight, false);
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, hight, false);
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, hight, false);
        dead = Bitmap.createScaledBitmap(dead, width, hight, false);


        y = screenY/2;
        x = (int) (64 * screenRetioX);

    }
    Bitmap getFlight(){
        if (toShoot != 0) {
            if (ShootCounter == 1) {
                ShootCounter++;
                return shoot1;
            }
            if (ShootCounter == 2){
                ShootCounter++;
                return shoot2;
            }
            if (ShootCounter == 3){
                ShootCounter++;
                return shoot3;
            }
            if (ShootCounter == 4){
                ShootCounter++;
                return shoot4;
            }
            ShootCounter = 1;
            toShoot--;
            gameView2.newBullet();
            return shoot5;
        }
        if (wingCounter == 0){
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;
    }
    Rect getCollisionShaps(){
        return new Rect(x, y, x + width, y + hight);
    }
    Bitmap getDead(){
        return dead;
    }
}
