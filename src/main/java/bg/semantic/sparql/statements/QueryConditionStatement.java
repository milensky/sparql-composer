package bg.semantic.sparql.statements;

import java.net.URI;
import java.net.URL;

import org.apache.jena.riot.thrift.wire.RDF_PrefixName;

import com.hp.hpl.jena.graph.Node_Variable;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import bg.semantic.sparql.querybuilder.blocks.WhereBlock;
import bg.semantic.sparql.resources.QueryVariable;
import bg.semantic.sparql.resources.RdfResource;
import bg.semantic.sparql.resources.URIResource;

public class QueryConditionStatement implements WhereBlock  {

	private QueryVariable subject;
	private RdfResource condition;
	//private Object object;
	private RdfResource object;
	//private boolean optional;
	private boolean literal;
	private int weight;

	@Override
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	QueryConditionStatement(QueryVariable subject, URIResource prop, RdfResource object) {
		this.subject = subject;
		this.condition = prop;
		this.object = object;
	}

	@Override
	public String toStatementString(int _index) {
		ParameterizedSparqlString sparqlString = new ParameterizedSparqlString();
		/*if (this.isOptional()) {
			sparqlString.append(" OPTIONAL ");
		}*/
		sparqlString.append(" { " + this.getSubject().sparqlEncode() 
				+ " " + this.getCondition().sparqlEncode() 
				+ " " + this.getObject().sparqlEncode() + " } \n");

		return sparqlString.toString();
	}
	/*
	public QueryConditionStatement(Variable subject, String condition, 
			RdfResource object, boolean optional) {
		this(subject, condition, object);	
		this.optional = optional;
	}
	
	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}
*/
	@Override
	public String toString() {		
		return  this.subject + " " + this.condition + " " + this.object;
	}

	public QueryVariable getSubject() {
		return subject;
	}

	public RdfResource getCondition() {
		return condition;
	}

	public RdfResource getObject() {
		return object;
	}

	public void setSubject(QueryVariable subject) {
		this.subject = subject;
	}

	public void setCondition(RdfResource condition) {
		this.condition = condition;
	}

	public void setObject(RdfResource object) {
		this.object = object;
	}
	/*
	public void setObject(String object) {
		this.object = (String) object;
	}
	
	public void setObject(URL object) {
		this.object = (URL) object;
	}

	public void setObject(Float object) {
		this.object = (Float) object;
	}
	
	public void setObject(Integer object) {
		this.object = (Integer) object;
	}
	public void setObject(Node_Variable object) {
		this.object = (Node_Variable) object;
	}
*/
	@Override
	public int compareTo(WhereBlock o) {		 
		return Integer.compare(this.weight, o.getWeight());
	}
}
