package com.cdp.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cdp.agenda.db.DbCliente;
import com.cdp.agenda.entidades.CLiente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InformacionCli extends AppCompatActivity {

    TextView txtNombre;
    TextView txtApellido;
    TextView txtEdad;
    TextView txtcedula;
    TextView txtCorreo;
    TextView txtTelefono;
    FloatingActionButton btnEdit, btnEliminar;

    CLiente cliente;
    int id = 0;
    boolean elimina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infromacion_cli);

        txtNombre = findViewById(R.id.txtName);
        txtApellido = findViewById(R.id.txtLastName);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtEdad = findViewById(R.id.txtMostraredad);
        txtcedula = findViewById(R.id.txtDocument);
        txtTelefono = findViewById(R.id.txtPhone);

        btnEdit = findViewById(R.id.favEditar);
        btnEliminar = findViewById(R.id.favDelete);

        DbCliente dbcliente = new DbCliente(InformacionCli.this);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformacionCli.this, EditarActivity.class);
                intent.putExtra("ID_Edit", cliente.getId());
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Mensaje = new AlertDialog.Builder(InformacionCli.this);
                Mensaje.setMessage("Desea realizar la Accion Eliminar Cliente" + "\nRecuerde los datos no podran ser recuperados")

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              elimina = dbcliente.EliminarCliente(id);
                              if(elimina == true){
                                  OK();
                              }
                            }
                        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        if(savedInstanceState == null){
           Bundle extraccion = getIntent().getExtras();

            if(extraccion != null){
                id = extraccion.getInt("Id_cli");

            }else{
                id = Integer.parseInt(null);

            }

        }else{
            id = (int) savedInstanceState.getSerializable("Id_cli");

        }

        cliente = dbcliente.mostrarEspecificado(id);

        if(cliente != null){

            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtCorreo.setText(cliente.getCorreo_electornico());
            txtTelefono.setText(cliente.getTelefono());
            txtcedula.setText(cliente.getCedula());
            txtEdad.setText( "" + cliente.getEdad());

        }else{

            Toast.makeText(InformacionCli.this, "Error al hacer consulta", Toast.LENGTH_SHORT).show();

        }

    }

    private void OK(){
        Intent intent = new Intent(InformacionCli.this, MainActivity.class);
        startActivity(intent);
    }

}