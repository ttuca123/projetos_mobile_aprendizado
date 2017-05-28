package br.com.android.chat.dao;

import br.com.android.chat.DaoSession;
import br.com.android.chat.UsuarioDao;

/**
 * Created by Tuca on 08/05/2017.
 */

public class EntityDao {


    public UsuarioDao getUsuarioDao(DaoSession daoSession){


        return  daoSession.getUsuarioDao();
    }
}
