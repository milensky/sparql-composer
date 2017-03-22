package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

public class FilterBlock extends AbstractCompoundBlock {
	int count = 0;
	public FilterBlock(int weight) {		
		super(weight);
	}
	
	public FilterBlock(WhereBlock block, int weight) {
		super(block, weight);
	}

	public FilterBlock(List<WhereBlock> list,int weight) {
		super(list,weight);
		
	}

	@Override
	public String setStatementString(int _index) {
		StringBuffer sb = new StringBuffer("");
		for (int i=0; i < unions.size(); i++ ) {
			List<WhereBlock> union = unions.get(i);
			if (count++ == 0) {
				sb.append(" FILTER ( ");
			}
			sb.append(" ");
			int count = 0;
			for (WhereBlock s : union) {
				count++;
				sb.append(s.setStatementString( _index));
				if (count < union.size())
					sb.append(" && ");
			}
			sb.append("  ) \n");
		}
		
		return sb.toString();
	}


}
