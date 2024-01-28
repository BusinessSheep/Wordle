package wordleClone;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Wordle implements Runnable{

	private gamePanel panel;
	private Thread gameThread;
	private int FPS = 60;
	private boolean win = false;
	private boolean lost = false;
	private int attempt = 0;
	public String[] validWords;
	
	private char[][] allWords;
	private Color[][] colourHints;
	
	private String wordToWin;
	
	public Wordle() {
		initialize();
		panel = new gamePanel(this);
		new gameFrame(panel);
		panel.setFocusable(true);
		panel.requestFocus();
		startGame();
	}
	
	public static String[] readIn(String filename, String[] Array) {
    	int i=0;
    	try{
    		BufferedReader FileInputPointer = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
    		while(FileInputPointer.ready()==true){
    			Array[i] = FileInputPointer.readLine();
    			i++;
    			}//end while
    		FileInputPointer.close();
    		} // end try
    	
        catch (FileNotFoundException e)  {
            System.out.println("Error - this file does not exist");
         } 

        catch (IOException e)  {
            System.out.println("error=" + e.toString() );
         }
    	
    	return Array;
    	}
	
	public static int checkSize(String filename) {
    	int numRec=0;
    	try{
    		BufferedReader FileInputPointer = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
    		while(FileInputPointer.ready()==true){
    			FileInputPointer.readLine();
    			numRec ++;
    			}//end while
    		FileInputPointer.close();
    		} // end try
    	
        catch (FileNotFoundException e)  {
            System.out.println("Error - this file does not exist");
         } 

        catch (IOException e)  {
            System.out.println("error=" + e.toString() );
         }
    	
    	return numRec;
    	}
	
	public void initialize() {
		allWords = new char[6][5];
		colourHints = new Color[6][5];
		for(int i = 0; i < allWords.length; i++) {
			for(int j = 0; j < allWords[i].length; j++) {
				allWords[i][j] = ' ';
				colourHints[i][j] = new Color(255, 255, 255);
			}
			
		}
		int totalWords = checkSize("valid-wordle-words.txt");
		validWords = new String[totalWords];
		validWords = readIn("valid-wordle-words.txt", validWords);
		wordToWin = validWords[randomNumber(0, totalWords-1)].toUpperCase();
	}
	
	public static int randomNumber(int low, int high) {
		return (int) (Math.random() * (high - (low - 1))) + low;
	}
	
	public Color compareLetters(int column, int row) {
		if(allWords[row][column] == wordToWin.charAt(column)) {
			return Color.green;
		}
		for(int i = 0; i < wordToWin.length(); i++) {
			if(allWords[row][column] == wordToWin.charAt(i)) {
				return Color.yellow;
			}
		}
		return new Color(200, 200, 200);
	}
	
	public void checkWord() {
		String word = "";
		for(int i = 0; i < allWords[attempt].length; i++) {
			word += allWords[attempt][i];
			colourHints[attempt][i] = compareLetters(i, attempt);
		}
		if(word.compareTo(wordToWin) == 0) {
			win = true;
		} else {
			win = false;
			attempt++;
			if(attempt > 5) {
				lost = true;
				System.out.println(wordToWin);
			}
		}
	}
	
	public void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {

	}
	
	public void addLetter(char newLetter) {
		for(int i = 0; i < allWords[Math.min(5, attempt)].length; i++) {
			if(allWords[Math.min(5, attempt)][i] ==  ' ') {
				allWords[Math.min(5, attempt)][i] = newLetter;
				return;
			}
		}
	}
	
	public void deleteLetter() {
		for(int i = 0; i < allWords[Math.min(5, attempt)].length; i++) {
			if(allWords[Math.min(5, attempt)][i] ==  ' ' && i > 0) {
				allWords[Math.min(5, attempt)][i-1] = ' ';
				return;
			}
			
		}
		allWords[attempt][4] = ' ';
	}
	
	private void writeWords(Graphics g) {
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 100));
		for(int i = 0; i < allWords.length; i++) {
			for(int j = 0; j < allWords[Math.min(5, attempt)].length; j++) {
				g.setColor(colourHints[i][j]);
				g.fillRect(j*100, i*120, 100, 120);
				g.setColor(Color.black);
				g.drawString(String.valueOf(allWords[i][j]), 20 + j * 100, 100 + 120*i);
			}
		}
		
	}
	
	
	public void draw(Graphics g) {
		writeWords(g);
		if(win) {
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, 500, 720);
			g.setColor(Color.red);
			g.drawString("YAYYY", 100, 300);
	} 
			else if(lost) {
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, 500, 720);
			g.setColor(Color.red);
			g.drawString("BOOOO", 100, 300);
			g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 50));
			g.drawString(wordToWin, 180, 400);
		}
	}
	
	public int getCurrentWordLen() {
		int len = allWords[Math.min(5, attempt)].length;
		for(int i = 0; i < allWords[Math.min(5, attempt)].length; i++) {
			if(allWords[Math.min(5, attempt)][i] == ' ') {
				len = i;
				break;
			}
		}
		return len;
	}
	
	public boolean validWord(int start, int end){
		String word = "";
		for(int i = 0; i < allWords[attempt].length; i++) {
			word += allWords[attempt][i];
		}
		int middle = (start + end) / 2; // The middle index of the array
		if (end < start) { // Base case if value is not found
			return false;
		}
		if (word.compareTo(validWords[middle].toUpperCase()) < 0) {
			return validWord(start, middle - 1);
		}
		if (word.compareTo(validWords[middle].toUpperCase()) > 0) {
			return validWord(middle + 1, end);
		}
		if (word.equals(validWords[middle].toUpperCase())) { // Base case if value is found
			return true;
		}
		return false;
	}

	public boolean hasWon() {
		return win;
	}
	
	public boolean hasLost() {
		return lost;
	}
	
	public void setLost(boolean lost) {
		this.lost = lost;
	}
	
	public int getAttempt() {
		return attempt;
	}
	
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	
	@Override
	public void run() {
		double frameTime = 1000000000.0 / FPS;

        long lastTime = System.nanoTime();

        double timeSinceLastFrame = 0;

        while (true) {
            long currentTime = System.nanoTime();

            timeSinceLastFrame += (currentTime - lastTime) / frameTime;
            lastTime = currentTime;
            if (timeSinceLastFrame >= 1) { 
            	update();
                panel.repaint();
                timeSinceLastFrame--; // Don't set to 0 as a means of catching up if frames are lost
            }

       

        }
	}

	
}
