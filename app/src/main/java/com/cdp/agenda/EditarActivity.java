package com.cdp.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cdp.agenda.db.DbCliente;
import com.cdp.agenda.entidades.CLiente;

public class EditarActivity extends AppCompatActivity {

    EditText name, lastName, email, phone, edad;
    Button btnModificar, btnAtras;

    //Variables de update
    int id = 0;
    CLiente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        name = findViewById(R.id.TxtNombre);
        lastName = findViewById(R.id.TxtApellido);
        email = findViewById(R.id.TxtCor);
        phone = findViewById(R.id.TxtCel);
        edad = findViewById(R.id.TxtEdad);

        btnModificar = findViewById(R.id.btnModificar);
        btnAtras = findViewById(R.id.btnAtras);



        btnModificar.setOnClickListener(this::onClick);
        btnAtras.setOnClickListener(this::onClick);

        if(savedInstanceState == null){
            Bundle extraccion = getIntent().getExtras();

            if(extraccion != null){
                id = extraccion.getInt("ID_Edit");

            }else{
                id = Integer.parseInt(null);

            }

        }else{
            id = (int) savedInstanceState.getSerializable("ID_Edit");

        }

        DbCliente dbcliente = new DbCliente(EditarActivity.this);
        cliente = dbcliente.mostrarEspecificado(id);

        if(cliente != null) {

            name.setText(cliente.getNombre());
            lastName.setText(cliente.getApellido());
            email.setText(cliente.getCorreo_electornico());
            phone.setText(cliente.getTelefono());
            edad.setText("" + cliente.getEdad());
        }
    }

    //Metodos Edicion
    private void onClick(@NonNull View view) {

        switch(view.getId()){
            case R.id.btnModificar:
                ValidarCam();
            break;

            case R.id.btnAtras:
                Intent intent = new Intent(EditarActivity.this, InformacionCli.class);
                startActivity(intent);
        }

    }

    private void ValidarCam() {
        String nombre = name.getText().toString().trim();
        String apellido = lastName.getText().toString().trim();
        String correo = email.getText().toString().trim();
        String telefono = phone.getText().toString().trim();
        int Edad = Integer.parseInt(edad.getText().toString().trim());

        if (nombre.isEmpty()) {
            name.setError("El campo de nombre es obligatorio no puede estar vacio");
            name.requestFocus();
            return;

        }
        if (apellido.isEmpty()) {
            lastName.setError("El campo apellido es obligatorio no puede estar vacio");
            lastName.requestFocus();
            return;

        }
        if (telefono.isEmpty()) {
            phone.setError("El campo telefono es obligatorio no puede estar vacio");
            phone.requestFocus();
            return;
        }

        if(name.getText().toString().equals("") || lastName.getText().toString().equals("") ||
                email.getText().toString().equals("") || phone.getText().toString().equals("")) {
            Toast.makeText(EditarActivity.this, "Llena los campos para actualizar tus credenciales", Toast.LENGTH_SHORT).show();

        }

        DbCliente dbCliente = new DbCliente(EditarActivity.this);
        boolean result = dbCliente.EditarCliente(1, nombre, apellido, correo, telefono, Edad);

        if(result == true){
            Toast.makeText(EditarActivity.this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show();
            Hecho();

        }else{
            Toast.makeText(EditarActivity.this, "ERROR AL REGISTRAR REINTENTA", Toast.LENGTH_SHORT).show();

        }

    }

    private void Hecho(){

        Intent intent = new Intent(EditarActivity.this, MainActivity.class);
        startActivity(intent);

    }

}