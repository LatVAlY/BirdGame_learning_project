package mygame.birdgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView2 extends SurfaceView implements Runnable {
    private Thread thread;
    public static float screenRetioX, screenRetioY;
    boolean isPlaying, isGameOver = false;
    private Bird[] birds;
    private Random random;
    private Flight flight;
    private Background background1,background2;
    int screenX, screenY;
    private Paint paint;
    private List<Bullet> bullets;

    public GameView2(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRetioX =1920f / screenX;
        screenRetioY = 1080f / screenY;
        background1 = new Background(screenX,screenY, getResources());
        background2 = new Background(screenX,screenY, getResources());
        background2.x = screenX;
        paint = new Paint();

        flight = new Flight(this, screenY, getResources());

        bullets = new ArrayList<>();

        birds = new Bird[4];
        for (int i = 0; i < 4; i++){
            Bird bird = new Bird(getResources());
            birds[i]= bird;

        }
        random = new Random();
    }

    @Override
    public void run() {

        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }
    private void update() {
        background1.x -= 5 * screenRetioX;
        background2.x -= 5 * screenRetioX;

        if(background1.x + background1.background.getWidth() < 0){
            background1.x = screenX;
    }
        if(background2.x + background2.background.getWidth() < 0){
            background2.x = screenX;
        }
        if (flight.isGoingUp)
            flight.y -= 30 * screenRetioY;
            else
            flight.y += 30 * screenRetioY;
        if (flight.y < 0)
            flight.y =  0;
        if (flight.y > screenY - flight.hight)
            flight.y = screenY - flight.hight;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets){
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRetioX;
            for (Bird bird : birds){
                if (Rect.intersects(bird.getCollisionShaps(), bullet.getCollisionShaps())){
                    bird.x = -500;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;
                }


            }
        }
        for (Bullet bullet : trash)
            bullets.remove(bullet);
        for(Bird bird : birds){
            bird.x -= bird.speed;
            if (bird.x + bird.width < 0){
                if (!bird.wasShot){
                    isGameOver = true;
                    return;
                }
                int bound = (int) (30 *screenRetioX);
                bird.speed = random.nextInt(bound);
                if (bird.speed < 5 * screenRetioX)
                    bird.speed = (int) (5 * screenRetioX);
                bird.x = screenX;
                bird.y = random.nextInt(screenY - bird.hight);
                bird.wasShot = false;
            }
            if (Rect.intersects(bird.getCollisionShaps(), flight.getCollisionShaps())){
                isGameOver  = true;
                return;
            }
        }

    }
    private void draw(){
        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);


            for (Bird bird : birds)
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }
            canvas.drawBitmap(flight.getFlight(),flight.x, flight.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }
    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }
    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX()< screenX / 2){
                    flight.isGoingUp = true;
                }
            break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if(event.getX()> screenX / 2)
                    flight.toShoot++;
            break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.hight / 2);
        bullets.add(bullet);

    }
}
