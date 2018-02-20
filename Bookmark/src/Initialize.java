
public class Initialize {
	
	public static void main(String[] args) {
		if (args.length > 0) {
			//figure out the input flags and pass them
			new BookmarkCLI();
		}
		else {
			new GUI();
		}
	}

}
