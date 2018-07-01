package com.example.user1.journal;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1.journal.data.JournalContract;

import java.util.ArrayList;

/**
 * Created by user1 on 6/28/2018.
 */

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.JournalViewHolder>{

    private Context mContext;
    private Cursor mCursor;
    final private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener{
        void onListItemClick(long id);
    }

    public JournalListAdapter(Context mContext, ListItemClickListener listener) {
        this.mContext = mContext;
        listItemClickListener = listener;
    }

    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.journal_list_item, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String title = mCursor.getString(mCursor.getColumnIndex(JournalContract.JournalEntry.COLUMN_TITLE));
        String body = mCursor.getString(mCursor.getColumnIndex(JournalContract.JournalEntry.COLUMN_BODY));
        long id = mCursor.getLong(mCursor.getColumnIndex(JournalContract.JournalEntry._ID));

        String[] bodyList = body.split(" ");
        if(bodyList.length > 3){
            body = bodyList[0] + " " + bodyList[1] + " "+ bodyList[2] + "....";
        }

        holder.titleTextView.setText(title);
        holder.excerptTextView.setText(body);
        holder.itemView.setTag(id);
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(mCursor != null){
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleTextView;
        TextView excerptTextView;

        public JournalViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.journal_title);
            excerptTextView = (TextView) itemView.findViewById(R.id.journal_excerpt);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            long clickedID = (long) v.getTag();
            listItemClickListener.onListItemClick(clickedID);
        }
    }
}
