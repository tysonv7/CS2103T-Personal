/**
 * This class is used to store the main function
 * @author Khairul Rizqi Bin Mohd Shariff
 */
import java.io.IOException;

public class TextBuddy{
	public static void main(String[] args) throws IOException {
		TextBuddyDriver instance = new TextBuddyDriver();
		instance.bootUp(args[0]);
	}
}