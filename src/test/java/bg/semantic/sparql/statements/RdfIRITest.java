package bg.semantic.sparql.statements;

import org.junit.Before;
import org.junit.Test;

import bg.semantic.sparql.resources.ResourceFactory;
import bg.semantic.sparql.resources.URIResource;

public class RdfIRITest {
	URIResource prefixedResource;
	URIResource unPrefixedResource;
	
	@Before
	public void setup() {
		unPrefixedResource = ResourceFactory.uriResource("http://xmlns.com/foaf/0.1/name");
		prefixedResource = ResourceFactory.uriResource("xsd:int");		
	}

	@Test
	public void test1() {
		System.out.println(prefixedResource.sparqlEncode());
		System.out.println(unPrefixedResource.sparqlEncode());
	}
}
