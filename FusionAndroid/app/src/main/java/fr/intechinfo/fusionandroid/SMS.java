package fr.intechinfo.fusionandroid;


public class SMS {
    private String _address;
    private String _body;
    private String _creator;
    private String _date;
    private String _type;

    public SMS(String address, String body, String creator, String date, String type){
        _address = address;
        _body = body;
        _creator = creator;
        _date = date;
        _type = type;
    }

    public String GetAddress(){
        return _address;
    }
    public String GetBody(){
        return _body;
    }
    public String GetCreator(){
        return _creator;
    }
    public String GetDate(){
        return _date;
    }
    public String GetType(){
        return _type;
    }


}
