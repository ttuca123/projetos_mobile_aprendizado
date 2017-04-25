package br.com.zenus.util;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import br.com.zenus.entidades.DaoMaster;
import br.com.zenus.entidades.DaoSession;

/**
 * Created by Tuca on 23/04/2017.
 */

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "prazer-city-encrypted" : "prazer-city");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
