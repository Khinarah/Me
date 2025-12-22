
class Snake {
    int[][]position; // tableau contenant la position des parties du spent de la tête à sa queue. La position de chaque partie du spent est un tableau de forme (ligne,colonne) en partant de l'indice 0
   
    public static void deplacement(Snake s, String rep){ // Fonction effectuant le déplacement du serpent
        int[] tete=s.position[0]; // tete: premier tableau de s représentant la position de la tête du serpent
        if (rep.equals("gauche")){ // Choix de déplacement à gauche
            gauche(s,tete);
        }else if (rep.equals("droite")){ // Choix de déplacement à droite
            droite(s,tete);
        }else if (rep.equals("haut")){ // Choix de déplacement en haut
            haut(s,tete);
        }else if (rep.equals("bas")){ // Choix de déplacement en bas
            bas(s,tete);
        }else{
            System.out.println("Réponse incorrect : "+rep+ ". Veuiller entrer une commande correct : haut | bas | gauche | droite .");
            //Arene.coupJoueur(s);
        }
    }

    public static void suppDernièrePosition(boolean[][] g, Snake s){
        int LASTX=s.position[s.position.length-1][0];
        int LASTY=s.position[s.position.length-1][1];
        System.out.println(LASTX);
        System.out.println(LASTY);
        g[LASTX][LASTY]=false;
    }


    public static void gauche(Snake s , int[] tete) { // Fonction de déplacement à gauche
        int[][] newpos=new int[s.position.length][s.position[0].length]; //newpos: tableau de la nouvelle position du serpent 
        int TEMP = tete[1] - 1; // Déplacement de la tête à gauche => colonne - 1 
        newpos[0][0] = s.position[0][0]; // Copie de l'ancienne position de la tête en x
        newpos[0][1] = TEMP; // Mise à jour de la position de la tête en y
        for (int i = 1; i < newpos.length; i++) {
            newpos[i][0] = s.position[i - 1][0]; // Copie de l'ancienne position x du segment précédent
            newpos[i][1] = s.position[i - 1][1]; // Copie de l'ancienne position y du segment précédent
        }
        s.position = newpos; // Mise à jour du serpent avec les nouvelles positions
    }

    public static void droite(Snake s, int[] tete){ // Fonction de déplacement à droite
        int[][] newpos=new int[s.position.length][s.position[0].length]; //newpos: tableau de la nouvelle position du serpent 
        int TEMP = tete[1] + 1; //Déplacement de la tête à droite => colonne + 1
        newpos[0][0]=s.position[0][0]; // Copie de l'ancienne position de la tête en x
        newpos[0][1]=TEMP; // Mise à jour de la position de la tête en y
        for (int i=1; i<newpos.length; i++){
            newpos[i][0] = s.position[i - 1][0]; // Copie de l'ancienne position x de la position précédente
            newpos[i][1] = s.position[i - 1][1]; // Copie de l'ancienne position y de la position précédente
        }
        s.position = newpos; // Mise à jour du spent avec les nouvelles positions
    }

    public static void haut(Snake s, int[] tete){ // Fonction de déplacement en haut
        int[][] newpos=new int[s.position.length][s.position[0].length];
        int TEMP = tete[0] - 1; //Déplacement de la tête en haut => i-1
        newpos[0][0]=TEMP; // Mise à jour de la position de la tête en x
        newpos[0][1]=s.position[0][1]; // Copie de l'ancienne position de la tête en y
        for (int i=1; i<newpos.length; i++){
            newpos[i][0] = s.position[i - 1][0]; // Copie de l'ancienne position x du position précédent
            newpos[i][1] = s.position[i - 1][1]; // Copie de l'ancienne position y du position précédent
        }
        s.position = newpos; // Mise à jour du spent avec les nouvelles positions
    }

    public static void bas(Snake s, int[] tete){ // Fonction de déplacement en bas
        int[][] newpos=new int[s.position.length][s.position[0].length]; //newpos: tableau de la nouvelle position du serpent 
        int TEMP = tete[0] + 1; //Déplacement de la tête en bas => ligne + 1
        newpos[0][0]=TEMP; // Mise à jour de la position de la tête en x
        newpos[0][1]=s.position[0][1]; // Copie de l'ancienne position de la tête en y
        for (int i=1; i<newpos.length; i++){
            newpos[i][0] = s.position[i - 1][0]; // Copie de l'ancienne position x du segment précédent
            newpos[i][1] = s.position[i - 1][1]; // Copie de l'ancienne position y du segment précédent
        }
        s.position = newpos ; // Mise à jour du spent avec les nouvelles positions
    }

    public static void afficher(Snake s){ // Afficher le tableau de la position du spent
        for (int i=0; i<s.position.length; i++){
            System.out.print(s.position[i][0]+","+s.position[i][1]);
            System.out.println();
        }
    }

    public static void testGauche(Snake s,int[] tete){
        Snake.gauche(s, tete);
    }

    public static void testDroite(Snake s,int[] tete){
        Snake.droite(s,tete);
    }

    public static void testHaut(Snake s, int[] tete){
        Snake.haut(s,tete);
    }

    public static void testBas(Snake s, int[] tete){
        Snake.bas(s,tete);
    }

    

    public static void main(String[] args) {
        Snake s1=new Snake(); //s1: serpent du joueur 1
        s1.position=new int[][]{{2,1},{3,1},{4,1},{5,1},{6,1},{7,1},{8,1},{9,1}};
        Snake s2=new Snake(); //s2: serpent du joueur 2
        s2.position=new int[][]{{2,5},{2,6},{2,7},{3,7},{3,8},{2,8},{2,9},{1,9}};

        //String rep=Joueur.commandeJoueur();
        //Snake.deplacement(s2, rep);
        
        //testGauche(s2, s2.position[0]);
        //Snake.deplacement(s2,"gauche"); // s2: (2,4) (2,5) (2,6) (2,7) (3,7) (3,8) (2,8) (2,9)
    
        //testDroite(s1,s1.position[0]);
        //Snake.deplacement(s1,"droite"); // s1: (2,2) (2,1) (3,1) (4,1) (5,1) (6,1) (7,1) (8,1)
        
        //testHaut(s2, s2.position[0]);
        //Snake.deplacement(s2, "haut"); // s2: (1,5) (2,5) (2,6) (2,7) (3,7) (3,8) (2,8) (2,9)

        //testBas(s2, s2.position[0]);
        //Snake.deplacement(s2,"bas"); // s2: (3,5) (2,5) (2,6) (2,7) (3,7) (3,8) (2,8) (2,9)

        afficher(s2);
    }
}

