package fr.intechinfo.fusionandroid;


public class SMS {
    private String Address;
    private String Body;
    private String Date;
    private String Type;

    public SMS(String address, String body, String date, String type){
        Address = address;
        Body = body;
        Date = date;
        Type = type;
    }

    public String GetAddress(){
        return Address;
    }
    public String GetBody(){
        return Body;
    }
    public String GetDate(){
        return Date;
    }
    public String GetType(){
        return Type;
    }


}
