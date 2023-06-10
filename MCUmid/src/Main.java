
public class Main {
	
	public static void main(String[] args) {
		Cpu cpu = new Cpu();
		Memory memory = new Memory();
		Loader loader = new Loader(memory);
		loader.load();
		cpu.associate(memory);
		memory.associate(cpu.mar, cpu.mbr);
		cpu.start();
	}
}
