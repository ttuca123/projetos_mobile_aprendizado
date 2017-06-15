package br.com.zenus.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.util.ArrayList;
import java.util.List;

import br.com.zenus.EnuServicos;
import br.com.zenus.adapter.MyLocalViewAdapter;
import br.com.zenus.entidades.Local;


import br.com.zenus.entidades.LocalMaster;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.BaseFragment;
import br.com.zenus.util.Utilitarios;

public class LocalFrag extends BaseFragment {

	private LayoutInflater inflater;
	private ViewGroup container;
	private Button btnRecarregarDados;
	private View viewFrag;


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

		prepararLista(viewFrag);

		if(Utilitarios.verificarConexao(viewFrag.getContext())){

			popularDados(viewFrag);

		}else{

			viewFrag = (RelativeLayout) inflater.inflate(R.layout.frg_sem_conexao, container, false);
			btnRecarregarDados = (Button) viewFrag.findViewById(R.id.btnCarregarDados);

			btnRecarregarDados.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					popularDados(view);
				}
			});
		}


		return viewFrag;
	}



	private void prepararLista(View view) {

		mReciclerView = (RecyclerView) view.findViewById(R.id.recycler_local);

		mReciclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(view.getContext());

		mReciclerView.setLayoutManager(mLayoutManager);

	}

	public void popularDados(final View viewDados){
		final ProgressDialog pd = new ProgressDialog(viewFrag.getContext());
		pd.setMessage("carregando");

		pd.show();
		Ion.with(viewDados.getContext()).load(EnuServicos.LOCAIS.getNomeAmigavel())
				.as(JsonObject.class)
				.setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {


						if (result != null) {
							Gson gson = new Gson();

							LocalMaster localMaster=null;

							localMaster = gson.fromJson(result, LocalMaster.class);

							locais = localMaster.getLocais();

							inflater = LayoutInflater.from(viewFrag.getContext());
							View Cv = inflater.inflate(R.layout.tab_layout_local,null);

							prepararLista(viewFrag);

							mAdapter = new MyLocalViewAdapter(locais);

							mReciclerView.setAdapter(mAdapter);

							container.addView(Cv);

							Toast.makeText(viewDados.getContext(), "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(viewDados.getContext(), "Erro ao carregar dados!", Toast.LENGTH_LONG).show();
						}
					}
				});
		pd.dismiss();

	}





}
