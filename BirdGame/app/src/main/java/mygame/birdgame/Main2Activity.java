package mygame.birdgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class Main2Activity extends AppCompatActivity {

    private GameView2 gameView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView2 = new GameView2(this,point.x,point.y);
        setContentView(gameView2);
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameView2.pause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        gameView2.resume();
    }
}
