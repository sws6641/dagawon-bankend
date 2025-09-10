package com.bankle.common.asis.component;

public class FaCompHmac {

    private String hmac = null;  
    
    private FaCompHmac () {
        
    }
    
    private static class Singleton {
         private static final FaCompHmac instance = new FaCompHmac();           
    }
    
     public static FaCompHmac getInstance () {
         System.out.println("create instance");
         return Singleton.instance;
    }
     
    public String getHmac() {
        return hmac;
    }
    
    public void setHmac(String hmac) {
        this.hmac = hmac;
    }
    
}
