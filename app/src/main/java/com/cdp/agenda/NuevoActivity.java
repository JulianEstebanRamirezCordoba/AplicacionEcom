package com.cdp.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cdp.agenda.db.DbCliente;

import java.nio.charset.StandardCharsets;

public class NuevoActivity extends AppCompatActivity {

    public EditText txtNombre, txtApellido, txtCedula, txtTelefono, txtCorreoElectronico, txtEdad;
    public Button btnGuarda, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEdad =  findViewById(R.id.txtEdad);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCedula = findViewById(R.id.txtCedula);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);

        btnGuarda = findViewById(R.id.btnGuarda);
        btnSalir = findViewById(R.id.btnSalir);

        btnGuarda.setOnClickListener(this::onClick);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnSalir();
            }
        });

    }

    public void onClick(@NonNull View v) {
        switch (v.getId()){
            case R.id.btnGuarda:
                GuardarCliente();
        }
    }


    //Metopo validaciones y envio datos
    private void GuardarCliente() {

        String nombre = txtNombre.getText().toString().trim();
        String apellido = txtApellido.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String documento = txtCedula.getText().toString().trim();
        int edad = Integer.parseInt(txtEdad.getText().toString().trim());
        String correo = txtCorreoElectronico.getText().toString().trim();

        if (nombre.isEmpty()) {
            txtNombre.setError("El campo de nombre es obligatorio no puede estar vacio");
            txtNombre.requestFocus();
            return;

        }
        if (apellido.isEmpty()) {
            txtApellido.setError("El campo apellido es obligatorio no puede estar vacio");
            txtNombre.requestFocus();
            return;

        }
        if (telefono.isEmpty()) {
            txtTelefono.setError("El campo telefono es obligatorio no puede estar vacio");
            txtTelefono.requestFocus();
            return;

        }
        if (documento.isEmpty()) {
            txtCedula.setError("El campo cedula es obligatorio no puede estar vacio");
            txtCedula.requestFocus();
            return;

        }
        if (edad < 18) {
            txtEdad.setError("No cumples con la edad necesaria para tener cedula o usar la aplicacion");
            txtEdad.requestFocus();
            return;
        }

        if(txtNombre.getText().toString().equals("") || txtApellido.getText().toString().equals("") ||
              txtCedula.getText().toString().equals("") || txtTelefono.getText().toString().equals("")) {

            Toast.makeText(NuevoActivity.this, "Llena los campos pedidos", Toast.LENGTH_SHORT).show();
            limpiar();
        }

        DbCliente dbclientes = new DbCliente(NuevoActivity.this);
        long id = dbclientes.insertarcliente(nombre, apellido, telefono, documento, correo, edad);

        if (id > 0) {
            Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
            limpiar();

        } else {
            Toast.makeText(NuevoActivity.this, "Error de Registro Reinicia", Toast.LENGTH_SHORT).show();
        }
    }

        private void BtnSalir(){
            Intent intent = new Intent(NuevoActivity.this, MainActivity.class);
            startActivity(intent);

        }

        private void limpiar () {
            setResult(RESULT_OK);
            txtCedula.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtEdad.setText("");
            txtTelefono.setText("");
            txtCorreoElectronico.setText("");
        }
    }