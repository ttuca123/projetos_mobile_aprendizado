package br.com.zenus.view;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;




import java.util.HashMap;


import br.com.zenus.fragments.LocalFrag;
import br.com.zenus.fragments.MapaFrag;
import br.com.zenus.fragments.ViewPagerAdapter;
import br.com.zenus.main.CarregaDados;
import br.com.zenus.main.MVP;
import br.com.zenus.main.SobreActivity;
import br.com.zenus.prazercity.R;
import br.com.zenus.presenter.ActMainPresenter;


public class ActMainView extends AppCompatActivity implements
        MVP.ViewImpl {

    private static MVP.PresenterImpl presenter;

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ActMainView.TabInfo>();
    private PagerAdapter mPagerAdapter;
    private int tabAtual=0;
    private MenuInflater menuInflater;
    private Menu menu;

    Bundle savedInstance;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout
        setContentView(R.layout.main_activity);
        this.savedInstance = savedInstanceState;

        if(presenter ==null){
            presenter = new ActMainPresenter();
        }

        presenter.setView(this, savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // Inicializa o TabHost

        presenter.initialiseTabHost(savedInstance);

        inicializarViewPage();
    }

    @Override
    public void inicializarViewPage() {

        mPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(), presenter.getFragmentos());

        this.mViewPager.setAdapter(mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    // Informação da Tab
    private class TabInfo {
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
//    class TabFactory implements TabHost.TabContentFactory {
//
//        private final Context mContext;
//
//        public TabFactory(Context context) {
//            mContext = context;
//        }
//
//        public View createTabContent(String tag) {
//            View v = new View(mContext);
//            v.setMinimumWidth(0);
//            v.setMinimumHeight(0);
//            return v;
//        }
//
//    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;

        this.menuInflater = getMenuInflater();

        this.menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent it;

            switch (id) {
                case R.id.action_about:

                    presenter.irParaActivitySobre();

                    break;
                case R.id.action_atualize:

                    presenter.carregarDados();

                    break;
            }

        return super.onOptionsItemSelected(item);
    }






//    private void initialiseTabHost(Bundle args) {
//
//        mTabHost.setup();
//
//
//        TabInfo tabInfo = null;
//        presenter.AddTab(this, this.mTabHost,
//                this.mTabHost.newTabSpec("Tab1").setIndicator("Locais"),
//
//                (tabInfo = new TabInfo("Tab1", LocalFrag.class, args)));
//
//        this.mapTabInfo.put(tabInfo.tag, tabInfo);
//        ActMainView.AddTab(this, this.mTabHost,
//                this.mTabHost.newTabSpec("Tab2").setIndicator("Mapa"),
//                (tabInfo = new TabInfo("Tab2", MapaFrag.class, args)));
//        this.mapTabInfo.put(tabInfo.tag, tabInfo);
//        mTabHost.setOnTabChangedListener(this);
//    }


//    private static void AddTab(ActMainView activity, TabHost tabHost,
//                               TabHost.TabSpec tabSpec, TabInfo tabInfo) {
//        // Attach uma Tab view factory para o spec
//        tabSpec.setContent(activity.new TabFactory(activity));
//        tabHost.addTab(tabSpec);
//    }

    public void onTabChanged(String tag) {
        // Avisa para o mViewPager qual a Tab que está ativa

        int pos = this.mTabHost.getCurrentTab();
        tabAtual=pos;

        this.mViewPager.setCurrentItem(pos);
    }

    protected void onSaveInstanceState(Bundle outState) {
        // salva a Tab selecionada
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
