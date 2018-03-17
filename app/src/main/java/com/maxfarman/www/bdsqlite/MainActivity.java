package com.maxfarman.www.bdsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //definimimos nuestras variables

    EditText t_id,t_nombre,t_dni,t_telefono;
    Button  b_select,b_insert,b_update,b_delete,b_primero,b_ultimo,b_anterior,b_siguiente,b_limpiar;
    SQLiteDatabase base;
    Cursor registros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //relacionamos
        t_id=findViewById(R.id.editText);
        t_nombre=findViewById(R.id.editText2);
        t_dni=findViewById(R.id.editText3);
        t_telefono=findViewById(R.id.editText4);
        b_select=findViewById(R.id.button);
        b_insert=findViewById(R.id.button2);
        b_update=findViewById(R.id.button3);
        b_delete=findViewById(R.id.button4);
        b_primero=findViewById(R.id.button5);
        b_ultimo=findViewById(R.id.button6);
        b_anterior=findViewById(R.id.button7);
        b_siguiente=findViewById(R.id.button8);
        b_limpiar=findViewById(R.id.button9);

        b_insert.setOnClickListener(this);
        b_select.setOnClickListener(this);

        b_update.setOnClickListener(this);
        b_delete.setOnClickListener(this);

        b_primero.setOnClickListener(this);
        b_ultimo.setOnClickListener(this);

        b_siguiente.setOnClickListener(this);
        b_anterior.setOnClickListener(this);

        b_limpiar.setOnClickListener(this);

          AsistenteSQLite asistente= new AsistenteSQLite(getApplicationContext(),"escuela",null,1);
          base=asistente.getWritableDatabase();
          registros = base.rawQuery("SELECT * FROM alumnos", null);

    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button:
                String id_input=t_id.getText().toString();
                if(id_input.equals(""))
                {
                    Context c =getApplicationContext();
                    String m ="El id está vacio";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                }
                else
                {
                    registros = base.rawQuery("SELECT * FROM alumnos", null);
                    boolean sw=true;
                    Cursor registroVerificacion=base.rawQuery("SELECT * FROM alumnos WHERE id=" + id_input,null);
                    if (registroVerificacion.moveToFirst())
                    {
                        while (sw)
                        {
                            if (registros.moveToNext())
                            {
                                    if(registros.getString(0).equals(id_input))
                                    {
                                        sw=false;
                                    }
                            }
                        }
                        t_nombre.setText(registros.getString(1));
                        t_dni.setText(registros.getString(2));
                        t_telefono.setText(registros.getString(3));
                    }
                        else
                        {
                            Context c=getApplicationContext();
                             String m="El id no existe";
                            Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                             LimpiarTextos();

                         }



                }
                break;
            case R.id.button2:
                String nombre_input=t_nombre.getText().toString();
                String dni_input=t_dni.getText().toString();
                String telefono_input=t_telefono.getText().toString();
                if(nombre_input.equals("") || dni_input.equals("") || telefono_input.equals("") )
                {
                    Context c = getApplicationContext();
                    String m= "no puedes ingresar datos en blanco";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                }
                else
                {
                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("nombre",nombre_input);
                    nuevoRegistro.put("dni",dni_input);
                    nuevoRegistro.put("telefono",telefono_input);
                    base.insert("alumnos",null,nuevoRegistro);
                    registros=base.rawQuery("SELECT * FROM alumnos",null);
                    Context c =getApplicationContext();
                    String m= "Los datos se ingresaron correctamente.";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                    LimpiarTextos();
                }
                break;
            case R.id.button3:
                String id_input2 = t_id.getText().toString();
                String nombre_input2=t_nombre.getText().toString();
                String dni_input2=t_dni.getText().toString();
                String telefono_input2=t_telefono.toString();
                if (id_input2.equals("")|| nombre_input2.equals("") || dni_input2.equals("") || telefono_input2.equals(""))
                {
                    Context c= getApplicationContext();
                    String m= "Los campos no pueden estar en blanco";
                    Toast.makeText(c,m, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Cursor registroVerificacion =base.rawQuery("SELECT * FROM alumnos WHERE id="+ id_input2,null);
                    if(registroVerificacion.moveToFirst())
                    {
                        ContentValues nuevoRegistro = new ContentValues();
                        nuevoRegistro.put("nombre",nombre_input2);
                        nuevoRegistro.put("dni",dni_input2);
                        nuevoRegistro.put("telefono",telefono_input2);
                        base.update("alumnos", nuevoRegistro, "id=" + id_input2,null);
                        Context c = getApplicationContext();
                        String m="Se actualizó el registro correctamente.";

                        Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                        LimpiarTextos();
                    }
                    else
                    {
                        Context c= getApplicationContext();
                        String m =" El id que intenta modificar no existe.";
                        Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.button4:
                String id_input3 = t_id.getText().toString();
                if (id_input3.equals(""))
                    {
                        Context c= getApplicationContext();
                        String m=" El id que intenta eliminar está en blanco";
                        Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                     Cursor registroVerificacion=base.rawQuery("SELECT * FROM alumnos WHERE id="+ id_input3, null);
                        if(registroVerificacion.moveToFirst())
                        {
                            base.delete("alumnos", "id=" + id_input3,null);
                            registros=base.rawQuery("SELECT * FROM alumnos",null);
                            Context c=getApplicationContext();
                            String m="El registro se eliminó correctamente.";
                            Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                            LimpiarTextos();
                        }

                     }

                break;
            case R.id.button5:
                registros = base.rawQuery("SELECT * FROM alumnos",null);
                if(registros.moveToFirst())
                {
                    t_id.setText(registros.getString(0));
                    t_nombre.setText(registros.getString(1));
                    t_dni.setText(registros.getString(2));
                    t_telefono.setText(registros.getString(3));
                }
                else
                {
                    Context c = getApplicationContext();
                    String m = "No hay registros existentes.";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.button6:
                registros = base.rawQuery("SELECT * FROM alumnos",null);
                if(registros.moveToLast())
                {
                    t_id.setText(registros.getString(0));
                    t_nombre.setText(registros.getString(1));
                    t_dni.setText(registros.getString(2));
                    t_telefono.setText(registros.getString(3));
                }
                else
                {
                    Context c = getApplicationContext();
                    String m = "No hay registros existentes.";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.button7:
                if(registros.moveToPrevious())
                {
                    t_id.setText(registros.getString(0));
                    t_nombre.setText(registros.getString(1));
                    t_dni.setText(registros.getString(2));
                    t_telefono.setText(registros.getString(3));
                }
                else
                {
                    Context c = getApplicationContext();
                    String m = "Ya no hay registros anteriores.";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.button8:
                if(registros.moveToNext())
                {
                    t_id.setText(registros.getString(0));
                    t_nombre.setText(registros.getString(1));
                    t_dni.setText(registros.getString(2));
                    t_telefono.setText(registros.getString(3));
                }
                else
                {
                    Context c = getApplicationContext();
                    String m = "Ya no hay registros siguientes.";
                    Toast.makeText(c,m,Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button9:
                LimpiarTextos();
                break;

        }

    }

    private  void LimpiarTextos(){
        t_id.setText("");
        t_nombre.setText("");
        t_dni.setText("");
        t_telefono.setText("");
    }
}
