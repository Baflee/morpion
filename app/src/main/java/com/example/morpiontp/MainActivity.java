package com.example.morpiontp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int [] tv = {0,0,0,0,0,0,0,0,0};
    private ImageButton[] tb = new ImageButton[9];
    private boolean peutjouer = true;
    private int joueur = 1;
    private int mouvement = 0;
    private boolean fini = false;
    private int gagne = -1;
    int[][] tvWins = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refCases();
    }

    private void refCases() {
        tb[0] = (ImageButton)findViewById(R.id.topleft);
        tb[1] = (ImageButton)findViewById(R.id.topcenter);
        tb[2] = (ImageButton)findViewById(R.id.topright);
        tb[3] = (ImageButton)findViewById(R.id.middleleft);
        tb[4] = (ImageButton)findViewById(R.id.middlecenter);
        tb[5] = (ImageButton)findViewById(R.id.middleright);
        tb[6] = (ImageButton)findViewById(R.id.bottomleft);
        tb[7] = (ImageButton)findViewById(R.id.bottomcenter);
        tb[8] = (ImageButton)findViewById(R.id.bottomright);
    }

    private void refPeutJouer(View view) {
        ImageButton button = (ImageButton) view;


        int index = 0;
        for (int i = 0; i < tb.length; i++)
        {
            if (tb[i].getId() == button.getId())
            {
                index = i;
                break;
            }
        }

        if (tv[index] == 0 && fini == false) {

            if (peutjouer) {
                tb[index].setImageResource(R.drawable.cellx);
                joueur = 1;
                TextView status = findViewById(R.id.player);
                tv[index] = 1;
                status.setText("Joueur 1");
                peutjouer = false;
            } else {
                tb[index].setImageResource(R.drawable.cello);
                joueur = 2;
                TextView status = findViewById(R.id.player);
                tv[index] = 2;
                status.setText("Joueur 2");
                peutjouer = true;
            }
            mouvement++;
        }
    };

    private void refGagne() {
        TextView matchState = findViewById(R.id.winlose);

        for(int[] tvWin : tvWins) {
            if (tv[tvWin[0]] == tv[tvWin[1]] &&
                tv[tvWin[1]] == tv[tvWin[2]] &&
                tv[tvWin[0]] != 0) {

                if (tv[tvWin[0]] == 1) {
                    gagne = 1;
                } else if(tv[tvWin[0]] == 2) {
                    gagne = 2;
                }
            }

            if(mouvement == 9 && gagne == -1) {
                gagne = 0;
            }
        }

        switch(gagne) {
            case -1:
                matchState.setText("");
                break;
            case 0:
                matchState.setText("Match Nul");
                fini = true;
                break;
            case 1:
                matchState.setText("Joueur 1 Gagnant");
                fini = true;
                break;
            case 2:
                matchState.setText("Joueur 2 Gagnant");
                fini = true;
                break;
        }
    };


    public void resetPlateau(View view) {
        for (int i = 0; i < tv.length; i++) {
            tv[i] = 0;
            tb[i].setImageResource(R.drawable.cell);
        }
        fini = false;
        peutjouer = true;
        gagne = -1;
        mouvement = 0;
        refGagne();
    }


    public void choisirCellule(View view) {
        ImageButton button = (ImageButton) view;
        refPeutJouer(view);
        refGagne();
    }

}