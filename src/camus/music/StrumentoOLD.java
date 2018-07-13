package camus.music;

public class StrumentoOLD {
	private int strumento;
	
	public StrumentoOLD(int livello) {
		strumento = livello;
		/*switch (livello) {
		case 0:
			strumento = 0;
			break;
		case 1:
			strumento = 1;
			break;
		case 2:
			strumento = 2;
			break;
		case 3:
			strumento = 3;
			
		}*/	
	}

	public int getStrumento() {
		return strumento;
	}
}
