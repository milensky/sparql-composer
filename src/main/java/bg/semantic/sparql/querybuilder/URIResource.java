package bg.semantic.sparql.querybuilder;

import java.net.URI;

public class URIResource implements RdfResource {

	private String uri;
	public URIResource(String uri) {
		this.uri = uri;
	}
	public URIResource(URI uri) {
		this.uri = uri.toString();
	}
	
	@Override
	public String toString() {
		return "<"+uri+">";
	}
}
