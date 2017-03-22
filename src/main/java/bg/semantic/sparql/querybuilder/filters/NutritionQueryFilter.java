package bg.semantic.sparql.querybuilder.filters;

import org.apache.jena.graph.Node_Variable;

import bg.semantic.sparql.querybuilder.QueryComposer;
import bg.semantic.sparql.querybuilder.QueryConditionStatement;
import bg.semantic.sparql.querybuilder.QueryModel;

public class NutritionQueryFilter implements IQueryFilter {

	@Override
	public void filter(QueryComposer queryComposer, QueryModel queryModel) {
		if (queryModel.getNutrition() != null) {
			String minProtein = queryModel.getNutrition().getMinProtein();
			String maxProtein = queryModel.getNutrition().getMaxProtein();
			String minFat = queryModel.getNutrition().getMinFat();
			String maxFat = queryModel.getNutrition().getMaxFat();
			String minKCal = queryModel.getNutrition().getMinKCal();
			String maxKCal = queryModel.getNutrition().getMaxKCal();
	 
			QueryConditionStatement s = null;
			
	//		s = new QueryConditionStatement("?resource", " microdata:item ", new Node_Variable("entity"));
			queryComposer.addWhere(s);
			s = new QueryConditionStatement("?entity", "<http://schema.org/Recipe/nutrition>", new Node_Variable("nutrition"));
			queryComposer.addWhere(s);
			
			if (minProtein.equals("") == false) {
				s = new QueryConditionStatement("?nutrition", "<http://schema.org/NutritionInformation/proteinContent>", new Node_Variable("proteinContent"));
				queryComposer.addWhere(s);
				QueryConditionStatement f = new QueryConditionStatement("?proteinContent", " > ", new Float(minProtein));
				queryComposer.addFilter(f);
	 
			}
	
			if (maxProtein.equals("") == false) {
				s = new QueryConditionStatement("?nutrition", "<http://schema.org/NutritionInformation/proteinContent>", new Node_Variable("proteinContent"));
				queryComposer.addWhere(s);
				QueryConditionStatement f = new QueryConditionStatement("?proteinContent", " < ", new Float(maxProtein));
				queryComposer.addFilter(f);
			}
			
			if (minFat.equals("") == false) {
				s = new QueryConditionStatement("?nutrition", "<http://schema.org/NutritionInformation/fatContent>", new Node_Variable("fatContent"));
				queryComposer.addWhere(s);
				QueryConditionStatement f = new QueryConditionStatement("?fatContent", " > ", new Float(minFat));
				queryComposer.addFilter(f);
			}
			
			if (maxFat.equals("") == false) {
				s = new QueryConditionStatement("?nutrition", "<http://schema.org/NutritionInformation/fatContent>", new Node_Variable("fatContent"));
				queryComposer.addWhere(s);
				QueryConditionStatement f = new QueryConditionStatement("?fatContent", " < ", new Float(maxFat));
				queryComposer.addFilter(f);
			}
			
			if (minKCal.equals("") == false) {
				s = new QueryConditionStatement("?nutrition", "<http://schema.org/NutritionInformation/calories>", new Node_Variable("calories"));
				queryComposer.addWhere(s);
				QueryConditionStatement f = new QueryConditionStatement("?calories", " > ", new Float(minKCal));
				queryComposer.addFilter(f);
			}
			
			if (maxKCal.equals("") == false) {
				s = new QueryConditionStatement("?nutrition", "<http://schema.org/NutritionInformation/calories>", new Node_Variable("calories"));
				queryComposer.addWhere(s);
				QueryConditionStatement f = new QueryConditionStatement("?calories", " < ", new Float(maxKCal));
				queryComposer.addFilter(f);
			}
			
		}
	}

}
