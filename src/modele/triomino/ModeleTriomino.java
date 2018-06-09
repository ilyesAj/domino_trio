package modele.triomino;

import java.util.ArrayList;
import java.util.Collections;

import modele.Point;

public class ModeleTriomino {
	private PieceTriomino table[][];
	private ArrayList<PieceTriomino> deck;
	private ArrayList<JoueurTriomino> joueurs;
	private ArrayList<Point> extremite;

	
	public ArrayList<JoueurTriomino> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<JoueurTriomino> joueurs) {
		this.joueurs = joueurs;
	}

	public ArrayList<Point> getExtremite() {
		return extremite;
	}

	public void setExtremite(ArrayList<Point> extremite) {
		this.extremite = extremite;
	}

	public ModeleTriomino(int jeu, ArrayList<JoueurTriomino> joueurs) { // dom=0,tri=1
		
			this.setTable(new PieceTriomino[113][113]);
			this.deck = new ArrayList<PieceTriomino>();
			this.joueurs = joueurs;
			this.extremite = new ArrayList<Point>();
			this.initDeck(jeu);
		
	}
	public static int getDirection(int i, int j){ //retourn 1 si le sommet est en haut 0 si en bas
		if(i%2 == j%2){
			return 0;
		}else 
			return 1;
	}
	public boolean coupValide(int jeu, PieceTriomino piece, Point p) {
		if(!this.getExtremite().contains(p)){ // si le point joué n'est pas dans le tableau d'extremites alors coup invalide ( non permis )
			return false;
		}
		int direction = ModeleTriomino.getDirection(p.getX(), p.getY()); // la variable direction bch t9olek sommet lfou9 wala louta ( 0 maaneha louta )
		int x=p.getX();
		int y=p.getY();
		if(direction==0){//sommet en bas
			boolean haut=(this.getTable()[x-1][y]==null || ( this.getTable()[x-1][y].getX()==piece.getX() && this.getTable()[x-1][y].getZ()==piece.getZ()));
			boolean bas=(this.getTable()[x+1][y]==null || ( this.getTable()[x+1][y].getY()==piece.getY()));
			boolean droite=(this.getTable()[x][y+1]==null || ( this.getTable()[x][y+1].getX()==piece.getY() && this.getTable()[x][y+1].getY()==piece.getZ()));
			boolean gauche=(this.getTable()[x][y-1]==null || ( this.getTable()[x][y-1].getZ()==piece.getY() && this.getTable()[x][y-1].getY()==piece.getX()));
			return (haut && bas && gauche && droite);
		}else {//sommet en haut
			boolean haut=(this.getTable()[x-1][y]==null || ( this.getTable()[x-1][y].getY()==piece.getY()));
			boolean bas=(this.getTable()[x+1][y]==null || ( this.getTable()[x][y+1].getX()==piece.getX() &&  this.getTable()[x+1][y].getZ()==piece.getZ()));
			boolean droite=(this.getTable()[x][y+1]==null || ( this.getTable()[x][y+1].getY()==piece.getZ() && this.getTable()[x][y+1].getX()==piece.getY()));
			boolean gauche=(this.getTable()[x][y-1]==null || ( this.getTable()[x][y-1].getY()==piece.getX() && this.getTable()[x][y-1].getZ()==piece.getY()));
			return (haut && bas && gauche && droite);
		}
	}

	public void initDeck(int jeu) {
		
			for (int i = 0; i <= 6; i++) {
				for (int j = i; j <= 6; j++) {
					for( int k=j; k<=6;k++){
					deck.add(new PieceTriomino(i, j,k));
					}
				}
			}
		
	}

	public ArrayList<PieceTriomino> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<PieceTriomino> deck) {
		this.deck = deck;
	}

	public PieceTriomino[][] getTable() {
		return table;
	}

	public void setTable(PieceTriomino table[][]) {
		this.table = table;
	}
	public boolean piocher(JoueurTriomino joueur){
		if(this.getDeck().size()==0)
			return false;
		Collections.shuffle(this.getDeck());
		this.deck.add(this.getDeck().get(0));
		this.getDeck().remove(0);
		return true;
	}
	public int finPartie(int jeu) { // retourne l'id du joueurs ganant 5 sinon a changer plus tard
		
			int min = 56;
			int minId = 5;
			
			boolean fin = true;
			for (int i = 0; i < joueurs.size(); i++) {
				
				if (joueurs.get(i).mainVide()) {
					return i;
				} else {
					if (joueurs.get(i).getMain().size() < min) {
						min = joueurs.get(i).getMain().size();
						minId = i;
				
					}
					fin = fin && joueurs.get(i).nePeutPasJouer(jeu, this);
				}
			}
			if (fin == true) {
				return minId;
			} else {
				return 5;
			}
		
	}
	public boolean verifPont(Point joue,PieceTriomino piece){
		int direction=ModeleTriomino.getDirection(joue.getX(), joue.getY());
		int x=joue.getX();
		int y=joue.getY();
		if(direction==1){ //sommet en haut
			boolean pontPointeGauche=(this.getTable()[x][y-1]==null && this.getTable()[x+1][y]==null && this.getTable()[x][y+1]!=null && this.getTable()[x][y-2]!=null && this.getTable()[x][y-2].getZ()==piece.getX());
			boolean pontPointeDroite=(this.getTable()[x][y+1]==null && this.getTable()[x+1][y]==null && this.getTable()[x][y-1]!=null && this.getTable()[x][y+2]!=null && this.getTable()[x][y-2].getX()==piece.getZ());
			boolean pontPointeHaut=(this.getTable()[x][y+1]==null && this.getTable()[x][y-1]==null && this.getTable()[x+1][y]!=null && this.getTable()[x-2][y]!=null && this.getTable()[x-2][y].getY()==piece.getY());
			return pontPointeGauche ||pontPointeDroite||pontPointeHaut;
		}else { //sommet en bas
			boolean pontPointeGauche=(this.getTable()[x][y-1]==null && this.getTable()[x-1][y]==null && this.getTable()[x][y+1]!=null && this.getTable()[x][y-2]!=null && this.getTable()[x][y-2].getZ()==piece.getX());
			boolean pontPointeDroite=(this.getTable()[x][y+1]==null && this.getTable()[x-1][y]==null && this.getTable()[x][y-1]!=null && this.getTable()[x][y+2]!=null && this.getTable()[x][y-2].getX()==piece.getZ());
			boolean pontPointeBas=(this.getTable()[x][y+1]==null && this.getTable()[x][y-1]==null && this.getTable()[x-1][y]!=null && this.getTable()[x-2][y]!=null && this.getTable()[x-2][y].getY()==piece.getY());
			return pontPointeGauche ||pontPointeDroite||pontPointeBas;
		}
		

	}
	public boolean verifHexagone(Point joue,int direction){ // 0 a droite  1 a gauche 2 haut ou bas
		int sommet=ModeleTriomino.getDirection(joue.getX(), joue.getY());
		int x=joue.getX();
		int y=joue.getY();
		if(sommet==1){
			if(direction==2){
				return (this.getTable()[x][y-1]!=null &&this.getTable()[x][y+1]!=null && this.getTable()[x-1][y]!=null && this.getTable()[x-1][y+1]!=null && this.getTable()[x-1][y-1]!=null );
			}
			else if(direction==0){
				return (this.getTable()[x][y+1]!=null &&this.getTable()[x][y+2]!=null && this.getTable()[x+1][y]!=null && this.getTable()[x+1][y+1]!=null && this.getTable()[x+1][y+2]!=null );
				
			}
			else{
				return (this.getTable()[x][y-1]!=null &&this.getTable()[x][y-2]!=null && this.getTable()[x+1][y]!=null && this.getTable()[x+1][y-1]!=null && this.getTable()[x+1][y-2]!=null );
				
			}
		}
		else{
			if(direction==2){
				return (this.getTable()[x][y-1]!=null &&this.getTable()[x][y+1]!=null && this.getTable()[x+1][y]!=null && this.getTable()[x+1][y+1]!=null && this.getTable()[x+1][y-1]!=null );
			}
			else if(direction==0){
				return (this.getTable()[x][y+1]!=null &&this.getTable()[x][y+2]!=null && this.getTable()[x-1][y]!=null && this.getTable()[x-1][y+1]!=null && this.getTable()[x-1][y+2]!=null );
				
			}
			else{
				return (this.getTable()[x][y-1]!=null &&this.getTable()[x][y-2]!=null && this.getTable()[x-1][y]!=null && this.getTable()[x-1][y-1]!=null && this.getTable()[x-1][y-2]!=null );
				
			}
		}
	}

	public boolean verifDoubleHexagone(Point joue){
		return (this.verifHexagone(joue, 0) && this.verifHexagone(joue, 1)) || (this.verifHexagone(joue, 0) && this.verifHexagone(joue, 2)) || (this.verifHexagone(joue, 2) && this.verifHexagone(joue, 1));
	}

	public boolean verifTripleHexagone(Point joue){
		return (this.verifHexagone(joue, 0) && this.verifHexagone(joue, 1)  && this.verifHexagone(joue, 2)); 
	}
}