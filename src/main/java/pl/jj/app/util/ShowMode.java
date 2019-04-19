package pl.jj.app.util;

/**
 * @author JNartowicz
 */
public enum ShowMode {

    DAY,
    WEEK,
    MONTH,
    YEAR;

    public static final String SHOW_MODES = "ENUM_SHOW_MODE_VALUES";
    public static final ShowMode findByName(String name){
        if(name == null) return null;
        for(ShowMode showMode: ShowMode.values()){
            if(showMode.name().toLowerCase().equals(name.toLowerCase())){
                return showMode;
            }
        }
        return null;
    }

}
