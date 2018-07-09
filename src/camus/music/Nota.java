package camus.music;

public class Nota {
	private int b;
	private int m;
	private int u;

	private String[] timeMorfology;
	private int strumento;
	private int bStart;
	private int bEnd;
	private int mStart;
	private int mEnd;
	private int uStart;
	private int uEnd;

	public static final int[] crome = {1, 2, 4, 8, 16, 32, 64};

	public Nota(int reference, int x, int y) {
		super();
		this.b = reference;
		this.m = reference + x;
		this.u = reference + x + y;
	}

	public int getStrumento() {
		return strumento;
	}

	public void setStrumento(int strumento) {
		this.strumento = strumento;
	}

	public void setTimeMorfology(String[] timeMorfology){
		this.timeMorfology = timeMorfology;
		setTimes();
	}

	public void setTimes(){
		int start[] = new int[3];
		int end[] = new int[3];
		start[0] = crome[(int)(Math.random() * 3)];
		start[1] = start[0] + crome[(int)(Math.random() * 4)];
		start[2] = start[1] + crome[(int)(Math.random() * 5)];

		end[2] = 128 - crome[(int)(Math.random() * 3)];
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
	
	
}
