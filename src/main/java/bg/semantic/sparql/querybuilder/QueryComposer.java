package bg.semantic.sparql.querybuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.FilterStatement;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public abstract class QueryComposer {
	private final List<QueryVariable> selectVariables;
	private final List<String> orderBy;
	private String limit;
	private String offset;
	private final List<WhereBlock> wheres;
	private final List<WhereBlock> filters;
	private final List<FilterStatement> filterStatements;
	private final List<QueryConditionStatement> groups;
		
	
	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public List<QueryVariable> getSelectVariables() {
		return selectVariables;
	}

	public List<WhereBlock> getWhereBlocks() {
		Collections.sort(wheres);
		return wheres;
	}

	/*
	public List<QueryConditionStatement> getFilters() {
		return filters;
	}
*/
	public List<QueryConditionStatement> getGroups() {
		return groups;
	}

	public QueryComposer() {
		groups = new ArrayList<QueryConditionStatement>();
		selectVariables = new ArrayList<QueryVariable>();
		filterStatements = new ArrayList<>();
		orderBy = new ArrayList<String>();
		filters = new ArrayList<WhereBlock>();
		wheres = new ArrayList<WhereBlock>();
		limit = new String();
	}
	
	public List<String> getOrderBy() {
		return orderBy;
	}

	private String getLimit() {
		return limit;
	}
	
	public QueryComposer setLimit(String limit) {
		this.limit = limit;
		return this;
	}

	public final QueryComposer addWhereBlock(WhereBlock block) {
		if (block != null)
			wheres.add(block);
		return this;
	}
	
	/**
	 * Add a filter to a Filter block (a block which contains several filters)
	 * @param block
	 * @return
	 */
	public final QueryComposer addFilterBlock(WhereBlock block) {
		if (block != null)
			filters.add(block);
		return this;
	}

	/**
	 * Add a standalone filter statement
	 * @param block
	 * @return
	 */
	public final QueryComposer addFilterStatement(FilterStatement filterStatement) {
		if (filterStatement != null)
			filterStatements.add(filterStatement);
		return this;
	}	
	
	public final QueryComposer addWhereBlocks(List<WhereBlock> blocks) {
		if (blocks != null)
			wheres.addAll(blocks);
		return this;
	}
	/*
	public final QueryComposer addFilter(QueryConditionStatement s) {
		if (s != null)
			filters.add(s);
		return this;
	}
	*/
	public final QueryComposer addGroupBy(QueryConditionStatement s) {
		if (s != null)
			groups.add(s);
		return this;
	}
	
	public final QueryComposer addSelectVariable(QueryVariable s) {
		selectVariables.add(s);
		return this;
	}
/*
	public final QueryComposer addOrderBy(String s) {
		if (s != null)
			orderBy.add(s);
		return this;
	}
*/
	public final QueryComposer addOrderBy(QueryVariable var) {
		if (var != null)
			orderBy.add(var.sparqlEncode());
		return this;
	}
	
	public final QueryComposer addOrderBy(QueryVariable var, String direction) {
		if (var != null)
			orderBy.add(direction + "(" + var.sparqlEncode() + ")");
		return this;
	}
	
	public String getSelectClause() {
		StringBuilder clauseBuilder = new StringBuilder();
		for (QueryVariable s:getSelectVariables()) {
			clauseBuilder.append(s.sparqlEncode());
		}
		return clauseBuilder.toString();
	}
	
	public String getWhereClause() {
		//ParameterizedSparqlString sparqlString = new ParameterizedSparqlString(); 
		StringBuilder sb = new StringBuilder("");
		int _index=0;
		for (WhereBlock whereBlock : getWhereBlocks()) {
			sb.append(whereBlock.toStatementString( _index));
			_index++;
		}
		return sb.toString();
//		return sparqlString.toString();
	}
	
	public String getFilterClause() { 
		int _index=0;
		ParameterizedSparqlString sparqlString = new ParameterizedSparqlString(); 
		for (WhereBlock s:getFilters()) {
			sparqlString.append(s.toStatementString(_index));
			sparqlString.append(" && ");
			_index++;
		}
		sparqlString.append(" 1 ");
		return sparqlString.toString();
	}
	
	public String getGroupByClause() {		
		for (QueryConditionStatement s:getGroups()) {
			throw new RuntimeException("to be implemented "+s);
		}
		return "";
	}
	

	public String getLimitClause() { 
		String s = getLimit();
		
		return s;
	}
	
	public String getOffsetClause() {
		String s = getOffset();
		
		return s;
	}

	public String getOrderByClause() {
		StringBuilder clauseBuilder = new StringBuilder();
		for (String s:getOrderBy()) {
			if (s.matches("^(desc|asc).*")) {
				clauseBuilder.append(s);
			}
			else {
				clauseBuilder.append(" " + s);
			}
		}
		return clauseBuilder.toString();
	}

	public List<WhereBlock> getFilters() {
		return filters;
	}
}
