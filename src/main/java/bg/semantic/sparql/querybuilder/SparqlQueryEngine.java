package bg.semantic.sparql.querybuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.adobe.xmp.impl.Base64;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryException;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import ws.semweb.thesis.dataobjects.Recipe;
import ws.semweb.thesis.dataobjects.jsonresponse.RecipeJsonResponse;
import ws.semweb.thesis.datastorage.DataStore;
import ws.semweb.thesis.web.models.WebQueryModel;
 
@Service
public class SparqlQueryEngine implements QueryEngine {

	private static final ParameterizedSparqlString sparqlQuery;
	private static final ParameterizedSparqlString defaultSparqlQuery; 
	private static final ParameterizedSparqlString singleRecordQuery;
	static {
		//sparqlQuery = new ParameterizedSparqlString("SELECT ?rating ?fatContent ?ingredient ?calories ?proteinContent ?sugarContent ?name ?image ");
		sparqlQuery = new ParameterizedSparqlString("SELECT ?rating (str(?name) as ?label)  ?image ");
		sparqlQuery.append(" WHERE { \n ");
		sparqlQuery.append(" { ?resource <http://www.w3.org/1999/xhtml/microdata#item> ?entity } \n");
		sparqlQuery.append(" { ?entity <http://schema.org/Recipe/image> ?image } \n");
		sparqlQuery.append("optional { ?ratingAgg <http://schema.org/AggregateRating/ratingValue> ?rating . \n");
		sparqlQuery.append("    ?entity <http://schema.org/Recipe/aggregateRating> ?ratingAgg } \n");
		sparqlQuery.append(" { ?entity <http://schema.org/Recipe/name> ?name. } \n");
		sparqlQuery.append( "} ");
		
		defaultSparqlQuery = new ParameterizedSparqlString("SELECT ?name ?rating");
		defaultSparqlQuery.append(" WHERE { \n ");
		defaultSparqlQuery.append(" { ?resource <http://www.w3.org/1999/xhtml/microdata#item> ?entity } \n");
		defaultSparqlQuery.append(" { ?entity <http://schema.org/Recipe/name> ?name. } \n");
		defaultSparqlQuery.append(" { ?ratingAgg <http://schema.org/AggregateRating/ratingValue> ?rating . \n");
		defaultSparqlQuery.append("    ?entity <http://schema.org/Recipe/aggregateRating> ?ratingAgg } \n");
		defaultSparqlQuery.append( " } ORDER BY (?rating) LIMIT 20 ");	
		
		
		singleRecordQuery = new ParameterizedSparqlString("SELECT ?rating "
				+ " (str(?_fatContent) as ?fatContent) (str(?_ingredient) as ?ingredient) (str(?_totalTime) as ?totalTime) "
				+ " (str(?_cookTime) as ?cookTime) (str(?_prepTime) as ?prepTime)"
				+ " ?category ?author (str(?_numberOfServings) as ?numberOfServings) "
				+ " (str(?_calories) as ?calories) (str(?_proteinContent) as ?proteinContent) "
				+ " (str(?_sugarContent) as ?sugarContent) (str(?_name) as ?name) ?image "
				+ " (str(?_sodiumContent) as ?sodiumContent) (str(?_fiberContent) as ?fiberContent) "
				+ " (str(?_cholesterolContent) as ?cholesterolContent) (str(?_carbohydrateContent) as ?carbohydrateContent) "
				+ " (str(?_transFatContent) as ?transFatContent) "
				+ " (str(?_saturatedFatContent) as ?saturatedFatContent) (str(?_unsaturatedFatContent) as ?unsaturatedFatContent) ");
		singleRecordQuery.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
		singleRecordQuery.append(" WHERE { \n ");
		singleRecordQuery.append(" { ?resource <http://www.w3.org/1999/xhtml/microdata#item> ?entity } \n");
		singleRecordQuery.append(" { ?entity <http://schema.org/Recipe/image> ?image } \n");
		singleRecordQuery.append("optional { ?ratingAgg <http://schema.org/AggregateRating/ratingValue> ?rating . \n");
		singleRecordQuery.append("	      ?entity <http://schema.org/Recipe/aggregateRating> ?ratingAgg } \n");
		singleRecordQuery.append(" { ?entity <http://schema.org/Recipe/name> ?_name. } \n");
		singleRecordQuery.append(" { ?entity <http://schema.org/Recipe/ingredients> ?_ingredient. } \n");
		singleRecordQuery.append("optional { ?entity <http://schema.org/Recipe/_recipeCategory> ?category. } \n");
		singleRecordQuery.append("optional { ?entity <http://schema.org/Recipe/author> ?author. } \n");
		singleRecordQuery.append("optional { ?entity <http://schema.org/Recipe/cookTime> ?_cookTime. } \n");
		singleRecordQuery.append("optional { ?entity <http://schema.org/Recipe/prepTime> ?_prepTime. } \n");
		singleRecordQuery.append("optional { ?entity <http://schema.org/Recipe/totalTime> ?_totalTime. } \n");
		singleRecordQuery.append("optional { ?entity <http://schema.org/Recipe/recipeYield> ?_numberOfServings. } \n"); 
		singleRecordQuery.append("optional { \n");
		singleRecordQuery.append(" { ?entity <http://schema.org/Recipe/nutrition> ?nutrition } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/fatContent> ?_fatContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/calories> ?_calories. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/proteinContent> ?_proteinContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/sugarContent> ?_sugarContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/saturatedFatContent> ?_saturatedFatContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/unsaturatedFatContent> ?_unsaturatedFatContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/transFatContent> ?_transFatContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/carbohydrateContent> ?_carbohydrateContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/cholesterolContent> ?_cholesterolContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/fiberContent> ?_fiberContent. } \n");
		singleRecordQuery.append("optional { ?nutrition <http://schema.org/NutritionInformation/sodiumContent> ?_sodiumContent. } \n");
		
		singleRecordQuery.append( " }\n");
		singleRecordQuery.append( " } ");
	}
	private QueryModel queryModel;
	private DataStore dataStore;
	private Map<String, String> prefixes;
	private ParameterizedSparqlString paramererizedSparqlString;
	private QueryComposer queryComposer;
	
	public void setDataStore(DataStore dataStore) {
		this.dataStore = dataStore;
	}


	public SparqlQueryEngine() {
		paramererizedSparqlString = new ParameterizedSparqlString();
	}
	
	public SparqlQueryEngine(Map<String, String> prefixes) {
		this();
		this.prefixes = prefixes;
		queryModel = new QueryModel();
		paramererizedSparqlString.setNsPrefixes(prefixes);
	//	paramererizedSparqlString.setCommandText("SELECT ?resource WHERE ");
		queryComposer = new SparqlQueryComposer(paramererizedSparqlString);
	/*	Iterator<Entry<String, String>> i = prefixes.entrySet().iterator();
		while(i.hasNext()) {			
			Map.Entry<String, String> pr =  i.next();
			prefixes.put(pr.getKey(), pr.getValue());
			System.out.print(pr.getKey() + pr.getValue());
		}*/
	}
	
	

	@Override
	public List<Recipe> queryExec(QueryModel qm) {
		this.queryModel = qm;
		List<Recipe> ret = new ArrayList<>();
		ResultSet result ;
		dataStore.prepareQuery(queryComposer,this.queryModel);
		paramererizedSparqlString.setCommandText(queryComposer.getComposedQuery());
		Query q;
		try {			
			q = paramererizedSparqlString.asQuery();
			System.out.println(q.toString());
 
			try {
				result = dataStore.execQuery(q.toString());
	
				while (result.hasNext()) {
					QuerySolution sol = result.next();
					RDFNode resource = sol.get("?resource");
					RDFNode word = sol.get("?label");					
					System.out.println(sol.toString());
					//System.out.println("URI: " + resource.toString() + " label: " + word.toString());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
 
		}
		catch (QueryException e) {
			e.printStackTrace();
		}		
		return ret;
	}


	public String getParamererizedSparqlString() {
		return paramererizedSparqlString.toString();
	}


	@Override
	public List<RecipeJsonResponse> getResults() {
		return getResults(queryModel);
	}


	@Override
	public List<RecipeJsonResponse> getResults(QueryModel qm) {
		this.queryModel = qm;
		List<RecipeJsonResponse> ret = new ArrayList<>();
		ResultSet result ;
		dataStore.prepareQuery(queryComposer,this.queryModel);
		paramererizedSparqlString.setCommandText(queryComposer.getComposedQuery());
		
		Query q;
		try {			
			q = paramererizedSparqlString.asQuery();
			System.out.println(q.toString());
 
			try {
				result = dataStore.execQuery(q.toString());
				
				while (result.hasNext()) {
					QuerySolution sol = result.next();
					RDFNode resource = sol.get("?resource");
					RDFNode word = sol.get("?label");
					RecipeJsonResponse rec = new RecipeJsonResponse(resource.toString());
					rec = loadFullRecipe(rec);
					ret.add(rec);
					rec.setId(Base64.encode(rec.getId())); 
					System.out.println(sol.toString());
					//System.out.println("URI: " + resource.toString() + " label: " + word.toString());
				}
			}
			catch (Exception e) { e.printStackTrace();	}
		}
		catch (QueryException e) { e.printStackTrace(); }		
		return ret;
	}


	@Override
	public List<Recipe> queryExec() {
		return queryExec(queryModel);
	}

	@Override
	public void setQueryModel(QueryModel webQueryModel) {
		this.queryModel = webQueryModel;		
	}
  
	@Override
	public QueryModel getQueryModel() {
		return queryModel;		
	}
	
	private RecipeJsonResponse loadFullRecipe(RecipeJsonResponse ret) {
		sparqlQuery.setIri("?resource", ret.getId());
		try {
			ResultSet detailsQueryResilt = dataStore.execQuery(sparqlQuery.toString());
		//	System.out.println(sparqlQuery.toString());
			while (detailsQueryResilt.hasNext()) {
				QuerySolution sol = detailsQueryResilt.next();
				RDFNode rating = sol.get("?rating");
				RDFNode name = sol.get("?label");
				RDFNode smallImageUrls = sol.get("?image");
				RDFNode ingredient = sol.get("?ingredient");
				ret.setRecipeName(name.toString());
				if (rating != null) {
					ret.setRating(rating.toString().substring(0, 1));
				}
				else {
					ret.setRating("4");
				}
				if (smallImageUrls == null) {
					ret.addSmallImageUrl("https://app.jodileefoundation.org.au/images/no_image_thumb.gif");
				}
				else {
					ret.addSmallImageUrl(smallImageUrls.toString());
				}
				if (ingredient != null) {
					ret.addIngredient(ingredient.toString());
				}
			// 	System.out.println(sol.toString());
				//System.out.println("URI: " + resource.toString() + " label: " + word.toString());
			}
		}
		catch (Exception e) {e.printStackTrace();}
		
		return ret;
	}


	@Override
	public Recipe getSingleResult(String id) {
		Recipe ret = new Recipe();
		singleRecordQuery.setIri("?resource", Base64.decode(id));
		try {
			ResultSet result = dataStore.execQuery(singleRecordQuery.toString());
			System.out.println(singleRecordQuery.toString());
			while (result.hasNext()) {
				QuerySolution sol = result.next();
		
				RDFNode author = sol.get("?author");
				RDFNode fatContent = sol.get("?fatContent");
				RDFNode rating = sol.get("?rating");
				RDFNode name = sol.get("?name");
				RDFNode category = sol.get("?category");
				RDFNode smallImageUrls = sol.get("?image");
				RDFNode ingredient = sol.get("?ingredient");
				RDFNode numberOfServings = sol.get("?numberOfServings");
				RDFNode calories = sol.get("?calories");
				RDFNode sugarContent = sol.get("?sugarContent");
				RDFNode proteinContent = sol.get("?proteinContent");
				RDFNode sodiumContent = sol.get("?sodiumContent");
				RDFNode fiberContent = sol.get("?fiberContent");
				RDFNode totalTime = sol.get("?totalTime");
				RDFNode cookTime = sol.get("?cookTime");
				RDFNode prepTime = sol.get("?prepTime");
				RDFNode cholesterolContent = sol.get("?cholesterolContent");
				RDFNode carbohydrateContent = sol.get("?carbohydrateContent");
				RDFNode transFatContent = sol.get("?transFatContent");
				RDFNode saturatedFatContent = sol.get("?saturatedFatContent");
				RDFNode unsaturatedFatContent = sol.get("?unsaturatedFatContent");
				
				ret.setId(id);
				ret.setUrl(URI.create(Base64.decode(id)));
				if (rating != null && rating.equals("") == false) { 
					ret.setRating(rating.toString().substring(0, 1));	} 
				else { ret.setRating("4"); }
				if (author != null) { ret.setAuthor(author.toString());	} else { ret.setAuthor(""); }
				if (name != null) { ret.setName(name.toString());	} else { ret.setName(""); }
				if (fatContent != null) { ret.setFatContent(fatContent.toString());	} else { ret.setFatContent(""); }
				if (category != null) { ret.setRecipeCategory(category.toString());	} else { ret.setRecipeCategory(""); }
				if (smallImageUrls != null) { ret.setImage(URI.create(smallImageUrls.toString()));	} else { ret.setImage(URI.create("https://app.jodileefoundation.org.au/images/no_image_thumb.gif")); }
				if (ingredient != null) { ret.addRecipeIngredient(ingredient.toString());	} else { ret.addRecipeIngredient(""); }
				if (numberOfServings != null) { ret.setRecipeYield(numberOfServings.toString());	} else { ret.setRecipeYield(""); }
				if (calories != null) { ret.setCalories(calories.toString());	} else { ret.setCalories(""); }
				if (sugarContent != null) { ret.setSugarContent(sugarContent.toString());	} else { ret.setSugarContent(""); }
				if (proteinContent != null) { ret.setProteinContent(proteinContent.toString());	} else { ret.setProteinContent(""); }
				if (sodiumContent != null) { ret.setSodiumContent(sodiumContent.toString());	} else { ret.setSodiumContent(""); }
				if (fiberContent != null) { ret.setFiberContent(fiberContent.toString());	} else { ret.setFiberContent(""); }
				if (cholesterolContent != null) { ret.setCholesterolContent(cholesterolContent.toString());	} else { ret.setCholesterolContent(""); }
				if (carbohydrateContent != null) { ret.setCarbohydrateContent(carbohydrateContent.toString());	} else { ret.setCarbohydrateContent(""); }
				if (transFatContent != null) { ret.setTransFatContent(transFatContent.toString());	} else { ret.setTransFatContent(""); }
				if (saturatedFatContent != null) { ret.setSaturatedFatContent(saturatedFatContent.toString());	} else { ret.setSaturatedFatContent(""); }
				if (unsaturatedFatContent != null) { ret.setUnsaturatedFatContent(unsaturatedFatContent.toString());	} else { ret.setUnsaturatedFatContent(""); }
				if (totalTime != null) { ret.setTotalTime(totalTime.toString());	} else { ret.setTotalTime("0"); }
				if (cookTime != null) { ret.setCookTime(cookTime.toString());	} else { ret.setCookTime("0"); }
				if (prepTime != null) { ret.setPrepTime(prepTime.toString());	} else { ret.setCookTime("0"); }
			}
			
		}
		catch (Exception e) {e.printStackTrace();}
		
		return ret;
	}
}
