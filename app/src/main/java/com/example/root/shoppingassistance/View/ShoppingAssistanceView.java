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
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.shoppingassistance.Controller.ShoppingAssistanceController;
import com.example.root.shoppingassistance.Database.DatabaseConnector;
import com.example.root.shoppingassistance.ListAdapter.CartListAdapter;
import com.example.root.shoppingassistance.ListAdapter.ItemListAdapter;
import com.example.root.shoppingassistance.Model.Item;
import com.example.root.shoppingassistance.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingAssistanceView extends AppCompatActivity implements TextToSpeech.OnInitListener {

    DatabaseConnector dbCon;

    private TextToSpeech tts;
    private Button btnStart;
    private Button btnSpeak;
    private EditText txtq;
    private EditText txta;
    String errorMessage = "Invalid request !";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ShoppingAssistanceController shoppingAssistanceController = ShoppingAssistanceController.getInstance();
    boolean isStarted = false;
    int index = 1;
    String message = "What are you looking for ?";
    ListView itemListView;
    ListView cartListView;
    int success = 0;
    double range;

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
        btnStart = (Button) findViewById(R.id.btnSpeak);
        btnSpeak = (Button) findViewById(R.id.btnS);
        itemListView = (ListView) findViewById(R.id.list_item);
        itemListView.setVisibility(View.GONE);
        cartListView = (ListView) findViewById(R.id.list_cart);
        cartListView.setVisibility(View.GONE);
        // Handle onClick event for button 'Speak'
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                start();
            }

        });
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpeechInput();
            }
        });
    }

    //method to speak
    private void speakOut(String text) {
        if(success==0) {
            txtq.setText(text);
            if (text.length() == 0) {
                txtq.setText(text);
                tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
            } else if (text.equals("success")) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            } else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                getSpeechInput();
            }
        }
        else if(success<4){
            if (text.length() == 0) {
                tts.speak("You haven't typed text", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                getSpeechInput();
            }
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
                btnStart.setEnabled(true);
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

                    Toast.makeText(getApplicationContext(), result.get(0),
                            Toast.LENGTH_LONG).show();

                    print(result.get(0));
                    if(success==0){
                        try {
                            if(isStarted) {
                                continueChat(result);
                            }
                            else {
                                startChat(result);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(success==1){
                        getRange(result);
                    }
                    else if(success==2){
                        addItem(result);
                    }
                    else if(success==3){
                        addMore(result);
                    }
                }
                break;
        }
    }

    public void startChat(ArrayList<String> s) throws ParseException {

        int valid = 0;
        int categorySize;
        for(int i=0;i<s.size();i++){
            print("result "+String.valueOf(i)+" "+s.get(i));
            categorySize = shoppingAssistanceController.getItemsOfCategory(s.get(i).toLowerCase()).size();
            if(categorySize>0){
                isStarted = true;
                txta.setText(s.get(i));
                print("started "+String.valueOf(categorySize));
                message = "what is the "+shoppingAssistanceController.getFirstQ()+" ?";
                speakOut(message);
                valid=1;
                break;
            }

        }

        if(valid==0){
            Toast.makeText(getApplicationContext(), errorMessage,
                    Toast.LENGTH_LONG).show();
            speakOut(errorMessage+" "+message);
            print(errorMessage);
            txta.setText(errorMessage);
        }

    }

    public void continueChat(ArrayList<String> s) throws ParseException {

        String response;
        int valid = 0;
        for(int i=0;i<s.size();i++){
            print("result "+String.valueOf(i)+" "+s.get(i));
            response = shoppingAssistanceController.nextQuestion(index,s.get(i).toLowerCase());
            if(response.equals("invalid")) {
                continue;
            }
            else if(response.equals("success")){
                txtq.setText("success");
                txta.setText("success");
                print("success");
                success = 1;
                message = "How much you can afford?";
                speakOut(message);
                valid=1;
                break;
            }
            else{
                message = "what is "+response+" ?";
                txta.setText(s.get(i));
                print(message);
                index++;
                speakOut(message);
                Toast.makeText(getApplicationContext(), s.get(i),
                        Toast.LENGTH_LONG).show();
                valid=1;
                break;
            }
        }

        if(valid==0){
            Toast.makeText(getApplicationContext(), errorMessage,
                    Toast.LENGTH_LONG).show();
            speakOut(errorMessage+ " "+message);
            print(errorMessage);
            txta.setText(errorMessage);
        }


    }

    private void print(String s){
        Log.e("message",s);
    }

    private void start(){
        txtq.setText("");
        txta.setText("");
        message = "What are you looking for ?";
        isStarted = false;
        index =1;
        success = 0;
        itemListView.setVisibility(View.GONE);
        shoppingAssistanceController = ShoppingAssistanceController.getInstance();
        speakOut(message);
    }

    private void getRange(ArrayList<String> s){
        int selected = 0;
        for(int i=0;i<s.size();i++) {
            try {
                print(s.get(i));
                shoppingAssistanceController.setRange(Double.valueOf(s.get(i)));
                selected = 1 ;
                generateList();
                break;
            } catch (NumberFormatException e) {
                continue;
            }
        }
        if(selected==0){
            txtq.setText("Invalid");
            txta.setText("Invalid");
            speakOut("Invalid! "+message);
        }
    }

    private void generateList(){
        List<Item> items = shoppingAssistanceController.getCategoryItems();
        ItemListAdapter itemListAdapter = new ItemListAdapter(getApplicationContext(),items);
        itemListView.setAdapter(itemListAdapter);
        itemListView.setVisibility(View.VISIBLE);
        String itemList = "Here are the suggestions. ";
        for(int i = 0;i<items.size();i++){
            itemList = itemList + " " + String.valueOf(i+1)+". "+ items.get(i).getName() + " for "+ String.valueOf(items.get(i).getPrice())+" rupees from "+items.get(i).getShop().getShopName() + ". ";
        }
        success = 2;
        message = itemList+"What kind of "+items.get(0).getCategory()+" do you want? ";
        speakOut(message);

    }

    private void addItem(ArrayList<String> s){
        int selected = 0;
        print("Succ "+String.valueOf(success));
        for(int i=0;i<s.size();i++) {
            try {
                print(s.get(i));
                int selectedItem = Integer.parseInt(s.get(i));
                shoppingAssistanceController.addToCart(selectedItem);
                selected = 1;
                print("selected");
                txtq.setText("Selected");
                txta.setText("Selected");
                generateCart();
                break;
            } catch (NumberFormatException e) {
                print("cont");
                continue;
            }
        }
        if(selected==0){
            txtq.setText("Invalid");
            txta.setText("Invalid");
            speakOut("Invalid! "+message);
        }
    }

    private void generateCart(){
        List<Item> items = shoppingAssistanceController.getCart();
        CartListAdapter cartListAdapter = new CartListAdapter(getApplicationContext(),items);
        cartListView.setAdapter(cartListAdapter);
        cartListView.setVisibility(View.VISIBLE);
        success = 3;
        message = "Do you want to add more items to the cart ?";
        speakOut(message);
    }

    private void addMore(ArrayList<String> s){
        for(int i = 0;i<s.size();i++){
            if(s.get(i).toLowerCase().equals("yes")){
                success = 0;
                start();
            }
            else if(s.get(i).toLowerCase().equals("no")){
                itemListView.setVisibility(View.GONE);
                success = 0;
            }
            else{
                speakOut("Invalid! "+message);
            }
        }
    }
}
