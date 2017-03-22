package bg.semantic.sparql.querybuilder;

import java.util.List;

import ws.semweb.thesis.dataobjects.Recipe;
import ws.semweb.thesis.dataobjects.jsonresponse.RecipeJsonResponse;
import ws.semweb.thesis.web.models.WebQueryModel;

public interface QueryEngine {

	public List<RecipeJsonResponse> getResults();
	public List<RecipeJsonResponse> getResults(QueryModel webQueryModel);
	
	
	public List<Recipe> queryExec();
	public List<Recipe> queryExec(QueryModel webQueryModel);
	
	public Recipe getSingleResult(String id);
	
	public void setQueryModel(QueryModel webQueryModel);
	public QueryModel getQueryModel();
}
