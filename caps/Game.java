package ca.yorku.eecs.caps;

import android.util.Pair;
import java.util.List;
import java.util.Map;
import ca.roumani.i2c.Country;
import ca.roumani.i2c.CountryDB;

public class Game
{

    private int score;
    private int questionNumber;
    private Pair<String, String> qa;

    private List<String> capitals;
    private Map<String, Country> data;
    private int length;

    public Game()
    {
        score = 0;
        questionNumber = 0;

        CountryDB countryDB = new CountryDB();
        capitals = countryDB.getCapitals();
        data = countryDB.getData();
        length = capitals.size();
        qa = getQA();
    }

    private Pair<String, String> getQA()
    {

        String question;
        String answer;

        int randomIndex = (int) (length * Math.random());
        String capital = capitals.get(randomIndex);
        String country = data.get(capital).getName();
        if (Math.random() < 0.5)
        {
            String QUESTION_1 = "What is the capital of ";
            question = QUESTION_1 + country;
            answer = capital;
        } else
        {
            String QUESTION_2 = " is the capital of?";
            question = capital + QUESTION_2;
            answer = country;
        }
        return new Pair<>(question, answer);
    }

    public String getQuestion()
    {
        return qa.first;
    }

    public String getAnswer()
    {
        return qa.second;
    }

    public void incrementScore()
    {
        score++;
    }

    public void incrementQuestionNumber()
    {
        questionNumber++;
    }

    public void newRound()
    {
        qa = getQA();
    }

    public int getQuestionNumber()
    {
        return questionNumber;
    }

    public int getScore()
    {
        return score;
    }

}
