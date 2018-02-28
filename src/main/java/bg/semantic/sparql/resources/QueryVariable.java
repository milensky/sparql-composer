package bg.semantic.sparql.resources;

public class QueryVariable implements RdfResource {

	private String variable;
	
	QueryVariable(String varName) {
		this.variable = varName;
	}
	
	@Override
	public String toString() {
		throw new RuntimeException("Change to string call to sparqlEncode");
	}

	@Override
	public String sparqlEncode() {
		return "?"+variable;
	}
	
}
