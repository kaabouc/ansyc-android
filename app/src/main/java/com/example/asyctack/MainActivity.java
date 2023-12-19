package com.example.asyctack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity3_aquisition_de_capteur.class);
                startActivity(intent);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });
        progressBar = findViewById(R.id.progressBar);

        //Exécute la tâche asynchrone
        new MyAsyncTask().execute("Paramètre 1", "Paramètre 2", "Paramètre 3","Paramètre 4");
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Float> {

        // Cette méthode est exécutée avant le début du traitement en arrière-plan
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showToast("OnPreExecute");
        }

        // Cette méthode effectue le traitement en arrière-plan
        @Override
        protected Float doInBackground(String... params) {
            int totalParams = params.length;
            float progress = 0;

            for (int i = 0; i < totalParams; i++) {
                // Simulation du traitement pour chaque paramètre
                try {
                    Thread.sleep(i*1000); // Simule une attente de 1 seconde
                    Log.d("MyAsyncTask", "Paramètre traité : " + params[i]);
                 //   Toast.makeText(MainActivity.this,params[i],Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Mise à jour de la progression
                progress = ((float) (i + 1) / totalParams) * 100;
                publishProgress((int) progress);

                Log.d("MyAsyncTask", "Paramètre traité : " + params[i]);
            }

            // La valeur retournée ici sera transmise à onPostExecute
            return progress;
        }

        // Cette méthode est exécutée lorsqu'il y a un appel à publishProgress depuis doInBackground
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Mettre à jour la barre de progression (hypothétique)
            progressBar.setProgress(values[0]);
        }

        // Cette méthode est exécutée après la fin du traitement en arrière-plan
        @Override
        protected void onPostExecute(Float result) {
            super.onPostExecute(result);
            showToast("OnPostExecute - Progression finale : " + result + "%");
        }
    }

    // Méthode utilitaire pour afficher un toast
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}