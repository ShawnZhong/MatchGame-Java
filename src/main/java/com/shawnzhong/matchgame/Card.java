package com.shawnzhong.matchgame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Shawn on 2017/6/5.
 * Card
 * Status:
 * -1: Closed
 * 0: Open
 * 1: Paired
 */

public class Card extends FrameLayout {
    private int num;
    private TextView label;
    private int status;
    private GradientDrawable shape;
    private GameView gameView;


    public Card(@NonNull Context context) {
        super(context);
        
        shape = new GradientDrawable();
        shape.setCornerRadius(12);

        gameView = GameView.getGameView();

        label = new TextView(getContext());
        label.setGravity(Gravity.CENTER);
        label.setTextColor(0xffffffff);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 10, 10);
        addView(label, lp);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.performClick();
                }
                return true;
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                label.setTextSize(getHeight() / 6);
            }
        });
    }

    @Override
    public boolean performClick() {
        super.performClick();

        final Card prevCard = gameView.getPreviousCard();

        setStatus(status > 0 ? 1 : 0);

        if (getStatus() != 1 && prevCard != this) {
            if (prevCard == null) {
                gameView.setPreviousCard(this);
            } else if (prevCard.getNum() == this.getNum()) {
                setStatus(1);
                prevCard.setStatus(1);
                gameView.setPreviousCard(null);
            } else {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setStatus(-1);
                        prevCard.setStatus(-1);
                    }
                }, 300);
                gameView.setPreviousCard(null);
            }
        }

        if (gameView.checkEnd()) {
            new AlertDialog.Builder(getContext())
                    .setTitle("WOW!")
                    .setMessage("You Win!")
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gameView.startGame();
                        }
                    }).show();
        }

        return true;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        setStatus(0);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

        switch (status) {
            case 0:
                label.setText(String.format("%s", num));
                switch (num) {
                    case 1:
                        shape.setColor(0xfff2995a);
                        break;
                    case 2:
                        shape.setColor(0xfff2e55a);
                        break;
                    case 3:
                        shape.setColor(0xffa6f25a);
                        break;
                    case 4:
                        shape.setColor(0xff5af2bf);
                        break;
                    case 5:
                        shape.setColor(0xff5a8df2);
                        break;
                    case 6:
                        shape.setColor(0xff995af2);
                        break;
                    case 7:
                        shape.setColor(0xfff25ad9);
                        break;
                    case 8:
                        shape.setColor(0xfff25a5a);
                        break;
                    case 9:
                        shape.setColor(0xffc20078);
                        break;
                    case 10:
                        shape.setColor(0xffff81c0);
                        break;
                }
                label.setBackground(shape);
                break;
            case 1:
                label.setText(String.format("%s", num));
                shape.setColor(0xffcccccc);
                label.setBackground(shape);
                break;
            case -1:
                label.setText("");
                shape.setColor(0xff204056);
                label.setBackground(shape);
                break;
        }
    }

}
