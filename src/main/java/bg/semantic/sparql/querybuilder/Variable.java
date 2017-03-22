package bg.semantic.sparql.querybuilder;

public class Variable implements RdfResource{

	private String variable;
	
	public Variable(String varName) {
		this.variable = varName;
	}
	
	@Override
	public String toString() {
		return "?"+variable;
	}
	
}
