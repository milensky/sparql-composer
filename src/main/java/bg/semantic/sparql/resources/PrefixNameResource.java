package bg.semantic.sparql.resources;

public class PrefixNameResource implements RdfResource {

	private String prefix;
	private String name;
	
	public PrefixNameResource(String prefix, String name) {
		this.name=name;
		this.prefix = prefix;
	}
	
	@Override
	public String toString() {
		throw new RuntimeException("Change to string call to sparqlEncode");
		//return prefix+":"+name;
	}

	@Override
	public String sparqlEncode() {
		return prefix+":"+name;
	}
}
