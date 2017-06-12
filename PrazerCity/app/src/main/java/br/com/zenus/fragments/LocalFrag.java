package br.com.zenus.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import br.com.zenus.adapter.MyLocalViewAdapter;
import br.com.zenus.entidades.Local;
import br.com.zenus.entidades.LocalDao;


import br.com.zenus.prazercity.R;
import br.com.zenus.util.App;
import br.com.zenus.util.BaseFragment;
import br.com.zenus.util.EntityDao;

public class LocalFrag extends BaseFragment {

	String urlLocais = "http://192.168.25.9/prazer-city/locais.php";

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

		// Inflamos o layout tab_layout_local
		return view;
	}

	private void createLista(final View view) {

		mReciclerView = (RecyclerView) view.findViewById(R.id.recycler_local);

		mReciclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(view.getContext());

		mReciclerView.setLayoutManager(mLayoutManager);

		try {
			entityDao = new EntityDao();
			daoSession = ((App) getActivity().getApplication()).getDaoSession();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}


		try {

			Ion.with(view.getContext()).load(urlLocais)
					.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
				@Override
				public void onCompleted(Exception e, JsonObject result) {

					if(result!=null ){

						Toast.makeText(view.getContext(), "Dados: "+result, Toast.LENGTH_LONG).show();
					}else{
						//Toast.makeText(getBaseContext(), "Erro ao enviar avaliação!", Toast.LENGTH_LONG).show();
					}
				}
			});



			LocalDao localDao = entityDao.getLocalDao(daoSession);

			locais = localDao.loadAll();

			locais = new ArrayList<Local>();

				Local local = new Local();

				Double latitude = -3.7286049;
				Double longitude = -38.5349044;
				local.setSeqLocal(128983L);
				local.setNome("Skalla Drinks ");
				local.setTelefone("9812914642");
				local.setAvaliacao(1.0);
				local.setLatitude(latitude.doubleValue());
				local.setLongitude(longitude.doubleValue());

				locais.add(local);

				local = new Local();

				latitude = -3.744482;
				longitude = -38.5340377;
				local.setSeqLocal(128983L);
				local.setNome(" 3010 ");
				local.setTelefone("987470788");
				local.setAvaliacao(4.0);
				local.setLatitude(latitude.doubleValue());
				local.setLongitude(longitude.doubleValue());

				locais.add(local);

		} catch (Exception e) {
			e.printStackTrace();
		}


		mAdapter = new MyLocalViewAdapter(locais);

		mReciclerView.setAdapter(mAdapter);

	}

}
