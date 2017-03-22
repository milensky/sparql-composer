package bg.semantic.sparql.querybuilder;

import java.net.URI;
import java.util.Map;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

import bg.semantic.sparql.querybuilder.blocks.QueryConditionStatement;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public class SparqlQueryComposer extends QueryComposer {
	
	private ParameterizedSparqlString sparqlQuery;
	
	public SparqlQueryComposer(/*ParameterizedSparqlString sparqlString*/) {
		this.sparqlQuery = new ParameterizedSparqlString();
	}
	
	public SparqlQueryComposer addPrefix(String prefix, URI uri) {
		this.sparqlQuery.setNsPrefix(prefix, uri.toString());
		return this;
	}
	
	public SparqlQueryComposer addPrefix(String prefix, String uri) {
		this.sparqlQuery.setNsPrefix(prefix, uri);
		return this;
	}

	public SparqlQueryComposer addPrefixes(Map<String,String> prefixes) {
		if (prefixes != null) {
			this.sparqlQuery.setNsPrefixes(prefixes);
		}
		else {
			throw new RuntimeException("PREFIXES list is empty");
		}
		return this;
	}
	
	@Override
	public String getSelectClause() {
		StringBuilder clauseBuilder = new StringBuilder();
		for (Variable s:getSelectVariables()) {
			clauseBuilder.append(s.toString());
		}
		return clauseBuilder.toString();
	}

	@Override
	public String getWhereClause() {
		//ParameterizedSparqlString sparqlString = new ParameterizedSparqlString(); 
		StringBuilder sb = new StringBuilder("");
		int _index=0;
		for (WhereBlock whereBlock : getWhereBlocks()) {
			sb.append(whereBlock.setStatementString( _index));
			_index++;
		}
		return sb.toString();
//		return sparqlString.toString();
	}

	@Override
	public String getFilterClause() { 
		int _index=0;
		ParameterizedSparqlString sparqlString = new ParameterizedSparqlString(); 
		/*for (QueryConditionStatement s:getFilters()) {
			sparqlString.append(  s.getSubject() + " " + s.getCondition() + " " + " ?var"+_index );
			if (s.getObject() instanceof Float)
				sparqlString.setLiteral("?var"+_index, ((Float) s.getObject()).floatValue());
			else
				sparqlString.setLiteral("?var"+_index, s.getObject().toString());
			sparqlString.append(" && ");
			_index++;
		}
		sparqlString.append(" 1 ");*/
		return sparqlString.toString();
	}

	@Override
	public String getGroupByClause() { 
		for (QueryConditionStatement s:getGroups()) {
			
		}
		return "";
	}
	
	@Override
	public String getOrderByClause() {
		StringBuilder clauseBuilder = new StringBuilder();
		for (String s:getOrderBy()) {
			if (s.matches("^(desc|asc).*")) {
				clauseBuilder.append(s);
			}
			else {
				clauseBuilder.append(" ?"+s);
			}
		}
		return clauseBuilder.toString();
	}

	@Override
	public String getLimitClause() { 
		String s = getLimit();
		
		return s;
	}
	
	@Override
	public String getComposedQuery() {
		StringBuffer buff = new StringBuffer();

		buff.append("SELECT distinct ");
		buff.append(getSelectClause());
		buff.append(" WHERE { \n");
		String whereClause = getWhereClause();
		if (whereClause.equals("") == false) {
			buff.append(whereClause);
		}

		/*	if (getFilters().size() > 0) {
				buff.append("FILTER  ( \n");
				buff.append(getFilterClause());
				buff.append(" ) \n");
			}*/
			if (getGroups().size() > 0) {
				buff.append("GROUP BY  { \n");
				buff.append(getGroupByClause());
				buff.append(" } \n");
			}
		buff.append(" } \n");
		
		if (getOrderBy() != null) {
			buff.append(" ORDER BY ");
			buff.append(getOrderByClause());
		}
		
		buff.append(" LIMIT ");
		buff.append(getLimitClause());
		buff.append(" OFFSET ");
		buff.append(getOffsetClause());
		
		System.out.println(buff.toString());
		String commandText = buff.toString();
		
		sparqlQuery.setCommandText(commandText);
		
		return sparqlQuery.toString();
	}

	@Override
	public String getOffsetClause() {
		String s = getOffset();
		
		return s;
	}
	
}
