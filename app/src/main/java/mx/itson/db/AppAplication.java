package mx.itson.db;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class AppAplication extends Application {

    private static RealmConfiguration realmConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static synchronized Realm getRealmInstance() {
        try {
            return Realm.getInstance(realmConfig);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfig);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfig);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }
    }

}
