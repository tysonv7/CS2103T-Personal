
/**
 * This class is used as the driver function for the TextBuddy
 * Implemented all the 5 operations which is to add, display, 
 * delete, clear and exit
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextBuddyDriver {
	final static String WELCOME_MESSAGE_PART_1 = "Welcome to TextBuddy. ";
	final static String WELCOME_MESSAGE_PART_2 = " is ready for use";
	private final static String ADD_COMMAND = "add";
	private final static String DISPLAY_COMMAND = "display";
	private final static String DELETE_COMMAND = "delete";
	private final static String CLEAR_COMMAND = "clear";
	private final static String EXIT_COMMAND = "exit";
	private static String FILENAME;
	static int lineCounter = 1;
	static FileWriter newFile;
	static BufferedWriter fileWriter;
	static Scanner commandScanner = new Scanner(System.in);
	static FileReader fileReader;
	static BufferedReader textReader;

	public void bootUp(String filename) throws IOException {
		fileInitializer(filename);
		TextBuddyDriver.printWelcomeMessage(filename);
		commandHandler();
	}

	private static void commandHandler() throws IOException {
		while (true) {
			String commandInput = commandScanner.next();
			if (commandInput.equals(ADD_COMMAND)) {
				addCommandExecution(commandScanner.nextLine());
			} else if (commandInput.equals(CLEAR_COMMAND)) {
				clearCommandExecution();
			} else if (commandInput.equals(DELETE_COMMAND)) {
				deleteCommandExecution(commandScanner.nextLine());
			} else if (commandInput.equals(DISPLAY_COMMAND)) {
				displayCommandExecution();
			} else if (commandInput.equals(EXIT_COMMAND)) {
				exitCommandExecution();
			}
		}
	}

	private static void exitCommandExecution() throws IOException {
		fileWriter.close();
		commandScanner.close();
		System.exit(0);
	}

	private static void displayCommandExecution() throws IOException {
		fileReader = new FileReader(FILENAME);
		textReader = new BufferedReader(fileReader);
		String line;
		int numberOfLines = 0;
		while ((line = textReader.readLine()) != null) {
			System.out.println(line);
			numberOfLines++;
		}
		if (numberOfLines == 0) {
			System.out.println(FILENAME + " is empty");
		}
		textReader.close();
		fileReader.close();
	}

	private static void deleteCommandExecution(String commandInput) throws IOException {
		fileReader = new FileReader(FILENAME);
		textReader = new BufferedReader(fileReader);
		int numberOfLinesToBeCopied = 0;
		String line;
		List<String> bufferList = new ArrayList<String>();// Used to store all
															// the inputs from
															// the file to be
		// transferred to a new file without having the line to be deleted
		String messageToBeDeleted = "";
		while ((line = textReader.readLine()) != null) {
			if (!line.substring(0, 1).equals(commandInput.substring(1))) {
				bufferList.add(line.substring(3));
				numberOfLinesToBeCopied++;
			} else {
				messageToBeDeleted = line.substring(3);
			}
		}
		lineCounter = 1;
		FileWriter afterDeleteFile = new FileWriter(FILENAME);
		BufferedWriter newFileWritter = new BufferedWriter(afterDeleteFile);
		for (int i = 0; i < numberOfLinesToBeCopied; i++) {
			newFileWritter.write(lineCounter + ". " + bufferList.get(i) + "\n");
			lineCounter++;
		}
		newFileWritter.flush();
		newFileWritter.close();
		System.out.println("deleted from " + FILENAME + ": \"" + messageToBeDeleted + "\"");
	}

	private static void clearCommandExecution() throws IOException {
		newFile = new FileWriter(FILENAME);
		lineCounter = 1;
		System.out.println("all content deleted from " + FILENAME);
	}

	private static void addCommandExecution(String textInput) throws IOException {
		String toBePrinted = lineCounter + "." + textInput; // to add the
															// numbering in
															// front of the text
		fileWriter.write(toBePrinted + "\n");
		fileWriter.flush();
		lineCounter++;
		System.out.println("added to " + FILENAME + " : " + "\"" + textInput.substring(1) + "\""); // to
																									// remove
																									// the
																									// empty
																									// space
																									// before
																									// the
																									// textInput
		return;

	}

	private static void printWelcomeMessage(String filename) {
		String printWelcomeMessage = WELCOME_MESSAGE_PART_1 + filename + WELCOME_MESSAGE_PART_2;
		System.out.println(printWelcomeMessage);
		return;
	}

	private static void fileInitializer(String filename) throws IOException {
		FILENAME = filename;
		newFile = new FileWriter(new File(FILENAME), true);
		fileWriter = new BufferedWriter(newFile);
		return;
	}
}