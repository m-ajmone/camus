package camus.music;

public class Strumento {
	private String name;
	private int strumentIndex;
	private double lunghezzaNota;
	private double distanzaNote;
	private double delay;
	private boolean sicronizzazione;
	private int ottava;
	
	public Strumento(){
		this("Strumento", 0, 1, 0, 0, true);
	}
	
	public Strumento(String name, int strumentIndex, double lunghezzaNota, double distanzaNote, double delay){
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
	
	
}
