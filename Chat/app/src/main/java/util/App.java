package util;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import br.com.android.chat.*;


/**
 * Created by Tuca on 08/05/2017.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private br.com.android.chat.DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper;
        helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "chat-encrypted" : "chat");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public br.com.android.chat.DaoSession getDaoSession() {
        return daoSession;
    }

}
