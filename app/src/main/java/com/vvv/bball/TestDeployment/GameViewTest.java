//package com.vvv.bball.TestDeployment;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.DashPathEffect;
//import android.graphics.Paint;
//import android.view.MotionEvent;
//import android.view.SurfaceView;
//
//import androidx.annotation.NonNull;
//
//import com.vvv.bball.R;
//
//public class GameViewTest extends SurfaceView implements Runnable {
//
//    private final Paint paint1;            // precursor of throw trajectory.
//    private final Paint paint2;            // axis in respect to initial point of the basketballTest.
//    private final Paint paint3;            // basketballTest hit-box.
//    private final Paint paint4;            // axis in respect to the basketballTest.
//    private final Paint paint5;            // trajectory of the basketballTest.
//    private final Paint path_paint;            // hitting point.
//    private final GroundTest groundTest;
//    private final short screenX, screenY;
//    private final GameActivityTest activity;
//    private final Context gameActivityContext;
//    // Objects
//    float SLEEP_MILLIS;
//    private final BackgroundTest backgroundTest;
//    private final BasketballTest basketballTest;
//    private BasketTest basketTest;
//    private float game_time;
//    private boolean isPlaying;
//    private Thread thread;
//    private byte quarterOfLaunch;
//
//    // Technical stuff
//    private Bitmap showAxis;   // screen axis in comparison to initial basketballTest place #12
//    private byte showAxisBool; // 0 -> false, 1 -> true
//
//
//    public GameViewTest(GameActivityTest activity, short screenX, short screenY) {
//        super(activity);
//
//        this.activity = activity;
//        gameActivityContext = this.activity;
//
//        this.screenX = screenX;
//        this.screenY = screenY;
//
//
//        isPlaying = true;
//
//
//        backgroundTest = new BackgroundTest(getResources(), screenX, screenY);
//        basketballTest = new BasketballTest(getResources(), screenX, screenY);
//        groundTest = new GroundTest(getResources(), screenX + 100, screenY);
//
//
//        basketballTest.maxBallPull = (short) (screenY - groundTest.height - basketballTest.initialY - basketballTest.height); // the radius of max dist of the basketballTest from the initial position
//
//        game_time = 0;
//
//        quarterOfLaunch = 0;
//        basketballTest.thrown = false;
//
//
//        paint1 = new Paint();
//        paint1.setColor(Color.rgb(224, 65, 11));
//        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint1.setPathEffect(new DashPathEffect(new float[]{20, 20}, 0)); // array of ON and OFF distances,
//        paint1.setStrokeWidth(3f);
//
//
//        paint2 = new Paint();
//        paint2.setColor(Color.GREEN);
//        paint2.setStyle(Paint.Style.FILL);
//        paint2.setStrokeWidth(5f);
//
//
//        paint3 = new Paint();
//        paint3.setColor(Color.RED);
//        paint3.setStyle(Paint.Style.FILL);
//        paint3.setStrokeWidth(1f);
//
//
//        paint4 = new Paint();
//        paint4.setColor(Color.WHITE);
//        paint4.setStyle(Paint.Style.FILL);
//        paint4.setStrokeWidth(3f);
//
//        paint5 = new Paint();
//        paint5.setColor(Color.BLUE);
//        paint5.setStyle(Paint.Style.FILL);
//        paint5.setStrokeWidth(4f);
//
//        path_paint = new Paint();
//        path_paint.setColor(Color.rgb(189, 146, 81));
//        path_paint.setStyle(Paint.Style.FILL);
//        path_paint.setStrokeWidth(4f);
//
//
//        showAxis = BitmapFactory.decodeResource(getResources(), R.drawable.play_btn);
//        showAxis = Bitmap.createScaledBitmap(showAxis, basketballTest.width * 3, basketballTest.height * 3, false);
//        showAxisBool = 0;
//
//    }
//
//
//    @Override
//    public void run() {
//        while (isPlaying) // run only if playing.
//        {
//            sleep();//To render motion (FPS).
//            update();//The components.
//            draw();//Components on screen.
//        }
//    }
//
//
//    public void update() // issue: physics #25
//    {//discussion (bug fix): physics don't work when angle is -90 or 90 #29
//
//
//        if (basketballTest.thrown) {
//            basketballTest.prevX = basketballTest.x;
//            basketballTest.prevY = basketballTest.y; // for collision physics.
//            // discussion: Changing Direction #34 || to prevent a bug (of dotArrayListX/Y) -> discussion: The Dots look disgusting #28
//
//            physicsUpdateNoCol();  // -> if collided will call physicsUpdate()
//        } else if (basketballTest.isTouched) // get quarter right before the basketballTest is thrown.
//            quarterOfLaunch = basketballTest.quarter; // discussion: From where has the basketballTest been thrown? #24
//
//
//        basketballTest.dotArrayListX.add(basketballTest.prevX + fixX()); // → ↓
//        basketballTest.dotArrayListY.add(basketballTest.prevY + fixY()); // show path of the basketballTest
//
//    }/* UPDATE */
//
//
//    public void physicsUpdateNoCol() // issue: physics #25
//    {
//
//        if (basketballTest.didCollide(groundTest.height) == 0) // if did not collide
//        {
//
//
//            if (quarterOfLaunch == 2) {
//                basketballTest.vx = abs(basketballTest.vx);
//                basketballTest.vy = abs(basketballTest.vy);
//            } else {
//                basketballTest.vy = quarterOfLaunch == 1 ? abs(basketballTest.vy) : -1 * abs(basketballTest.vy);
//                basketballTest.vx = quarterOfLaunch == 3 ? abs(basketballTest.vx) : -1 * abs(basketballTest.vx);
//            } // =4 -> -1 * abs(basketballTest.vy) && -1 * abs(basketballTest.vx)
//
//            basketballTest.vy = basketballTest.v0y + basketballTest.GRAVITY * basketballTest.time;
//
//
//            basketballTest.x = calcX();
//            basketballTest.y = calcY();
//
//
//            // Explanation: In previous attempts, I didn't change the velocities, but rather the way the basketballTest moves,
//            // for example: basketballTest.x = basketballTest.initialX - basketballTest.vx * time;
//            // Now this did work but I had to make 4 different cases for | -- | -+ | +- | ++ |
//            // which is unreadable. Instead, I'll change the velocities according to where they should move towards.
//            // Discussion: physics #25
//        } else
//            physicsUpdate(basketballTest.collision); // discussion: Changing Direction #34 | issue: Collision Physics #26
//    }
//
//
//    public void physicsUpdate(byte col) // col -> collision number (type)
//    {
//
//        switch (col) {
//            case 1: // right wall
//
//
//                basketballTest.vy = basketballTest.v0y + basketballTest.GRAVITY * basketballTest.time;
//
//                basketballTest.x = calcX() + basketballTest.colX - basketballTest.initialX + (basketballTest.colX - basketballTest.initialX); // ✔️? but why ?
//                basketballTest.y = calcY(); // ✔️
//
//
//                break;
//            // (function[y]) + new axis = new function! 🤩
//            /*ALSO WORKS:  +(screenX - basketballTest.initialX + basketballTest.colX - abs(basketballTest.colX - screenX))*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//            case 2: // left wall
//
//                basketballTest.vy = basketballTest.v0y + basketballTest.GRAVITY * basketballTest.time;
//
//                basketballTest.x = calcX() - screenX - basketballTest.initialX;
//                basketballTest.y = calcY();
//
//                break;
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//            default: // floor
//
//                basketballTest.vy = basketballTest.v0y + basketballTest.GRAVITY * basketballTest.time;
//
//                basketballTest.vy = -1 * Math.abs(basketballTest.vy);
//
//                basketballTest.x = calcX();
//
//                basketballTest.y = calcY();
//
//                break;
//
//        }
//
//
//    }
//
//
//    public void draw() {
//        if (getHolder().getSurface().isValid()) // is the surface valid?
//        {
//            Canvas screenCanvas = getHolder().lockCanvas(); // create the canvas
//
//            // KEEP IN MIND THAT THE ORDER MATTERS! ↓
//
//            if (showAxisBool == 0)
//                screenCanvas.drawBitmap(backgroundTest.backgroundBitmap, 0, 0, paint1);//backgroundTest
////            else
////                screenCanvas.drawBitmap(backgroundTest.devBackgroundBitmap, 0, 0, paint1);//backgroundTest
//
//
//            if (basketballTest.time > 0.032)
//                for (short i = 0; i < basketballTest.dotArrayListX.size() - 1; i++)
//                    try {
////                        if (!(basketballTest.y + basketballTest.height + (basketballTest.y - basketballTest.prevY) >= screenY - groundTest.height)) // TODO
//                        screenCanvas.drawCircle(basketballTest.dotArrayListX.get(i), basketballTest.dotArrayListY.get(i), basketballTest.width / 20f, paint3/*path_paint*/);
//                    } catch (Exception ignored) {
//                    }
//            // discussion: The Dots look disgusting #28
//
//
//            screenCanvas.drawBitmap(basketballTest.ballBitmap, basketballTest.x, basketballTest.y, paint1);//basketballTest
//            screenCanvas.drawBitmap(groundTest.groundBitmap, groundTest.x, groundTest.y, paint1);//groundTest
//
//            showStats(screenCanvas);
//
//
//            if (!basketballTest.thrown) // draw this line only before the basketballTest is thrown.
//            {
//                basketballTest.v = 0;
//                basketballTest.vx = 0;
//                basketballTest.vy = 0;
//
//
//                //discussion: X and Y of stop screenCanvas.DrawLine #15 | issue: correcting the line with the basketballTest #13
//                if (basketballTest.calcDistanceFromI(basketballTest.x + fixX(), basketballTest.y + fixY()) > basketballTest.width / 2f) // don't draw inside the basketballTest
//                {
//
//                    float lineStopX = abs((Math.cos(basketballTest.ballAngle()) * fixX())); // similar to perpAdj
//                    float lineStopY = abs((Math.sin(basketballTest.ballAngle()) * fixX())); // similar to perpOpp | fixX() = radius of the basketballTest so..
//
//
//                    switch (basketballTest.quarter) // draw a line to the opposite corner
//                    {
//                        case 1:
//                            screenCanvas.drawLine(basketballTest.x + fixX() - lineStopX, basketballTest.y + fixY() + lineStopY,
//                                    basketballTest.initialX - (basketballTest.x - basketballTest.initialX) - fixX(),
//                                    basketballTest.initialY + (basketballTest.initialY - basketballTest.y) - fixY(), paint1);
//                            break;
//                        case 2:
//                            screenCanvas.drawLine(basketballTest.x + fixX() + lineStopX, basketballTest.y + fixY() + lineStopY,
//                                    basketballTest.initialX + (basketballTest.initialX - basketballTest.x) - fixX(),
//                                    basketballTest.initialY + (basketballTest.initialY - basketballTest.y) - fixY(), paint1);
//                            break;
//                        case 3:
//                            screenCanvas.drawLine(basketballTest.x + fixX() + lineStopX, basketballTest.y + fixY() - lineStopY,
//                                    basketballTest.initialX + (basketballTest.initialX - basketballTest.x) - fixX(),
//                                    basketballTest.initialY - (basketballTest.y - basketballTest.initialY) - fixY(), paint1);
//                            break;
//                        case 4:
//                            screenCanvas.drawLine(basketballTest.x + fixX() - lineStopX, basketballTest.y + fixY() - lineStopY,
//                                    basketballTest.initialX - (basketballTest.x - basketballTest.initialX) - fixX(),
//                                    basketballTest.initialY - (basketballTest.y - basketballTest.initialY) - fixY(), paint1);
//                            break;
//                    }
//                }
//            }
//            getHolder().unlockCanvasAndPost(screenCanvas);
//        }
//    } /* DRAW */
//
//
//    private void sleep() // discussion: Time updating #33 | byte is like int | refresh rate is (1000 / SLEEP_MILLIS = 62.5 FPS)
//    {
//        SLEEP_MILLIS = 1000 / 120f;//
//
//        try {
//            Thread.sleep((long) (SLEEP_MILLIS / 2));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //count time from throw:
////        basketballTest.time = basketballTest.thrown ? basketballTest.time + SLEEP_MILLIS/1000f : 0;
//        if (basketballTest.thrown)
//            basketballTest.time += SLEEP_MILLIS / 1000;  // = 0.022 -> cuz it looks good
//        else
//            basketballTest.time = 0;
//
//
//        game_time += SLEEP_MILLIS / 1000;  // = 0.01
//    }
//
//
//    float calcX() // d0(x0) + Vx * t
//    {
//        return (basketballTest.initialX + basketballTest.vx * basketballTest.time);
//    }
//
//    float calcY() // h(y0) + Vy * t - g * t² / 2
//    {
//        return (basketballTest.initialY + basketballTest.vy * basketballTest.time - basketballTest.GRAVITY * basketballTest.time * basketballTest.time / 2);
//    }
//
//    // ooh fancy 👌
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) // this is a method that helps me detect touch.
//    {
//        switch (event.getAction()) // down/move/up
//        {
//
//            case MotionEvent.ACTION_DOWN:// started touch
//
//                if (basketballTest.isTouching(event.getX(), event.getY()) && !basketballTest.thrown)
//                    basketballTest.isTouched = true; // 'basketballTest' has a boolean method that indicates whether the object is touched.
//
//
//                if (basketballTest.thrown && basketballTest.calcDistanceFromI(event.getX(), event.getY()) <= basketballTest.maxBallPull)
//                    basketballTest.reset(); // reset when touch origin.
//
//
//                if (event.getRawX() >= screenX / 2f - basketballTest.width * 3 && event.getRawX() <= screenX / 2f + basketballTest.width * 3
//                        && event.getRawY() >= 0 && event.getRawY() <= basketballTest.height * 3)
//
//                    showAxisBool = (byte) ((showAxisBool == 0) ? 1 : 0);
//
//
//                // turn dev mode on or off.
//
//                break;
//
//
//            case MotionEvent.ACTION_MOVE: // pressed and moving
//            {
//                if (basketballTest.isTouched) // if touched the basketballTest
//                {
//
//                    float angle_of_touch = basketballTest.findAngleWhenOutside(event.getX(), event.getY()); // also sets basketballTest.angle
//
//
//                    if (abs(180 / Math.PI * angle_of_touch) > 90) // right side
//                        if (180 / Math.PI * angle_of_touch >= 0)
//                            basketballTest.quarter = 1; // top right corner
//                        else
//                            basketballTest.quarter = 4; // bottom right corner
//
//                    else // left side
//                        if (180 / Math.PI * angle_of_touch >= 0)
//                            basketballTest.quarter = 2; // top left corner
//                        else
//                            basketballTest.quarter = 3; // bottom left corner
//
//                    // TODO: DON'T EVER CHANGE THIS !!!
//
//
//                    if (basketballTest.calcDistanceFromI(event.getX(), event.getY()) < basketballTest.maxBallPull) // if in drag-able circle
//                        basketballTest.setPosition(event.getX(), event.getY());                         // than drag normally.
//
//
//                    else // issue: finger drag outside of radius #4
//                    {
//                        float perpOpp = abs(Math.sin(angle_of_touch) * basketballTest.maxBallPull); // for y of maxBallPull
//                        float perpAdj = abs(Math.cos(angle_of_touch) * basketballTest.maxBallPull); // for x
//                        //  perp- perpendicular (ניצב), adj- adjacent (ליד), opp- opposite (מול)
//
//
//                        switch (basketballTest.quarter) {
//                            case 1:
//                                basketballTest.setPosition(basketballTest.initialX + perpAdj, basketballTest.initialY - perpOpp);
//                                break;
//                            case 2:
//                                basketballTest.setPosition(basketballTest.initialX - perpAdj, basketballTest.initialY - perpOpp);
//                                break;
//                            case 3:
//                                basketballTest.setPosition(basketballTest.initialX - perpAdj, basketballTest.initialY + perpOpp);
//                                break;
//                            case 4:
//                                basketballTest.setPosition(basketballTest.initialX + perpAdj, basketballTest.initialY + perpOpp);
//                                break;
//                        } // discussion: screen axis in comparison to initial basketballTest place #12
//
//                    }// outside drag-able circle
//                }// if touched the basketballTest
//            }//ACTION_MOVE
//
//            break;
//
//
//            case MotionEvent.ACTION_UP: // ended touch || discussion: Throw from stretched point #40
//
//                if (basketballTest.isTouched) // if touched the basketballTest in the first place.
//                {
//                    if (basketballTest.calcDistanceFromI(basketballTest.x + fixX(), basketballTest.y + fixY()) < basketballTest.width) {
//                        basketballTest.x = basketballTest.orgIX - fixX();
//                        basketballTest.y = basketballTest.orgIY - fixY();
//                    } // discussion: Disable basketballTest movement when only touched briefly #31
//
//
//                    else { // shot
//
//                        basketballTest.thrown = true;
//
//
//                        basketballTest.percentOfPull = basketballTest.calcDistanceFromI(basketballTest.x + fixX(), basketballTest.y + fixY()) / basketballTest.maxBallPull;
//
//                        basketballTest.v = basketballTest.percentOfPull * basketballTest.MAX_VELOCITY;
//                        // Percent of pull * max velocity = percent of max velocity
//
//                        basketballTest.vx = abs(Math.cos(basketballTest.angle) * basketballTest.v);
//
//                        if (quarterOfLaunch == 1 || quarterOfLaunch == 2)
//                            basketballTest.v0y = abs(Math.sin(basketballTest.angle) * basketballTest.v);
//                        else
//                            basketballTest.v0y = -1 * abs(Math.sin(basketballTest.angle) * basketballTest.v);
//                        // Both of these values never change after the basketballTest is thrown.
//
//                        basketballTest.orgIX = basketballTest.initialX;
//                        basketballTest.orgIY = basketballTest.initialY;
//
//                        basketballTest.initialX = basketballTest.x;
//                        basketballTest.initialY = basketballTest.y;
//                    }
//
//                }
//                basketballTest.isTouched = false;
//
//                break;
//        }
//        return true;
//    }
//
//
//    /////////////////////////////////////////////////////////////////////////
//    // general functions
//
//
//    public void showStats(@NonNull Canvas screenCanvas) {
//
//        screenCanvas.drawLine(0, 0, 0, screenY, paint5);
//        screenCanvas.drawLine(screenX, 0, screenX, screenY, paint5);
//
//        if (basketballTest.collision != 0) {
//            screenCanvas.drawPoint(basketballTest.colX, basketballTest.colY, paint5);
//            screenCanvas.drawLine(basketballTest.colX, basketballTest.colY, basketballTest.x + fixX(), basketballTest.y + fixY(), paint5);
//        }
//
//        if (showAxisBool == 1) {
//
//            if (basketballTest.thrown) {
//                screenCanvas.drawLine(basketballTest.prevX, basketballTest.prevY + fixY(), basketballTest.x, basketballTest.y + fixY(), paint5);
////              SHOW BALL AXIS:
//                screenCanvas.drawLine(0, basketballTest.y + fixY(), screenX, basketballTest.y + fixY(), paint4);
//                screenCanvas.drawLine(basketballTest.x + fixX(), 0, basketballTest.x + fixX(), screenY, paint4);
//
//                // SHOW i AXIS:
//                screenCanvas.drawLine(0, basketballTest.initialY + fixY(), screenX, basketballTest.initialY + fixX(), paint3);
//                screenCanvas.drawLine(basketballTest.initialX + fixX(), 0, basketballTest.initialX + fixX(), screenY, paint3);
//
//            }
////              SHOW org AXIS:
//            screenCanvas.drawLine(0, basketballTest.orgIY, screenX, basketballTest.orgIY, paint2);
//            screenCanvas.drawLine(basketballTest.orgIX, 0, basketballTest.orgIX, screenY, paint2);
//
//
////          SHOW BALL HITBOX
//            screenCanvas.drawLine(basketballTest.x, basketballTest.y, basketballTest.x + basketballTest.width, basketballTest.y, paint3);
//            screenCanvas.drawLine(basketballTest.x, basketballTest.y, basketballTest.x, basketballTest.y + basketballTest.height, paint3);
//            screenCanvas.drawLine(basketballTest.x + basketballTest.width, basketballTest.y, basketballTest.x + basketballTest.width, basketballTest.y + basketballTest.height, paint3);
//            screenCanvas.drawLine(basketballTest.x, basketballTest.y + basketballTest.height, basketballTest.x + basketballTest.width, basketballTest.y + basketballTest.height, paint3);
//
//
//            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//            screenCanvas.drawText("X: " + basketballTest.x + fixX(), 75, 50, paint2);
//            screenCanvas.drawText("Y: " + basketballTest.y + fixY(), 75, 75, paint2);
//
//            screenCanvas.drawText("Angle: ∠ " + (float) (180 / Math.PI * basketballTest.angle) + "°", 75, 110, paint2);
//            screenCanvas.drawText("velocity (m/s): " + basketballTest.v / basketballTest.ratioMtoPX, 75, 130, paint2);
//            screenCanvas.drawText("velocityX (m/s): " + basketballTest.vx / basketballTest.ratioMtoPX, 75, 150, paint2);
//            screenCanvas.drawText("velocityY (m/s): " + basketballTest.vy / basketballTest.ratioMtoPX, 75, 170, paint2);
//            screenCanvas.drawText("v0y: " + basketballTest.v0y / basketballTest.ratioMtoPX, 75, 210, paint2);
//
//            screenCanvas.drawText("collided: " + basketballTest.collision, screenX / 2f - basketballTest.width * 3, basketballTest.height * 3 + 50, paint2);
//            screenCanvas.drawText("colX: " + basketballTest.colX, screenX / 2f - basketballTest.width * 3, basketballTest.height * 3 + 70, paint2);
//            screenCanvas.drawText("colY: " + basketballTest.colY, screenX / 2f - basketballTest.width * 3, basketballTest.height * 3 + 90, paint2);
//
//            screenCanvas.drawText("quarterOfLaunch: " + quarterOfLaunch, screenX / 2f - basketballTest.width * 3, basketballTest.height * 3 + 110, paint2);
//            screenCanvas.drawText("range: " + basketballTest.range, screenX / 2f - basketballTest.width * 3, basketballTest.height * 3 + 130, paint2);
//            screenCanvas.drawText("HEIGHT: " + basketballTest.HEIGHT, screenX / 2f - basketballTest.width * 3, basketballTest.height * 3 + 150, paint2);
//
//            screenCanvas.drawText("Time: " + (int) game_time + "s", screenX / 2f + basketballTest.width * 3, 50, paint2);
//
//
//        }
//
//        screenCanvas.drawBitmap(showAxis, screenX / 2f - basketballTest.width * 3, 0, paint1);//button to show initial axis
//
//    } // TODO: THIS BLOCK IS TEMP
//
//
//    public void resume() // discussion: "activity lifecycle"
//    {
//        isPlaying = true;
//        thread = new Thread(this); // -> "this" is the run() method above.
//        thread.start();
//    } // resume the game
//
//
//    public void pause()  // discussion: "activity lifecycle"
//    {
//        try {
//            isPlaying = false;
//            thread.join(); // join = stop
//            Thread.sleep(100);
//
////            activity.PauseMenu();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public Context getGameActivityContext() {
//        return gameActivityContext;
//    }
//
//
//    public float fixX() {
//        return basketballTest.width / 2f;
//    }// for comfortable coding.
//
//    public float fixY() {
//        return basketballTest.height / 2f;
//    }// for comfortable coding.
//
//    public float abs(double num) {
//        return (float) Math.abs(num);
//    } // saves space -> '(float)' and 'Math.' are unnecessary.
//}