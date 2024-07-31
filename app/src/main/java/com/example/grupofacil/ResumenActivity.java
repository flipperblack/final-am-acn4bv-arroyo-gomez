package com.example.grupofacil;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;


public class ResumenActivity extends AppCompatActivity {


    private String groupName;
    private String groupDescription;
    private ArrayList<String> groupParticipants;
    private int participantsPerGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);


        groupName = getIntent().getStringExtra("groupName");
        groupDescription = getIntent().getStringExtra("groupDescription");
        groupParticipants = getIntent().getStringArrayListExtra("groupParticipants");
        participantsPerGroup = getIntent().getIntExtra("participantsPerGroup", 1);


        TextView resumenTitle = findViewById(R.id.resumenTitle);
        resumenTitle.setText("Nombre de Grupo: " + groupName);


        TextView resumenDescription = findViewById(R.id.resumenDescription);
        resumenDescription.setText("Descripción: " + groupDescription);


        ScrollView resumenScrollView = findViewById(R.id.resumenScrollView);
        TextView resumenGrupos = findViewById(R.id.resumenGrupos);


        Collections.shuffle(groupParticipants);
        StringBuilder resumenTexto = new StringBuilder();


        int grupoNumero = 1;
        for (int i = 0; i < groupParticipants.size(); i++) {
            if (i % participantsPerGroup == 0) {
                resumenTexto.append("\n").append("Grupo ").append(grupoNumero).append(":\n");
                grupoNumero++;
            }
            resumenTexto.append(groupParticipants.get(i)).append("\n");
        }


        resumenGrupos.setText(resumenTexto.toString());


        Button finalizarButton = findViewById(R.id.finalizarButton);
        Button backButton = findViewById(R.id.backButton);


        finalizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("groupName", groupName);
                data.putExtra("groupDescription", groupDescription);
                data.putStringArrayListExtra("groupParticipants", groupParticipants);
                data.putExtra("participantsPerGroup", participantsPerGroup);
                setResult(RESULT_OK, data);
                finish();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

