package com.fortests.meet12practicecp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {
    Note mNote;
    TextView mA3Name;
    TextView mA3Time;
    TextView mA3Content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        mNote = new Note();
        initViews();
        mA3Time.setText(android.text.format.DateFormat.format("EEE MMM d HH:mm:ss",mNote.getTime()));
        initTextWatchers();
    }

    private void initTextWatchers() {
        mA3Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mA3Content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViews() {
        mA3Name = findViewById(R.id.activity3_name);
        mA3Content = findViewById(R.id.activity3_content);
        mA3Time = findViewById(R.id.activity3_time);
    }

    public static Intent newIntent(Context context/*, int position*/){
        Intent intent = new Intent(context, Activity3.class);
        //intent.putExtra("position", position);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().insert(MainActivity.CONTENT_URI,getContentValues(mNote));
        //DBManager manager = new DBManager(this);
        //manager.addNote(mNote);
    }
    private static ContentValues getContentValues(Note note){
        ContentValues values = new ContentValues();
        values.put("name", note.getName());
        values.put("time", note.getTime().getTime());
        values.put("content", note.getContent());
        return values;
    }
}
