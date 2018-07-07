package camus.music;

public class Nota {
	private int reference;
	private int note2;
	private int note3;
	private String[] timeMorfology;
	private int strumento;

	public Nota(int reference, int x, int y) {
		super();
		this.reference = reference;
		this.note2 = reference + x;
		this.note3 = reference + x + y;
	}
	
	public int getStrumento() {
		return strumento;
	}

	public void setStrumento(int strumento) {
		this.strumento = strumento;
	}
	
	public void setTimeMorfology(String[] timeMorfology){
		this.timeMorfology = timeMorfology;
	}
	
	public int getReference() {
		return reference;
	}


	public int getNote2() {
		return note2;
	}


	public int getNote3() {
		return note3;
	}
	
	public String[] getTimeMorfology(){
		return timeMorfology;
	}
}
