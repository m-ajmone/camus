package notesInstruments;

public class Nota {
	private int reference;
	private int note2;
	private int note3;
	
	public Nota(int reference, int x, int y) {
		super();
		this.reference = reference;
		this.note2 = reference + x;
		this.note3 = reference + x + y;
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
}
