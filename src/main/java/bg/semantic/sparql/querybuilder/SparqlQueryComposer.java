package bg.semantic.sparql.querybuilder;

import java.net.URI;
import java.util.Map;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

public class SparqlQueryComposer extends QueryComposer {
	
	private ParameterizedSparqlString sparqlQuery;
	
	public SparqlQueryComposer() {
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

 
	public String getComposedQuery() {
		StringBuffer buff = new StringBuffer();

		buff.append("SELECT distinct ");
		buff.append(getSelectClause());
		buff.append(" WHERE { \n");
		String whereClause = getWhereClause();
		if (whereClause.equals("") == false) {
			buff.append(whereClause);
		}

			if (getFilters().size() > 0) {
				buff.append("FILTER  ( \n");
				buff.append(getFilterClause());
				buff.append(" ) \n");
			}
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

 
	
}
