package bg.semantic.sparql.statements;

import java.util.ArrayList;
import java.util.List;

import bg.semantic.sparql.querybuilder.blocks.BlockFactory;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.resources.PrefixNameResource;
import bg.semantic.sparql.resources.QueryVariable;
import bg.semantic.sparql.resources.ResourceFactory;

public class TypeQueryFilter implements IQueryFilter {

	@Override
	public List<WhereBlock> filter(QueryModel queryModel) {
		List<WhereBlock> statements = new ArrayList<>();
		QueryVariable resource = ResourceFactory.queryVariable("resource"); 
		if (queryModel.isSeafood()) {
			QueryConditionStatement s = (QueryConditionStatement) StatementFactory.queryCondition(resource, 
					ResourceFactory.uriResource("rdf:type"), 
					new PrefixNameResource("thesis","seafood"));
			statements.add(s);
		}
		if (queryModel.isVegetarian()) {
			QueryConditionStatement s =(QueryConditionStatement) StatementFactory.queryCondition(resource, 
					ResourceFactory.uriResource("rdf:type"), 
					new PrefixNameResource("thesis","vegeratian"));
			statements.add(s);
		}
		if (queryModel.isVegan()) {
			QueryConditionStatement s = (QueryConditionStatement) StatementFactory.queryCondition(resource, 
					ResourceFactory.uriResource("rdf:type"), 
					new PrefixNameResource("thesis","vegan"));
			statements.add(s);
		}
		return statements;
	}


}
