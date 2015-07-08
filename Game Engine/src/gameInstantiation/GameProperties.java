package gameInstantiation;

public class GameProperties {

	private final String title;

	private final Class<?> mainClass;

	public GameProperties(String title, Class<?> mainClass) {
		this.title = title;
		this.mainClass = mainClass;
	}

	public String getTitle() {
		return title;
	}

	public Class<?> getMainClass() {
		return mainClass;
	}

	

	
}
