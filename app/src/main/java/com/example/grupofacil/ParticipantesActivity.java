package com.example.grupofacil;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ParticipantesActivity extends AppCompatActivity {

    private EditText participantInput;
    private LinearLayout participantListLayout;
    private ArrayList<String> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        participantInput = findViewById(R.id.participantInput);
        participantListLayout = findViewById(R.id.participantListLayout);
        Button addParticipantButton = findViewById(R.id.addParticipantButton);
        Button nextButton = findViewById(R.id.nextButton);
        participants = new ArrayList<>();

        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String participant = participantInput.getText().toString().trim();
                if (!participant.isEmpty()) {
                    participants.add(participant);
                    addParticipantToList(participant);
                    participantInput.setText("");
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParticipantesActivity.this, ConfiguracionActivity.class);
                intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                intent.putExtra("groupDescription", getIntent().getStringExtra("groupDescription"));
                intent.putStringArrayListExtra("groupParticipants", participants);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void addParticipantToList(String participant) {
        TextView textView = new TextView(this);
        textView.setText(participant);
        participantListLayout.addView(textView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
