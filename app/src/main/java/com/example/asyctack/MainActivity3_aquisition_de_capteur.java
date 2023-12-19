package com.example.asyctack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3_aquisition_de_capteur extends AppCompatActivity implements SensorEventListener {

            private SensorManager sensorManager;
            private Sensor lightSensor;
            private TextView sensorValueTextView;

            private Handler handler = new Handler();
            private final int SENSOR_READ_INTERVAL = 10000; // 10 seconds

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main_activity3_aquisition_de_capteur);

                sensorValueTextView = findViewById(R.id.sensorValueTextView);

                // Initialise le SensorManager et récupère le capteur de luminosité
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

                if (lightSensor == null) {
                    Toast.makeText(this, "Le capteur de luminosité n'est pas disponible sur cet appareil.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onResume() {
                super.onResume();

                // Enregistre le listener du capteur lorsque l'activité est en cours d'exécution
                if (lightSensor != null) {
                    sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    startSensorReadings();
                }
            }

            @Override
            protected void onPause() {
                super.onPause();

                // Désenregistre le listener du capteur lorsque l'activité est en pause
                if (lightSensor != null) {
                    sensorManager.unregisterListener(MainActivity3_aquisition_de_capteur.this);
                    stopSensorReadings();
                }
            }

            // Démarrer les lectures périodiques du capteur
            private void startSensorReadings() {
                handler.postDelayed(sensorReadRunnable, SENSOR_READ_INTERVAL);
            }

            // Arrêter les lectures périodiques du capteur
            private void stopSensorReadings() {
                handler.removeCallbacks(sensorReadRunnable);
            }

            // Exécute périodiquement la lecture du capteur
            private Runnable sensorReadRunnable = new Runnable() {
                @Override
                public void run() {
                    // Lire les valeurs du capteur ici
                    float lightValue = getCurrentLightValue();

                    // Afficher la valeur du capteur
                    sensorValueTextView.setText("Valeur du capteur de luminosité : " + lightValue);

                    // Programmer la prochaine lecture après 10 secondes
                    handler.postDelayed(this, SENSOR_READ_INTERVAL);
                }
            };

            // Méthode pour obtenir la valeur actuelle du capteur de luminosité
            private float getCurrentLightValue() {
                // Cette méthode doit être adaptée en fonction du capteur spécifique que vous utilisez
                // Ici, nous supposons que vous utilisez le capteur de luminosité
                // Vous pouvez également adapter cette méthode pour d'autres types de capteurs

                // La valeur simulée est utilisée ici, vous devez remplacer cela par la vraie lecture du capteur
                return (float) Math.random() * 100; // Valeur simulée entre 0 et 100
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                // Si vous utilisez un listener de capteur en temps réel, cette méthode sera appelée chaque fois que les données du capteur changent
                // Vous pouvez traiter les nouvelles données du capteur ici si nécessaire
            }



    @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Cette méthode est appelée lorsque la précision du capteur change
                // Vous pouvez gérer cela en fonction de vos besoins
            }
        }