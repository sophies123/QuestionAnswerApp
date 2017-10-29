package com.example.sophiaheng.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddTopicActivity extends AppCompatActivity {
    private Button mConfirmButton;
    private EditText mAddQuestionText;
    private EditText mAddAnswerText;
    private Button mReturnButton;
    private ArrayList<String> mQuestionAnd =new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        //从MainActivity接收数据
       // mConfirmButton=(Button)findViewById(R.id.confirm_Button);
        mAddQuestionText=(EditText) findViewById(R.id.addQusetionText);
        mAddAnswerText=(EditText) findViewById(R.id.addAnswerText);

     /*   mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String QuestionText=mAddQuestionText.getText().toString();
                mQuestionAnd.add(QuestionText);
              //  Toast.makeText(AddTopicActivity.this,QuestionText,Toast.LENGTH_SHORT).show();
                String AnswerText=mAddAnswerText.getText().toString();
                mQuestionAnd.add(AnswerText);
              //  Toast.makeText(AddTopicActivity.this,AnswerText,Toast.LENGTH_SHORT).show();
                Log.e("mQuestionList","The ArrayList contains the following elements: "
                        + mQuestionAnd);

            }
        });*/
        mReturnButton=(Button)findViewById(R.id.return_main);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent=new Intent();
                returnIntent.setClass(AddTopicActivity.this,MainActivity.class);
                //返回题目和答案
                returnIntent.putExtra("mQuestionAdd",mAddQuestionText.getText().toString());
                returnIntent.putExtra("mAnswerAdd",mAddAnswerText.getText().toString());
                startActivity(returnIntent);
            }
        });

    }


}
