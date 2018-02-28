package bg.semantic.sparql.querybuilder.blocks;

import java.util.List;

import com.hp.hpl.jena.query.ParameterizedSparqlString;

import bg.semantic.sparql.resources.QueryVariable;

public class BindStatement implements WhereBlock {
	private int weight = 100;
	private String bindFunction;
	private String[] arguments;
	
	public BindStatement(String...  arguments) {
		this.bindFunction = "BIND";
		this.arguments = arguments;
	}
	
	public BindStatement(String bindFunction, QueryVariable var) {
		this.bindFunction = "BIND";
		this.arguments = new String[2];
		this.arguments[0] = bindFunction;
		this.arguments[1] = var.sparqlEncode();
	}
	
	
	public BindStatement(String bindFunction, List<String> arguments ) {
		this.bindFunction = bindFunction;
		this.arguments = (String[]) arguments.toArray();
	}
	
	@Override
	public int compareTo(WhereBlock o) {
		return Integer.compare(this.weight, o.getWeight());
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public String toStatementString(int _index) {
		ParameterizedSparqlString sb = new ParameterizedSparqlString(); 
		sb.append(bindFunction);
		sb.append("(");
		for (int i=0; i<arguments.length; i++) {
			sb.append(arguments[i]);
			if (i < arguments.length-1)
				sb.append(" as ");
		}
		sb.append(")");
		return sb.toString();
	}

}
