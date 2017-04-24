package br.com.zenus.fragments;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import br.com.zenus.prazercity.R;
import br.com.zenus.util.PermissionUtils;

public class MapaFrag extends Fragment  {




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		// Inflamos o layout tab_layout_local
		return (RelativeLayout) inflater.inflate(R.layout.tab_layout_mapa, container, false);
	}



}
