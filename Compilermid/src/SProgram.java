public class SProgram implements INode{
	private SSymbolTable symbolTable;
	private SHeader header;
	private SCodeSegment codeSegment;
	public SProgram() {
		this.symbolTable = new SSymbolTable();
		this.header = new SHeader(symbolTable);
		this.codeSegment = new SCodeSegment(symbolTable);
	}

	public void secondPhaseParse() {
		this.codeSegment.secondPhaseParse();
	}

	@Override
	public String parse(SLex lex) {
		String token = lex.getToken();
		if(token.compareTo(".header") == 0) {
			token = this.header.parse(lex);
		}
		if(token.compareTo(".code") == 0) {
			token = this.codeSegment.parse(lex);
		}
		System.out.println("symbolTable \n " + symbolTable.toString());
		return token;
	}

	@Override
	public String toString() {
		String result = "";
		result += "$Header"+"\n";
		result += this.header.toString();
		result += "$Code"+"\n";
		result += this.codeSegment.toString();
		return result;
	}
}