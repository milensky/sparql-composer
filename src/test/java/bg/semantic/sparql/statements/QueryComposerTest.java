package bg.semantic.sparql.statements;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import bg.semantic.sparql.querybuilder.SparqlQueryComposer;
import bg.semantic.sparql.querybuilder.blocks.BindBlock;
import bg.semantic.sparql.querybuilder.blocks.BindStatement;
import bg.semantic.sparql.querybuilder.blocks.BlockFactory;
import bg.semantic.sparql.querybuilder.blocks.FilterBlock;
import bg.semantic.sparql.querybuilder.blocks.OptionalWhereBlock;
import bg.semantic.sparql.querybuilder.blocks.UnionBlock;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.resources.QueryVariable;
import bg.semantic.sparql.resources.ResourceFactory;
import bg.semantic.sparql.resources.URIResource;
import bg.semantic.sparql.statements.FilterStatement;

public class QueryComposerTest {

	private SparqlQueryComposer queryComposer;
	private List<IQueryFilter> queryFilters;
	private QueryModel queryModel;

	@Before
	public void setup() {

		queryComposer = new SparqlQueryComposer();
		queryComposer.addPrefixes(prepareTestNsPrefixes());
		queryFilters = new ArrayList<IQueryFilter>();
		
		addQueryFilter(new TypeQueryFilter());

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

		QueryVariable entityVar = ResourceFactory.queryVariable("entity");
		QueryVariable resourceVar = ResourceFactory.queryVariable("resource");
		queryComposer.addWhereBlock(StatementFactory.queryCondition(resourceVar, 
				ResourceFactory.uriResource("microdata:item"), 
				ResourceFactory.queryVariable("entity")))
				.addWhereBlock(
						StatementFactory.queryCondition(entityVar, 
								ResourceFactory.uriResource("rdf:type"), 
								ResourceFactory.uriResource("http://schema.org/Recipe")))
				.addWhereBlock(StatementFactory.queryCondition(entityVar, 
						ResourceFactory.uriResource("recipe:image"), 
						ResourceFactory.queryVariable("image")))
				.addWhereBlock(StatementFactory.queryCondition(entityVar, 
						ResourceFactory.uriResource("recipe:name"), 
						ResourceFactory.queryVariable("name")));
		
		List<WhereBlock> options = new ArrayList<>();
		options.add(StatementFactory.queryCondition(
				ResourceFactory.queryVariable("ratingAgg"),
				ResourceFactory.uriResource("http://schema.org/AggregateRating/ratingValue"),
				ResourceFactory.queryVariable("rating")));
		options.add(
				StatementFactory.queryCondition(entityVar, 
				ResourceFactory.uriResource("recipe:aggregateRating"),
				ResourceFactory.queryVariable("ratingAgg")));
		OptionalWhereBlock owb = new OptionalWhereBlock(options);
		owb.addBlock(
				StatementFactory.queryCondition(entityVar, 
				ResourceFactory.uriResource("recipe:aggregateRating"),
				ResourceFactory.queryVariable("ratingAgg")));
		queryComposer.addWhereBlock(owb);

		//List<WhereBlock> filters = new ArrayList<>();
		//filters.add(StatementsFactory.filterCondition("langMatches", Arrays.asList(new String[] {"lang(?name)", "\""+queryModel.getLang()+"\""} )));
		//filters.add(Statements.FilterCondition("app:customDate(?date) > \"2005-02-28T00:00:00Z\"^^xsd:dateTime", Arrays.asList(new String[]{})));
		
		QueryVariable randSort = ResourceFactory.queryVariable("randSort");
		BindBlock queryBindBlock = new BindBlock(new BindStatement("RAND()", randSort), 150);

		WhereBlock langFilter = StatementFactory.filterCondition("langMatches",
				Arrays.asList(new String[] { "lang(?name)", "\"" + queryModel.getLang() + "\"" }));
		WhereBlock dateFilter = StatementFactory.filterCondition(
				"skos:customDate(?date) > \"2005-02-28T00:00:00Z\"^^xsd:dateTime", 
				Arrays.asList(new String[]{}));		

		FilterBlock langFilterBlock = BlockFactory.filterBlock(langFilter);
		
		UnionBlock union = new UnionBlock(
				StatementFactory.queryCondition(entityVar, 
						ResourceFactory.uriResource("rdf:type"), resourceVar), 10);
		
		queryComposer.addSelectVariable(resourceVar)
				.addSelectVariable(ResourceFactory.queryVariable("image"))
				.addWhereBlock(union)
				.addWhereBlock(queryBindBlock)
				.addFilterBlock(langFilter)
				.addFilterBlock(dateFilter)
				.addOrderBy(ResourceFactory.queryVariable("rating"),"desc")
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
