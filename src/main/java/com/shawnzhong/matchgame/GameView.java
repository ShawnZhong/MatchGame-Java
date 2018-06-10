package com.shawnzhong.matchgame;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shawn on 2017/6/5.
 * It's the main view of the game.
 */

public class GameView extends GridLayout {
    private int row = 5;
    private int column = 4;
    private static GameView gameView = null;
    private Card previousCard = null;
    private Card[][] cardsMap;

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public static GameView getGameView() {
        return gameView;
    }

    public void initGameView() {
        gameView = this;
        post(new Runnable() {
            @Override
            public void run() {
                initGame(row, column);
            }
        });
    }

    public void initGame(int row, int column) {
        removeAllViews();

        this.column = column;
        this.row = row;
        setColumnCount(column);
        setRowCount(row);
        cardsMap = new Card[column][row];

        int cardSize = getWidth() / column;

        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                cardsMap[x][y] = new Card(getContext());
                addView(cardsMap[x][y], cardSize, cardSize);
            }
        }


        startGame();
    }


    public void startGame() {
        List<Point> points = new ArrayList<>(row * column);
        previousCard = null;

        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                points.add(new Point(x, y));
            }
        }

        for (int i = 1; i <= column * row / 2; i++) {
            Point p = points.remove((int) (Math.random() * points.size()));
            cardsMap[p.x][p.y].setNum(i);
            p = points.remove((int) (Math.random() * points.size()));
            cardsMap[p.x][p.y].setNum(i);
        }

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < column; x++) {
                    for (int y = 0; y < row; y++) {
                        cardsMap[x][y].setStatus(-1);
                    }
                }
            }
        }, column * row * 200);

    }

    public boolean checkEnd() {
        for (int x = 0; x < column; x++) {
            for (int y = 0; y < row; y++) {
                if (cardsMap[x][y].getStatus() != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public Card getPreviousCard() {
        return previousCard;
    }

    public void setPreviousCard(Card previousCard) {
        this.previousCard = previousCard;
    }
}


