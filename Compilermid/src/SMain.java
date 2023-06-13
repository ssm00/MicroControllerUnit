import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SMain {
	private SLex lex;
	private SParser parser;
	public SMain() {
		
	}
	public void initialize() {
		lex = new SLex();
		lex.initialize("source/assembly");
		parser = new SParser();
	}
	public void finalize() {
		lex.finalize();
	}
	public void run() {
		parser.parse(this.lex);
	}
	
	public static void main(String[] args) {
		SMain main = new SMain();
		main.initialize();
		main.run();
		main.finalize();
	}
	
}
