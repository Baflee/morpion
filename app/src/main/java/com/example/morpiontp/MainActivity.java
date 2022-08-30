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
    Random rand = new Random();

    /* Plateau & Cases :
     * 0 : case vide
     * 1 : case croix
     * 2 : case rond
     */
    private int [] tv = {0,0,0,0,0,0,0,0,0};


    private ImageButton[] tb = new ImageButton[9];

    /*
     * Tour (variable peutjouer) :
     * true : Tour du joueur 1
     * false : Tour du joueur 2
     */
    private boolean peutjouer = true;

    /*
    * Type de Jeu :
    * 1 : Joueur vs Bot (random number choose)
    * 2 : Joueur vs Joueur (on the same phone)
     */
    private int joueur = 2;

    //Variable de Scores
    private int joueur1Score = 0;
    private int joueur2Score = 0;
    private int drawScore = 0;

    //Mouvement : 9 mouvements sans position gagnante = Match Nul
    private int mouvement = 0;

    /*
     * Fini (variable fini) :
     * false : Partie en cours
     * true :  Partie terminée
     *
     * Gagnant (variable gagne) :
     * -1 : Partie en Cours
     * 0 : Match Nul
     * 1 : Joueur 1 Gagnant
     * 2 : Joueur 2 Gagnant
     *
     * Position Gagnante (variable tvWins) :
     * Contient toutes les positions permettant de gagner une partie
     */
    private boolean fini = false;
    private int gagne = -1;
    int[][] tvWins = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.tictactoeicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        TextView score = findViewById(R.id.score);
        score.setText(" J1 - " + 0 + "       Draw - " + 0 + "       J2 - " + 0 );
        refCases();
    }

    //Attribution des boutons selon leur position
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

    // Fonction permettant de changer le type de jeu
    public void refJoueur(View view) {
        Button gamemode = (Button) view;
        if(joueur == 2) {
            gamemode.setText("1 Joueur");
            joueur = 1;

            if(!peutjouer && !fini) {
                Joueur2ChoisisCellule();
            }

        } else {
            gamemode.setText("2 Joueurs");
            joueur = 2;
        }
    }

    // Fonction permettant de jouer et de changer de tour
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

        if (tv[index] == 0 && !fini) {

            if (peutjouer) {
                tb[index].setImageResource(R.drawable.cellx);
                TextView status = findViewById(R.id.player);
                tv[index] = 1;
                status.setText("Joueur 2");
                peutjouer = false;
                mouvement++;

                refGagne();

                if(!fini) {
                    Joueur2ChoisisCellule();
                }

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

    // Fonction vérifiant l'état du jeu selon le nombre de mouvement, positions gagnantes
    private void refGagne() {
        TextView matchState = findViewById(R.id.winlose);
        TextView score = findViewById(R.id.score);

        for(int[] tvWin : tvWins) {
            if (tv[tvWin[1]] == tv[tvWin[2]] && tv[tvWin[0]] == tv[tvWin[1]] && tv[tvWin[0]] != 0) {

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

        if(!fini) {
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

    //Fonction permettant un choix aleatoire
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

    //Fonction permettant de réinitialiser le jeu
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

    //Fonction permettant de réinitialiser le score
    public void resetScore(View view) {
        joueur1Score = 0;
        joueur2Score = 0;
        drawScore = 0;
        resetPlateau(view);
        refGagne();
    }

    //Fonction permettant de détecter la cellule du plateau actionné
    public void choisirCellule(View view) {
        refPeutJouer(view);
        refGagne();
    }

}