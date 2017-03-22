package bg.semantic.sparql.querybuilder.blocks;

/**
 * General interface representing a sparql statement
 * @author milen
 *
 */
public interface WhereBlock extends Comparable<WhereBlock> { 
	/**
	 * Returns the weight if the statement
	 * @return
	 */
	public int getWeight();
	
	/**
	 * Converts the statement to SPARQL and sets its weight 
	 * 
	 * @param _index is used for generating proper SPARQL variable names
	 * @return
	 */
	public String setStatementString(int _index);
}
