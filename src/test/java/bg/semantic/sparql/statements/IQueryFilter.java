package bg.semantic.sparql.statements;

import java.util.List;

import bg.semantic.sparql.querybuilder.blocks.WhereBlock;

/**
 * Interface for classes which generate QueryModel specific where blocks
 * @author milen
 *
 */
public interface IQueryFilter {

	List<WhereBlock> filter(QueryModel queryModel);

}
