package com.ustb.utils;

public class Data {
	  
	        private int id;
		    private String clientno;  
		    private String validfrom;  
		    private String validto;  
		    private String userid;
		    private String description;
		  
		    public Data() {  
		        super();  
		    }  
		  
		    public Data(int id, String clientno, String validfrom, String validto, String userid,
		    		String description) {  
		        super(); 
		        this.id=id;
		       this.clientno = clientno;  
		        this.validfrom = validfrom;  
		        this.validto = validto;  
		        this.userid = userid;
		        this.description = description;
		        
		    }  
		  
		    public int getId() {  
		        return id;  
		    }  
		  
		    public void setId(int id) {  
		        this.id = id;  
		   }  
		  
		    
		    public String getClieneno() {  
		        return clientno;  
		    }  
		  
		    public void setClienenot(String clientno) {  
		        this.clientno = clientno;  
		   }  
		  
		   public String getValidfrom() {  
		        return validfrom;  
		    } 
		   
		    public void setValidfrom(String validfrom) {  
		       this.validfrom = validfrom;  
		    }  
		  
		    public String getValidto() {  
		       return validto;  
		    }  
		 
		    public void setValidto(String validto) {  
		        this.validto = validto;  
		    }  
		  
		    public String getUserid() {  
		        return userid;  
		    }  
		  
		    public void setUserid(String userid) {  
		        this.userid = userid;  
		   }  
		    
		    public String getDescription() {  
		        return description;  
		    }  
		  
		    public void setDescription(String description) {  
		        this.description = description;  
		   }  
		    
		   @Override  
		   public String toString() {  
		        return "Data [id=" + id + ", clientno=" + clientno + ", validfrom="  
		                + validfrom+", validto=" + validto+ ", userid="+userid+",description="+description+"]";  
		    }  
		  
		

}
