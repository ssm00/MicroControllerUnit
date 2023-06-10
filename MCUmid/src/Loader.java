import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {
	public enum ESymbolType{
		eVariable,
		eLabel,
		eRegister
	}
	class SymbolEntity{
		public ESymbolType eSymbolType;
		public int value;

		public SymbolEntity(ESymbolType eSymbolType, int value) {
			this.eSymbolType = eSymbolType;
			this.value = value;
		}
	}

	HashMap<String, SymbolEntity> symbolTable;
	Memory memory;
	public Loader(Memory memory) {
		symbolTable = new HashMap<String, SymbolEntity>();
		this.memory = memory;
	}

	public void load() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("code/exe1"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		parseHeader(scanner);
		parseCode(scanner);
		scanner.close();
	}

	private void parseHeader(Scanner scanner) {
		String [] tokens = getTokens(scanner);
		if (tokens[0].equals("$Header")) {
			tokens = getTokens(scanner);
			while (!tokens[0].equals("$Code")) {
				int memoryAddress = Integer.parseInt(tokens[1]);
				memory.getMemory().set(100 + memoryAddress, 0);
				tokens = getTokens(scanner);
			}
		}
	}

	private void parseCode(Scanner scanner) {
		int count = 0;
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			int i = Integer.parseInt(line.substring(2), 16);
			memory.getMemory().set(0 + count, i);
			count++;
		}
	}

	private String [] getTokens(Scanner scanner) {
		String line = scanner.nextLine();
		String [] tokens = line.split(" ");
		return tokens;
	}

}
