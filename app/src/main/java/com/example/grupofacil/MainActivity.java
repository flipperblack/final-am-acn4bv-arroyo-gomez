package com.example.grupofacil;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "GrupoFacilPrefs";
    private static final String GROUPS_KEY = "groups";


    private LinearLayout linearLayout;
    private ArrayList<Grupo> grupos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Vinculo vistas
        ScrollView scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.linearLayout);


        // Recuperar grupos de SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String groupsJson = prefs.getString(GROUPS_KEY, "");
        if (!groupsJson.isEmpty()) {
            Type type = new TypeToken<ArrayList<Grupo>>() {}.getType();
            grupos = new Gson().fromJson(groupsJson, type);
        } else {
            grupos = new ArrayList<>();
        }


        // Mostrar grupos guardados
        if (grupos != null) {
            Log.d(TAG, "Mostrando grupos guardados: " + grupos.toString());
            for (Grupo grupo : grupos) {
                addGroupButton(grupo);
            }
        }


        // Botón para iniciar el proceso de creación del grupo
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NombreActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    private void addGroupButton(Grupo grupo) {
        Log.d(TAG, "Agregando botón para grupo: " + grupo.getNombre());
        Button newButton = new Button(getApplicationContext());
        newButton.setText(grupo.getNombre());
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.poppins);
        newButton.setTypeface(typeface);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        newButton.setBackgroundResource(R.drawable.button_background);
        layoutParams.setMargins(0, 0, 5, 16);
        newButton.setLayoutParams(layoutParams);
        newButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);


        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VerGrupoActivity.class);
                intent.putExtra("groupName", grupo.getNombre());
                intent.putExtra("groupDescription", grupo.getDescripcion());
                intent.putStringArrayListExtra("groupParticipants", grupo.getParticipantes());
                intent.putExtra("participantsPerGroup", grupo.getParticipantesPorGrupo());
                startActivityForResult(intent, 2);
            }
        });


        linearLayout.addView(newButton);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String groupName = data.getStringExtra("groupName");
            String groupDescription = data.getStringExtra("groupDescription");
            ArrayList<String> groupParticipants = data.getStringArrayListExtra("groupParticipants");
            int participantsPerGroup = data.getIntExtra("participantsPerGroup", 1);


            if (groupName != null && !groupName.isEmpty() && groupParticipants != null) {
                Grupo nuevoGrupo = new Grupo(groupName, groupDescription, groupParticipants, participantsPerGroup);
                grupos.add(nuevoGrupo);
                addGroupButton(nuevoGrupo);
                saveGroupsToPreferences();
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String groupName = data.getStringExtra("groupName");
            if (groupName != null) {
                Log.d(TAG, "Grupo eliminado: " + groupName);
                for (int i = 0; i < grupos.size(); i++) {
                    if (grupos.get(i).getNombre().equals(groupName)) {
                        grupos.remove(i);
                        break;
                    }
                }
                saveGroupsToPreferences();
                recreate();  // Recargar la actividad para actualizar la lista
            }
        }
    }


    private void saveGroupsToPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String groupsJson = new Gson().toJson(grupos);
        editor.putString(GROUPS_KEY, groupsJson);
        editor.apply();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Guardando estado: " + grupos.toString());
        outState.putString("groups", new Gson().toJson(grupos));
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String groupsJson = savedInstanceState.getString("groups");
        if (groupsJson != null) {
            Type type = new TypeToken<ArrayList<Grupo>>() {}.getType();
            grupos = new Gson().fromJson(groupsJson, type);
        }
    }
}

