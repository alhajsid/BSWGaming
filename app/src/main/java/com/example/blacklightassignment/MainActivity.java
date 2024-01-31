package com.example.blacklightassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager(
                this,
                findViewById(R.id.holder_1),
                findViewById(R.id.holder_2),
                findViewById(R.id.holder_3),
                findViewById(R.id.holder_4),
                findViewById(R.id.scoreBoardTextView)
        );
    }

    @Override
    protected void onDestroy() {
        if (gameManager != null) {
            gameManager.destroy();
        }
        super.onDestroy();
    }
}
