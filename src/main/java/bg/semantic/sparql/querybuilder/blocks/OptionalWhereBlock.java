package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

/**
 * A class representing an OPTIONAL WHERE block surrounded by {} 
 * @author milen
 *
 */
public class OptionalWhereBlock extends AbstractCompoundBlock {

	public OptionalWhereBlock() {}

	public OptionalWhereBlock(List<WhereBlock> list, int weight) {		
		super(list,weight);
	}
	
	public OptionalWhereBlock(List<WhereBlock> list) {		
		super(list);
	}
	
	public OptionalWhereBlock(WhereBlock block, int weight) {
		super(block, weight);
	}

	public OptionalWhereBlock(int weight) {		
		super(weight);
	}
	
	@Override	
	public String setStatementString(int _index) {
		StringBuffer sb = new StringBuffer("");
		for (int i=0; i < unions.size(); i++ ) {
			List<WhereBlock> union = unions.get(i);
			if (count == 0) {
				sb.append(" OPTIONAL ");
			}
			sb.append(" { \n");
			for (WhereBlock s : union) {
				sb.append(s.setStatementString( _index));
			}
			sb.append(" } \n");
		}
		
		return sb.toString();
	}

}
