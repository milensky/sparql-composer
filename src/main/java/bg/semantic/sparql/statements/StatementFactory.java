package bg.semantic.sparql.statements;

import java.util.List;

import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.resources.QueryVariable;
import bg.semantic.sparql.resources.RdfResource;
import bg.semantic.sparql.resources.URIResource;

public class StatementFactory {

	private static Factory instance = new Impl();

	public static WhereBlock queryCondition(QueryVariable var, URIResource prop, RdfResource resource){
		return instance.queryCondition(var,prop,resource);
	}
	
	public static FilterStatement filterCondition(String filterFunction, List<String> arguments) {
		return instance.filterCondition(filterFunction, arguments);
	}
	
	public static FilterBlock filterBlock(String filterFunction, List<String> arguments) {
		return instance.filterCondition(filterFunction, arguments);
	}
	
	public interface Factory {
		public WhereBlock queryCondition(QueryVariable var, URIResource prop, RdfResource resource);
		public FilterStatement filterCondition(String filterFunction, List<String> arguments);
		public FilterBlock filterBlock(String filterFunction, List<String> arguments);		
	}
	
	private static class Impl implements Factory {
		@Override
		public FilterStatement filterCondition(String filterFunction, List<String> arguments) {
			return new FilterStatement(filterFunction, arguments);
		}
		
		@Override
		public WhereBlock queryCondition(QueryVariable var, URIResource prop, RdfResource resource) {
			return new QueryConditionStatement(var,prop,resource);
		}
		
		@Override
		public FilterBlock filterBlock(String filterFunction, List<String> arguments) {
			return new FilterStatement(filterFunction, arguments);
		}		
	}
	
}
