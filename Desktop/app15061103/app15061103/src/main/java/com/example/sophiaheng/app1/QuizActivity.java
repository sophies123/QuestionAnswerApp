package com.example.sophiaheng.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private Button mLastButton;
    private Button mRefreshButton;
    private Button mCheckAnswerButton;
    private TextView mQuestionView;
    private TextView mTotalNumView;
    private TextView mCurrentNumView;
    private TextView mAnswerTextView;
    private TextView mTotalScore;
    private int mTextResId;
    private boolean TrueSelect=false;
    private boolean FalseSelect=false;
    private boolean mAnswerTrue;
    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT=0;


    private int mTotalIndex;
    private int mCurrentIndex;
    private int mScore;
    private boolean mIsCheater;
    private String[] mQuestionBank;
    private int mRightNum=0;
    private int mWrongNum=0;
    private ArrayList<String> mAnswerList = new ArrayList<>();

    private ArrayList<String> mAnswer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
       //显示题目信息
        mTotalNumView=(TextView)findViewById(R.id.total_num);
        mCurrentNumView=(TextView)findViewById(R.id.current_num);
        mQuestionView=(TextView)findViewById(R.id.question_view);
        //选择答案
        mRefreshButton=(Button)findViewById(R.id.refreshQuiz);

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = getIntent();
                Bundle b= i.getBundleExtra("Bundle");
                ArrayList AllQuestions= (ArrayList<String>) b.getSerializable("Send");
                Log.e("Quiz_mQuestionGet","The Quiz_mBundleGet contains the following elements: "
                        + AllQuestions);
                mQuestionBank=new String[AllQuestions.size()];
                AllQuestions.toArray(mQuestionBank);
                Log.e("Quiz_mGetArrayList","The zip contains the following elements: "
                        + mQuestionBank);
                mTotalIndex=sizeof(mQuestionBank);
                Log.e("mTotalIndex", "mTotalIndex"+ mTotalIndex/2);
                mTotalNumView.setText(getString(R.string.totalIndex)+mTotalIndex/2);
                updateQuestion(mCurrentIndex);
                getCurrentIndex(mCurrentIndex);

            }
        });


        mTrueButton=(Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TrueSelect&&!FalseSelect){
                    checkAnswer(true);
                    TrueSelect=true;
                }
                else{
                    haveSelectAnswer();
                }

            }
        });
        mFalseButton=(Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!TrueSelect&&!FalseSelect){
                    checkAnswer(false);
                    FalseSelect=true;
                }
                else{
                    haveSelectAnswer();
                }
            }
        });
        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+2)%sizeof(mQuestionBank);
                mIsCheater=false;
                FalseSelect=false;
                TrueSelect=false;
                updateQuestion(mCurrentIndex);
                getCurrentIndex(mCurrentIndex);
                mAnswerTextView.setText("核对答案");

            }


        });
        mLastButton=(Button)findViewById(R.id.last_button);
        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex-2)%mQuestionBank.length;
                mIsCheater=false;
                if(mCurrentIndex!=-2){
                    updateQuestion(mCurrentIndex);
                    getCurrentIndex(mCurrentIndex);

                }
                else{
                    showWarmingToast1();
                }
                mAnswerTextView.setText("核对答案");
            }
        });
        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue=(mQuestionBank[mCurrentIndex]=="TRUE");
                Intent i=CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });
        mAnswerTextView=(TextView)findViewById(R.id.CheckAnswer_button);
        mAnswerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TrueSelect|FalseSelect){
                    if(mQuestionBank[mCurrentIndex]=="TRUE"){
                        mAnswerTextView.setText("TRUE");
                    }else{
                        mAnswerTextView.setText("FALSE");
                    }
                }else{
                    ShowPromptMessage();
                }



            }
        });
        mTotalScore=(TextView)findViewById(R.id.CheckScore_button);
        mTotalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将每次的选择情况和总分打包发送至ResultActivity
                String TotalScore=String.valueOf(mScore);
                String RightNum=String.valueOf(mRightNum);
                String WrongNum=String.valueOf(mWrongNum);
                mAnswerList.add("TotalScore: ");
                mAnswerList.add(TotalScore);
                mAnswerList.add("RightNum：");
                mAnswerList.add(RightNum);
                mAnswerList.add("WrongNum：");
                mAnswerList.add(WrongNum);

                Log.e("mAnswerList","mAnswerList"+mAnswerList);
                Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("mAnswerList",mAnswerList);
                bundle.putSerializable("mAnswer",mAnswer);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

    }
    private int sizeof(String[] questionBank) {
        return questionBank.length;
    }
    private void getCurrentIndex(int mCurrentIndex){
        mCurrentNumView.setText(getString(R.string.currentIndex)+(mCurrentIndex/2+1));

    }
    private void updateQuestion(int mCurrentIndex){
        String question=mQuestionBank[mCurrentIndex];
        mQuestionView.setText(question);
        if(mCurrentIndex==0){
            mScore=0;
        }
    }
    //接收更新后的问题，ArrayList->Array

    private  void checkAnswer(boolean userPressedTrue){

        boolean answerIsTrue=(mQuestionBank[mCurrentIndex+1]=="TRUE");
        int messageResId = 0;
        if(mIsCheater){
            messageResId=R.string.judgement_toast;
        }

        else{
            if(userPressedTrue==answerIsTrue){
                messageResId=R.string.correct_toast;
                mScore=mScore+20;
                mRightNum++;
                mAnswer.add("True");
            }
            else{
                messageResId=R.string.incorrect_toast;
                mWrongNum++;
                mAnswer.add("FALSE");
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();

    }

    private void unfinished() {
        Toast toast1=Toast.makeText(this, "Please complete all the test questions first", Toast.LENGTH_SHORT);
        toast1.show();
    }


    private void haveSelectAnswer() {
        Toast toast1=Toast.makeText(this, "There is only one choice for each question", Toast.LENGTH_SHORT);
        toast1.show();
    }

    private void ShowPromptMessage() {
        Toast toast1=Toast.makeText(this, "Please choose an answer first", Toast.LENGTH_SHORT);
        toast1.show();
    }

    private void showWarmingToast1() {
        Toast toast1=Toast.makeText(this, "The current problem is NO.1!", Toast.LENGTH_SHORT);
        toast1.show();
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE_CHEAT){
            if(data==null){
                return ;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }

    public int getTextResId(){
        return mTextResId;
    }
    public void setTextResId(int textResId){
        mTextResId=textResId;
    }
    public boolean isAnswerTrue(){
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue=answerTrue;
    }
}
