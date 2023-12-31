//package com.vvv.bball.TestDeployment;
//
//import android.graphics.Point;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class GameActivityTest extends AppCompatActivity {
//
//    GameViewTest gameViewTest;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//
//        Point point = new Point();
//        getWindowManager().getDefaultDisplay().getSize(point);
//
//        gameViewTest = new GameViewTest(this, (short) point.x, (short) point.y);
//
//        setContentView(gameViewTest);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        gameViewTest.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        gameViewTest.resume();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//}
