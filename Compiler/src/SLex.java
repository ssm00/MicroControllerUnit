import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SLex {

	private Scanner scanner;
	public SLex() {
	}
	public void initialize(String fileName) {
		try {
			scanner = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void finalize() {
		scanner.close();
	}
	public String getToken() {
		if(scanner.hasNext()) {
			return scanner.next();
		}
		return null;
	}
	public String[] getTokens() {
		if(scanner.hasNext()) {
			String line = scanner.nextLine();
			while (line.isEmpty()) {
				line = scanner.nextLine();
			}
			String [] tokens = line.strip().split("[ \t]+");
			return tokens;
		}
		return null;
	}
}
