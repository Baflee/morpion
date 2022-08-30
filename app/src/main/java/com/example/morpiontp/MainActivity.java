package com.example.morpiontp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Plateau & Cellules
    private int [] tv = {0,0,0,0,0,0,0,0,0};
    private ImageButton[] tb = new ImageButton[9];
    private boolean peutjouer = true;

    //Type de Jeu : 1 Joueur = Joueur vs hasard | 2 Joueurs = LAN
    private int joueur = 2;
    Random rand = new Random();


    //Score
    private int joueur1Score = 0;
    private int joueur2Score = 0;
    private int drawScore = 0;

    //Mouvement : Si c'est egale a 9 = draws
    private int mouvement = 0;

    // Variable gagnant + postion gagnante
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

    public void refJoueur(View view) {
        Button gamemodeplayer = (Button) view;
        if(joueur == 2) {
            gamemodeplayer.setText("1 Joueur");
            joueur = 1;

            if(!peutjouer) {
                Joueur2ChoisisCellule();
            }

        } else {
            gamemodeplayer.setText("2 Joueurs");
            joueur = 2;
        }
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
                TextView status = findViewById(R.id.player);
                tv[index] = 1;
                status.setText("Joueur 2");
                peutjouer = false;
                mouvement++;

                Joueur2ChoisisCellule();

            } else if (joueur == 2) {
                tb[index].setImageResource(R.drawable.cello);
                TextView status = findViewById(R.id.player);
                tv[index] = 2;
                status.setText("Joueur 1");
                peutjouer = true;
                mouvement++;
            }
        }
    };

    private void refGagne() {
        TextView matchState = findViewById(R.id.winlose);
        TextView score = findViewById(R.id.score);

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

        if(fini != true) {
            switch (gagne) {
                case -1:
                    matchState.setText("");
                    break;
                case 0:
                    matchState.setText("Match Nul");
                    drawScore++;
                    fini = true;
                    break;
                case 1:
                    matchState.setText("Joueur 1 Gagnant");
                    joueur1Score++;
                    fini = true;
                    break;
                case 2:
                    matchState.setText("Joueur 2 Gagnant");
                    joueur2Score++;
                    fini = true;
                    break;
            }
        }
        score.setText(" J1 - " + joueur1Score + "       Draw - " + drawScore + "       J2 - " + joueur2Score );
    };

    private void Joueur2ChoisisCellule() {
        TextView status = findViewById(R.id.player);
        if(joueur == 1 && mouvement < 8) {
            while(!peutjouer) {
                int randomIndex = rand.nextInt(8);
                if(tv[randomIndex] == 0) {
                    tb[randomIndex].setImageResource(R.drawable.cello);
                    tv[randomIndex] = 2;
                    status.setText("Joueur 1");
                    mouvement++;
                    peutjouer = true;
                }
            }
        }
    }

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

    public void resetScore(View view) {
        joueur1Score = 0;
        joueur2Score = 0;
        drawScore = 0;
        resetPlateau(view);
        refGagne();
    }

    public void choisirCellule(View view) {
        ImageButton button = (ImageButton) view;
        refPeutJouer(view);
        refGagne();
    }

}