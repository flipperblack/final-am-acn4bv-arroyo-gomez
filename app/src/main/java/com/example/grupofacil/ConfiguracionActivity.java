package com.example.grupofacil;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ConfiguracionActivity extends AppCompatActivity {


    private EditText participantsPerGroupInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);


        participantsPerGroupInput = findViewById(R.id.participantsPerGroupInput);
        Button nextButton = findViewById(R.id.nextButton);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int participantsPerGroup = Integer.parseInt(participantsPerGroupInput.getText().toString());
                Intent intent = new Intent(ConfiguracionActivity.this, ResumenActivity.class);
                intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                intent.putExtra("groupDescription", getIntent().getStringExtra("groupDescription"));
                intent.putStringArrayListExtra("groupParticipants", getIntent().getStringArrayListExtra("groupParticipants"));
                intent.putExtra("participantsPerGroup", participantsPerGroup);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}

