package br.com.zenus.adapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.zenus.entidades.Local;
import br.com.zenus.fragments.DetalheLocal;
import br.com.zenus.main.MainActivity;
import br.com.zenus.main.SobreActivity;
import br.com.zenus.prazercity.R;


/**
 * Created by Tuca on 24/04/2017.
 */

public class MyLocalViewAdapter extends RecyclerView.Adapter<MyLocalViewAdapter.DataObjectHolder> {


    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Local> mDataset;


    class DataObjectHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mNome;
        TextView mAvaliacao;
        TextView mTelefone;
        TextView mLatitude;
        TextView mLongitude;
        AlertDialog alertDialog;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            mView = itemView;
            mTelefone = (TextView) itemView.findViewById(R.id.txtTelefone);
            mAvaliacao = (TextView) itemView.findViewById(R.id.txtAvaliacaoItem);
            mNome = (TextView) itemView.findViewById(R.id.txtNome);
            mLatitude = (TextView) itemView.findViewById(R.id.txtLa);
            mLongitude = (TextView) itemView.findViewById(R.id.txtLo);

            Log.i(LOG_TAG, "Adding Listener");

        }

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

       final String nome = mDataset.get(position).getNome();
        final Double avaliacao = mDataset.get(position).getAvaliacao();
        final   String telefone = mDataset.get(position).getTelefone();
        final  Double latitude = mDataset.get(position).getLatitude();
        final  Double longitude = mDataset.get(position).getLongitude();

        holder.mNome.setText(nome);
        holder.mTelefone.setText(telefone);
        holder.mAvaliacao.setText("Nota de Avaliaçao: "+avaliacao.toString());

        holder.mLatitude.setText(latitude.toString());
        holder.mLongitude.setText(longitude.toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(v.getContext(), DetalheLocal.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                bundle.putString("telefone", telefone);
                bundle.putDouble("avaliacao", avaliacao);
                bundle.putDouble("media", avaliacao);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);

                it.putExtras(bundle);
                v.getContext().startActivity(it);
            }
        });

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
