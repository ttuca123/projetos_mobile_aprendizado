package br.com.zenus.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;




import java.util.HashMap;
import java.util.List;
import java.util.Vector;



import br.com.zenus.fragments.LocalFrag;
import br.com.zenus.fragments.MapaFrag;
import br.com.zenus.fragments.ViewPagerAdapter;
import br.com.zenus.prazercity.R;


public class MainActivity extends AppCompatActivity implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;
    private int tabAtual=0;


    Bundle savedInstance;



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


    private void criarView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);

        // Inicializa o TabHost
        this.initialiseTabHost(this.savedInstance);
        if (savedInstance != null) {
            // Define a Tab de acordo com o estado salvo
            mTabHost.setCurrentTabByTag(savedInstance.getString("tab"));
        }


        // Inicializa o ViewPager
        this.intialiseViewPager();

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout
        setContentView(R.layout.main_activity);
        this.savedInstance = savedInstanceState;


        criarView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent it;

            switch (id) {
                case R.id.action_about:

                     it = new Intent(MainActivity.this, SobreActivity.class);
                    startActivity(it);
                    break;
                case R.id.action_atualize:

                    it = new Intent(MainActivity.this, CarregaDados.class);
                    startActivity(it);
                    finish();
                    break;
            }


        return super.onOptionsItemSelected(item);
    }


    protected void onSaveInstanceState(Bundle outState) {
        // salva a Tab selecionada
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();

        fragments.add(Fragment.instantiate(this, LocalFrag.class.getName()));
        fragments.add(Fragment.instantiate(this, MapaFrag.class.getName()));
        this.mPagerAdapter = new ViewPagerAdapter(
                super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Tab1").setIndicator("Locais"),
                (tabInfo = new TabInfo("Tab1", LocalFrag.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Tab2").setIndicator("Mapa"),
                (tabInfo = new TabInfo("Tab2", MapaFrag.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        mTabHost.setOnTabChangedListener(this);
    }


    private static void AddTab(MainActivity activity, TabHost tabHost,
                               TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach uma Tab view factory para o spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        // Avisa para o mViewPager qual a Tab que está ativa

        int pos = this.mTabHost.getCurrentTab();
        tabAtual=pos;

        this.mViewPager.setCurrentItem(pos);
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
