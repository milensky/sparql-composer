package bg.semantic.sparql.querybuilder.queryfilters;

import java.util.ArrayList;
import java.util.List;

import bg.semantic.sparql.querybuilder.PrefixNameResource;
import bg.semantic.sparql.querybuilder.QueryConditionStatement;
import bg.semantic.sparql.querybuilder.QueryModel;
import bg.semantic.sparql.querybuilder.QueryVariable;
import bg.semantic.sparql.querybuilder.StatementsFactory;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public class TypeQueryFilter implements IQueryFilter {

	@Override
	public List<WhereBlock> filter(QueryModel queryModel) {
		List<WhereBlock> statements = new ArrayList<>();
		QueryVariable resource = StatementsFactory.queryVariable("resource"); 
		if (queryModel.isSeafood()) {
			QueryConditionStatement s = (QueryConditionStatement) StatementsFactory.queryCondition(resource, StatementsFactory.uriResource("rdf:type"), 
					new PrefixNameResource("thesis","seafood"));
			statements.add(s);
		}
		if (queryModel.isVegetarian()) {
			QueryConditionStatement s =(QueryConditionStatement) StatementsFactory.queryCondition(resource, StatementsFactory.uriResource("rdf:type"), 
					new PrefixNameResource("thesis","vegeratian"));
			statements.add(s);
		}
		if (queryModel.isVegan()) {
			QueryConditionStatement s = (QueryConditionStatement) StatementsFactory.queryCondition(resource, StatementsFactory.uriResource("rdf:type"), 
					new PrefixNameResource("thesis","vegan"));
			statements.add(s);
		}
		return statements;
	}


}
