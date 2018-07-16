package camus.music;

public class Nota {
	private int b;
	private int m;
	private int u;
	
	private int lunghezzaGcg;
	private double[] lunghezze = {0.3, 0.5, 0.8, 1, 1.2, 1.5};
	private String[] timeMorfology;
	private Strumento strumento;
	private int bStart;
	private int bEnd;
	private int mStart;
	private int mEnd;
	private int uStart;
	private int uEnd;

	public static final int[] crome = {16, 32};

	public Nota(int reference, int x, int y) {
		super();
		this.b = reference;
		this.m = x;
		this.u = y;
	}

	public Strumento getStrumento() {
		return strumento;
	}

	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}

	public void setTimeMorfology(String[] timeMorfology, int quartina){
		this.timeMorfology = timeMorfology;
		if(strumento.isSicronizzazione())
			setTimesSincrona(quartina);
		else
			setTimesAsincrona(quartina);
	}
	
	public void setTimesAsincrona(int quartina){
		int duration[] = new int[3];
		duration[0] = crome[(int)(Math.random()*2)];
		duration[1] = crome[(int)(Math.random()*2)];
		duration[2] = crome[(int)(Math.random()*2)];

		switch(timeMorfology[0]) {
		case "B[UM]":
			bStart = 0;
			bEnd = duration[0];
			mStart = duration[0];
			uStart = mStart;
			mEnd = duration[1];
			uEnd = mEnd;
			break;
		case "[UMB]":
			bStart = 0;
			bEnd = duration[0];
			mStart = bStart;
			uStart = mStart;
			mEnd = bEnd;
			uEnd = mEnd;
			break;
		case "BUM":
			bStart = 0;
			bEnd = duration[0];
			mStart = duration[1];
			mEnd = duration[2];
			uStart = duration[0];
			uEnd = duration[1];
			break;
		case "UMB":
			bStart = duration[1];
			bEnd = duration[2];
			mStart = duration[0];
			mEnd = duration[1];
			uStart = 0;
			uEnd = duration[0];
			break;
		case "BMU":
			bStart = 0;
			bEnd = duration[0];
			mStart = duration[0];
			mEnd = duration[1];
			uStart = duration[1];
			uEnd = duration[2];
			break;
		case "UBM":
			bStart = duration[0];
			bEnd = duration[1];
			mStart = duration[1];
			mEnd = duration[2];
			uStart = 0;
			uEnd = duration[0];
			break;
		case "MBU":
			bStart = duration[0];
			bEnd = duration[1];
			mStart = 0;
			mEnd = duration[0];
			uStart = duration[1];
			uEnd = duration[2];
			break;
		case "U[MB]":
			bStart = duration[0];
			bEnd = duration[1];
			mStart = duration[0];
			mEnd = duration[1];
			uStart = 0;
			uEnd = duration[0];
			break;
		case "MUB":
			bStart = duration[1];
			bEnd = duration[2];
			mStart = 0;
			mEnd = duration[0];
			uStart = duration[0];
			uEnd = duration[1];
			break;
		case "M[UB]":
			bStart = duration[0];
			bEnd = duration[1];
			mStart = 0;
			mEnd = duration[0];
			uStart = duration[0];
			uEnd = duration[1];
			break;
		}

	}
	
	public void setTimesSincrona(int quartina){
		int start[] = new int[3];
		int end[] = new int[3];
		start[0] = 0;
		start[1] = start[0] + (int)(crome[(int)(Math.random() * 2)] * strumento.getDelay());
		start[2] = start[1] + (int)(crome[(int)(Math.random() * 2)] * strumento.getDelay());

		end[2] = (int)(quartina * strumento.getLunghezzaNota() * lunghezze[lunghezzaGcg]) - (int)(crome[(int)(Math.random() * 2)] * strumento.getDelay());
		end[1] = end[2] - (int)(crome[(int)(Math.random() * 2)] * strumento.getDelay());
		end[0] = end[1] - (int)(crome[(int)(Math.random() * 2)] * strumento.getDelay());

		
		if(timeMorfology[0].indexOf('[') == 0){
			bStart = start[0];
			mStart = start[0];
			uStart = start[0];
		}else{
			int i;
			for(i = 0; i < timeMorfology[0].length(); i++){
				if(timeMorfology[0].charAt(i) == 'B')
					bStart = start[i];
				if(timeMorfology[0].charAt(i) == 'M')
					mStart = start[i];
				if(timeMorfology[0].charAt(i) == 'U')
					uStart = start[i];
				if(timeMorfology[0].charAt(i) == '[')
					break;
			}

			if(i < timeMorfology[0].length()){
				if(timeMorfology[0].charAt(0) == 'B'){
					uStart = start[i];
					mStart = start[i];
				}
				if(timeMorfology[0].charAt(0) == 'M'){
					uStart = start[i];
					bStart = start[i];
				}
				if(timeMorfology[0].charAt(0) == 'U'){
					bStart = start[i];
					mStart = start[i];
				}
			}
		}
		
		if(timeMorfology[1].indexOf('[') == 0){
			bEnd = end[0];
			mEnd = end[0];
			uEnd = end[0];
		}else{
			int i;
			for(i = 0; i < timeMorfology[1].length(); i++){
				if(timeMorfology[1].charAt(i) == 'B')
					bEnd = end[i];
				if(timeMorfology[1].charAt(i) == 'M')
					mEnd = end[i];
				if(timeMorfology[1].charAt(i) == 'U')
					uEnd = end[i];
				if(timeMorfology[1].charAt(i) == '[')
					break;
			}

			if(i < timeMorfology[1].length()){
				if(timeMorfology[1].charAt(0) == 'B'){
					uEnd = end[i];
					mEnd = end[i];
				}
				if(timeMorfology[1].charAt(0) == 'M'){
					uEnd = end[i];
					bEnd = end[i];
				}
				if(timeMorfology[1].charAt(0) == 'U'){
					bEnd = end[i];
					mEnd = end[i];
				}
			}
		}
	}
	
	public void setTimes(int quartina){
		int start[] = new int[3];
		int end[] = new int[3];
		start[0] = crome[(int)(Math.random() * 3)];
		start[1] = start[0] + crome[(int)(Math.random() * 4)];
		start[2] = start[1] + crome[(int)(Math.random() * 5)];

		end[2] = (quartina * 2) - crome[(int)(Math.random() * 3)];
		end[1] = end[2] - crome[(int)(Math.random() * 4)];
		end[0] = end[1] - crome[(int)(Math.random() * 5)];

		
		if(timeMorfology[0].indexOf('[') == 0){
			bStart = start[0];
			mStart = start[0];
			uStart = start[0];
		}else{
			int i;
			for(i = 0; i < timeMorfology[0].length(); i++){
				if(timeMorfology[0].charAt(i) == 'B')
					bStart = start[i];
				if(timeMorfology[0].charAt(i) == 'M')
					mStart = start[i];
				if(timeMorfology[0].charAt(i) == 'U')
					uStart = start[i];
				if(timeMorfology[0].charAt(i) == '[')
					break;
			}

			if(i < timeMorfology[0].length()){
				if(timeMorfology[0].charAt(0) == 'B'){
					uStart = start[i];
					mStart = start[i];
				}
				if(timeMorfology[0].charAt(0) == 'M'){
					uStart = start[i];
					bStart = start[i];
				}
				if(timeMorfology[0].charAt(0) == 'U'){
					bStart = start[i];
					mStart = start[i];
				}
			}
		}
		
		if(timeMorfology[1].indexOf('[') == 0){
			bEnd = end[0];
			mEnd = end[0];
			uEnd = end[0];
		}else{
			int i;
			for(i = 0; i < timeMorfology[1].length(); i++){
				if(timeMorfology[1].charAt(i) == 'B')
					bEnd = end[i];
				if(timeMorfology[1].charAt(i) == 'M')
					mEnd = end[i];
				if(timeMorfology[1].charAt(i) == 'U')
					uEnd = end[i];
				if(timeMorfology[1].charAt(i) == '[')
					break;
			}

			if(i < timeMorfology[1].length()){
				if(timeMorfology[1].charAt(0) == 'B'){
					uEnd = end[i];
					mEnd = end[i];
				}
				if(timeMorfology[1].charAt(0) == 'M'){
					uEnd = end[i];
					bEnd = end[i];
				}
				if(timeMorfology[1].charAt(0) == 'U'){
					bEnd = end[i];
					mEnd = end[i];
				}
			}
		}
	}

	public int getB() {
		return b;
	}


	public int getM() {
		return m;
	}


	public int getU() {
		return u;
	}

	public String[] getTimeMorfology(){
		return timeMorfology;
	}

	public int getbStart() {
		return bStart;
	}

	public void setbStart(int bStart) {
		this.bStart = bStart;
	}

	public int getbEnd() {
		return bEnd;
	}

	public void setbEnd(int bEnd) {
		this.bEnd = bEnd;
	}

	public int getmStart() {
		return mStart;
	}

	public void setmStart(int mStart) {
		this.mStart = mStart;
	}

	public int getmEnd() {
		return mEnd;
	}

	public void setmEnd(int mEnd) {
		this.mEnd = mEnd;
	}

	public int getuStart() {
		return uStart;
	}

	public void setuStart(int uStart) {
		this.uStart = uStart;
	}

	public int getuEnd() {
		return uEnd;
	}

	public void setuEnd(int uEnd) {
		this.uEnd = uEnd;
	}

	public int getLunghezzaGcg() {
		return lunghezzaGcg;
	}

	public void setLunghezzaGcg(int lunghezzaGcg) {
		this.lunghezzaGcg = lunghezzaGcg;
	}
	
	
}
