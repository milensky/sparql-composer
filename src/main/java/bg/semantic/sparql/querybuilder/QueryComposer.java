package bg.semantic.sparql.querybuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bg.semantic.sparql.querybuilder.blocks.QueryConditionStatement;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public abstract class QueryComposer {
	private final List<Variable> selectVariables;
	private final List<String> orderBy;
	private String limit;
	private String offset;
	//private List<IQueryFilter>
	
	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public List<Variable> getSelectVariables() {
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
	private final List<WhereBlock> wheres;
	private final List<QueryConditionStatement> filters;
	private final List<QueryConditionStatement> groups;
	
	public QueryComposer() {
		groups = new ArrayList<QueryConditionStatement>();
		selectVariables = new ArrayList<Variable>();
		orderBy = new ArrayList<String>();
		filters = new ArrayList<QueryConditionStatement>();
		wheres = new ArrayList<WhereBlock>();
		limit = new String();
	}
	
	public List<String> getOrderBy() {
		return orderBy;
	}

	public String getLimit() {
		return limit;
	}
	
	public QueryComposer setLimit(String limit) {
		this.limit = limit;
		return this;
	}

	public final QueryComposer addWhereBlock(WhereBlock s) {
		if (s != null)
			wheres.add(s);
		return this;
	}

	public final QueryComposer addWhereBlock(List<WhereBlock> s) {
		if (s != null)
			wheres.addAll(s);
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
	
	public final QueryComposer addSelectVariable(Variable s) {
		selectVariables.add(s);
		return this;
	}

	public final QueryComposer addOrderBy(String s) {
		if (s != null)
			orderBy.add(s);
		return this;
	}

	public abstract String getSelectClause();
	public abstract String getWhereClause();
	public abstract String getFilterClause();
	public abstract String getGroupByClause();
	public abstract String getComposedQuery();
	public abstract String getLimitClause();
	public abstract String getOffsetClause();

	public String getOrderByClause() {
		// TODO Auto-generated method stub
		return null;
	}
}
