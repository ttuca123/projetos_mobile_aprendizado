package br.com.android.chat;

import android.net.Uri;
import android.support.design.widget.TabLayout;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.android.chat.ChatActivity;

import static br.com.android.chat.R.*;

public class ChatActivity extends AppCompatActivity implements ContatosFragment.OnFragmentInteractionListener,
        ConversasFragment.OnFragmentInteractionListener , PerfilFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), ChatActivity.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        for(int posicao=0; posicao<tabLayout.getTabCount();posicao++){
            TabLayout.Tab tab = tabLayout.getTabAt(posicao);
            tab.setCustomView(pagerAdapter.getTableView(posicao));


        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
