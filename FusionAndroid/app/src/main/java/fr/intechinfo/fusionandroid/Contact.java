package fr.intechinfo.fusionandroid;

public class Contact {
    private String _name;
    private String _number;
    public Contact(){
    }

    public String GetName(){
        return _name;
    }
    public String GetNumber(){
        return _number;
    }
    public void SetName(String name) { _name = name; }
    public void SetNumber(String number) { _number = number; }
}
