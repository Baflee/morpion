package com.example.morpiontp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    private String [] tv = {"case","case","case","case","case","case","case","case","case"};
    private Button[] tb = new Button[9];
    private boolean peutjouer = true;
    private int joueur = 0;
    private boolean fini = false;

    private void refJoueur() {

    };
    private void refPeutJouer() {};

    private DatabaseReference refGagne() {
        return null;
    };

    private void refCases() {};

    public void click(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (tv[tappedImage] == "case") {

            if (peutjouer == true) {
                img.setImageResource(R.drawable.cellx);
                joueur = 2;
                TextView status = findViewById(R.id.player);

                status.setText("Joueur 1");
            } else {
                img.setImageResource(R.drawable.cello);
                joueur = 1;
                TextView status = findViewById(R.id.player);

                status.setText("Joueur 2");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}