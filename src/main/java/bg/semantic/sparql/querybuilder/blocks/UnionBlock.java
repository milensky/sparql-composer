package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

/**
 * A class representing a SPARQL UNION of blocks
 * @author milen
 *
 */
public class UnionBlock extends AbstractCompoundBlock  {

	
	public UnionBlock(int weight) {		
		super(weight);
	}
	
	public UnionBlock(WhereBlock block, int weight) {
		super(block, weight);
	}

	public UnionBlock(List<WhereBlock> list, int weight) {
		super(list, weight); 
	}

	public String setStatementString(int _index) {
		StringBuffer sb = new StringBuffer("");
		for (int i=0; i < unions.size(); i++ ) {
			List<WhereBlock> union = unions.get(i);
			if (count == 0) {
				sb.append(" UNION ");
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
