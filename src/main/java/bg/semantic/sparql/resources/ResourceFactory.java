package bg.semantic.sparql.resources;

import java.util.List;

import com.hp.hpl.jena.rdf.model.Property;

import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.statements.FilterStatement;
import bg.semantic.sparql.statements.QueryConditionStatement;

public class ResourceFactory {

	private static Factory instance = new Impl();
	
	private ResourceFactory(){}
	
	public static Factory getInstace() {
		return instance;
	}

	public static QueryVariable queryVariable(String varname) {
		return instance.queryVariable(varname);
	}	
	
	public static URIResource uriResource(String uri) {
		return instance.uriResource(uri);
	}	
	
	public interface Factory {

		public QueryVariable queryVariable(String varName);
		public URIResource uriResource(String uri);
	}
	
	static class Impl implements Factory {

		Impl(){}
		
		@Override
		public QueryVariable queryVariable(String varName) {
			return new QueryVariable(varName);
		}
		
		@Override
		public URIResource uriResource(String uri) {
			return new URIResource(uri);
		}

	}
}
