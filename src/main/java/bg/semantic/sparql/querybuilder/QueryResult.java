package bg.semantic.sparql.querybuilder;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Model;

import ws.semweb.thesis.app.ThesisConfiguration;

public abstract  class QueryResult {
	protected Model moodleModel;
	protected Model defaultDataModel;
	protected Model inferredDataModel;
	private static Map<String, String> prefixes;
	protected ParameterizedSparqlString q;
	protected ThesisConfiguration config; 

	public void setConfig(ThesisConfiguration config) {
		this.config = config;
	}

	protected QueryResult() {
		this.prefixes = new HashMap<String, String>();
	}
	protected QueryResult(Map<String, String> prefixes) {
		moodleModel = null;//JenaDataStoreManager.getInstance().getSource(config.moodleNS);
		defaultDataModel = null;//JenaDataStoreManager.getInstance().getSource(config.domainSrcNS);
		inferredDataModel = null;//JenaDataStoreManager.getInstance().getSource(config.domainOntNS);
		this.prefixes = prefixes;
		q.setNsPrefixes(prefixes);
		resetQ(q);
	}

	public abstract HashMap<String,String> getResult(HashMap<String,String> queryParams);
	public abstract HashMap<String,String> getResult(String[] ingredients,HashMap<String,String> queryParams);
//	public abstract void modifyQuery(String[] ingredients,HashMap<String,String> queryParams, SparqlResultQuery query);
	
	public void resetQ(ParameterizedSparqlString q) {
		 
		q.setNsPrefixes(this.prefixes);
	}
	public void resetQ( ) {
 
		q.setNsPrefixes(this.prefixes);
	}
	
	public static void setPrefixes(ParameterizedSparqlString q) {
		q.setNsPrefixes(prefixes);
	}
} 
