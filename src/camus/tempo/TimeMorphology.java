package camus.tempo;

public class TimeMorphology {

	public String[] calculateTimeMorphology(boolean a, boolean b, boolean c, boolean d, boolean m, boolean n, boolean o, boolean p) {
		
		String timeMorph[] = new String[2];
		
		
		
		int abcd = Integer.valueOf(String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d));
		int dcba = Integer.valueOf(String.valueOf(d) + String.valueOf(c) + String.valueOf(b) + String.valueOf(a));
		int mnop = Integer.valueOf(String.valueOf(m) + String.valueOf(n) + String.valueOf(o) + String.valueOf(p));
		int ponm = Integer.valueOf(String.valueOf(p) + String.valueOf(o) + String.valueOf(n) + String.valueOf(m));
		
		int tgg = abcd | dcba;
		int dur = mnop | ponm;
		
		switch(tgg) {
			case 0000:
				timeMorph[0] = "B[UM]";
				break;
			case 0001:
				timeMorph[0] = "[UMB]";
				break;
			case 0010:
				timeMorph[0] = "BUM";
				break;
			case 0011:
				timeMorph[0] = "UMB";
				break;
			case 0101:
				timeMorph[0] = "BMU";
				break;
			case 0110:
				timeMorph[0] = "UBM";
				break;
			case 0111:
				timeMorph[0] = "MBU";
				break;
			case 1001:
				timeMorph[0] = "U[MB]";
				break;
			case 1011:
				timeMorph[0] = "MUB";
				break;
			case 1111:
				timeMorph[0] = "M[UB]";
				break;
			
		}
		
		switch(dur) {
			case 0000:
				timeMorph[1] = "B[UM]";
				break;
			case 0001:
				timeMorph[1] = "[UMB]";
				break;
			case 0010:
				timeMorph[1] = "BUM";
				break;
			case 0011:
				timeMorph[1] = "UMB";
				break;
			case 0101:
				timeMorph[1] = "BMU";
				break;
			case 0110:
				timeMorph[1] = "UBM";
				break;
			case 0111:
				timeMorph[1] = "MBU";
				break;
			case 1001:
				timeMorph[1] = "U[MB]";
				break;
			case 1011:
				timeMorph[1] = "MUB";
				break;
			case 1111:
				timeMorph[1] = "M[UB]";
				break;
		}
		
		return timeMorph;
	}
	
}


