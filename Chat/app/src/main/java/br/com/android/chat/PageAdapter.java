package br.com.android.chat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Tuca on 01/06/2017.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private Context context;
    String tabs[] = new String[]{"Contatos", "Chats", "Perfil"};

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            default:
                return null;
            case 0:
                return new ContatosFragment();

            case 1:
                return new ConversasFragment();
            case 2:
                return new PerfilFragment();

        }
    }

    @Override
    public int getCount() {

        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position){

        return tabs[position];
    }

    public View getTableView(int position){

        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView txvTab = (TextView)tab.findViewById(R.id.txvTab);
        txvTab.setText(getPageTitle(position));
        return tab;
    }
}
