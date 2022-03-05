package treci.labos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SemantickiAnalizator {

	static int redak = 0;
	static String line;
	static String[] split;
	static BufferedReader br;
	
	public static void main(String[] args) throws IOException {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		
		Map<String, Integer> var = new HashMap<>();
		
		novi_blok(var);

		br.close();
	}
	
	public static void novi_blok(Map<String, Integer> Gvar) throws IOException {
		
		Map<String, Integer> Lvar = new HashMap<>();
		String x = "";
		Integer y = 0;
		
		while(true) {
			line = br.readLine();
			if (line == null) return;
			
			if (line.contains("<naredba_pridruzivanja>")) {
				redak++;
				x = "";
				y = 0;
				boolean novaVar = false;
				
				//lijevo od =
				line = br.readLine();
				line = line.trim();
				split = line.split(" ");
				if (!Lvar.containsKey(split[2])) {
					x = split[2];
					y = redak;
					novaVar = true;
				}
				
				//desno od =
				while(!line.contains("<lista_naredbi>")) {
					
					line = br.readLine();
					if (line.contains("IDN")) {
						line = line.trim();
						split = line.split(" ");
						
						if (split[2].equals(x)) {
							System.out.println("err " + redak + " " + split[2]);
							System.exit(0);
						}
						
						if (Lvar.containsKey(split[2])) {
							System.out.println(redak + " " + Lvar.get(split[2]) + " " + split[2]);
						} else if (Gvar.containsKey(split[2])) {
							System.out.println(redak + " " + Gvar.get(split[2]) + " " + split[2]);
						} else {
							System.out.println("err " + redak + " " + split[2]);
							System.exit(0);
						}
					}
				}
				
				if (novaVar) {
					Lvar.put(x, y);
				}
				
				
			} else if (line.contains("<za_petlja>")) {
				redak++;
				
				Map<String, Integer> nova = new HashMap<>();
				nova.putAll(Gvar);
				nova.putAll(Lvar);
				novi_blok(nova);
				
			} else if (line.contains("KR_ZA")) {
				
				line = br.readLine();
				line = line.trim();
				split = line.split(" ");
				x = split[2];
				y = redak;
				
				while(!line.contains("<lista_naredbi>")) {
					
					line = br.readLine();
					if (line.contains("IDN")) {
						line = line.trim();
						split = line.split(" ");
						
						if (split[2].equals(x)) {
							System.out.println("err " + redak + " " + split[2]);
							System.exit(0);
						}
						
						if (Lvar.containsKey(split[2])) {
							System.out.println(redak + " " + Lvar.get(split[2]) + " " + split[2]);
						} else if (Gvar.containsKey(split[2])) {
							System.out.println(redak + " " + Gvar.get(split[2]) + " " + split[2]);
						} else {
							System.out.println("err " + redak + " " + split[2]);
							System.exit(0);
						}
					}
				}
	
				Lvar.put(x, y);
				
			} else if (line.contains("KR_AZ")) {
				redak++;
				return;
			}
		}
	}
}
