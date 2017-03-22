package sparqlquery;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.vocabulary.RDF;

import bg.semantic.sparql.querybuilder.QueryComposer;
import bg.semantic.sparql.querybuilder.QueryModel;
import bg.semantic.sparql.querybuilder.SparqlQueryComposer;
import bg.semantic.sparql.querybuilder.URIResource;
import bg.semantic.sparql.querybuilder.Variable;
import bg.semantic.sparql.querybuilder.blocks.BindBlock;
import bg.semantic.sparql.querybuilder.blocks.BindStatement;
import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.OptionalWhereBlock;
import bg.semantic.sparql.querybuilder.blocks.Statements;
import bg.semantic.sparql.querybuilder.blocks.UnionBlock;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.querybuilder.filters.IQueryFilter;
import bg.semantic.sparql.querybuilder.filters.TypeQueryFilter;

public class QueryComposerTest {

	private SparqlQueryComposer queryComposer;
	private List<IQueryFilter> queryFilters;
	private QueryModel queryModel;

	@Before
	public void setup() {

		queryComposer = new SparqlQueryComposer(/* paramererizedSparqlString */);
		queryComposer.addPrefixes(prepareTestNsPrefixes());
		queryFilters = new ArrayList<IQueryFilter>();
		// addQueryFilter(new KeywordQueryFilter());
		addQueryFilter(new TypeQueryFilter());
		// addQueryFilter(new NutritionQueryFilter());
		// addQueryFilter(new OptionsQueryFilter());

		// TODO make querymodel interface
		this.queryModel = prepareTestQueryModel();

		try {
			prepareQuery(queryComposer, queryModel);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {

		String sparql = queryComposer.getComposedQuery();
		System.out.println("Result: " + sparql);

	}

	private void addQueryFilter(IQueryFilter filter) {
		queryFilters.add(filter);
	}

	// TODO convert to builder pattern?
	private void prepareQuery(QueryComposer queryComposer, QueryModel queryModel) throws URISyntaxException {

		Variable entityVar = new Variable("entity");
		Variable resourceVar = new Variable("resource");
		queryComposer.addWhereBlock(Statements.QueryCondition(resourceVar, " microdata:item ", new Variable("entity")))
				.addWhereBlock(
						Statements.QueryCondition(entityVar, RDF.type, new URIResource("http://schema.org/Recipe")))
				.addWhereBlock(Statements.QueryCondition(entityVar, " recipe:image ", new Variable("image")))
				.addWhereBlock(Statements.QueryCondition(entityVar, " recipe:name ", new Variable("name")));
		// queryComposer.addWhere(new QueryConditionStatement("?ratingAgg", "
		// <http://schema.org/AggregateRating/ratingValue> ", new
		// Node_Variable("rating")));
		// queryComposer.addWhere(new QueryConditionStatement("?entity", "
		// <http://schema.org/Recipe/aggregateRating> ", new
		// Node_Variable("ratingAgg")));

		List<WhereBlock> options = new ArrayList<>();
		options.add(Statements.QueryCondition(new Variable("ratingAgg"),
				" <http://schema.org/AggregateRating/ratingValue> ", new Variable("rating")));
		options.add(Statements.QueryCondition(entityVar, "  <http://schema.org/Recipe/aggregateRating> ",
				new Variable("ratingAgg")));
		OptionalWhereBlock owb = new OptionalWhereBlock(options);
		owb.addBlock(Statements.QueryCondition(entityVar, "  <http://schema.org/Recipe/aggregateRating> ",
				new Variable("ratingAgg")));
		queryComposer.addWhereBlock(owb);

		List<WhereBlock> filters = new ArrayList<>();
		filters.add(Statements.FilterCondition("langMatches", Arrays.asList(new String[] {"lang(?name)", "\""+queryModel.getLang()+"\""} )));
		filters.add(Statements.FilterCondition("app:customDate(?date) > \"2005-02-28T00:00:00Z\"^^xsd:dateTime", 
				Arrays.asList(new String[]{})));
		
		BindBlock queryBindBlock = new BindBlock(new BindStatement("BIND", "RAND()", " ?randSort"), 150);

		WhereBlock filterStatement = Statements.FilterCondition("langMatches",
				Arrays.asList(new String[] { "lang(?name)", "\"" + queryModel.getLang() + "\"" }));
		FilterBlock queryFilterBlock = new FilterBlock(filters, 200);

		UnionBlock ub = new UnionBlock(Statements.QueryCondition(entityVar, RDF.type, resourceVar), 10);

		queryComposer.addWhereBlock(ub).addWhereBlock(queryBindBlock).addWhereBlock(queryFilterBlock);

		
		
		// TODO candidates for querymodel interface methods
		queryComposer.addSelectVariable(resourceVar).addSelectVariable(new Variable("image"))
				.addOrderBy("desc(?rating) ?randSort").setLimit(Integer.toString(queryModel.getMaxResult()))
				.setOffset(Integer.toString(queryModel.getStart()));

		for (IQueryFilter queryFilter : queryFilters) {
			queryComposer.addWhereBlock(queryFilter.filter(queryModel));
		}

	}

	private QueryModel prepareTestQueryModel() {
		QueryModel qm = new QueryModel("");
		qm.setLang("en");
		qm.setQ(Arrays.asList(new String[] { "salt", "pepper", "apple" }));
		qm.setVegan(true);
		qm.setSeafood(true);
		qm.setMaxFat("10000");
		return qm;
	}

	private Map<String, String> prepareTestNsPrefixes() {
		Map<String, String> prefixes = new HashMap<>();
		prefixes.put("moodle", "http://www.moodle.com/ontology#");
		prefixes.put("thesis", "http://www.semantic.bg/reciped#");
		prefixes.put("microdata", "http://www.w3.org/1999/xhtml/microdata#");
		prefixes.put("text", "http://jena.apache.org/text#");
		prefixes.put("recipe", "http://schema.org/Recipe/");
		prefixes.put("skos", "http://www.w3.org/2004/02/skos/core#");
		prefixes.put("xsd", "http://www.w3.org/2001/XMLSchema#");
		prefixes.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		prefixes.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		return prefixes;
	}


}
