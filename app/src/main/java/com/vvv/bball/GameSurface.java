package com.vvv.bball;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread gameLoopThread;
    private final Basketball basketball;
    private final Paint scorePaint = new Paint();
    private final Hoop hoop;
    private int screenW;
    private int screenH;
    private int score;
    private float initialTouchX, initialTouchY;
    private final Paint paint = new Paint();
    private boolean hasScored = false;
    private final Background background;
    private boolean isAboveHoop = false;
    private boolean isPassingThroughHoop = false;
    private boolean isEnteringHoop = false;
    SharedPreferences sharedPreferences;
    private final CountDownTimer countDownTimer;
    private long remainingTime = 60;
    private boolean isTimerStarted = false;



    public GameSurface(Context context) {
        super(context);
        sharedPreferences = context.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;

        gameLoopThread = new GameLoopThread(getHolder(), this);

        basketball = new Basketball(context, screenW, screenH);
        hoop = new Hoop(context, screenW, screenH);

        background = new Background(context);

        countDownTimer = new CountDownTimer(60000, 1000) { // 60000 milliseconds = 1 minute, tick every 1000 milliseconds
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished / 1000;
            }

            public void onFinish() {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> existingScores = sharedPreferences.getStringSet("all_scores", new HashSet<String>());
                existingScores.add(String.valueOf(score));
                editor.putStringSet("all_scores", existingScores);

                editor.apply();


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.custom_dialog, null);

                TextView title = view.findViewById(R.id.title);
                TextView message = view.findViewById(R.id.message);
                title.setText("Time's Up!");
                message.setText("Your score is: " + score);

                builder.setView(view)
                        .setPositiveButton("Go to Leaderboard", (dialog, which) -> {
                            Intent intent = new Intent(getContext(), LeaderboardActivity.class);
                            getContext().startActivity(intent);
                        })
                        .setNeutralButton("Next Level", (dialog, which) -> resetGame())
                        .setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();

                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.ground);

                dialog.setOnShowListener(dialogInterface -> {
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.BLUE);
                    positiveButton.setTextSize(18);

                    Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    neutralButton.setTextColor(Color.GREEN);
                    neutralButton.setTextSize(18);
                });
                dialog.show();
            }
        };

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        score = 0;
        getHolder().addCallback(this);
    }

    private void resetGame() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("newGame", true);
        getContext().startActivity(intent);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (gameLoopThread.getState() == Thread.State.NEW) {
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        } else if (gameLoopThread.getState() == Thread.State.TERMINATED) {
            gameLoopThread = new GameLoopThread(holder, this);
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.screenW = width;
        this.screenH = height;
        this.background.setScreenSize(screenW, screenH);

        float hoopX = screenW - hoop.getWidth() - 10;
        float hoopY = 500;

        hoop.setPosition(hoopX, hoopY);

        float basketballX = 100;
        float basketballY = screenH - basketball.getHeight() - 300;

        basketball.setX(basketballX);
        basketball.setY(basketballY);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameLoopThread.terminate();
        if (basketball != null) {
            basketball.recycle();
        }
        if (hoop != null) {
            hoop.recycle();
        }
        if (background != null) {
            background.recycle();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        background.draw(canvas);
        basketball.draw(canvas);
        hoop.draw(canvas);
        //debugDraw(canvas);

        int timerX = screenW / 2 - 130;
        int timerY = 100;
        int scoreX = screenW / 2 - 130;
        int scoreY = timerY + 110;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("TIME: " + remainingTime, timerX, timerY, paint);
        canvas.drawText("SCORE: " + score, scoreX, scoreY, paint);

    }
    private void debugDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawLine(0, 0, screenW, 0, paint); // Top edge
        canvas.drawLine(screenW, 0, screenW, screenH, paint); // Right edge
        canvas.drawLine(0, screenH, screenW, screenH, paint); // Bottom edge
        canvas.drawLine(0, 0, 0, screenH, paint); // Left edge
        Log.d("ScreenDimensions", "Width: " + screenW + ", Height: " + screenH);

    }

    public void update() {
        basketball.update();
        hoop.update();

        if (CollisionDetector.checkCollisionWithHoopBottom(basketball, hoop)) {
            basketball.setY(hoop.getY() + hoop.getHeight());
            basketball.setVelocity(basketball.getVelocityX(), -Math.abs(basketball.getYVelocity()) * basketball.energyLoss);
        }

        if (CollisionDetector.checkCollisionWithHoopRightSide(basketball, hoop)) {
            basketball.setVelocity(-Math.abs(basketball.getVelocityX()), basketball.getYVelocity());
        }

        if (CollisionDetector.checkCollisionWithHoopLeftSide(basketball, hoop)) {
            basketball.setVelocity(Math.abs(basketball.getVelocityX()), -Math.abs(basketball.getYVelocity()));
        }

        if (CollisionDetector.checkCollisionWithHoopCorners(basketball, hoop)) {
            basketball.setX(basketball.getX() - 5);
            basketball.setY(basketball.getY() + 5);
            basketball.setVelocity(-basketball.getVelocityX(), -basketball.getYVelocity());
        }

        if (basketball.getY() + basketball.getRadius() < hoop.getY()) {
            isAboveHoop = true;
        }

        if (basketball.getY() > hoop.getY() + hoop.getHeight()) {
            isPassingThroughHoop = true;
        }
        if (basketball.getX() + 2 * basketball.getRadius() >= hoop.getX() + hoop.getWidth()) {
            basketball.setX(hoop.getX() + hoop.getWidth() - 2 * basketball.getRadius() - 1);
            basketball.setVelocity(-Math.abs(basketball.getVelocityX()), basketball.getYVelocity());
        }

        if (CollisionDetector.checkCollision(basketball, hoop)) {
            if (isAboveHoop) {
                isEnteringHoop = true;
            }

            if (isEnteringHoop && isPassingThroughHoop) {
                if (!hasScored) {
                    score += 2;
                    hasScored = true;
                }
            } else {
                hasScored = false;
                isEnteringHoop = false;
            }
        }

        if (isPassingThroughHoop) {
            isAboveHoop = false;
            isPassingThroughHoop = false;
            isEnteringHoop = false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (basketball.isBallReset()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialTouchX = event.getX();
                    initialTouchY = event.getY();
                    if (!isTimerStarted) {
                        countDownTimer.start();
                        isTimerStarted = true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float dx = (event.getX() - initialTouchX) / 8;
                    float dy = (event.getY() - initialTouchY) / 8;
                    basketball.setVelocity(dx, dy);
                    break;
            }
            return true;
        }
        return false;
    }
    public GameLoopThread getGameLoopThread() {
        return gameLoopThread;
    }
}
