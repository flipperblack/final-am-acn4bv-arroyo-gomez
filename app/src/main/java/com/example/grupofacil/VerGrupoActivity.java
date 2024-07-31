package com.example.grupofacil;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class VerGrupoActivity extends AppCompatActivity {

    private String groupName;
    private String groupDescription;
    private ArrayList<String> groupParticipants;
    private int participantsPerGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_grupo);

        groupName = getIntent().getStringExtra("groupName");
        groupDescription = getIntent().getStringExtra("groupDescription");
        groupParticipants = getIntent().getStringArrayListExtra("groupParticipants");
        participantsPerGroup = getIntent().getIntExtra("participantsPerGroup", 1);

        TextView verGrupoTitle = findViewById(R.id.verGrupoTitle);
        verGrupoTitle.setText("Grupo: " + groupName);

        TextView verGrupoDescription = findViewById(R.id.verGrupoDescription);
        verGrupoDescription.setText("Descripción: " + groupDescription);

        ScrollView verGrupoScrollView = findViewById(R.id.verGrupoScrollView);
        TextView verGrupoParticipants = findViewById(R.id.verGrupoParticipants);

        StringBuilder participantsText = new StringBuilder();

        int grupoNumero = 1;
        for (int i = 0; i < groupParticipants.size(); i++) {
            if (i % participantsPerGroup == 0) {
                participantsText.append("\n").append("Grupo ").append(grupoNumero).append(":\n");
                grupoNumero++;
            }
            participantsText.append(groupParticipants.get(i)).append("\n");
        }

        verGrupoParticipants.setText(participantsText.toString());



        Button eliminarButton = findViewById(R.id.eliminarButton);
        Button backButton = findViewById(R.id.backButton);

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("groupName", groupName);
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
