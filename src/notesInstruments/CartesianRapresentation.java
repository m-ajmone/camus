package notesInstruments;
import java.util.ArrayList;
import notesInstruments.Nota;
import notesInstruments.Strumento;

public class CartesianRapresentation {
	static boolean[][] GOL = new boolean[10][10];
	static int[][] GCG = new int[10][10];
	static int reference = 0;
	static ArrayList<Nota> Spartito= new ArrayList<Nota>();
	static ArrayList<Strumento> Orchestra = new ArrayList <Strumento>();
	
	private static void riempimentoBooleano(boolean[][] matrice) {
		for(int i=0; i< matrice.length; i++) {
			for(int j=0; j < matrice[i].length; j++) {
				GOL[i][j] = getRandomBoolean();        	
		    }
		}
	}
	
	private static void riempimentoInt(int[][] matrice) {
		for(int i=0; i< matrice.length; i++) {
			for(int j=0; j < matrice[i].length; j++) {
				GCG[i][j] = (int) (Math.random() * 4);        	
		    }
		}
	}
	
	private static void estrazione(boolean[][] matrice, int[][] matrice2) {
		for(int i=0; i< matrice.length; i++) {
			for(int j=0; j < matrice[i].length; j++) {
				if (GOL[i][j]) {
					Spartito.add(new Nota(reference, matrice[i].length - i, j+1));
					Orchestra.add(new Strumento(matrice2[i][j]));
				}
		    }
		}
	}
	
	 
	 
	private static boolean getRandomBoolean() {
		return Math.random() < 0.5;
	}
	
	public static void creaSpartito() {
		riempimentoBooleano(GOL);
		riempimentoInt(GCG);
		estrazione(GOL, GCG);
		print2DB(GOL);
		System.out.println("");
		print2DI(GCG);
		printList();
	
	}
	
	public static void printList() {
	    for(Nota elemento : Spartito) {
	    	System.out.println(elemento.getReference() + " " + elemento.getNote2() + " " + elemento.getNote3());
	    }
	    for(Strumento elemento : Orchestra) {
	    	System.out.println(elemento.getStrumento());
	    }
	}
	
	public static void print2DB(boolean mat[][])
    {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
	
	public static void print2DI(int mat[][])
    {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
			
}


