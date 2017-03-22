package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

import com.hp.hpl.jena.rdf.model.Property;

import bg.semantic.sparql.querybuilder.RdfResource;
import bg.semantic.sparql.querybuilder.Variable;

public class Statements {

	private static Factory instance = new Impl();
	
	private Statements(){}
	
	public static Factory getInstace() {
		return instance;
	}

	public static WhereBlock QueryCondition(Variable var, Property prop, RdfResource resource){
		return instance.QueryCondition(var,prop,resource);
	}

	public static WhereBlock QueryCondition(Variable var, String prop, RdfResource resource){
		return instance.QueryCondition(var,prop,resource);
	}
	
	public static WhereBlock FilterCondition(String filterFunction, List<String> arguments) {
		return instance.FilterCondition(filterFunction, arguments);
	}
	
	public interface Factory {
		public WhereBlock QueryCondition(Variable var, Property prop, RdfResource resource);
		public WhereBlock QueryCondition(Variable var, String prop, RdfResource resource);
		public WhereBlock FilterCondition(String filterFunction, List<String> arguments);
	}
	
	static class Impl implements Factory {

		Impl(){}
		
		@Override
		public WhereBlock QueryCondition(Variable var, Property prop, RdfResource resource) {			
			return new QueryConditionStatement(var,prop,resource);
		}

		@Override
		public WhereBlock QueryCondition(Variable var, String prop, RdfResource resource) {			
			return new QueryConditionStatement(var,prop,resource);
		}

		@Override
		public WhereBlock FilterCondition(String filterFunction, List<String> arguments) {
			return new FilterStatement(filterFunction, arguments);
		}
	}
}
