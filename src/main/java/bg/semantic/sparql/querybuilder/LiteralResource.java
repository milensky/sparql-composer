package bg.semantic.sparql.querybuilder;

public class LiteralResource implements RdfResource {

	private String data;
	public LiteralResource (String data) {
		this.data= data;
	}
	@Override 
	public String toString() {
		return data;
	}
}
