package fr.intechinfo.fusionandroid;

public class Contact {
    private String Name;
    private String Number;
    public Contact(){
    }

    public String GetName(){
        return Name;
    }
    public String GetNumber(){
        return Number;
    }
    public void SetName(String name) { Name = name; }
    public void SetNumber(String number) { Number = number; }

}
