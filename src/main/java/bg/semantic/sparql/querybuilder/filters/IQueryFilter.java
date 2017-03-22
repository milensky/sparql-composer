package bg.semantic.sparql.querybuilder.filters;

import java.util.List;

import bg.semantic.sparql.querybuilder.QueryModel;
import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

/**
 * Interface for classes which generate QueryModel specific where blocks
 * @author milen
 *
 */
public interface IQueryFilter {

	public List<WhereBlock> filter(QueryModel queryModel);
}
