package com.fortests.meet12practicecp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    //private DBManager dbManager;

    //private Button mButton;
    //private TextView mTextView;
    Cursor cursor;

    private static final String AUTHORITY =
            "com.fortests.meet9practice2.MyContentProvider";
    private static final String NOTE_TABLE = "notepad";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE);
            //Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE + "/" + "3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        updateUI();
/*
        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textview);
        cursor = getContentResolver().query(CONTENT_URI,null,null, null,null);
        cursor.moveToFirst();
        mTextView.setText(cursor.getString(cursor.getColumnIndex("name")));
        //mTextView.setText(note.getName());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor.moveToNext()){
                    mTextView.setText(cursor.getString(cursor.getColumnIndex("name")));
                }
            }
        });*/
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recycler_view_id);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //dividerText
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        //dbManager = new DBManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_note:
                Intent intent = Activity3.newIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = getContentResolver().query(CONTENT_URI,null,null, null,null);
        while (cursor.moveToNext()){
            Note note = new Note();
            note.setName(cursor.getString(cursor.getColumnIndex("name")));
            note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            note.setTime(new Date(cursor.getLong(cursor.getColumnIndex("time"))));
            notes.add(note);
        }
        cursor.close();
        if (mAdapter == null){
            mAdapter = new MyAdapter(notes);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNotes(notes);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
