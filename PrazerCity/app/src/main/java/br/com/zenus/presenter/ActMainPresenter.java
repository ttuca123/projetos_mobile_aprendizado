package br.com.zenus.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import br.com.zenus.fragments.LocalFrag;
import br.com.zenus.fragments.MapaFrag;
import br.com.zenus.fragments.ViewPagerAdapter;
import br.com.zenus.main.CarregaDados;
import br.com.zenus.main.MVP;
import br.com.zenus.main.SobreActivity;
import br.com.zenus.model.ActMainModel;
import br.com.zenus.prazercity.R;
import br.com.zenus.view.ActMainView;


/**
 * Created by Tuca on 15/07/2017.
 */

public class ActMainPresenter implements MVP.PresenterImpl {

    private TabHost mTabHost;
    private MVP.ModelImpl model;
    private MVP.ViewImpl view;
    private Bundle savedInstance;


    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ActMainPresenter.TabInfo>();
    private PagerAdapter mPagerAdapter;
    private int tabAtual=0;

    // Informação da Tab
    public class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }
    }

    // Um simples factory que retorna View para o TabHost
    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    public ActMainPresenter( ){
        model = new ActMainModel(this);
    }



    @Override
    public Context getContext() {
        return (Context) view;
    }

    @Override
    public void setTabs() {

    }

    @Override
    public void setView(MVP.ViewImpl view, Bundle savedInstance) {
        this.view = view;

        //this.initialiseTabHost(this.savedInstance);
        if (savedInstance != null) {
            // Define a Tab de acordo com o estado salvo
            mTabHost.setCurrentTabByTag(savedInstance.getString("tab"));

        }

    }

    @Override
    public void AddTab(Activity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {


        tabSpec.setContent(new ActMainPresenter.TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public List<Fragment> getFragmentos() {

        List<Fragment> fragments = new Vector<Fragment>();

        fragments.add(Fragment.instantiate(getContext(), LocalFrag.class.getName()));
        fragments.add(Fragment.instantiate(getContext(), MapaFrag.class.getName()));

        return fragments;
    }

    @Override
    public void carregarDados() {

        Intent it = new Intent(getContext(), CarregaDados.class);
        getContext().startActivity(it);


    }

    @Override
    public void irParaActivitySobre() {
        Intent it = new Intent(getContext(), SobreActivity.class);
        getContext().startActivity(it);
    }

    public void initialiseTabHost(Bundle args) {

        if (args != null) {
            // Define a Tab de acordo com o estado salvo
            mTabHost.setCurrentTabByTag(savedInstance.getString("tab"));
        }

        mTabHost.setup();


        TabInfo tabInfo = null;
//        AddTab((Activity) view, this.mTabHost,
//                this.mTabHost.newTabSpec("Tab1").setIndicator("Locais"),
//
//                (tabInfo = new TabInfo("Tab1", LocalFrag.class, args)));

//        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        AddTab((Activity) view, this.mTabHost,
                this.mTabHost.newTabSpec("Tab2").setIndicator("Mapa"),
                (tabInfo = new TabInfo("Tab2", MapaFrag.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

    }
}
