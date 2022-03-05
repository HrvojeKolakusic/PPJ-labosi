package prvi.labos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeksickiAnalizator {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		String[] split;
		
		int brRed = 1;
		
		while ((line = br.readLine()) != null) {
			line = line.trim();
			split = line.split(" ");
			for (String x : split) {
				x = x.trim();
				if (x.isEmpty()) continue;
				if (x.equals("//")) break;
				
				// kljucne rijeci
				if (x.equals("za")) System.out.println("KR_ZA " + brRed + " " + x);
				else if (x.equals("od")) System.out.println("KR_OD " + brRed + " " + x);
				else if (x.equals("do")) System.out.println("KR_DO " + brRed + " " + x);
				else if (x.equals("az")) System.out.println("KR_AZ " + brRed + " " + x);
				
				// aritmetika, brojevi, varijable
				else {
					char[] xArray = x.toCharArray();
					for (int i = 0; i < xArray.length; ++i) {
						
						//aritmetika
						if (xArray[i] == '=') System.out.println("OP_PRIDRUZI " + brRed + " =");
						else if (xArray[i] == '+') System.out.println("OP_PLUS " + brRed + " +");
						else if (xArray[i] == '-') System.out.println("OP_MINUS " + brRed + " -");
						else if (xArray[i] == '*') System.out.println("OP_PUTA " + brRed + " *");
						else if (xArray[i] == '/') System.out.println("OP_DIJELI " + brRed + " /");
						else if (xArray[i] == '(') System.out.println("L_ZAGRADA " + brRed + " (");
						else if (xArray[i] == ')') System.out.println("D_ZAGRADA " + brRed + " )");
						
						//brojevi
						else if (Character.isDigit(xArray[i])) {
							String broj = "";
							broj = broj + xArray[i];
							for (i = i + 1; i < xArray.length; ++i) {
								if (Character.isDigit(xArray[i])) broj = broj + xArray[i];
								else {
									--i;
									break;
								}
							}
							System.out.println("BROJ " + brRed + " " + broj);
						}
						
						//varijable
						else {
							String var = "";
							var = var + xArray[i];
							for (i = i + 1; i < xArray.length; ++i) {
								if (xArray[i] == '=' || xArray[i] == '+' || xArray[i] == '-'
										|| xArray[i] == '*' || xArray[i] == '/' || xArray[i] == '(' || xArray[i] == ')') {
									--i;
									break;
								} else {
									var = var + xArray[i];
								}
							}
							System.out.println("IDN " + brRed + " " + var);
						}
					}
				}
			}
			++brRed;
		}
		br.close();
	}
}
