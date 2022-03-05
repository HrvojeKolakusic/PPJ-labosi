package drugi.labos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SintaksniAnalizator {

	static int brR = 0;
	static String line;
	static BufferedReader br;
	static String ispis = "";
	
	public static void main(String[] args) throws IOException {
		
		br = new BufferedReader(new InputStreamReader(System.in));
		
		line = br.readLine();
		program();
		System.out.print(ispis);

		br.close();
	}
	
	public static void program() throws IOException {
		razmaci();
		ispis += "<program>\n";
		brR++;
		
		if (line == null) {
			lista_naredbi();
			brR--;
			return;
		}
		
		if (line.startsWith("IDN") || line.startsWith("KR_ZA")) {
			
			lista_naredbi();
			brR--;
			return;
			
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void razmaci() {
		for (int i = 0; i < brR; ++i) {
			ispis += " ";
		}
	}
	
	public static void lista_naredbi() throws IOException {
		razmaci();
		ispis += "<lista_naredbi>\n";
		brR++;
		
		if (line == null) {
			razmaci();
			ispis += "$\n";
			return;
		}
		
		if (line.startsWith("IDN") || line.startsWith("KR_ZA")) {
			naredba();
			brR--;
			lista_naredbi();
			brR--;
			return;
		} else if (line.startsWith("KR_AZ")) {
			razmaci();
			ispis += "$\n";
			return;
			
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
		
	}
	
	public static void naredba() throws IOException {
		razmaci();
		ispis += "<naredba>\n";
		brR++;
		
		if (line == null) {
			System.out.print("err kraj");
			System.exit(0);
		}
		
		if (line.startsWith("IDN")) {
			naredba_pridruzivanja();
			brR--;
			return;
			
		} else if (line.startsWith("KR_ZA")) {
			za_petlja();
			brR--;
			return;
			
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void naredba_pridruzivanja() throws IOException {
		razmaci();
		ispis += "<naredba_pridruzivanja>\n";
		brR++;
		
		if (line == null) {
			System.out.print("err kraj");
			System.exit(0);
		}
		
		if (line.startsWith("IDN")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			if (line == null) {
				System.out.print("err kraj");
				System.exit(0);
			}
			
			if (line.startsWith("OP_PRIDRUZI")) {
				razmaci();
				ispis += line + "\n";
				line = br.readLine();
			} else {
				System.out.print("err " + line);
				System.exit(0);
			}
			
			E();
			brR--;
			return;
			
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void za_petlja() throws IOException {
		razmaci();
		ispis += "<za_petlja>\n";
		brR++;
		
		if (line == null) {
			System.out.print("err kraj");
			System.exit(0);
		}
		
		if (line.startsWith("KR_ZA")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			if (line == null) {
				System.out.print("err kraj");
				System.exit(0);
			}
			
			if (line.startsWith("IDN")) {
				razmaci();
				ispis += line + "\n";
				line = br.readLine();
			} else {
				System.out.print("err " + line);
				System.exit(0);
			}
			
			if (line == null) {
				System.out.print("err kraj");
				System.exit(0);
			}
			
			if (line.startsWith("KR_OD")) {
				razmaci();
				ispis += line + "\n";
				line = br.readLine();
			} else {
				System.out.print("err " + line);
				System.exit(0);
			}
			
			E();
			brR--;
			
			if (line == null) {
				System.out.print("err kraj");
				System.exit(0);
			}
			
			if (line.startsWith("KR_DO")) {
				razmaci();
				ispis += line + "\n";
				line = br.readLine();
			} else {
				System.out.print("err " + line);
				System.exit(0);
			}
			
			E();
			brR--;
			
			lista_naredbi();
			brR--;
			
			if (line == null) {
				System.out.print("err kraj");
				System.exit(0);
			}
			
			if (line.startsWith("KR_AZ")) {
				razmaci();
				ispis += line + "\n";
				line = br.readLine();
			} else {
				System.out.print("err " + line);
				System.exit(0);
			}
			
			return;
		}
	}
	
	public static void E() throws IOException {
		razmaci();
		ispis += "<E>\n";
		brR++;
		
		if (line == null) {
			System.out.print("err kraj");
			System.exit(0);
		}
		
		if (line.startsWith("IDN") || line.startsWith("BROJ") || 
			line.startsWith("OP_PLUS") || line.startsWith("OP_MINUS") || line.startsWith("L_ZAGRADA")) {
			T();
			brR--;
			E_lista();
			brR--;
			return;
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void E_lista() throws IOException {
		razmaci();
		ispis += "<E_lista>\n";
		brR++;
		
		if (line == null) {
			razmaci();
			ispis += "$\n";
			return;
		}
		
		if (line.startsWith("OP_PLUS")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			E();
			brR--;
			return;
			
		} else if (line.startsWith("OP_MINUS")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			E();
			brR--;
			return;
			
		} else if (line.startsWith("IDN") || line.startsWith("KR_ZA") || 
				 	line.startsWith("KR_DO") || line.startsWith("KR_AZ") || 
				 	line.startsWith("D_ZAGRADA")) {
			razmaci();
			ispis += "$\n";
			return;
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void T() throws IOException {
		razmaci();
		ispis += "<T>\n";
		brR++;
		
		if (line == null) {
			System.out.print("err kraj");
			System.exit(0);
		}
		
		if (line.startsWith("IDN") || line.startsWith("BROJ") 
			|| line.startsWith("OP_PLUS") || line.startsWith("OP_MINUS") 
			|| line.startsWith("L_ZAGRADA")) {
			P();
			brR--;
			
			T_lista();
			brR--;
			
			return;
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void T_lista() throws IOException {
		razmaci();
		ispis += "<T_lista>\n";
		brR++;
		
		if (line == null) {
			razmaci();
			ispis += "$\n";
			return;
		}
		
		if (line.startsWith("OP_PUTA")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			T();
			brR--;
			
			return;
			
		} else if (line.startsWith("OP_DIJELI")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			T();
			brR--;
			
			return;
			
		} else if (line.startsWith("IDN") || line.startsWith("KR_ZA") || 
				line.startsWith("KR_DO") || line.startsWith("KR_AZ") || 
				line.startsWith("OP_PLUS") || line.startsWith("OP_MINUS") || 
				line.startsWith("D_ZAGRADA")) {
			razmaci();
			ispis += "$\n";
			return;
			
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
	
	public static void P() throws IOException {
		razmaci();
		ispis += "<P>\n";
		brR++;
		
		if (line == null) {
			System.out.print("err kraj");
			System.exit(0);
		}
		
		if (line.startsWith("OP_PLUS")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			P();
			brR--;
			
			return;
			
		} else if (line.startsWith("OP_MINUS")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			P();
			brR--;
			
			return;
			
		} else if (line.startsWith("L_ZAGRADA")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			E();
			brR--;
			
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			return;
			
		} else if (line.startsWith("IDN")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			return;
			
		} else if (line.startsWith("BROJ")) {
			razmaci();
			ispis += line + "\n";
			line = br.readLine();
			
			return;
			
		} else {
			System.out.print("err " + line);
			System.exit(0);
		}
	}
}
