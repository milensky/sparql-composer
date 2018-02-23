package sparqlquery;

import org.junit.Before;
import org.junit.Test;

import bg.semantic.sparql.querybuilder.URIResource;

public class RdfIRITest {
	URIResource prefixedResource;
	URIResource unPrefixedResource;
	
	@Before
	public void setup() {
		unPrefixedResource = new URIResource("http://xmlns.com/foaf/0.1/name");
		prefixedResource = new URIResource("xsd:int");		
	}

	@Test
	public void test1() {
		System.out.println(prefixedResource.sparqlEncode());
		System.out.println(unPrefixedResource.sparqlEncode());
	}
}
