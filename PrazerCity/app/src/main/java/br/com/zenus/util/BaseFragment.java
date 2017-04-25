package br.com.zenus.util;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import br.com.zenus.entidades.DaoSession;


/**
 * Created by Tuca on 24/04/2017.
 */

public class BaseFragment extends Fragment {

    protected RecyclerView mReciclerView;

    protected RecyclerView.Adapter mAdapter;

    protected RecyclerView.LayoutManager mLayoutManager;

    protected EntityDao entityDao;

    protected DaoSession daoSession;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";

}
