package com.shawnzhong.matchgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void restart(View view) {
        ((GameView) findViewById(R.id.gameView)).startGame();
    }

    public void setting(View view) {
        final GameView gameView = (GameView) findViewById(R.id.gameView);

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.settingTitle)
                .setItems(new String[]{ "4 × 3", "4 × 4","5 × 4"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                gameView.initGame(4, 3);
                                break;
                            case 1:
                                gameView.initGame(4, 4);
                                break;
                            case 2:
                                gameView.initGame(5, 4);
                                break;
                        }
                    }
                }).show();
    }
}
