public class SHeader implements INode{
	private SSymbolTable declarations;
	private SSymbolTable symbolTable;

	public SHeader(SSymbolTable symbolTable) {
		this.declarations = new SSymbolTable();
		this.symbolTable = symbolTable;
	}
	@Override
	public String parse(SLex lex) {
		String token = lex.getToken();
		while(token.compareTo(".code") != 0) {
			SSymbolEntity declaration = new SSymbolEntity();
			declaration.setVariableName(token);
			declaration.setValue(Integer.parseInt(lex.getToken()));
			this.declarations.add(declaration);
			this.symbolTable.add(declaration);
			token = lex.getToken();
		}
		return token;
	}

	public String getDs() {
		String result = "DS ";
		int ds = this.declarations.size();
		result += ds;
		return result;
	}

}