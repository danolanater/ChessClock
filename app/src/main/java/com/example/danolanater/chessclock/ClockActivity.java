package com.example.danolanater.chessclock;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

// todo: handle bundle from tournament Fragment, update menu buttons

public class ClockActivity extends AppCompatActivity {

    private TextView whiteMovesTextView, blackMovesTextView, stagesRemainingTextView, tournamentNameTextView;
    private Button whiteButton, blackButton;
    private int whiteTimeInt, blackTimeInt, whiteIncrementInt, blackIncrementInt, whiteDelayInt, blackDelayInt;

    private ImageView settings, play, pause, restart;

    private CountDownTimer whiteTimer, blackTimer;
    private int whiteMoveCount = 0, blackMoveCount = -1;
    private boolean isHourglass, isTournament;
    private boolean isStopwatch = false;
    private Chronometer whiteChronometer, blackChronometer;
    private long whiteStopwatchOffset = 0, blackStopwatchOffset = 0, pauseBase = 0;

    private boolean activeGame = false;
    private String whiteText, blackText;

    private ArrayList<Stage> stages;
    private Stage currentStage;
    private ListIterator<Stage> stageIterator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_layout);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        setValues(b);

        // this timer has no use other than to exist so initial calls to blackTimer wont throw errors
        blackTimer = new CountDownTimer(1, 0) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };

        whiteButton.setClickable(false);


        if (isStopwatch) {

            blackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setToActiveGame();

                    whiteChronometer.setBase(SystemClock.elapsedRealtime() - whiteStopwatchOffset);
                    whiteChronometer.start();

                    blackChronometer.stop();
                    blackStopwatchOffset = SystemClock.elapsedRealtime() - blackChronometer.getBase();

                    incrementBlackMove();

                    blackButton.setClickable(false);
                    whiteButton.setClickable(true);
                }
            });

            whiteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setToActiveGame();

                    blackChronometer.setBase(SystemClock.elapsedRealtime() - blackStopwatchOffset);
                    blackChronometer.start();

                    whiteStopwatchOffset = SystemClock.elapsedRealtime() - whiteChronometer.getBase();
                    whiteChronometer.stop();

                    incrementWhiteMove();

                    whiteButton.setClickable(false);
                    blackButton.setClickable(true);
                }
            });
        } else {
            blackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setToActiveGame();
                    whiteTimer = new CountDownTimer(whiteTimeInt, 100) {
                        int delay = whiteDelayInt;

                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (activeGame) {
                                if (delay > 0) {
                                    delay -= 100;
                                } else {
                                    whiteTimeInt -= 100;
                                    if (isHourglass)
                                        blackTimeInt += 100;

                                    blackText = parseString(blackTimeInt);
                                    whiteText = parseString(whiteTimeInt);
                                    blackButton.setText(blackText);
                                    whiteButton.setText(whiteText);
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            whiteButton.setClickable(false);
                            blackButton.setClickable(false);
                            whiteButton.setText("Black Wins!");
                        }
                    };

                    blackTimer.cancel();
                    incrementBlackMove();
                    whiteTimer.start();

                    if (blackIncrementInt > 0 && blackMoveCount != 0) {
                        blackTimeInt += blackIncrementInt;
                        blackButton.setText(parseString(blackTimeInt));
                    }

                    if (isTournament) {

                        if(!stageIterator.hasNext())
                            blackMovesTextView.setText("Move " + blackMoveCount + " of Sudden Death");
                        else
                            blackMovesTextView.setText("Move " + blackMoveCount + " of " + currentStage.getMoves());

                        if(stageCompleted(blackMoveCount)) {

                            blackMoveCount = 0;
                            currentStage = stageIterator.next();
                            currentStage = stageIterator.next();
                            blackTimeInt += parseTime(currentStage.getTime());
                            blackButton.setText(parseString(blackTimeInt));

                            if(!stageIterator.hasNext()) {
                                stagesRemainingTextView.setText("Stage " + stages.size() + " of " + stages.size());
                                whiteMovesTextView.setText("Move 0 of Sudden Death");
                            } else {
                                stagesRemainingTextView.setText(currentStage.getName() + " of " + stages.size());
                                whiteMovesTextView.setText("Move 0 of " + currentStage.getMoves());
                            }
                        }
                    }

                    whiteButton.setEnabled(true);
                    blackButton.setEnabled(false);
                }
            });

            whiteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setToActiveGame();
                    blackTimer = new CountDownTimer(blackTimeInt, 100) {
                        int delay = blackDelayInt;

                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (activeGame) {
                                if (delay > 0) {
                                    delay -= 100;
                                } else {
                                    blackTimeInt -= 100;
                                    if (isHourglass)
                                        whiteTimeInt += 100;

                                    blackText = parseString(blackTimeInt);
                                    whiteText = parseString(whiteTimeInt);
                                    blackButton.setText(blackText);
                                    whiteButton.setText(whiteText);
                                }


                            }
                        }

                        @Override
                        public void onFinish() {
                            blackButton.setClickable(false);
                            whiteButton.setClickable(false);
                            blackButton.setText("White Wins!");
                        }
                    };

                    whiteTimer.cancel();
                    incrementWhiteMove();
                    blackTimer.start();
                    if (whiteIncrementInt > 0) {
                        whiteTimeInt += whiteIncrementInt;
                        whiteButton.setText(parseString(whiteTimeInt));
                    }

                    if (isTournament) {

                        if(!stageIterator.hasNext())
                            whiteMovesTextView.setText("Move " + whiteMoveCount + " of Sudden Death");
                        else
                            whiteMovesTextView.setText("Move " + whiteMoveCount + " of " + currentStage.getMoves());

                        if(stageCompleted(whiteMoveCount)) {
                            whiteMoveCount = 0;
                            currentStage = stageIterator.next();

                            whiteTimeInt += parseTime(currentStage.getTime());
                            whiteButton.setText(parseString(whiteTimeInt));

                            currentStage = stageIterator.previous();
                            currentStage = stageIterator.previous();

                        } else if(whiteMoveCount == 1) {
                            if(currentStage.getMoves() == -1)
                                blackMovesTextView.setText("Move 0 of Sudden Death");
                            else
                                blackMovesTextView.setText("Move 0 of " + currentStage.getMoves());
                        }
                    }

                    whiteButton.setEnabled(false);
                    blackButton.setEnabled(true);
                }
            });
        }

    }

    private boolean stageCompleted(int moveCount) {
        if(moveCount == currentStage.getMoves())
            return true;
        else
            return false;
    }

    private void setToActiveGame() {
        activeGame = true;
        displayPauseIcon();
    }


    private void setValues(Bundle b) {

        whiteButton = (Button) findViewById(R.id.buttonWhite);
        blackButton = (Button) findViewById(R.id.buttonBlack);

        whiteMovesTextView = (TextView) findViewById(R.id.whiteMovesTextView);
        blackMovesTextView = (TextView) findViewById(R.id.blackMovesTextView);
        stagesRemainingTextView = (TextView) findViewById(R.id.stagesRemainingTextView);
        tournamentNameTextView = (TextView) findViewById(R.id.tournamentName);

        settings = (ImageView) findViewById(R.id.settings);
        play = (ImageView) findViewById(R.id.play);
        pause = (ImageView) findViewById(R.id.pause);
        restart = (ImageView) findViewById(R.id.restart);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isStopwatch) {
                    // if it's whites turn
                    if (whiteMoveCount == blackMoveCount) {
                        whiteChronometer.setBase(SystemClock.elapsedRealtime() - (pauseBase - whiteChronometer.getBase()));
                    } else { //it's blacks turn
                        blackChronometer.setBase(SystemClock.elapsedRealtime() - (pauseBase - blackChronometer.getBase()));
                    }
                }

                toggleGameState();

                if (whiteMoveCount == 0 && blackMoveCount == -1) {
                    blackButton.callOnClick();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGameState();

                if (isStopwatch) {
                    pauseBase = SystemClock.elapsedRealtime();
                }
            }
        });

        // Stopwatch Mode Game
        if (b.getBoolean("stopwatchMode")) {
            isStopwatch = true;

            whiteButton.setText("00:00");
            blackButton.setText("00:00");

            whiteChronometer = findViewById(R.id.whiteChronometer);
            blackChronometer = findViewById(R.id.blackChronometer);

            Chronometer.OnChronometerTickListener whiteChronometerTickListener = new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (activeGame)
                        whiteButton.setText(whiteChronometer.getText());
                }
            };

            Chronometer.OnChronometerTickListener blackChronometerTickListener = new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (activeGame)
                        blackButton.setText(blackChronometer.getText());
                }
            };

            whiteChronometer.setOnChronometerTickListener(whiteChronometerTickListener);
            blackChronometer.setOnChronometerTickListener(blackChronometerTickListener);

        } else {

            whiteIncrementInt = parseTime(b.getString("whiteIncrement"));
            blackIncrementInt = parseTime(b.getString("blackIncrement"));

            whiteDelayInt = parseTime(b.getString("whiteDelay"));
            blackDelayInt = parseTime(b.getString("blackDelay"));

            // Standard Game Mode
            if (b.getString("gameMode").equals("Standard")) {

                whiteTimeInt = parseTime(b.getString("whiteTime"));
                blackTimeInt = parseTime(b.getString("blackTime"));

                whiteButton.setText(parseString(whiteTimeInt));
                blackButton.setText(parseString(blackTimeInt));

                if (b.getBoolean("hourglassMode")) {
                    isHourglass = true;
                }

                stagesRemainingTextView.setVisibility(View.GONE);
                tournamentNameTextView.setVisibility(View.GONE);

                // Tournament Game Mode
            } else if (b.getString("gameMode").equals("Tournament")) {
                isTournament = true;



                stages = b.getParcelableArrayList("stages");
                stageIterator = stages.listIterator();

                currentStage = stageIterator.next();

                whiteMovesTextView.setText("Move 0 of " + currentStage.getMoves());

                whiteTimeInt = parseTime(currentStage.getTime());
                blackTimeInt = parseTime(currentStage.getTime());

                whiteButton.setText(parseString(whiteTimeInt));
                blackButton.setText(parseString(blackTimeInt));

                stagesRemainingTextView.setText(currentStage.getName() + " of " + stages.size());
                tournamentNameTextView.setText(b.getString("tournamentName"));
            }
        }
    }

    private void toggleGameState() {
        activeGame = !activeGame;

        if (activeGame)
            displayPauseIcon();
        else
            displayPlayIcon();
    }

    private void displayPauseIcon() {
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams settingsParams = (RelativeLayout.LayoutParams) settings.getLayoutParams();
        settingsParams.addRule(RelativeLayout.START_OF, pause.getId());
        settings.setLayoutParams(settingsParams);

        RelativeLayout.LayoutParams restartParams = (RelativeLayout.LayoutParams) restart.getLayoutParams();
        restartParams.addRule(RelativeLayout.END_OF, pause.getId());
        restart.setLayoutParams(restartParams);
    }

    private void displayPlayIcon() {
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.GONE);

        RelativeLayout.LayoutParams settingsParams = (RelativeLayout.LayoutParams) settings.getLayoutParams();
        settingsParams.addRule(RelativeLayout.START_OF, play.getId());
        settings.setLayoutParams(settingsParams);

        RelativeLayout.LayoutParams restartParams = (RelativeLayout.LayoutParams) restart.getLayoutParams();
        restartParams.addRule(RelativeLayout.END_OF, play.getId());
        restart.setLayoutParams(restartParams);
    }

    private int parseTime(String s) {

        String[] time = s.split(":");
        int secs, mins, hours;

        if (time.length == 2) {
            mins = Integer.parseInt(time[0]);
            secs = Integer.parseInt(time[1]);

            return (mins * 60 + secs) * 1000;
        } else {
            hours = Integer.parseInt(time[0]);
            mins = Integer.parseInt(time[1]);
            secs = Integer.parseInt(time[2]);

            return ((hours * 60 + mins) * 60 + secs) * 1000;
        }

    }

    private String parseString(int value) {
        int hours, mins, sec, tenth;

        int i = value;
        hours = i / (1000 * 60 * 60);
        i -= hours * 1000 * 60 * 60;
        mins = i / (1000 * 60);
        i -= mins * 1000 * 60;
        sec = i / 1000;
        i -= sec * 1000;
        tenth = i / 100;

        // if longer than 1 hour remains...
        if (value >= 1000 * 60 * 60) {
            if (mins >= 10) {
                if (sec >= 10) {
                    if (tenth > 0)
                        return hours + ":" + mins + ":" + sec;
                    else
                        return hours + ":" + mins + ":" + sec;
                } else {
                    if (tenth > 0)
                        return hours + ":" + mins + ":0" + sec;
                    else
                        return hours + ":" + mins + ":0" + sec;
                }
            } else {
                if (sec >= 10) {
                    if (tenth > 0)
                        return hours + ":0" + mins + ":" + sec;
                    else
                        return hours + ":0" + mins + ":" + sec;
                } else {
                    if (tenth > 0)
                        return hours + ":0" + mins + ":0" + sec;
                    else
                        return hours + ":0" + mins + ":0" + sec;
                }
            }

        }

        // if longer than 1 minute remains...
        else if (value >= 1000 * 60) {
            if (sec >= 10) {
                if (tenth > 0)
                    return mins + ":" + sec;
                else
                    return mins + ":" + sec;
            } else {
                if (tenth > 0)
                    return mins + ":0" + sec;
                else
                    return mins + ":0" + sec;
            }
        }
        // if less than a minute remains
        else {
            if (sec >= 10)
                return "0:" + sec + ":" + tenth;
            else
                return "0:0" + sec + ":" + tenth;
        }

    }

    private void incrementWhiteMove() {
        whiteMoveCount++;
        whiteMovesTextView.setText("Moves: " + whiteMoveCount);
    }

    private void incrementBlackMove() {
        blackMoveCount++;
        blackMovesTextView.setText("Moves: " + blackMoveCount);
    }

}
