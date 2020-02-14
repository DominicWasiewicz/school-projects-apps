package ca.yorku.eecs.caps;


import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class CapsActivity extends AppCompatActivity
{


    private String log;
    private ToneGenerator tg;
    private int question;
    private String questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caps_layout);
    }


    public void onClick(View v)
    {

        Button checkAnswerButton = findViewById(R.id.done);
        EditText answerEditText = findViewById(R.id.answer);

        ((TextView) findViewById(R.id.question)).setText(this.question);
        ((TextView) findViewById(R.id.questionNumber)).setText("Q#" + this.questionNumber);

        TextView scoreTextView = findViewById(R.id.score);

        TextView logTextView = findViewById(R.id.log);

        this.tg = new ToneGenerator(AudioManager.STREAM_ALARM, 100);

        final Game game = new Game();
        log = "";

        scoreTextView.setText(String.valueOf(game.getScore()));
        String answer = answerEditText.getText().toString().toUpperCase();
        answerEditText.setText("");

        if (answer.equals(game.getAnswer().toUpperCase()))
        {
            game.incrementScore();
            scoreTextView.setText(String.valueOf(game.getScore()));
        } else
        {

            this.tg = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        }


        game.incrementQuestionNumber();


        log += game.getQuestion() + "\n" + answer + "; The right answer was: " + game.getAnswer() + "\n\n";
        logTextView.setText(log);

        game.newRound();
        ((TextView) findViewById(R.id.question)).setText(this.question);
        ((TextView) findViewById(R.id.questionNumber)).setText("Q#" + this.questionNumber);

        if (game.getQuestionNumber() > 9)
        {
            checkAnswerButton.setEnabled(false);
            String text = "The game is over, your score is " + game.getScore() + game.getQuestionNumber();
            Toast.makeText(CapsActivity.this, text, Toast.LENGTH_LONG).show();
        }
    }

}


