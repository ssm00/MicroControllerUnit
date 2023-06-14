import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Loader {
	public enum ESymbolType{
		eVariable,
		eLabel,
		eRegister,
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
	Cpu cpu;
	public Loader(Memory memory, Cpu cpu) {
		symbolTable = new HashMap<String, SymbolEntity>();
		this.memory = memory;
		this.cpu = cpu;
	}

	public void load() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("code/exe"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		parseHeader(scanner);
		parseCode(scanner);
		scanner.close();
	}

	private void parseHeader(Scanner scanner) {
		String [] tokens = getTokens(scanner);
		cpu.cs.setValue(0);
		if (tokens[0].equals("$Header")) {
			tokens = getTokens(scanner);
			while (!tokens[0].equals("$Code")) {
				if (tokens[0].equals("CS")) {
					int segmentSize = Integer.parseInt(tokens[1]) + 1;
					cpu.ds.setValue(segmentSize);
				} else if(tokens[0].equals("DS")) {
					int segmentSize = Integer.parseInt(tokens[1])*4;
					cpu.ss.setValue(cpu.ds.getValue()+segmentSize);
				}
				tokens = getTokens(scanner);
			}
		}
		//cpu.ds.setValue(100);
		//cpu.ss.setValue(200);
		cpu.hs.setValue(cpu.ss.getValue()+256);
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
