package camus.music;

public class Strumento implements Comparable<Strumento> {
	private String name;
	private int strumentIndex;
	
	private double lunghezzaNota;
	private double lunghezzaGcg;
	private int gapQartina;
	private double delay;
	private boolean sicronizzazione;
	private int ottava;
	private int orchestraIndex;
	private int[][] scala;
	private int forzaOn;
	private int quartina;
	private int[] repeat;
	private int inizio;
	private int continuaPer;
	
	public Strumento(){
		this("Strumento", 0, 1, 0, true);
	}
	
	public Strumento(String name,int strumentIndex, double lunghezzaNota, int gapQuartina){
		this(name, strumentIndex, lunghezzaNota, gapQuartina, true);
	}
	
	public Strumento(String name, int strumentIndex, double lunghezzaNota, int gapQuartina, boolean sincr) {
		this.name = name;
		this.strumentIndex = strumentIndex;
		this.lunghezzaNota = lunghezzaNota;
		this.gapQartina = gapQuartina;
		this.delay = 0;
		this.sicronizzazione = sincr;
		this.ottava = 0;
		this.orchestraIndex = 0;
		int[][] s = {{28, 31, 33, 35, 38, 40, 43, 45, 47, 50, 52, 55, 57, 59, 62, 64, 67, 69, 71, 74, 79, 81, 83, 86, 88, 91}};
		this.scala = s;
		this.forzaOn = 1000;
		this.delay = 0;
		this.lunghezzaGcg = 1;
		this.quartina = 64;
		this.repeat = null;
		this.inizio = 0;
		this.continuaPer = 0;
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

	public int getGapQuartina() {
		return gapQartina;
	}

	public void setGapQuartina(int gapQartina) {
		this.gapQartina = gapQartina;
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
	
	public double getLunghezzaGcg() {
		return lunghezzaGcg;
	}

	public void setLunghezzaGcg(double lunghezzaGcg) {
		this.lunghezzaGcg = lunghezzaGcg;
	}

	public int getForzaOn() {
		return forzaOn;
	}

	public void setForzaOn(int forzaOn) {
		this.forzaOn = forzaOn;
	}
	
	public int getQuartina() {
		return quartina;
	}

	public void setQuartina(int quartina) {
		this.quartina = quartina;
	}
	
	public int[] getRepeat() {
		return repeat;
	}

	public void setRepeat(int[] repeat) {
		this.repeat = repeat;
	}
	
	public int getInizio() {
		return inizio;
	}

	public void setInizio(int inizio) {
		this.inizio = inizio;
	}

	public int getContinuaPer() {
		return continuaPer;
	}

	public void setContinuaPer(int continuaPer) {
		this.continuaPer = continuaPer;
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
