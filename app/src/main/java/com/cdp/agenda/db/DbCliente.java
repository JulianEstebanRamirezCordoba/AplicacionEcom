package com.cdp.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cdp.agenda.entidades.CLiente;

import java.util.ArrayList;

public class DbCliente extends DbHelper {

    Context context;

    public DbCliente(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarcliente(String nombre, String apellido, String telefono, String cedula, String correo_electronico, int edad) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("cli_nombre", nombre);
            values.put("cli_apellido", apellido);
            values.put("cli_telefo", telefono);
            values.put("cli_cedula",cedula);
            values.put("cli_email", correo_electronico);
            values.put("cli_edad",edad);
            id = db.insert(TABLE_CLIENTE, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<CLiente> mostrarclientes() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<CLiente> listaclientes = new ArrayList<>();
        CLiente cliente;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT cli_id, cli_nombre, cli_apellido, cli_edad, cli_telefo, cli_email FROM " + TABLE_CLIENTE + " ORDER BY cli_nombre ASC", null);

        if (cursorCliente.moveToFirst()) {
            do {
                cliente = new CLiente();
                cliente.setId(cursorCliente.getInt(0));
                cliente.setNombre(cursorCliente.getString(1));
                cliente.setApellido(cursorCliente.getString(2));
                cliente.setEdad(cursorCliente.getInt(3));
                cliente.setTelefono(cursorCliente.getString(4));
                cliente.setCorreo_electornico(cursorCliente.getString(5));
                listaclientes.add(cliente);
            } while (cursorCliente.moveToNext());
        }

        cursorCliente.close();
        db.close();
        return listaclientes;
    }

     public CLiente mostrarEspecificado(int id){

         DbHelper dbHelper = new DbHelper(context);
         SQLiteDatabase db = dbHelper.getWritableDatabase();

         CLiente cliente = null;
         Cursor cursorCliente;

         cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE cli_id = " + id + " LIMIT 1", null);

         if (cursorCliente.moveToFirst()) {
                 cliente = new CLiente();
                 cliente.setId(cursorCliente.getInt(0));
                 cliente.setNombre(cursorCliente.getString(1));
                 cliente.setApellido(cursorCliente.getString(2));
                 cliente.setCorreo_electornico(cursorCliente.getString(6));
                 cliente.setTelefono(cursorCliente.getString(4));
                 cliente.setCedula(cursorCliente.getString(3));
                 cliente.setEdad(cursorCliente.getInt(5));
         }

         cursorCliente.close();
         return cliente;

     }

    //Metodo Editar
    public boolean EditarCliente(int id, String nombre, String apellido,String correo, String telefono, int edad){

        boolean result = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
             db.execSQL("UPDATE " + TABLE_CLIENTE + " SET cli_nombre='" + nombre + "', cli_apellido='" + apellido +
                     "', cli_edad='" + edad + "' , cli_telefo='" + telefono + "', cli_email='" + correo +
                     "' WHERE cli_id='" + id + "' ");

            result = true;
        } catch (Exception ex) {
            ex.toString();
           result = false;

        }finally {
            db.close();

        }
        return result;

    }

    //Metodo Desactivar
    public boolean EliminarCliente(int id){

        boolean result = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CLIENTE + " WHERE cli_id ='" + id + "'");

            result = true;
        } catch (Exception ex) {
            ex.toString();
            result = false;

        }finally {
            db.close();

        }
        return result;

    }

}
