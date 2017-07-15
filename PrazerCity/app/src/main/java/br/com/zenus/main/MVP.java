package br.com.zenus.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import java.util.List;

import br.com.zenus.presenter.ActMainPresenter;

/**
 * Created by Tuca on 15/07/2017.
 */

public interface MVP {

   interface ViewImpl extends TabHost.OnTabChangeListener,
           ViewPager.OnPageChangeListener{

            public void inicializarViewPage();



    }

    interface PresenterImpl {

        public Context getContext();
        public void setTabs();
        public void setView(ViewImpl view, Bundle savedInstance);
        public  void AddTab(Activity activity, TabHost tabHost,
                            TabHost.TabSpec tabSpec, ActMainPresenter.TabInfo tabInfo);
        public List<Fragment> getFragmentos();
        public void carregarDados();
        public void irParaActivitySobre();
        public void initialiseTabHost(Bundle args);
    }

    interface ModelImpl{

    }

}
