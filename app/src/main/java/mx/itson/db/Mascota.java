package mx.itson.db;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Mascota extends RealmObject {

    @PrimaryKey
    private String id;

    private String nombre;

    private String tipo;

    public Mascota() {
    }

    public Mascota(String nombre, String tipo) {
        String uuid = UUID.randomUUID().toString();
        this.id = uuid;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static void save(Mascota mascota) {
        Realm realm = AppAplication.getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(mascota);
        realm.commitTransaction();
    }

    public static List<Mascota> getAll() {
        Realm realm = AppAplication.getRealmInstance();
        return realm.copyFromRealm( realm.where(Mascota.class).findAll() );
    }

    public static void edit(Mascota mascota) {
        Realm realm = AppAplication.getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(mascota);
        realm.commitTransaction();
    }

    public static Mascota getById(String id) {
        Realm realm = AppAplication.getRealmInstance();
        Mascota mascota = realm.where(Mascota.class).equalTo("id", id).findFirst();
        if(mascota != null) {
            return realm.copyFromRealm( mascota );
        }
        return null;
    }

    public static void delete(String id){
        Realm realm = AppAplication.getRealmInstance();
        realm.beginTransaction();
        Mascota mascota = realm.where(Mascota.class).equalTo("id", id).findFirst();
        if(mascota != null)
            mascota.deleteFromRealm();
        realm.commitTransaction();
    }

}
