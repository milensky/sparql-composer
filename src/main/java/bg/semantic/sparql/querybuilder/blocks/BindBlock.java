package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

public class BindBlock extends AbstractCompoundBlock {

	public BindBlock(WhereBlock block, int weight) {
		super(block, weight); 
	}

	public BindBlock(int weight) {		
		super(weight);
	}
	
	public BindBlock(List<WhereBlock> list, int weight) {
		super(list, weight);
	}

	@Override
	public String setStatementString(int _index) {
		StringBuffer sb = new StringBuffer("");
		for (int i=0; i < unions.size(); i++ ) {
			List<WhereBlock> union = unions.get(i);
 
			sb.append(" ");
			for (WhereBlock s : union) {
				sb.append(s.setStatementString( _index));
			}
			sb.append("  \n");
		}
		
		return sb.toString();
	}

}
