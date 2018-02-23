package bg.semantic.sparql.querybuilder;

import java.util.List;

import com.hp.hpl.jena.rdf.model.Property;

import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.FilterStatement;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public class StatementsFactory {

	private static Factory instance = new Impl();
	
	private StatementsFactory(){}
	
	public static Factory getInstace() {
		return instance;
	}

	/*public static WhereBlock QueryCondition(QueryVariable var, Property prop, RdfResource resource){
		return instance.QueryCondition(var,prop,resource);
	}*/

/*	public static WhereBlock QueryCondition(QueryVariable var, String prop, RdfResource resource){
		return instance.QueryCondition(var,prop,resource);
	}
*/
	public static WhereBlock queryCondition(QueryVariable var, URIResource prop, RdfResource resource){
		return instance.queryCondition(var,prop,resource);
	}
	
	public static FilterStatement filterCondition(String filterFunction, List<String> arguments) {
		return instance.filterCondition(filterFunction, arguments);
	}
	
	public static FilterBlock filterBlock(String filterFunction, List<String> arguments) {
		return instance.filterCondition(filterFunction, arguments);
	}
	
	public static FilterBlock filterBlock(List<WhereBlock> statements) {
		return instance.filterBlock(statements);
	}
	
	public static FilterBlock filterBlock(WhereBlock statement) {
		return instance.filterBlock(statement);
	}	
	
	public static QueryVariable queryVariable(String varname) {
		return instance.queryVariable(varname);
	}	
	
	public static URIResource uriResource(String uri) {
		return instance.uriResource(uri);
	}	
	
	public interface Factory {
	//	public WhereBlock QueryCondition(QueryVariable var, Property prop, RdfResource resource);
	//	public WhereBlock QueryCondition(QueryVariable var, String prop, RdfResource resource);
		public WhereBlock queryCondition(QueryVariable var, URIResource prop, RdfResource resource);
		public FilterBlock filterBlock(WhereBlock statement);
		public FilterBlock filterBlock(List<WhereBlock> statements);
		public FilterStatement filterCondition(String filterFunction, List<String> arguments);
		public FilterBlock filterBlock(String filterFunction, List<String> arguments);
		public QueryVariable queryVariable(String varName);
		public URIResource uriResource(String uri);
	}
	
	static class Impl implements Factory {

		Impl(){}
	/*	
		@Override
		public WhereBlock QueryCondition(QueryVariable var, RdfResource prop, RdfResource resource) {			
			return new QueryConditionStatement(var,prop,resource);
		}*/
/*
		@Override
		public WhereBlock QueryCondition(QueryVariable var, String prop, RdfResource resource) {			
			return new QueryConditionStatement(var,prop,resource);
		}
*/
		@Override
		public FilterBlock filterBlock(String filterFunction, List<String> arguments) {
			return new FilterStatement(filterFunction, arguments);
		}

		@Override
		public FilterStatement filterCondition(String filterFunction, List<String> arguments) {
			return new FilterStatement(filterFunction, arguments);
		}
		
		@Override
		public WhereBlock queryCondition(QueryVariable var, URIResource prop, RdfResource resource) {
			return new QueryConditionStatement(var,prop,resource);
		}
		@Override
		public QueryVariable queryVariable(String varName) {
			return new QueryVariable(varName);
		}
		
		@Override
		public URIResource uriResource(String uri) {
			return new URIResource(uri);
		}
		@Override
		public FilterBlock filterBlock(List<WhereBlock> statements) {
			return new FilterBlock(statements,200);
		}
		@Override
		public FilterBlock filterBlock(WhereBlock statement) {
			return new FilterBlock(statement,200);
		}
	}
}
