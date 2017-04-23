package br.com.zenus.prazercity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class MainActivity extends FragmentActivity implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainActivity.TabInfo>();
    private PagerAdapter mPagerAdapter;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout
        setContentView(R.layout.activity_main);
        // Inicializa o TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            // Define a Tab de acordo com o estado salvo
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        // Inicializa o ViewPager
        this.intialiseViewPager();
    }

    protected void onSaveInstanceState(Bundle outState) {
        // salva a Tab selecionada
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, TabFragmentA.class.getName()));
        fragments.add(Fragment.instantiate(this, TabFragmentB.class.getName()));
        fragments.add(Fragment.instantiate(this, TabFragmentC.class.getName()));
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
                this.mTabHost.newTabSpec("Tab1").setIndicator("A"),
                (tabInfo = new TabInfo("Tab1", TabFragmentA.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Tab2").setIndicator("B"),
                (tabInfo = new TabInfo("Tab2", TabFragmentB.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Tab3").setIndicator("C"),
                (tabInfo = new TabInfo("Tab3", TabFragmentC.class, args)));
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
