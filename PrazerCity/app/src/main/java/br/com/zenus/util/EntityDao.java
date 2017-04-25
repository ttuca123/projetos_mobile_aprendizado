package br.com.zenus.util;

import br.com.zenus.entidades.DaoSession;
import br.com.zenus.entidades.LocalDao;

/**
 * Created by Tuca on 24/04/2017.
 */

public class EntityDao {

    public LocalDao getLocalDao(DaoSession daoSession){


        return  daoSession.getLocalDao();
    }
}
