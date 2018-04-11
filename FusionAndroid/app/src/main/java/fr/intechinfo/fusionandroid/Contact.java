package fr.intechinfo.fusionandroid;

public class Contact {
    String _name;
    String _number;
    public Contact(String name, String number){
        _name = name;
        _number = number;
    }

    public String GetName(){
        return _name;
    }
    public String GetNumber(){
        return _number;
    }
}
