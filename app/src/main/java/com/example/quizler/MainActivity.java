package com.example.quizler;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private final TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };

    private static final String SCORE_KEY = "ScoreKey";
    private static final String INDEX_KEY = "IndexKey";
    private static final String GAME_OVER_KEY = "GameOverKey";

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private ProgressBar mProgressBar;

    private int mIndex;
    private int mScore;
    private boolean mGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(SCORE_KEY, 0);
            mIndex = savedInstanceState.getInt(INDEX_KEY, 0);
            mGameOver = savedInstanceState.getBoolean(GAME_OVER_KEY, false);
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);

        mProgressBar.setMax(mQuestionBank.length);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion(false);
            }
        });

        updateScreen();

        if (mGameOver) {
            showGameOverDialog();
        }
    }

    private void answerQuestion(boolean userSelection) {
        if (mGameOver) {
            return;
        }

        checkAnswer(userSelection);

        mIndex++;
        mProgressBar.setProgress(mIndex);
        updateScore();

        if (mIndex >= mQuestionBank.length) {
            mGameOver = true;
            showGameOverDialog();
        } else {
            updateQuestion();
        }
    }

    private void updateScreen() {
        if (mIndex < mQuestionBank.length) {
            updateQuestion();
        }

        mProgressBar.setProgress(mIndex);
        updateScore();
    }

    private void updateQuestion() {
        int questionId = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(questionId);
    }

    private void updateScore() {
        mScoreTextView.setText(
                getString(R.string.score_format, mScore, mQuestionBank.length)
        );
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if (userSelection == correctAnswer) {
            mScore++;
            Toast.makeText(
                    this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void showGameOverDialog() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);

        new AlertDialog.Builder(this)
                .setTitle(R.string.game_over_title)
                .setCancelable(false)
                .setMessage(getString(
                        R.string.game_over_message,
                        mScore,
                        mQuestionBank.length
                ))
                .setPositiveButton(
                        R.string.close_application,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }
                )
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SCORE_KEY, mScore);
        outState.putInt(INDEX_KEY, mIndex);
        outState.putBoolean(GAME_OVER_KEY, mGameOver);

        super.onSaveInstanceState(outState);
    }
}