package com.example.grupofacil;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DescripcionActivity extends AppCompatActivity {

    private EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        descriptionInput = findViewById(R.id.descriptionInput);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupDescription = descriptionInput.getText().toString();
                Intent intent = new Intent(DescripcionActivity.this, ParticipantesActivity.class);
                intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                intent.putExtra("groupDescription", groupDescription);
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
