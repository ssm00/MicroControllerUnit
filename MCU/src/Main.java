
public class Main {
	
	public static void main(String[] args) {
		Cpu cpu = new Cpu();
		Memory memory = new Memory();
		KeyBoard keyBoard = new KeyBoard();
		Loader loader = new Loader(memory,cpu);
		loader.load();
		cpu.associate(memory,keyBoard);
		memory.associate(cpu.mar, cpu.mbr);
		cpu.start();
	}
}
