package com.example.alejandro.trabajoandroid1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int CODE1 = 1 ;
    private static final int CODE2 = 2 ;
    private static int id = -1;
    private SharedPreferences sp;
    private ListView lista;
    private List<Videojuego> listavideojuegos = new ArrayList<>();
    private AdaptadorVideojuegos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (MainActivity.this, AddEditActivity.class);
                startActivityForResult(i, CODE1);
            }
        });

        sp = getSharedPreferences("datosUsu",MODE_PRIVATE);

        lista = (ListView) findViewById(R.id.lista);
        cargarDatos();

        adaptador = new AdaptadorVideojuegos(this);
        lista.setAdapter(adaptador);

        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        LayoutInflater inflate = getLayoutInflater();
        View item = inflate.inflate(R.layout.header, null);

        menu.setHeaderView(item);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones,menu);



    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        id = info.position;
        switch (item.getItemId()){
            case R.id.opcDel:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.tituloDialogo));

                builder.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listavideojuegos.remove(info.position);
                        adaptador.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.opcEdit:
                Videojuego v = listavideojuegos.get(info.position);

                Intent i = new Intent (MainActivity.this, AddEditActivity.class);
                i.putExtra("videojuego", v);
                startActivityForResult(i, CODE2);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CODE1:
                if (resultCode == RESULT_OK){
                    Videojuego v = (Videojuego) data.getSerializableExtra("videojuego");
                    v.setId(listavideojuegos.size()+1);
                    listavideojuegos.add(v);

                    adaptador.notifyDataSetChanged();
                }
                break;
            case CODE2:
                if (resultCode == RESULT_OK){
                    Videojuego v = (Videojuego) data.getSerializableExtra("videojuego");
                    listavideojuegos.set(id,v);
                    adaptador.notifyDataSetChanged();
                }
                break;
        }
    }

    private void cargarDatos() {
        listavideojuegos.add(new Videojuego(1,"Hearthstone",50,50.20,getResources().getString(R.string.cards),0));
        listavideojuegos.add(new Videojuego(2,"Fifa 17",70,55.50,getResources().getString(R.string.sports),1));
        listavideojuegos.add(new Videojuego(3,"Uncharted 4",100,35.00,getResources().getString(R.string.adventure),2));
        listavideojuegos.add(new Videojuego(4,"Paragon",200,5.20,"MOBA",3));
        listavideojuegos.add(new Videojuego(5,"Destiny",60,70.00,"FPS",4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_del_all) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.tituloDialogoAll));

            builder.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listavideojuegos.clear();
                    adaptador.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.tituloDialogoLogout));

            builder.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("nick");
                    editor.remove("pass");
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            });

            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class AdaptadorVideojuegos extends ArrayAdapter {
        Activity context;

        public AdaptadorVideojuegos(Activity context) {
            super(context, R.layout.fila_videojuego, listavideojuegos);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;

            ViewHolder holder;

            if (item == null){
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.fila_videojuego, null);

                holder = new ViewHolder();
                holder.txtNombre = (TextView) item.findViewById(R.id.txtNombre);
                holder.txtTipo = (TextView) item.findViewById(R.id.txtTipo);
                holder.txtPrecio = (TextView) item.findViewById(R.id.txtPrecio);
                holder.txtStock = (TextView) item.findViewById(R.id.txtStock);
                holder.imagen = (ImageView) item.findViewById(R.id.imagen);

                item.setTag(holder);
            }else{
                holder = (ViewHolder) item.getTag();
            }

            holder.txtNombre.setText(listavideojuegos.get(position).getNombre());
            holder.txtTipo.setText(listavideojuegos.get(position).getTipo());
            holder.txtPrecio.setText(String.valueOf(listavideojuegos.get(position).getPrecio()));
            holder.txtStock.setText(String.valueOf(listavideojuegos.get(position).getStock()));

            switch (listavideojuegos.get(position).getImg()){
                case 0:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_hearthstone);
                    break;
                case 1:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_fifa17);
                    break;
                case 2:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_uncharted4);
                    break;
                case 3:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_paragon);
                    break;
                case 4:
                    holder.imagen.setBackgroundResource(R.mipmap.ic_destiny);
                    break;
            }

            return item;
        }
    }

    static class ViewHolder {
        TextView txtNombre;
        TextView txtTipo;
        TextView txtPrecio;
        TextView txtStock;
        ImageView imagen;
    }
}
