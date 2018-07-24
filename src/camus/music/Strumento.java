package camus.music;

public class Strumento implements Comparable<Strumento> {
	private String name;
	private int strumentIndex;
	private double lunghezzaNota;
	private double distanzaNote;
	private double delay;
	private int index;
	private boolean sicronizzazione;
	private int ottava;
	private int orchestraIndex;
	private int[][] scala;
	private int forzaOn;
	
	public Strumento(){
		this("Strumento", 0, 1, 0, 0, true);
	}
	
	public Strumento(String name,int strumentIndex, double lunghezzaNota, double distanzaNote, double delay){
		this(name, strumentIndex, lunghezzaNota, distanzaNote, delay, true);
	}
	
	public Strumento(String name, int strumentIndex, double lunghezzaNota, double distanzaNote, double delay, boolean sincr) {
		this.name = name;
		this.strumentIndex = strumentIndex;
		this.lunghezzaNota = lunghezzaNota;
		this.distanzaNote = distanzaNote;
		this.delay = delay;
		this.sicronizzazione = sincr;
		this.ottava = 0;
		this.orchestraIndex = 0;
		int[][] s = {{28, 31, 33, 35, 38, 40, 43, 45, 47, 50, 52, 55, 57, 59, 62, 64, 67, 69, 71, 74, 79, 81, 83, 86, 88, 91}};
		this.scala = s;
		this.forzaOn = 1000;
		this.index = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrumentIndex() {
		return strumentIndex;
	}

	public void setStrumentIndex(int strumentIndex) {
		this.strumentIndex = strumentIndex;
	}

	public double getLunghezzaNota() {
		return lunghezzaNota;
	}

	public void setLunghezzaNota(double lunghezzaNota) {
		this.lunghezzaNota = lunghezzaNota;
	}

	public double getDistanzaNote() {
		return distanzaNote;
	}

	public void setDistanzaNote(double distanzaNote) {
		this.distanzaNote = distanzaNote;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public boolean isSicronizzazione() {
		return sicronizzazione;
	}

	public void setSicronizzazione(boolean sicronizzazione) {
		this.sicronizzazione = sicronizzazione;
	}

	public int getOttava() {
		return ottava;
	}

	public void setOttava(int ottava) {
		this.ottava = ottava;
	}
	
	public int getOrchestraIndex() {
		return orchestraIndex;
	}

	public void setOrchestraIndex(int orchestraIndex) {
		this.orchestraIndex = orchestraIndex;
	}
	
	public int[][] getScala() {
		return scala;
	}

	public void setScala(int[][] scala) {
		this.scala = scala;
	}
	
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getForzaOn() {
		return forzaOn;
	}

	public void setForzaOn(int forzaOn) {
		this.forzaOn = forzaOn;
	}

	@Override
	public int compareTo(Strumento s1) {
		if( orchestraIndex < s1.getOrchestraIndex())
			return -1;
		else
			if(orchestraIndex == s1.getOrchestraIndex())
				return 0;
			else
				return 1;
	}
	
	
}
