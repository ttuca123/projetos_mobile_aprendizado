package br.com.zenus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.greenrobot.greendao.database.Database;

import br.com.zenus.prazercity.R;

public class LocalFrag extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}

        DevOpenHelper helper = new DevOpenHelper(this, "notes-db");
		Database db = helper.getWritableDb();

		ListView listaLocais = (ListView) getActivity().findViewById(R.id.lista);

//		ArrayAdapter<LocalFrag> adapter = new ArrayAdapter<LocalFrag>();
		
		// Inflamos o layout tab_layout_local
		return (RelativeLayout) inflater.inflate(R.layout.tab_layout_local, container, false);
	}

}
