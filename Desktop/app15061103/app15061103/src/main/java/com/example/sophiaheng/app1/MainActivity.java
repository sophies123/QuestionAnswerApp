package com.example.sophiaheng.app1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public  final static String SER_KEY = "com.cn.daming.serializable";
    private Button mAddButton;
    private Button mStartButton;
    private Button mRefreshButton;

    private ArrayList mQuestionList;

    private ArrayList<String> mQuestionBank =new ArrayList<String>();
    private String[] question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //默认题目列表
        InitQuestion();

        mAddButton=(Button)findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddTopicActivity.class);
                startActivity(intent);

            }
        });
        //更新题库操作
        mRefreshButton=(Button)findViewById(R.id.refresh_button);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent QuestionIntent=getIntent();
                String AddQuestion=QuestionIntent.getStringExtra("mQuestionAdd");
                String AddAnswer=QuestionIntent.getStringExtra("mAnswerAdd");

                mQuestionBank.add(AddQuestion);
                mQuestionBank.add(AddAnswer);
              Log.e("mQuestionBank","please"+mQuestionBank);
            }
        });
        mStartButton=(Button)findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("quiz_QuestionList","The ArrayList contains the following elements: "
                        + mQuestionBank);
             /*   QuizIntent.putStringArrayListExtra("Quiz_RefreshBank",mQuestionBank);*/
             // question= (String[]) mQuestionBank.toArray(new String[10]);
               /* Log.e("Question","The question contains the following elements: "
                        + question[0]);*/

                Intent mIntent=new Intent();
                mIntent.setClass(MainActivity.this,QuizActivity.class);
                Bundle mBundle=new Bundle();
                mBundle.putSerializable("Send",mQuestionBank);
                mIntent.putExtra("Bundle",mBundle);
                Log.e("Send_Question","The question contains the following elements: "
                        + mBundle);
                startActivity(mIntent);

            }
        });


    }

    private void InitQuestion() {
        mQuestionBank.add("NO.1 The Pacific Ocean is larger than the Atlantic Ocean.");
        mQuestionBank.add("TRUE");
        mQuestionBank.add("NO.2 The Sue cancel connect the red Ocean");
        mQuestionBank.add("FALSE");
        mQuestionBank.add("NO.3 The source of the neil river is in Egypt");
        mQuestionBank.add("FALSE");
        mQuestionBank.add("NO.4 The Amazon river is the longest");
        mQuestionBank.add("TRUE");
        mQuestionBank.add("NO.5 Lake Baikal is the world \\'s oldest and deepest fresh water lake");
        mQuestionBank.add("TRUE");

    }

}
