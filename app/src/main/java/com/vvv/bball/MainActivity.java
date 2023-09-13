package com.vvv.bball;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GameLoopThread gameLoopThread;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        GameSurface gameSurface = new GameSurface(this);
        setContentView(gameSurface);
        gameLoopThread = gameSurface.getGameLoopThread();

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLoopThread.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameLoopThread.resumeGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameLoopThread.terminate();
    }
}