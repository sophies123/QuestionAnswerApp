package com.example.sophiaheng.app1;

import java.io.Serializable;

/**
 * Created by sophiaheng on 2017/10/27.
 */

public class Question implements Serializable{
    private String content;
    private int mTextResId;
    public Question(String content){
        super();
        this.content=content;

    }


    public int getTextResId() {

        return mTextResId;
    }
    //    public int getIndexnum(){
//        return mIndexnum;
//    }
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

}
