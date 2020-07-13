package com.example.veronica.assignment1_memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public RelativeLayout layout;

    public LinearLayout mainMenu, helpMenu, settingsMenu, playGame;

    public String userName, easy, hard, scoreText, mistakeText, temp, phone, license;

    public TextView setPhoneNum, setLicensePlate, chosenDifficulty, win, lose, correctGuesses;

    public EditText guessMe;

    public Button ready, backToMain;

    //max guesses, default score and max mistakes allowed
    public int maxValue = 10, score = 0, mistakesRem = 1;
    public String[] characters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    //remove and reset functions to be used by any view to show up or make space for other views
    public void remove(View view) {
        view.setVisibility(View.GONE);
    }

    public void reset(View view) {view.setVisibility(View.VISIBLE);}

    public boolean isGuessing = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Welcome to Memory Game!", Toast.LENGTH_SHORT).show();
        Log.i("tag:", "User logged");

        layout = (RelativeLayout) findViewById(R.id.main);
        mainMenu = (LinearLayout) findViewById(R.id.main_menu);
        backToMain = (Button) findViewById(R.id.back);
        helpMenu = (LinearLayout) findViewById(R.id.help);
        settingsMenu = (LinearLayout) findViewById(R.id.difficulty_menu);
        chosenDifficulty = (TextView) findViewById(R.id.set_difficulty);
        easy = (String) getText(R.string.set_phone);
        hard = (String) getText(R.string.set_license);
        playGame = (LinearLayout) findViewById(R.id.guess_menu);
        setPhoneNum = (TextView) findViewById(R.id.set_phone_number);
        setLicensePlate = (TextView) findViewById(R.id.set_license_plate);
        ready = (Button) findViewById(R.id.ready_button);
        guessMe = (EditText) findViewById(R.id.guess_me);
        userName = (String) getText(R.string.user_name);
        win = (TextView) findViewById(R.id.win);
        lose = (TextView) findViewById(R.id.lose);
        correctGuesses = (TextView) findViewById(R.id.correct_guesses);
        scoreText = (String) getText(R.string.score);
        mistakeText = (String) getText(R.string.mistakes);


        remove(backToMain);
        remove(correctGuesses);
        reset(layout);
        reset(mainMenu);
    }

    //main menu buttons
    public void help(View view) {
        remove(mainMenu);
        reset(helpMenu);
        reset(backToMain);
    }

    public void changeDifficulty(View view)
    {
        int tag;
        tag = Integer.parseInt(view.getTag().toString());

        if (tag == 1)
        {
            chosenDifficulty.setTextColor(getColor(R.color.gray));
            chosenDifficulty.setText(easy);
            Log.i("1", easy);
        }

        else if (tag == 2)
        {
            chosenDifficulty.setTextColor(getColor(R.color.colorAccent));
            chosenDifficulty.setText(hard);
            Log.i("2", hard);
        }
    }

    public void set(View view)
    {
        remove(mainMenu);
        reset(settingsMenu);
        reset(backToMain);
    }

    //located on all except main menu layouts
    public void back2Main(View view)
    {
        if (helpMenu.getVisibility() == View.VISIBLE) {remove(helpMenu);}
        if (settingsMenu.getVisibility() == View.VISIBLE) {remove(settingsMenu);}
        if (playGame.getVisibility() == View.VISIBLE || win.getVisibility() == View.VISIBLE || lose.getVisibility() == View.VISIBLE)
        {
            score=0;
            mistakesRem=1;
            remove(correctGuesses);
            remove(playGame);
            remove(win);
            remove(lose);
        }
        remove(backToMain);
        reset(mainMenu);
    }

    //phone format
    public String generatePhone()
    {
        Random randomPhone = new Random();
        for (int i = 0; i < 6; i++)
        {
            phone += randomPhone.nextInt(maxValue)+"";
        }
        return phone;
    }

    //license plate format
    public String generateLicense()
    {
        Random randomChar = new Random();
        license = characters[randomChar.nextInt(26)];
        for (int i = 1; i < 5; i++)
        {
            license += randomChar.nextInt(9) + "";
        }
        license += characters[randomChar.nextInt(26)];
        return license;
    }

    //to be used in startGame and the loop during the game
    public void phoneLoop ()
    {
        phone="";
        reset(setPhoneNum);
        reset(correctGuesses);
        phone = generatePhone();
        temp = phone;
        setPhoneNum.setText(temp);
    }

    //that too
    public void licenseLoop()
    {
        license="";
        reset(setLicensePlate);
        reset(correctGuesses);
        license = generateLicense();
        temp = license;
        setLicensePlate.setText(temp);
    }

    //start from main
    public void startGame(View view)
    {
        String difficulty = chosenDifficulty.getText().toString();

        remove(mainMenu);
        reset(playGame);
        remove(guessMe);
        reset(ready);
        reset(backToMain);

        isGuessing = false;
        score=0;
        mistakesRem=1;
        correctGuesses.setText(scoreText + score + " " + mistakeText + mistakesRem);

        if (difficulty.equals(easy)){ phoneLoop(); }

        else if (difficulty.equals(hard)) {licenseLoop();}
    }

    //ready button from startMenu (while playing)
    public void go(View view)
    {
        String input = guessMe.getText().toString();
        String difficulty = chosenDifficulty.getText().toString();

        if (isGuessing == false)
        {
            if (difficulty.equals(easy))
                remove(setPhoneNum);
            else
                remove(setLicensePlate);

            isGuessing = true;
            reset(guessMe);
        }

        else if (isGuessing && input.equals(temp))
        {
            if (score<maxValue)
            {
                if (difficulty.equals(easy)) {phoneLoop();}
                else if (difficulty.equals(hard)) { licenseLoop();}

                score++;
                correctGuesses.setText(scoreText + score + " " + mistakeText + mistakesRem);
                Log.i("Correct guess", userName);
            }

            if (score == maxValue)
            {
                Log.i("user", "USER WINS");
                reset(win);
            }

            guessMe.setText("");
            remove(guessMe);
            isGuessing = false;
        }

        else if (isGuessing && input.isEmpty() && guessMe.getVisibility() == View.VISIBLE)
            {Toast.makeText(MainActivity.this, "Please enter a valid value", Toast.LENGTH_SHORT).show();}

        else if (isGuessing && !input.equals(temp))
        {
            mistakesRem -= 1;
            correctGuesses.setText(scoreText+ score+ " " + mistakeText+ mistakesRem);
            guessMe.setText("");
            remove(guessMe);
            isGuessing = false;
        }

        if (mistakesRem == 0)
        {
            reset(lose);
            Log.i("Incorrect Guess", "GAME OVER");
        }
    }
}
