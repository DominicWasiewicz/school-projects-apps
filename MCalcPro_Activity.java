package ca.yorku.eecs.mcalcpro;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

 class MCalcProActivity extends AppCompatActivity
         implements TextToSpeech.OnInitListener, SensorEventListener {

    TextToSpeech tts;
    MPro mortgage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);
        this.tts = new TextToSpeech(this, this);
    }

    public void onInit(int initStatus) {
        this.tts.setLanguage(Locale.CANADA);
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    public void onSensorChanged(SensorEvent event) {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a;
        a = (Math.sqrt((ax * ax) + (ay * ay) + (az * az)));
        if (a > 20)
        {
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");

        }

    }


    public void buttonclicked(View v)
    {

        try
        {
            EditText pr = (EditText) findViewById(R.id.pBox);
            String principle = pr.getText().toString();
            EditText am = (EditText) findViewById(R.id.aBox);
            String amo = am.getText().toString();
            EditText interest = (EditText) findViewById(R.id.iBox);
            String i = interest.getText().toString();

            mortgage = new MPro(principle, amo, i);
            String s = "Monthly Payment =" + "\t" + mortgage.computePayment("%,.2f");
            s += "\n\n";
            s += "By making this monthly payments for " + "\t" + amo + "\t" + "Years," +
                    "This mortgage on its nth anniversary, the balance " +
                    "Owing depends on " + "n as shown below.";
            s += "\n\n";

            for (int n = 0; n <= 5; n++)
                s += String.format("%8d", n) + mortgage.outstandingAfter(n, "%,16.0f" + "\n");

            for (int n = 10; n <= 20; n+=5)
                s += String.format("%8d", n) + mortgage.outstandingAfter(n, "%,16.0f" + "\n");


            ((TextView) findViewById(R.id.output)).setText(s);
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);

        } catch (Exception e)
        {

            String msg = "This is an Invalid Entry!";
            Toast label = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            label.show();

        }


    }

}
