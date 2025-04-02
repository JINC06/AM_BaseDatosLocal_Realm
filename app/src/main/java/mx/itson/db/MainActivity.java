package mx.itson.db;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MascotaAdapter.OnItemLongClickListener {

    private EditText edNombre;
    private Spinner spSpinner;
    private Button btnGuardar;
    private RecyclerView rvListado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNombre = findViewById(R.id.edNombre);
        spSpinner = findViewById(R.id.spSpinner);
        btnGuardar = findViewById(R.id.btnGuardar);
        rvListado = findViewById(R.id.rvListado);
        rvListado.setLayoutManager(new LinearLayoutManager(this));

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edNombre.getText().toString();
                String tipo = spSpinner.getSelectedItem().toString();

                Mascota mascota = new Mascota(nombre, tipo);
                Mascota.save(mascota);

                edNombre.setText("");
                spSpinner.setSelection(0, true);

                cargarLista();
            }
        });

        cargarLista();
    }

    private void cargarLista() {
        List<Mascota> mascotas = Mascota.getAll();
        MascotaAdapter adapter = new MascotaAdapter(mascotas, MainActivity.this, MainActivity.this);
        rvListado.setAdapter( adapter );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        int id = v.getId();
        MenuInflater inflater = getMenuInflater();
        if(id == R.id.llMainContainer) {
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.action_edit) {
            this.editElement = mascotaSelected;
            mascotaSelected = null;
            mascotaSelectedPosition = -1;
            fillDataToEdit();
            return true;
        }else if(item.getItemId() == R.id.action_delete) {
            Mascota.delete(mascotaSelected.getId());
            mascotaSelected = null;
            mascotaSelectedPosition = -1;
            cargarLista();
            return true;
        }else {
            return super.onContextItemSelected(item);
        }
    }

    private void fillDataToEdit() {
        
    }

    private Mascota editElement;
    private Mascota mascotaSelected;
    private int mascotaSelectedPosition;

    @Override
    public void onSelectionLongClick(Mascota mascota, int position) {
        this.mascotaSelected = mascota;
        this.mascotaSelectedPosition = position;
    }

}