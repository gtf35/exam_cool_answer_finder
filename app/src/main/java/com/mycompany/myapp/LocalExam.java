package com.mycompany.myapp;

public class LocalExam {

    private String question;

    private String answer;

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "["+getClass().getCanonicalName()+"]"
         + "\nquestion=" + question
         + ",\nanswer=" + answer;
    }

}