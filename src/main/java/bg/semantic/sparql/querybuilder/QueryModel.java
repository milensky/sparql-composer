package bg.semantic.sparql.querybuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class QueryModel   {
	
	private boolean vegan;	
	private boolean vegetarian;
	private boolean seafood;
	private String minKCal;
	private String maxKCal;
	private String maxProtein;
	private String minProtein;
	private String minFat;
	private String maxFat;
	private List<String> allowedCuisine = new ArrayList<String>();
	private List<String> allowedAllergy = new ArrayList<String>();
	private List<String> allowedCourse = new ArrayList<String>();
	private List<String> allowedHoliday = new ArrayList<String>();
	private List<String> allowedDiet = new ArrayList<String>();
	private List<String> q =  new ArrayList<String>();
	private String[] cookingTime;
	private String lang;
	private Map<String,String> cookingTimeOptions ;
	private int start = 0; // query page start
	private int maxResult = 20; // query page size
		

	public QueryModel(String s) {
		/*Map<String,String[]> params = new HashMap<String, String[]>();
		RequestUtil.parseParameters(params, s, "UTF-8");
		if (params.get("q") != null) setQ(Arrays.asList(params.get("q")));
	//	if (params.get("lang") != null) setLang(params.get("lang")[0]); else setLang("en");
		if (params.get("allowedDiet") != null) setAllowedDiet(Arrays.asList(params.get("allowedDiet")));
		if (params.get("allowedCuisine") != null) setAllowedCuisine(Arrays.asList(params.get("allowedCuisine")));
		if (params.get("allowedHoliday") != null) setAllowedHoliday(Arrays.asList(params.get("allowedHoliday")));
		if (params.get("allowedAllergy") != null) setAllowedAllergy(Arrays.asList(params.get("allowedAllergy")));
		if (params.get("allowedCourse") != null) setAllowedCourse(Arrays.asList(params.get("allowedCourse")));
		if (params.get("minKCal") != null) setMinKCal(params.get("minKCal")[0]);
		if (params.get("maxKCal") != null) setMaxKCal(params.get("maxKCal")[0]);
		if (params.get("minProtein") != null) setMinProtein(params.get("minProtein")[0]);
		if (params.get("maxProtein") != null) setMaxProtein(params.get("maxProtein")[0]);
*/		
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
 

	public List<String> getAllowedAllergy() {
		return allowedAllergy;
	}

	public void setAllowedAllergy(List<String> allowedAllergy) {
		this.allowedAllergy = allowedAllergy;
	}

	public List<String> getAllowedCourse() {
		return allowedCourse;
	}

	public void setAllowedCourse(List<String> allowedCourse) {
		this.allowedCourse = allowedCourse;
	}

	public List<String> getAllowedHoliday() {
		return allowedHoliday;
	}

	public void setAllowedHoliday(List<String> allowedHoliday) {
		this.allowedHoliday = allowedHoliday;
	}

	public List<String> getAllowedDiet() {
		return allowedDiet;
	}

	public void setAllowedDiet(List<String> allowedDiet) {
		this.allowedDiet = allowedDiet;
	}

	public List<String> getAllowedCuisine() {
		return allowedCuisine;
	}

	public void setAllowedCuisine(List<String> allowedCuisine) {
		this.allowedCuisine = allowedCuisine;
	}


	public boolean isValidQuery() {
		return (vegan || 
				vegetarian || 
				seafood || 
				allowedAllergy.size() != 0 ||
				allowedCourse.size() != 0 ||
				allowedCuisine.size() != 0||
				allowedDiet.size() != 0|| 
				allowedHoliday.size() != 0||
				 
				(q.size() > 0));
	}
	


	public String getMinKCal() {
		return minKCal;
	}


	public void setMinKCal(String minKCal) {
		this.minKCal = minKCal;
	}


	public String getMaxKCal() {
		return maxKCal;
	}


	public void setMaxKCal(String maxKCal) {
		this.maxKCal = maxKCal;
	}


	public String getMaxProtein() {
		return maxProtein;
	}


	public void setMaxProtein(String maxProtein) {
		this.maxProtein = maxProtein;
	}


	public String getMinProtein() {
		return minProtein;
	}


	public void setMinProtein(String minProtein) {
		this.minProtein = minProtein;
	}


	public String getMinFat() {
		return minFat;
	}


	public void setMinFat(String minFat) {
		this.minFat = minFat;
	}


	public String getMaxFat() {
		return maxFat;
	}


	public void setMaxFat(String maxFat) {
		this.maxFat = maxFat;
	}

	public QueryModel() {
		lang = "EN";
		cookingTimeOptions = new LinkedHashMap<String, String>();
		cookingTimeOptions.put("any","Any");
		cookingTimeOptions.put("short","30 min");
		cookingTimeOptions.put("normal","30 min-2h");
		cookingTimeOptions.put("long","2h+");
		//allowedCuisine = new ArrayList<>();
		//q= new ArrayList<String>();
	}
	


	public Map<String, String> getRadioOptions() {
		return cookingTimeOptions;
	}

	public String[] getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(String[] cookingTime) {
		this.cookingTime = cookingTime;
	}
	public boolean isVegan() {
		return vegan;
	}
	
	public void setVegan(boolean vegan) {
		this.vegan = vegan;
	}
	
	public boolean isVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}
	public boolean isSeafood() {
		return seafood;
	}
	 
	public void setSeafood(boolean seafood) {
		this.seafood = seafood;
	}
 
	public List<String> getQ() {
		return q;
	}
 
	public void addQ(String q) {
		this.q.add(q);
	} 
	
	public void setQ(List<String> q) {
		for (String _q : q) {
			if (_q.contains(",")) { 
				String [] subQs = _q.split(",");
				for (int i=0; i< subQs.length; i++) {
					this.q.add(subQs[i].trim());
				}
			}
			else {
				this.q.add(_q);
			}
		}
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
 

}
