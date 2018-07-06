package com.fortests.meet12practicecp;

import java.util.Date;

public class Note {
    private String mName;
    private Date mTime;
    private String mContent;

    public Note(String name, String content) {
        mName = name;
        mContent = content;
        mTime = new Date();
    }

    public Note(){
        mTime = new Date();
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
