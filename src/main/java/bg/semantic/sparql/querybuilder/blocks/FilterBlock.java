package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

public class FilterBlock extends AbstractCompoundBlock {
	int count = 0;
	
	protected FilterBlock() {}
	
	FilterBlock(int weight) {		
		super(weight);
	}

	FilterBlock(WhereBlock block, int weight) {
		super(block, weight);
	}

	FilterBlock(List<WhereBlock> list,int weight) {
		super(list,weight);		
	}

	@Override
	public String toStatementString(int _index) {
		StringBuffer sb = new StringBuffer("");
		for (int i=0; i < unions.size(); i++ ) {
			List<WhereBlock> union = unions.get(i);
			if (count++ == 0) {
			//	sb.append(" FILTER ( ");
			}
			sb.append(" ");
			int count = 0;
			for (WhereBlock s : union) {
				count++;
				sb.append(s.toStatementString( _index));
				if (count < union.size())
					sb.append(" && ");
			}
		//	sb.append("  ) \n");
		}
		
		return sb.toString();
	}


}
