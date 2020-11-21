package gautero.parcialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ToggleButton tb;
    EditText et;
    Button bt, btver;
    ListView lv;
    private ArrayAdapter<String> recomendationArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = findViewById(R.id.toggleButtonRecomienda);
        CharSequence t3 = "No se Recomienda";
        tb.setTextOff(t3);

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tb.isChecked()){
                    CharSequence t1 = "Se Recomienda";
                    tb.setTextOn(t1);
                }else{
                    CharSequence t2 = "No se Recomienda";
                    tb.setTextOff(t2);
                }
            }
        });

        et = findViewById(R.id.editTextSeriePelicula);
        bt = findViewById(R.id.buttonGuardar);

        if(!et.getText().toString().isEmpty()){
            bt.setEnabled(true);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("titulo", et.getText().toString());
                if(tb.isChecked()){
                    values.put("seRecomienda", "Recomendada");
                }else{
                    values.put("seRecomienda", "No Recomendada");
                }


                db.insert("Recomendations", null, values);

                et.setText("");
                Toast.makeText(MainActivity.this, "Reco Guardada", Toast.LENGTH_SHORT).show();

            }
        });

        btver = findViewById(R.id.buttonVerRecos);
        lv = findViewById(R.id.listaRecos);

        btver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setVisibility(View.VISIBLE);

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);

                Cursor cursor = dbHelper.alldata();
                List<String> listRecos = new ArrayList<>();

                while(cursor.moveToNext()){
                    String recomendationString;
                    recomendationString = "  " + cursor.getString(2) + ": " + cursor.getString(1);
                    listRecos.add(recomendationString);
                }

                recomendationArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listRecos);

                lv.setAdapter(recomendationArrayAdapter);



            }
        });


    }
}