package com.example.blacklightassignment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.example.blacklightassignment.enums.GameState;

import java.util.Random;

public class GameManager {

    private final Context context;
    private final View holderOne;
    private final View holderTwo;
    private final View holderThree;
    private final View holderFour;
    private final TextView scoreBoard;

    private GameState state = GameState.RUNNING;
    private int score = 0;
    private int currentHolder = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final long TIME = 1000;
    private final Vibrator vibrator;

    public GameManager(Context context, View holderOne, View holderTwo, View holderThree,
                       View holderFour, TextView scoreBoard) {
        this.context = context;
        this.holderOne = holderOne;
        this.holderTwo = holderTwo;
        this.holderThree = holderThree;
        this.holderFour = holderFour;
        this.scoreBoard = scoreBoard;

        restart();
        inputManager();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void restart() {
        state = GameState.RUNNING;
        changeHolder();
    }

    private void inputManager() {
        holderOne.setOnClickListener(view -> selectHolder(1));

        holderTwo.setOnClickListener(view -> selectHolder(2));

        holderThree.setOnClickListener(view -> selectHolder(3));

        holderFour.setOnClickListener(view -> selectHolder(4));
    }

    private void vibrate() {
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }

    private void gameOver() {
        state = GameState.GAME_OVER;

        new AlertDialog.Builder(context)
                .setTitle("Game Over")
                .setMessage("Score is: " + score)
                .setCancelable(false)
                .setPositiveButton("Retry", (dialog, which) -> {
                    restart();
                    dialog.dismiss();
                })
                .show();

        score = 0;
    }

    private void selectHolder(int holder) {
        vibrate();
        if (state != GameState.RUNNING) return;

        handler.removeCallbacksAndMessages(null);

        if (holder == currentHolder) {
            score++;
            changeHolder();
        } else {
            gameOver();
        }
    }

    private void changeCurrentSelectedHolderColor() {
        int color = context.getColor(androidx.appcompat.R.color.material_grey_850);
        switch (currentHolder) {
            case 1:
                holderOne.setBackgroundColor(color);
                break;
            case 2:
                holderTwo.setBackgroundColor(color);
                break;
            case 3:
                holderThree.setBackgroundColor(color);
                break;
            case 4:
                holderFour.setBackgroundColor(color);
                break;
        }
    }

    private void restoreColorOfAllHolders() {
        switch (currentHolder) {
            case 1:
                holderOne.setBackgroundColor(context.getColor(R.color.roseWater));
                break;
            case 2:
                holderTwo.setBackgroundColor(context.getColor(R.color.spearMint));
                break;
            case 3:
                holderThree.setBackgroundColor(context.getColor(R.color.peach));
                break;
            case 4:
                holderFour.setBackgroundColor(context.getColor(R.color.turquoise));
                break;
        }
    }

    private int getRandomHolder() {
        Random random = new Random();
        int holder = random.nextInt(4) + 1;
        return (holder != currentHolder) ? holder : getRandomHolder();
    }

    private void changeHolder() {
        scoreBoard.setText("Score: " + score);
        restoreColorOfAllHolders();
        currentHolder = getRandomHolder();
        changeCurrentSelectedHolderColor();
        handler.postDelayed(() -> gameOver(), TIME);
    }

    public void destroy() {
        handler.removeCallbacksAndMessages(null);
    }
}
