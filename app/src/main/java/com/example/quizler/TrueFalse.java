package com.example.quizler;

public class TrueFalse {
    private int mQuestionID;    //rappresenta l'ID della domanda
    private boolean mAnswer;    //rappresenta la risposta

    public TrueFalse(int mQuestionID, boolean mAnswer){
        this.mQuestionID = mQuestionID;
        this.mAnswer = mAnswer;
    }

    public int getmQuestionID() {
        return mQuestionID;
    }

    public void setmQuestionID(int mQuestionID) {
        this.mQuestionID = mQuestionID;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
