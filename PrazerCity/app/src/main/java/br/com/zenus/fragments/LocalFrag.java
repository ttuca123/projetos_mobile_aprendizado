package br.com.zenus.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

	private void createLista(View view) {

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
			LocalDao localDao = entityDao.getLocalDao(daoSession);

			locais = localDao.loadAll();

			locais = new ArrayList<Local>();

				Local local = new Local();

				Double latitude = -3.7286049;
				Double longitude = -38.5349044;
				local.setSeqLocal(128983L);
				local.setNome("Skalla Drinks ");
				local.setTelefone("226-9292321309");
				local.setAvaliacao(1.0);
				local.setLatitude(latitude.doubleValue());
				local.setLongitude(longitude.doubleValue());

				locais.add(local);

				local = new Local();

				latitude = -3.744482;
				longitude = -38.5340377;
				local.setSeqLocal(128983L);
				local.setNome("Puteiro 3010 ");
				local.setTelefone("226-9292099");
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
