package com.example.root.shoppingassistance.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.shoppingassistance.Controller.ShoppingAssistanceController;
import com.example.root.shoppingassistance.Database.DatabaseConnector;
import com.example.root.shoppingassistance.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class ShoppingAssistanceView extends AppCompatActivity implements TextToSpeech.OnInitListener {

    DatabaseConnector dbCon;

    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtq;
    private EditText txta;
    String startText = "What are you looking for ?";
    String errorMessage = "Invalid request !";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ShoppingAssistanceController shoppingAssistanceController = new ShoppingAssistanceController();
    boolean isStarted = false;
    int index =1;
    String message;

    public ShoppingAssistanceView() throws ParseException {
        dbCon=DatabaseConnector.getInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_assistance);

        tts = new TextToSpeech(this, this);

        txtq = (EditText) findViewById(R.id.txtQ);
        txta = (EditText) findViewById(R.id.txtA);

        // Refer 'Speak' button
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
        // Handle onClick event for button 'Speak'
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Method yet to be defined
                isStarted = false;
                index =1;
                try {
                    shoppingAssistanceController = new ShoppingAssistanceController();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                speakOut(startText);
            }

        });
    }

    //method to speak
    private void speakOut(String text) {
        txtq.setText(text);
        if (text.length() == 0) {
            txtq.setText(text);
            tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
        }
        else if(text.equals("success")){
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
        else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            getSpeechInput();
        }

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Setting speech language
            int result = tts.setLanguage(Locale.US);
            // If your device doesn't support language you set above
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported",
                        Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
            // Enable the button - It was disabled in main.xml (Go back and
            // Check it)
            else {
                btnSpeak.setEnabled(true);
            }
            // TTS is not initialized properly
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
                    .show();
            Log.e("TTS", "Initilization Failed");
        }
    }

    //method to listen
    public void getSpeechInput () {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txta.setText(result.get(0));
                    Toast.makeText(getApplicationContext(), result.get(0),
                            Toast.LENGTH_LONG).show();

                    print(result.get(0));
                    try {
                        if(isStarted) {
                            continueChat(result.get(0));
                        }
                        else {
                            startChat(result.get(0));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void startChat(String s) throws ParseException {
        if(shoppingAssistanceController.getItemsOfCategory(s).size()>0){
            isStarted = true;
            print("started");
            message = "what is the "+shoppingAssistanceController.getFirstQ()+" ?";
            speakOut(message);
        }
        else{
            Toast.makeText(getApplicationContext(), errorMessage,
                    Toast.LENGTH_LONG).show();
            speakOut(errorMessage);
            print(errorMessage);

        }

    }

    public void continueChat(String s) throws ParseException {

        if(shoppingAssistanceController.nextQuestion(index,s).equals("invalid")) {
            Toast.makeText(getApplicationContext(), errorMessage,
                    Toast.LENGTH_LONG).show();
            speakOut(errorMessage+" you said "+s+" !");
            print(errorMessage);

        }
        else if(shoppingAssistanceController.nextQuestion(index,s).equals("success")){
            print("success");
            speakOut("success");
        }
        else{
            message = "what is "+shoppingAssistanceController.nextQuestion(index,s)+" ?";
            print(message);
            index++;
            speakOut(message);
            Toast.makeText(getApplicationContext(), s,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void print(String s){
        Log.e("message",s);
    }
}
