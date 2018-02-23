package sparqlquery;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import bg.semantic.sparql.querybuilder.QueryModel;
import bg.semantic.sparql.querybuilder.QueryVariable;
import bg.semantic.sparql.querybuilder.SparqlQueryComposer;
import bg.semantic.sparql.querybuilder.StatementsFactory;
import bg.semantic.sparql.querybuilder.URIResource;
import bg.semantic.sparql.querybuilder.blocks.BindBlock;
import bg.semantic.sparql.querybuilder.blocks.BindStatement;
import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.FilterStatement;
import bg.semantic.sparql.querybuilder.blocks.OptionalWhereBlock;
import bg.semantic.sparql.querybuilder.blocks.UnionBlock;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.querybuilder.queryfilters.IQueryFilter;
import bg.semantic.sparql.querybuilder.queryfilters.TypeQueryFilter;

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

		this.queryModel = prepareTestQueryModel();

		try {
			prepareQuery(queryModel);
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
 
	private void prepareQuery( QueryModel queryModel) throws URISyntaxException {

		QueryVariable entityVar = StatementsFactory.queryVariable("entity");
		QueryVariable resourceVar = StatementsFactory.queryVariable("resource");
		queryComposer.addWhereBlock(StatementsFactory.queryCondition(resourceVar, 
				StatementsFactory.uriResource("microdata:item"), 
				StatementsFactory.queryVariable("entity")))
				.addWhereBlock(
						StatementsFactory.queryCondition(entityVar, 
								StatementsFactory.uriResource("rdf:type"), 
								StatementsFactory.uriResource("http://schema.org/Recipe")))
				.addWhereBlock(StatementsFactory.queryCondition(entityVar, 
						StatementsFactory.uriResource("recipe:image"), 
						StatementsFactory.queryVariable("image")))
				.addWhereBlock(StatementsFactory.queryCondition(entityVar, 
						StatementsFactory.uriResource("recipe:name"), 
						StatementsFactory.queryVariable("name")));
		
		List<WhereBlock> options = new ArrayList<>();
		options.add(StatementsFactory.queryCondition(StatementsFactory.queryVariable("ratingAgg"),
				new URIResource("http://schema.org/AggregateRating/ratingValue"),StatementsFactory.queryVariable("rating")));
		options.add(StatementsFactory.queryCondition(entityVar, new URIResource("recipe:aggregateRating"),
				StatementsFactory.queryVariable("ratingAgg")));
		OptionalWhereBlock owb = new OptionalWhereBlock(options);
		owb.addBlock(StatementsFactory.queryCondition(entityVar, new URIResource("recipe:aggregateRating"),
				StatementsFactory.queryVariable("ratingAgg")));
		queryComposer.addWhereBlock(owb);

		//List<WhereBlock> filters = new ArrayList<>();
		//filters.add(StatementsFactory.filterCondition("langMatches", Arrays.asList(new String[] {"lang(?name)", "\""+queryModel.getLang()+"\""} )));
		//filters.add(Statements.FilterCondition("app:customDate(?date) > \"2005-02-28T00:00:00Z\"^^xsd:dateTime", Arrays.asList(new String[]{})));
		
		QueryVariable randSort = StatementsFactory.queryVariable("randSort");
		BindBlock queryBindBlock = new BindBlock(new BindStatement("RAND()", randSort), 150);

		WhereBlock langFilter = StatementsFactory.filterCondition("langMatches",
				Arrays.asList(new String[] { "lang(?name)", "\"" + queryModel.getLang() + "\"" }));
		WhereBlock dateFilter = StatementsFactory.filterCondition(
				"skos:customDate(?date) > \"2005-02-28T00:00:00Z\"^^xsd:dateTime", 
				Arrays.asList(new String[]{}));		

		FilterBlock langFilterBlock = StatementsFactory.filterBlock(langFilter);
		
		UnionBlock union = new UnionBlock(
				StatementsFactory.queryCondition(entityVar, 
						StatementsFactory.uriResource("rdf:type"), resourceVar), 10);
		
		queryComposer.addSelectVariable(resourceVar)
				.addSelectVariable(StatementsFactory.queryVariable("image"))
				.addWhereBlock(union)
				.addWhereBlock(queryBindBlock)
				.addFilterBlock(langFilter)
				.addFilterBlock(dateFilter)
				.addOrderBy(StatementsFactory.queryVariable("rating"),"desc")
				.addOrderBy(randSort)				
				.setLimit(Integer.toString(queryModel.getMaxResult()))
				.setOffset(Integer.toString(queryModel.getStart()));

		for (IQueryFilter queryFilter : queryFilters) {
			queryComposer.addWhereBlocks(queryFilter.filter(queryModel));
		}

	}

	private QueryModel prepareTestQueryModel() {
		QueryModel qm = new QueryModel("");
		qm.setLang("en");
		qm.setQ(Arrays.asList(new String[] { "salt", "pepper", "apple" }));
		qm.setVegan(false);
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
