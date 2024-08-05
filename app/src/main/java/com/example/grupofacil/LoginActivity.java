package com.example.grupofacil;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Conectando a la db
        FirebaseApp.initializeApp(this);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton); //logear
        Button registerButton = findViewById(R.id.registerButton); //registrar
        Button skipButton = findViewById(R.id.skipButton); //saltear


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Autentificacion
                String email = usernameInput.getText().toString();
                String pass = passwordInput.getText().toString();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    // Llamo al metodo de validadcion
                    validarUsuario(email, pass);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Con esto llamo al dialog_register (Pop up)
                mostrarPopupRegistro();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para testing y por las dudas dejamos este botón que en producción no seria viable dejarlo
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Finaliza la activiti para que no se pueda volver
            }
        });
    }

    private void validarUsuario(String email, String pass) { //Este metodo valida al usuario en la db
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        //Consultamos si el email y pass coinciden dentro de la db
        usersRef.whereEqualTo("email", email)
                .whereEqualTo("pass", pass)
                .get()

                //Esperamos que se complete la consulta ocn onCompleteLitener
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) { //No permitimos levantar nulls
                        if (task.isSuccessful()) {
                            boolean usuarioEncontrado = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Boolean isActive = document.getBoolean("is_active");
                                Log.d(TAG, "Usuario encontrado: " + document.getId() + ", activo: " + isActive);

                                if (isActive != null && isActive) {
                                    usuarioEncontrado = true;
                                    break;
                                }
                            }

                            if (usuarioEncontrado) {
                                Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos, o usuario no activo.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(TAG, "Error en la consulta de Firestore", task.getException());
                            Toast.makeText(LoginActivity.this, "Error en la autenticación", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Traemos el popup en pantalla para el registro
    private void mostrarPopupRegistro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_register, null);
        builder.setView(dialogView);

        final EditText nameInput = dialogView.findViewById(R.id.nameInput);
        final EditText emailInput = dialogView.findViewById(R.id.emailInput);
        final EditText passInput = dialogView.findViewById(R.id.passInput);
        final EditText confirmPassInput = dialogView.findViewById(R.id.confirmPassInput);
        Button registerConfirmButton = dialogView.findViewById(R.id.registerConfirmButton);

        AlertDialog alertDialog = builder.create();

        registerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String pass = passInput.getText().toString();
                String confirmPass = confirmPassInput.getText().toString();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginActivity.this, "Por favor, ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(confirmPass)) {
                    Toast.makeText(LoginActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else {
                    // Registrar nuevo usuario
                    registrarUsuario(name, email, pass);
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    //Metodo para grabar en la db el usuario nuevo
    private void registrarUsuario(String name, String email, String pass) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        // Datos del nuevo usuario
        Map<String, Object> nuevoUsuario = new HashMap<>();
        nuevoUsuario.put("email", email);
        nuevoUsuario.put("pass", pass);
        nuevoUsuario.put("name", name);
        nuevoUsuario.put("is_active", true);

        usersRef.add(nuevoUsuario)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) { //Esperamos que la db resuelva con exito el grabado
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Error al registrar el usuario", task.getException());
                            Toast.makeText(LoginActivity.this, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
