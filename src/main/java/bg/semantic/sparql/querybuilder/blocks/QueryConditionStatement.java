package bg.semantic.sparql.querybuilder.blocks;

import java.net.URI;
import java.net.URL;

import org.apache.jena.riot.thrift.wire.RDF_PrefixName;

import com.hp.hpl.jena.graph.Node_Variable;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import bg.semantic.sparql.querybuilder.RdfResource;
import bg.semantic.sparql.querybuilder.Variable;

public class QueryConditionStatement implements WhereBlock  {

	private Variable subject;
	private String condition;
	//private Object object;
	private RdfResource object;
	//private boolean optional;
	private boolean literal;
	private int weight;
	

	/*public QueryConditionStatement(Variable subject, String condition, 
			RdfResource object, boolean optional, boolean literal) {
		this.subject = subject;
		this.condition = condition;
		this.object = object;
		this.optional = optional;
		this.literal = literal;
		this.weight = 0;
	}

	public QueryConditionStatement(Variable subject, String condition, 
			RdfResource object, boolean optional, boolean literal,
			int weight) {
		this.subject = subject;
		this.condition = condition;
		this.object = object;
		this.optional = optional;
		this.literal = literal;
		this.weight = weight;
	}*/
/*
	public boolean isLiteral() {
		return literal;
	}

	public void setLiteral(boolean literal) {
		this.literal = literal;
	}
*/
	@Override
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public QueryConditionStatement(Variable subject, String condition, RdfResource object) {
		super();
		this.subject = subject;
		this.condition = condition;
		this.object = object;
	//	this.optional = false;
		this.literal = false;
	}
	
	public QueryConditionStatement(Variable subject, Property condition, RdfResource object) {
		super();
		this.subject = subject;
		this.condition = "<"+condition.getURI()+">";
		this.object = object;
	//	this.optional = false;
		this.literal = false;
	}
	
	//TODO to be refactored
	@Override
	public String setStatementString(int _index) {
		ParameterizedSparqlString sparqlString = new ParameterizedSparqlString();
		/*if (this.isOptional()) {
			sparqlString.append(" OPTIONAL ");
		}*/
		sparqlString.append(" { " + this.getSubject() +" " + this.getCondition() + " " + this.getObject()+" } \n");
	/*
		if (this.getObject() instanceof RDF_PrefixName) {
			RDF_PrefixName o = (RDF_PrefixName)this.getObject();
			sparqlString.append(" { " + this.getSubject() +" " + this.getCondition() + " " + o.prefix+":"+o.localName +" } \n");
		}
		else if (this.getObject() instanceof URI) {
			sparqlString.append(" { " + this.getSubject() +" " + this.getCondition() + " <" + this.getObject() +"> } \n");
		}
		else if (this.isLiteral()) {
			sparqlString.append(" { " + this.getSubject() +" " + this.getCondition() + " " + this.getObject().toString() + " } \n");
		}
		else {
			sparqlString.append(" { " + this.getSubject() +" " + this.getCondition() + " " + " ?var"+_index + " } \n");
			
			if (this.getObject() instanceof Node_Variable)
				sparqlString.setParam("?var"+_index, (Node_Variable)this.getObject());
			else if (this.getObject() instanceof String)
				sparqlString.setLiteral("?var"+_index, this.getObject().toString());
			else 
				sparqlString.setIri("?var"+_index,   this.getObject().toString() );
		}
		*/	
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

	public Variable getSubject() {
		return subject;
	}

	public String getCondition() {
		return condition;
	}

	public Object getObject() {
		return object;
	}

	public void setSubject(Variable subject) {
		this.subject = subject;
	}

	public void setCondition(String condition) {
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
