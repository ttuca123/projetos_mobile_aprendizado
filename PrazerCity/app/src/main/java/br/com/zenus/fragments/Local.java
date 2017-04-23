package br.com.zenus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.zenus.prazercity.R;

public class Local extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		
		// Inflamos o layout tab_layout_local
		return (RelativeLayout) inflater.inflate(R.layout.tab_layout_local, container, false);
	}

}