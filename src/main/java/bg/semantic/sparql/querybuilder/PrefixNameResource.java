package bg.semantic.sparql.querybuilder;

public class PrefixNameResource implements RdfResource {

	private String prefix;
	private String name;
	public PrefixNameResource(String prefix, String name) {
		this.name=name;
		this.prefix = prefix;
	}
	
	@Override
	public String toString() {
		return prefix+":"+name;
	}
}
