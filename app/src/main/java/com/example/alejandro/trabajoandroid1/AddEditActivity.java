package com.example.alejandro.trabajoandroid1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEditActivity extends AppCompatActivity {
    EditText edtNombre;
    EditText edtPrecio;
    EditText edtStock;
    Spinner listaTipos;
    Button btnAddEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtPrecio = (EditText) findViewById(R.id.edtPrecio);
        edtStock =(EditText) findViewById(R.id.edtStock);
        btnAddEdit = (Button) findViewById(R.id.btnAddEdit);

        listaTipos = (Spinner) findViewById(R.id.listaTipos);
        String [] tipos = {getResources().getString(R.string.cards), getResources().getString(R.string.sports), getResources().getString(R.string.adventure), "MOBA", "FPS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tipos);
        listaTipos.setAdapter(adapter);

        //Comprobacion si venimos de editar o de añadir.
        Videojuego v = (Videojuego) getIntent().getSerializableExtra("videojuego");
        if (v != null) {
            edtNombre.setText(v.getNombre());
            edtPrecio.setText(String.valueOf(v.getPrecio()));
            edtStock.setText(String.valueOf(v.getStock()));
            listaTipos.setSelection(v.getImg());
            btnAddEdit.setText(getResources().getString(R.string.editar));
        }

        btnAddEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Videojuego videoju = new Videojuego(null,edtNombre.getText().toString(),
                        Integer.parseInt(edtStock.getText().toString()),
                        Double.parseDouble(edtPrecio.getText().toString()),
                        String.valueOf(listaTipos.getSelectedItem()),
                        listaTipos.getSelectedItemPosition());

                if (v != null) {
                    videoju.setId(v.getId());
                }

                Intent i = new Intent();
                i.putExtra("videojuego",videoju);
                setResult(RESULT_OK,i);
                finish();


            }

        });

    }
}
