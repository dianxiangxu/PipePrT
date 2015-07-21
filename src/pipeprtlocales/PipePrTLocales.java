package pipeprtlocales;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PipePrTLocales {
	
	private static ResourceBundle resourceBundle;
	
	private static String[][] validLocales ={
		{"en", "US"},
		{"zh", "CN"}
	};

	private static boolean isLocaleSupported(String locale, String country){
		for (int index=0; index<validLocales.length; index++)
			if (validLocales[index][0].equals(locale) && validLocales[index][1].equals(country))
				return true;
		return false;
	}
	
	public static void setResourceBundle(){
		String locale = System.getProperty("user.language");
		String country = System.getProperty("user.country");
		if (isLocaleSupported(locale, country)){
			try {
//				resourceBundle = ResourceBundle.getBundle("pipeprtlocales/MessagesBundle", new Locale("zh", "CN"));
//				resourceBundle = ResourceBundle.getBundle("pipeprtlocales/MessagesBundle", new Locale("en", "US"));
				resourceBundle = ResourceBundle.getBundle("pipeprtlocales/MessagesBundle", new Locale(locale, country));
			} catch (MissingResourceException e){
				resourceBundle = ResourceBundle.getBundle("pipeprtlocales/MessagesBundle", new Locale("en", "US"));
			}
		} else
			resourceBundle = ResourceBundle.getBundle("pipeprtlocales/MessagesBundle", new Locale("en", "US"));
	} 
	
	public static String bundleString(String name){
		try {
			String str = resourceBundle.getString(name.toUpperCase().replace(" ", "_").replace("-", "_"));
			return str;
		}
		catch (Exception e){
			return name;
		}
	}
	
	public static void main(String arg[]){
		setResourceBundle();
		System.out.println(bundleString("Function net"));
		System.out.println(bundleString("Verify_Transition_Reachability"));
	}
}
