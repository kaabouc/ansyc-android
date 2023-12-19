package com.example.asyctack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private String[] texts = {"Hi ! ", "Welcome ", "To application"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressBar = findViewById(R.id.progressBar2);
        textView = findViewById(R.id.textView);

        // Exécute la tâche asynchrone
        new DisplayTextTask().execute(texts);
    }

    private class DisplayTextTask extends AsyncTask<String, String, Void> {

        // Cette méthode est exécutée avant le début du traitement en arrière-plan
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showToast("Début de l'affichage progressif");
        }

        // Cette méthode effectue le traitement en arrière-plan
        @Override
        protected Void doInBackground(String... params) {
            for (String text : params) {
                // Simulation du traitement pour chaque texte
                try {
                    Thread.sleep(1000); // Simule une attente de 1 seconde
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Publie le texte actuel pour être mis à jour dans onProgressUpdate
                publishProgress(text);
            }

            return null;
        }

        // Cette méthode est exécutée lorsqu'il y a un appel à publishProgress depuis doInBackground
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            // Mettre à jour le TextView avec le texte actuel
            textView.setText(values[0]);

            // Mettre à jour la barre de progression (hypothétique)
            int progress = ((textView.length() * 100) / texts.length);
            progressBar.setProgress(progress);
        }

        // Cette méthode est exécutée après la fin du traitement en arrière-plan
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            showToast("Fin de l'affichage progressif");
        }
    }

    // Méthode utilitaire pour afficher un toast
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
