package bg.semantic.sparql.querybuilder;

import org.apache.jena.iri.IRI;

public class LiteralResource implements RdfResource {

	private String data;
	private String langTag;
	private IRI dataType;
	
	public LiteralResource (String data) {
		this.data = data;
	}
	
	
	public LiteralResource(String data, String langTag) {
		super();
		this.data = data;
		this.langTag = langTag;
	}


	public LiteralResource(String data, String langTag, IRI dataType) {
		super();
		this.data = data;
		this.langTag = langTag;
		this.dataType = dataType;
	}


	@Override
	public String toString() {
		return "LiteralResource [data=" + data + ", langTag=" + langTag + ", dataType=" + dataType + "]";
	}


	@Override
	public String sparqlEncode() {
		if (langTag != null) {
			return data + "@"+langTag;
		}
		if (dataType != null) {
			return data + "^^"+dataType;
		}			
		return data;
	}
 
}
