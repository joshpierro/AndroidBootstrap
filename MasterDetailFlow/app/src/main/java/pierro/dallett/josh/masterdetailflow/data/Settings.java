package pierro.dallett.josh.masterdetailflow.data;



import java.util.ArrayList;

public class Settings {

    public CharSequence SearchText;
    public boolean FirstRun;
    public ArrayList<ArtistListItem> ArtistList;


    private static Settings instance = new Settings();
    public Settings(){
        this.FirstRun = true;
    }
    public static Settings getInstance(){
        return instance;
    }

}



