package com.fortests.meet12practicecp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class Activity2WithFragents extends AppCompatActivity {
    int mNotePosition;

    private static final String AUTHORITY =
            "com.fortests.meet9practice2.MyContentProvider";
    private static final String NOTE_TABLE = "notepad";
    //public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE);
    //Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE + "/" + "3");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        if (savedInstanceState == null){
            initFragment1();
        } else {
            //do nothing i suppose
        }

    }

    private void initFragment1() {
        mNotePosition = getIntent().getIntExtra("position",0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragment1 myFragment1 = new MyFragment1();
        Bundle bundle = new Bundle();
        bundle.putInt("position",mNotePosition);
        myFragment1.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.fragment_container,myFragment1).commit();
    }

    public static Intent newIntent(Context context, int position){
        Intent intent = new Intent(context, Activity2WithFragents.class);
        intent.putExtra("position", position);
        return intent;
    }

    public static class MyFragment1 extends Fragment{
        TextView mF1Name;
        TextView mF1Time;
        TextView mF1Content;

        int mPosition;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment1,null);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mF1Name = view.findViewById(R.id.fragment1_name);
            mF1Time = view.findViewById(R.id.fragment1_time);
            mF1Content = view.findViewById(R.id.fragment1_content);

            mPosition = getArguments().getInt("position");
            mPosition++;

            //DBManager manager = new DBManager(getContext());
            //Note note = manager.getNote(mPosition);

            Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE + "/" + mPosition),null,null, null,null);
            Note note = new Note();
            while (cursor.moveToNext()){
                note.setName(cursor.getString(cursor.getColumnIndex("name")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                note.setTime(new Date(cursor.getLong(cursor.getColumnIndex("time"))));
            }
            cursor.close();


            mF1Name.setText(note.getName());
            mF1Content.setText(note.getContent());
            mF1Time.setText(android.text.format.DateFormat.format("EEE MMM d HH:mm:ss",note.getTime()));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyFragment2 myFragment2 = new MyFragment2();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",mPosition);
                    myFragment2.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment2).commit();
                }
            });
        }
    }

    public static class MyFragment2 extends Fragment{
        EditText mF2Name;
        TextView mF2Time;
        EditText mF2Content;
        //DBManager manager;

        Note note;

        int mPosition2;


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment2,null);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mF2Name = view.findViewById(R.id.fragment2_name);
            mF2Time = view.findViewById(R.id.fragment2_time);
            mF2Content = view.findViewById(R.id.fragment2_content);

            mPosition2 = getArguments().getInt("position");

            //manager = new DBManager(getContext());
            //note = manager.getNote(mPosition2);

            Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE + "/" + mPosition2),null,null, null,null);
            note = new Note();
            while (cursor.moveToNext()){
                note.setName(cursor.getString(cursor.getColumnIndex("name")));
                note.setContent(cursor.getString(cursor.getColumnIndex("content")));
                note.setTime(new Date(cursor.getLong(cursor.getColumnIndex("time"))));
            }
            cursor.close();

            mF2Name.setText(note.getName());
            mF2Content.setText(note.getContent());
            mF2Time.setText(android.text.format.DateFormat.format("EEE MMM d HH:mm:ss",note.getTime()));

            mF2Name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mF2Content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setContent(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        @Override
        public void onPause() {
            super.onPause();

            //manager.update(note, mPosition2);
            getActivity().getContentResolver().update(Uri.parse("content://" + AUTHORITY + "/" + NOTE_TABLE + "/" + mPosition2),getContentValues(note),null,null);
        }

        private static ContentValues getContentValues(Note note){
            ContentValues values = new ContentValues();
            values.put("name", note.getName());
            values.put("time", note.getTime().getTime());
            values.put("content", note.getContent());
            return values;
        }
    }
}
