package edu.floridapoly.cop4656.spring19.Neyra;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mNoteNames;
    private ArrayList<String> mNoteDates;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> noteNames, ArrayList<String> noteDates, Context context) {
        mNoteNames = noteNames;
        mNoteDates = noteDates;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.noteNameTextView.setText(mNoteNames.get(position));
        holder.dateTextView.setText(mNoteDates.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mNoteNames.get(position));

                Toast.makeText(mContext, mNoteNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, NewNoteActivity.class);
                intent.putExtra("noteName", mNoteNames.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNoteNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteNameTextView;
        TextView dateTextView;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteNameTextView = itemView.findViewById(R.id.noteNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
