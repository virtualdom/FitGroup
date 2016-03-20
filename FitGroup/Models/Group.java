package FitGroup.models;

import java.util.Date;

public class Group {
	    //private int ID;
	    private String name;
	    private String createDate;
	    private String IC;
	    
	    public Group (String name, String createDate, String IC){
	        this.name = name;
	        this.createDate = createDate;
	        this.IC = IC;
	    }

	    public String getname () {
	        return name;
	    }

	    public String getCreateDate () {
	        return createDate;
	    }

	    public String getIC () {
	        return IC;
	    }

	    public void SetIC (String IC) {
	        this.IC=IC;
	    }
}
