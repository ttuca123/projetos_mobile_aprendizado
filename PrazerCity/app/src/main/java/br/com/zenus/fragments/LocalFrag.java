package br.com.zenus.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.zenus.adapter.MyLocalViewAdapter;
import br.com.zenus.entidades.Local;
import br.com.zenus.entidades.LocalDao;


import br.com.zenus.entidades.LocalMaster;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.App;
import br.com.zenus.util.BaseFragment;
import br.com.zenus.util.EntityDao;

public class LocalFrag extends BaseFragment {

	String urlLocais = "http://192.168.25.9/prazer-city/buscar_locais.php";

	public LocalFrag() {
		// Required empty public constructor
		entityDao = new EntityDao();
	}

	List<Local> locais;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		View view = (RelativeLayout) inflater.inflate(R.layout.tab_layout_local, container, false);

		createLista(view);

		return view;
	}

	private void createLista(View view) {

		locais = new ArrayList<Local>();

		mReciclerView = (RecyclerView) view.findViewById(R.id.recycler_local);

		mReciclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(view.getContext());

		mReciclerView.setLayoutManager(mLayoutManager);

		atualizarDados(view);
	}



	public void atualizarDados(final View view){

		Ion.with(view.getContext()).load(urlLocais)
				.progress(new ProgressCallback() {
					@Override
					public void onProgress(long downloaded, long total) {

					}
				}).as(JsonObject.class)
				.setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {
						if (result != null) {
							Gson gson = new Gson();

							LocalMaster localMaster=null;

							localMaster = gson.fromJson(result, LocalMaster.class);

							locais = localMaster.getLocais();

							Toast.makeText(view.getContext(), "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();

							mAdapter = new MyLocalViewAdapter(locais);

							mReciclerView.setAdapter(mAdapter);
						} else {
							Toast.makeText(view.getContext(), "Erro ao carregar dados!", Toast.LENGTH_LONG).show();
						}
					}
				});

	}



}
