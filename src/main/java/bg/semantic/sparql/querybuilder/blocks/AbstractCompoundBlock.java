package bg.semantic.sparql.querybuilder.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * A general class represenging a block of WHERE blocks. Specializations include OptionalWhereBlock, QueryBindBlock
 * QUeryFilterBlock, QueryUnionStatement
 * @author milen
 *
 */
public abstract class AbstractCompoundBlock implements WhereBlock {
	protected List<List<WhereBlock>> unions;
	protected static int count = 0;
	protected int weight;
	
	public AbstractCompoundBlock() {
		unions = new ArrayList<List<WhereBlock>>();
	}
	
	public AbstractCompoundBlock(int weight) {		
		this();
		count=0;
		this.weight = weight;
	}
	
	public AbstractCompoundBlock(List<WhereBlock> list) {
		this();
		unions.add(list);
	}

	public AbstractCompoundBlock(List<WhereBlock> list,int weight) {
		this(weight);
		unions.add(list);
	}

	public AbstractCompoundBlock(WhereBlock block,int weight) {
		this(weight);
		List<WhereBlock> list = new ArrayList<>();
		list.add(block);
		unions.add(list);
	}
	
	public AbstractCompoundBlock addBlock(WhereBlock block) {
		List<WhereBlock> list = new ArrayList<>();
		list.add(block);
		unions.add(list);
		return this;
	}
	
	public AbstractCompoundBlock addBlock(List<WhereBlock> block) {
		unions.add(block);
		return this;
	}
	@Override
	public final int compareTo(WhereBlock o) {		
		return Integer.compare(this.weight, o.getWeight());
	}

	@Override
	public int getWeight() {		 
		return weight;
	}
}
