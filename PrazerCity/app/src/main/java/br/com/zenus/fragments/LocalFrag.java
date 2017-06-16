package br.com.zenus.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.zenus.adapter.MyLocalViewAdapter;
import br.com.zenus.entidades.Local;


import br.com.zenus.main.CarregaDados;
import br.com.zenus.main.MainActivity;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.Base;
import br.com.zenus.util.ThreadProcessamento;

public class LocalFrag extends Base {

	private LayoutInflater inflater;
	private ViewGroup container;
	private Button btnRecarregarDados;
	private View viewFrag;



	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//chamo um método para melhor organização.
			if(msg.what==1){

				popularDados();

			}

		}
	};


	public LocalFrag() {
		// Required empty public constructor
	}

	List<Local> locais;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		this.inflater = inflater;

		if (container == null) {
			return null;
		}

		locais = new ArrayList<Local>();

		viewFrag = (RelativeLayout) inflater.inflate(R.layout.tab_layout_local, container, false);

		daoSession = CarregaDados.daoSession;

		prepararLista(viewFrag);

		popularDados();

		ThreadProcessamento processo = new ThreadProcessamento(handler);
		Thread t = new Thread(processo);
		t.start();

		return viewFrag;
	}

	private void prepararLista(View view) {

		mReciclerView = (RecyclerView) view.findViewById(R.id.recycler_local);

		mReciclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(view.getContext());

		mReciclerView.setLayoutManager(mLayoutManager);

	}

	public void popularDados(){

				locais = daoSession.loadAll(Local.class);

				mAdapter = new MyLocalViewAdapter(locais);

				mReciclerView.setAdapter(mAdapter);
	}







}
