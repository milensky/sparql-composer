package bg.semantic.sparql.querybuilder.filters;

import java.net.MalformedURLException;
import java.net.URI;

import javax.swing.text.html.HTML.Tag;

import org.springframework.context.ApplicationContext;

import bg.semantic.sparql.querybuilder.QueryComposer;
import bg.semantic.sparql.querybuilder.QueryConditionStatement;
import bg.semantic.sparql.querybuilder.QueryModel;
import ws.semweb.thesis.app.ApplicationContextProvider;
import ws.semweb.thesis.datamodel.domainspecific.QueryModifier;
import ws.semweb.thesis.lang.StemService;

public class KeywordQueryFilter implements IQueryFilter {
	private StemService stemService;
	private QueryModifier queryHelper;
	
	public KeywordQueryFilter() {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		stemService = (StemService) context.getBean("stemService");
		queryHelper = (QueryModifier) context.getBean("domainQueryHelper");
	}
	
	@Override
	public void filter(QueryComposer queryComposer,QueryModel queryModel) {
		for (String w : queryModel.getQ()) {
			String word = stemService.stemTerm(w, "en");
			URI wordUri = queryHelper.uriForTag(new Tag(word,"en"));
			if (wordUri != null) {
				try {
					QueryConditionStatement s = new QueryConditionStatement("?resource", " thesis:relatedTo ", wordUri.toURL());
					queryComposer.addWhere(s);
				}
				catch (MalformedURLException e) {
				
				}
			}
			else {
				QueryConditionStatement s = new QueryConditionStatement("?resource", " microdata:item ", " ?search ", true);
				//queryComposer.addWhere(s);
				//s =	new QueryConditionStatement("?entity", "  text:query ", "(recipe:name '"+w+"')", false,true,-10);
				//queryComposer.addWhere(s);
				s = new QueryConditionStatement("?entity", " text:query ", "(recipe:ingredients '"+w+"')", false,true,-8);
				queryComposer.addWhere(s);
			}
		//	paramererizedSparqlString.append(" { ?resource thesis:relatedTo "+q+" } . \n");
		}
	}

}
