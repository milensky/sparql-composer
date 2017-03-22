package bg.semantic.sparql.querybuilder.filters;

import java.util.ArrayList;
import java.util.List;

import bg.semantic.sparql.querybuilder.PrefixNameResource;
import bg.semantic.sparql.querybuilder.QueryModel;
import bg.semantic.sparql.querybuilder.Variable;
import bg.semantic.sparql.querybuilder.blocks.QueryConditionStatement;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public class TypeQueryFilter implements IQueryFilter {

	@Override
	public List<WhereBlock> filter(QueryModel queryModel) {
		List<WhereBlock> statements = new ArrayList<>();
		Variable resource = new Variable("resource"); 
		if (queryModel.isSeafood()) {
			QueryConditionStatement s = new QueryConditionStatement(resource, "rdf:type", 
					new PrefixNameResource("thesis","seafood"));
			statements.add(s);
		}
		if (queryModel.isVegetarian()) {
			QueryConditionStatement s = new QueryConditionStatement(resource, "rdf:type", 
					new PrefixNameResource("thesis","vegeratian"));
			statements.add(s);
		}
		if (queryModel.isVegan()) {
			QueryConditionStatement s = new QueryConditionStatement(resource, "rdf:type", 
					new PrefixNameResource("thesis","vegan"));
			statements.add(s);
		}
		return statements;
	}

}
