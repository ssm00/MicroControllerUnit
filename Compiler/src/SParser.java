import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class SParser {
	public void parse(SLex lex) {
		SProgram program = new SProgram();
		program.parse(lex);
		makeFirstPhaseExe(program);
		program.secondPhaseParse();
		makeExe(program);
	}

	private void makeFirstPhaseExe(SProgram program) {
		File file = new File("first/exe1");
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(program.toString());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void makeExe(SProgram program) {
		File file = new File("executable/exe1");
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(program.toString());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
