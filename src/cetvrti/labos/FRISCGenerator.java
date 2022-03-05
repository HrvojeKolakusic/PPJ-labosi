package cetvrti.labos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FRISCGenerator {
	
	static String line;
	static String[] split;
	static BufferedReader br;
	static String brVar = "0";
	static Integer pom = 0;
	static BufferedWriter bw;
	
	static boolean plusStart = false;
	static boolean plusEnd = false;
	static boolean minusStart = false;
	static boolean minusEnd = false;
	static boolean putaStart = false;
	static boolean putaEnd = false;
	static boolean divStart = false;
	static boolean divEnd = false;
	
	
	public static void main(String[] args) throws IOException {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		
		bw = new BufferedWriter(new FileWriter("a.frisc"));
		
		Map<String, String> var = new HashMap<>();
		
		bw.append(" MOVE 40000, R7\n");
		
		novi_blok(var);
		
		bw.append("MD_SGN MOVE 0, R6\n" + 
				" XOR R0, 0, R0\n" + 
				" JP_P MD_TST1\n" + 
				" XOR R0, ­1, R0\n" + 
				" ADD R0, 1, R0\n" + 
				" MOVE 1, R6\n" + 
				"MD_TST1 XOR R1, 0, R1\n" + 
				" JP_P MD_SGNR\n" + 
				" XOR R1, ­1, R1\n" + 
				" ADD R1, 1, R1\n" + 
				" XOR R6, 1, R6\n" + 
				"MD_SGNR RET\n" + 
				"MD_INIT POP R4\n" + 
				" POP R3\n" + 
				" POP R1\n" + 
				" POP R0\n" + 
				" CALL MD_SGN\n" + 
				" MOVE 0, R2\n" + 
				" PUSH R4\n" + 
				" RET\n" + 
				"MD_RET XOR R6, 0, R6\n" + 
				" JP_Z MD_RET1\n" + 
				" XOR R2, ­1, R2\n" + 
				" ADD R2, 1, R2\n" + 
				"MD_RET1 POP R4\n" + 
				" PUSH R2\n" + 
				" PUSH R3\n" + 
				" PUSH R4\n" + 
				" RET\n" + 
				"MUL CALL MD_INIT\n" + 
				" XOR R1, 0, R1\n" + 
				" JP_Z MUL_RET\n" + 
				" SUB R1, 1, R1\n" + 
				"MUL_1 ADD R2, R0, R2\n" + 
				" SUB R1, 1, R1\n" + 
				" JP_NN MUL_1\n" + 
				"MUL_RET CALL MD_RET\n" + 
				" RET\n" + 
				"DIV CALL MD_INIT\n" + 
				" XOR R1, 0, R1\n" + 
				" JP_Z DIV_RET\n" + 
				"DIV_1 ADD R2, 1, R2\n" + 
				" SUB R0, R1, R0\n" + 
				" JP_NN DIV_1\n" + 
				" SUB R2, 1, R2\n" + 
				"DIV_RET CALL MD_RET\n" + 
				" RET");



		br.close();
		bw.close();
		System.out.println("kraj");
	}
	
	public static void novi_blok(Map<String, String> Gvar) throws IOException {
		
		Map<String, String> Lvar = new HashMap<>();
		String leftVar = "";
		
		while(true) {
			line = br.readLine();
			if (line == null) break;
			
			if (line.contains("<naredba_pridruzivanja>")) {
				
				//lijevo od =
				line = br.readLine();
				line = line.trim();
				split = line.split(" ");
				leftVar = split[2];
				
				if (!Lvar.containsKey(split[2])) {
					Lvar.put(split[2], brVar);
					++pom;
					brVar = pom.toString();
				}
				
				//desno od =
				while(!line.contains("<lista_naredbi>")) {
					
					line = br.readLine();
					if (line.contains("IDN")) {
						line = line.trim();
						split = line.split(" ");
						
						if (!Lvar.containsKey(split[2])) {
							Lvar.put(split[2], brVar);
							++pom;
							brVar = pom.toString();
						}
						
						bw.append(" LOAD R0, (V" + Lvar.get(split[2]) + ")\n");
						bw.append(" PUSH R0\n");
						
						if (plusStart) plusEnd = true;
						if (minusStart) minusEnd = true;
						if (putaStart) putaEnd = true;
						if (divStart) divEnd = true;
						
					} else if (line.contains("BROJ")) {
						line = line.trim();
						split = line.split(" ");
						
						bw.append(" MOVE %D " + split[2] + ", R0\n");
						bw.append(" PUSH R0\n");
						
						if (plusStart) plusEnd = true;
						if (minusStart) minusEnd = true;
						if (putaStart) putaEnd = true;
						if (divStart) divEnd = true;
						
					} else if (line.contains("+")) {
						plusStart = true;
					} else if (line.contains("-")) {
						minusStart = true;
					} else if (line.contains("*")) {
						putaStart = true;
					} else if (line.contains("/")) {
						divStart = true;
					}
					
					if (plusEnd) {
						plusEnd = false;
						plusStart = false;
						
						bw.append(" POP R1\n");
						bw.append(" POP R0\n");
						bw.append(" ADD R0, R1, R2\n");
						bw.append(" PUSH R2\n");
					}
					
					if (minusEnd) {
						minusEnd = false;
						minusStart = false;
						
						bw.append(" POP R1\n");
						bw.append(" POP R0\n");
						bw.append(" SUB R0, R1, R2\n");
						bw.append(" PUSH R2\n");
					}
					
					if (putaEnd) {
						putaEnd = false;
						putaStart = false;
						
						bw.append(" CALL MUL\n");
					}
					
					if (divEnd) {
						divEnd = false;
						divStart = false;
						
						bw.append(" CALL DIV\n");
					}
					
					
					
				}
				
				bw.append(" POP R0\n");
				bw.append(" STORE R0, (V" + Lvar.get(leftVar) + ")\n");
				
			} //else if (line.contains("<za_petlja>")) {
//				
//				
//				Map<String, String> nova = new HashMap<>();
//				nova.putAll(Gvar);
//				nova.putAll(Lvar);
//				novi_blok(nova);
//				
//			} else if (line.contains("KR_ZA")) {
//				
//				line = br.readLine();
//				line = line.trim();
//				split = line.split(" ");
//				String startFor = split[2];
//				Lvar.put(split[2], brVar);
//				++pom;
//				brVar = pom.toString();
//				
//				
//				while(!line.contains("<lista_naredbi>")) {
//			
//					line = br.readLine();
//					if (line.contains("BROJ")) {
//						line = line.trim();
//						split = line.split(" ");
//						
//						bw.write(" MOVE %D " + split[2] + ", R0\n");
//						bw.write(" STORE R0, (V" + Lvar.get(startFor) + ")\n");
//
//						
//						if (Lvar.containsKey(split[2])) {
//							System.out.println(redak + " " + Lvar.get(split[2]) + " " + split[2]);
//						} else if (Gvar.containsKey(split[2])) {
//							System.out.println(redak + " " + Gvar.get(split[2]) + " " + split[2]);
//						} else {
//							System.out.println("err " + redak + " " + split[2]);
//							System.exit(0);
//						}
//					}
//				}
//	
//				Lvar.put(x, y);
//				
//			} else if (line.contains("KR_AZ")) {
//				redak++;
//				return;
//			}
		}
		
		bw.append(" LOAD R6, (V" + Lvar.get("rez") + ")\n");
		bw.append(" HALT\n");
		
		for (int i = 0; i < pom; ++i) {
			bw.append("V" + Integer.toString(i) + " DW 0\n");
		}
		return;
	}
}
