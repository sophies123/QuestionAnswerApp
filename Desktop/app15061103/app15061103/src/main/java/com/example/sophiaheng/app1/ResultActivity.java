package com.example.sophiaheng.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private Button mReturnQuiz;
    private String[] mAnswerBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //接收并显示答案和分数
        Intent i=getIntent();
        Bundle b=i.getBundleExtra("bundle");
        ArrayList AllAnswers= (ArrayList<String>) b.getSerializable("mAnswerList");
        ArrayList alAnswerShow=(ArrayList<String>) b.getSerializable("mAnswer");
        //ArrayList to array
        mAnswerBank=new String[AllAnswers.size()];
        AllAnswers.toArray(mAnswerBank);
        //add items
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ResultActivity.this,android.R.layout.simple_list_item_1,mAnswerBank);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
             Intent intent=new Intent(ResultActivity.this,QuizActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
