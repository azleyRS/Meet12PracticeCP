package com.fortests.meet12practicecp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Note> mNotepad;

    //testing? delete later
 /*   public MyAdapter() {
        mNotepad = new ArrayList<>();
        mNotepad.add(new Note("1","11111"));
        mNotepad.add(new Note("2","22222"));
        mNotepad.add(new Note("3","33333"));
    }*/

    public MyAdapter(List<Note> notepad) {
        mNotepad = notepad;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.bindPosition(position);
        holder.mName.setText(mNotepad.get(position).getName());
        holder.mContent.setText(mNotepad.get(position).getContent());

        //android.text.format.DateFormat.format("EEE,MMM d HH:mm:ss",mNotepad.get(position).getTime());

        holder.mTime.setText(android.text.format.DateFormat.format("EEE MMM d HH:mm:ss",mNotepad.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return mNotepad.size();
    }

    public void setNotes(List<Note> notes) {
        mNotepad = notes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int mNotePosition;

        TextView mName;
        TextView mTime;
        TextView mContent;

        public MyViewHolder(final View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mTime = itemView.findViewById(R.id.time);
            mContent = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = Activity2WithFragents.newIntent(itemView.getContext(), mNotePosition);
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void bindPosition(int position) {
            mNotePosition = position;
        }
    }
}
