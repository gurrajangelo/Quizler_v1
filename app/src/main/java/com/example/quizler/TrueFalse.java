package com.example.quizler;

public class TrueFalse {

    private int mQuestionID;
    private boolean mAnswer;

    public TrueFalse(int questionResourceID, boolean answer) {
        mQuestionID = questionResourceID;
        mAnswer = answer;
    }

    public int getQuestionID() {
        return mQuestionID;
    }

    public void setQuestionID(int questionID) {
        mQuestionID = questionID;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}