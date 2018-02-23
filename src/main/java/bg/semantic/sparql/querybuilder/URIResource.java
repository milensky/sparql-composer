package bg.semantic.sparql.querybuilder;

import org.apache.jena.iri.IRI;
import org.apache.jena.iri.IRIFactory;

public class URIResource implements RdfResource {

	private String iri;
	
	public URIResource(String uri) {
		if (uri.contains(":")) { 
			this.iri  = uri;
		}
		else {
			throw new RuntimeException("Uri should be either a IRI or a XML namespace prefixed property.");
		}
	}
	

	@Override
	public String sparqlEncode() {
		if (this.iri.startsWith("http")) {
			return "<" + this.iri + ">";
		}
		return iri;
	} 
}
