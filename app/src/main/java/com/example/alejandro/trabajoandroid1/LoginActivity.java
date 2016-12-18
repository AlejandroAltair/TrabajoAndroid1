package com.example.alejandro.trabajoandroid1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
        private static final int CODE3 = 3;
        TextView txtUsu;
        TextView txtPass;
        EditText edtUsu;
        EditText edtPas;
        Button btnLogin;
        private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsu = (TextView) findViewById(R.id.txtUsu);
        txtPass = (TextView) findViewById(R.id.txtPass);
        edtUsu = (EditText) findViewById(R.id.edtUsu);
        edtPas = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        sp = getSharedPreferences("datosUsu", MODE_PRIVATE);
        if (sp.contains("nick") && sp.contains("pass")){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

        btnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (edtUsu.getText().length() != 0 && edtPas.getText().length() != 0){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("nick",edtUsu.getText().toString());
                    editor.putString("pass",edtPas.getText().toString());
                    editor.commit();

                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, getResources().getString(R.string.loginToast), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
