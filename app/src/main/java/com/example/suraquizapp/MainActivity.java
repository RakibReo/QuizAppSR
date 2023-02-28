package com.example.suraquizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.suraquizapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityMainBinding binding;

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        binding.ansA.setOnClickListener(this);
        binding.ansB.setOnClickListener(this);
        binding.ansC.setOnClickListener(this);
        binding.ansD.setOnClickListener(this);
        binding.submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : "+totalQuestion);

        loadNewQuestion();




    }

    @Override
    public void onClick(View view) {

        binding.ansA.setBackgroundColor(Color.WHITE);
        binding.ansB.setBackgroundColor(Color.WHITE);
        binding.ansC.setBackgroundColor(Color.WHITE);
        binding.ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;


            }

            currentQuestionIndex++;
            loadNewQuestion();

        }


        else{

            //choices button clicked

            selectedAnswer  = clickedButton.getText().toString();

            if(selectedAnswer.equalsIgnoreCase(QuestionAnswer.correctAnswers[currentQuestionIndex])){

                clickedButton.setBackgroundColor(Color.GREEN);

            }else{

                clickedButton.setBackgroundColor(Color.RED);


            }


        }


    }

    void loadNewQuestion(){                   //for load new question

        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        binding.ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        binding.ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);   // take option from question answer class
        binding.ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        binding.ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }

    void finishQuiz(){                                                    //pass or fail code
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Congratulation,You are Passed";
        }else{
            passStatus = "Failed, Better luck next time";
        }

        new AlertDialog.Builder(this)     //restart quiz code
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setPositiveButton("Again you give quiz to Click it ",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();


    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }

}
