package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

public class FilterStatement implements WhereBlock {
	private int weight = 200;
	private String filterFunction;
	private String[] arguments;
	
	/*
	public FilterStatement(String filterFunction,String...  arguments) {
		this.filterFunction = filterFunction;
		this.arguments = arguments;
	}
	*/
	public FilterStatement(String filterFunction, List<String> arguments ) {
		this.filterFunction = filterFunction;
		this.arguments = (String[]) arguments.toArray();
	}
	
	@Override
	public String setStatementString(int _index) {
		ParameterizedSparqlString sb = new ParameterizedSparqlString(); 
		sb.append(filterFunction);
		if(arguments.length > 0) {
			sb.append("(");
			for (int i=0; i<arguments.length; i++) {
				sb.append(arguments[i]);
				if (i < arguments.length-1)
					sb.append(", ");
			}
			sb.append(")");
		}
		return sb.toString();
	}
	
	
	@Override
	public int compareTo(WhereBlock o) {		 
		return Integer.compare(this.weight, o.getWeight());
	}

	@Override
	public int getWeight() { 
		return weight;
	}



}