package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

import bg.semantic.sparql.resources.QueryVariable;
import bg.semantic.sparql.resources.RdfResource;
import bg.semantic.sparql.resources.URIResource;
import bg.semantic.sparql.statements.FilterStatement;
import bg.semantic.sparql.statements.QueryConditionStatement;

public class BlockFactory {

	private static Factory instance = new Impl();
	
	private BlockFactory(){}
	
	public static Factory getInstace() {
		return instance;
	}
	

	
	public static FilterBlock filterBlock(List<WhereBlock> statements) {
		return instance.filterBlock(statements);
	}
	
	public static FilterBlock filterBlock(WhereBlock statement) {
		return instance.filterBlock(statement);
	}
	
	interface Factory {

		public FilterBlock filterBlock(WhereBlock statement);
		public FilterBlock filterBlock(List<WhereBlock> statements);

	}
	
	public static class Impl implements Factory {

		
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
