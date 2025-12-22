import javax.swing.JOptionPane;

class Arene {
    boolean[][] grille=new boolean[10][10]; //L'arène est représenté sous forme d'un tableau à 2 dimensions
    int NTOUR = TourAugment();
    int TOURACTUELLE = 0;
    
    
    public void casesSerpent(Snake s){ // Fonction plaçant les serpents selon leur position dans la grille
        for (int i=0; i<grille.length; i++){
            for (int j=0; j<grille[0].length; j++){
                int X=i; // X stocke l'abscisse de la grille
                int Y=j; // Y stocke l'ordonnée de la grille
                for (int k=0; k<s.position.length ; k++){ //Boucle parcourant les positions du serpent 
                        if ((X==s.position[k][0]) && (Y==s.position[k][1])){ // Vérifier si telle coordonnée apparait dans les positions du serpent.
                            grille[i][j]=true;
                        }
                }
            }
        }
    }

    
    public void afficherGrille() { // Affiche la grille
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                if (grille[i][j]==true) {
                    System.out.print("o "); // S pour une case occupée par le serpent
                } else {
                    System.out.print(". "); // . pour une case vide
                }
            }
            System.out.println();
        }
    }

    public static void allongeOuPas(Arene a,Snake s, String r){
        if (a.TOURACTUELLE%a.NTOUR!=0){
            Snake.suppDernièrePosition(a.grille, s);
        }
        Snake.deplacement(s, r);
        }

    public void tourSuivant(){
        TOURACTUELLE++;
        System.out.println("Tour : "+TOURACTUELLE);
    }
    

    public static int TourAugment(){ //Fonction demandant le nombre de tour avant que le serpent augmente sa taille
        String t=JOptionPane.showInputDialog("Tous les N tours, le serpent augmente : N = ");
        int tour=Integer.parseInt(t);
        return tour;
    }

    
    public static String coupJoueur(boolean[][] g,Snake s){ //Fonction demandant la commande du joueur puis effectue le déplacement en conséquence.
        String rep=Joueur.commandeJoueur();
        return rep;
    }

    public static void main(String[] args){

        Arene g1=new Arene();
        System.out.println(g1.NTOUR);

        Snake s1=new Snake(); //s1: serpent du joueur 1
        s1.position=new int[][]{{2,1},{3,1},{4,1},{5,1},{6,1},{7,1},{8,1},{9,1}};
        Snake s2=new Snake(); //s2: serpent du joueur 2
        s2.position=new int[][]{{2,5},{2,6},{2,7},{3,7},{3,8},{2,8},{2,9},{1,9}};
        g1.casesSerpent(s1);
        g1.casesSerpent(s2);
        g1.afficherGrille();

     
        for (int i=0; i<8; i++){    
            g1.tourSuivant(); // Tour suivant
            
            /* Demande de coup au joueur */
            String reponse=Arene.coupJoueur(g1.grille,s1);
            allongeOuPas(g1, s1, reponse);

            /* Mise à jour du serpent */
            Snake.afficher(s1);
            g1.casesSerpent(s1);
            g1.afficherGrille();

            
            }
    }
}


