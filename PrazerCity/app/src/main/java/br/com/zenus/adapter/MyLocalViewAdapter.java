package br.com.zenus.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.zenus.entidades.Local;
import br.com.zenus.prazercity.R;




/**
 * Created by Tuca on 24/04/2017.
 */

public class MyLocalViewAdapter extends RecyclerView.Adapter<MyLocalViewAdapter.DataObjectHolder> {


    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Local> mDataset;
    private static MyClickListener myClickListener;

    class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView mNome;
        TextView mTelefone;


        public DataObjectHolder(final View itemView) {
            super(itemView);
            mTelefone = (TextView) itemView.findViewById(R.id.txtTelefone);
            mNome = (TextView) itemView.findViewById(R.id.txtNome);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {


        this.myClickListener = myClickListener;
    }

    public MyLocalViewAdapter(List<Local> myDataSet) {
        mDataset = myDataSet;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.local_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        String nome = mDataset.get(position).getNome();


    }

    public void addItem(Local local, int index) {
        mDataset.add(index, local);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {

        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
