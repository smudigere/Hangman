import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HangmanGUI {

    private JPanel panel;
    private JFrame jFrame1, jFrame2;
    private Font serif24;
    private List<String> words;

    private String randomWord;

    private char[] f;

    private JLabel mTries, mGuessText;
    private int mTriesCounter;

    HangmanGUI()    {
        serif24 = new Font("Serif", Font.PLAIN, 24);
    }

    /**
     * Set screen 1.
     */
    public void setScreen1() {

        setWords();
        //1. Create the frame.
        jFrame1 = new JFrame("Guess the Word");

        //2. Optional: What happens when the frame closes?
        jFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //4. Size the frame.
        jFrame1.setSize(650, 400);

        //5. Show it.
        jFrame1.setVisible(true);

        panel = (JPanel) jFrame1.getContentPane();
        panel.setLayout(null);

        JLabel title = new JLabel("Welcome to Hangman");
        title.setFont(serif24);
        panel.add(title);
        Dimension size = title.getPreferredSize();
        title.setBounds(200, 0, size.width, size.height);

        JLabel xxx = new JLabel("X X X X X X X X  X X X X X X X X");
        xxx.setFont(new Font("Serif", Font.BOLD, 36));
        panel.add(xxx);
        size = xxx.getPreferredSize();
        xxx.setBounds(20, 50, size.width, size.height);

        int x = 20;
        for (int i = 65; i <= 74; i++) {
            setCharButton(i, x, 120, false);
            x += 60;
        }

        x = 20;
        for (int i = 75; i <= 84; i++) {
            setCharButton(i, x, 180, false);
            x += 60;
        }

        x = 100;
        for (int i = 85; i <= 90; i++) {
            setCharButton(i, x, 240, false);
            x += 60;
        }

        final JButton startButton = new JButton("Start Game");
        startButton.setFont(serif24);
        panel.add(startButton);
        size = startButton.getPreferredSize();
        startButton.setBounds(80, 300, size.width, size.height);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame1.dispose();
                setScreen2();
            }
        });
    }


    /**
     * Sets the character buttons in the Frame.
     *
     * @param i ASCII value of the character.
     * @param xPos The x position of the button in the Panel.
     * @param yPos The y position of the button in the Panel.
     * @param clickable The button is clickable only in screen 2, but not in screen 1.
     */
    private void setCharButton(int i, int xPos, int yPos, boolean clickable) {
        final JButton jLabel = new JButton(String.valueOf((char) i));
        jLabel.setFont(serif24);
        panel.add(jLabel);
        Dimension size = jLabel.getPreferredSize();
        jLabel.setBounds(xPos, yPos, size.width, size.height);

        if (clickable) {
            jLabel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guess(jLabel);  //A guess of a character was made.
                }
            });
        }
    }


    /**
     * Set screen 2.
     */
    private void setScreen2()    {

        randomWord = words.get(new Random().nextInt(words.size())); //Choose a random word from the ArrayList.

        f = new char[randomWord.length()];  //Creating a char array with the number of characters in the randomWord.

        for (int i = 0; i < f.length; i++)  //Initializing the array with '-' character.
            f[i] = '-';

        mTriesCounter = 0;  //Number of characters clicked.
        //1. Create the frame.
        jFrame2 = new JFrame("Guess the Word");

        //2. Optional: What happens when the frame closes?
        jFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //4. Size the frame.
        jFrame2.setSize(1000, 400);

        //5. Show it.
        jFrame2.setVisible(true);

        panel = (JPanel) jFrame2.getContentPane();
        panel.setLayout(null);

        JLabel title = new JLabel("Welcome to Hangman");
        title.setFont(serif24);
        panel.add(title);
        Dimension size = title.getPreferredSize();
        title.setBounds(200, 0, size.width, size.height);

        mGuessText = new JLabel();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < randomWord.length(); i++)
            temp.append(" - ");

        mGuessText.setText(temp.toString());
        mGuessText.setFont(new Font("Serif", Font.BOLD, 44));
        panel.add(mGuessText);
        size = mGuessText.getPreferredSize();
        mGuessText.setBounds(80, 50, size.width + 200, size.height + 20);

        int x = 20;
        for (int i = 65; i <= 74; i++) {
            setCharButton(i, x, 120, true);
            x += 60;
        }

        x = 20;
        for (int i = 75; i <= 84; i++) {
            setCharButton(i, x, 180, true);
            x += 60;
        }

        x = 100;
        for (int i = 85; i <= 90; i++) {
            setCharButton(i, x, 240, true);
            x += 60;
        }

        final JButton startButton = new JButton("Stop Game");
        startButton.setFont(serif24);
        panel.add(startButton);
        size = startButton.getPreferredSize();
        startButton.setBounds(80, 300, size.width, size.height);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame2.dispose();
                setScreen1();
            }
        });

        mTries = new JLabel(" 0 tries");
        mTries.setForeground(Color.WHITE);
        mTries.setBackground(Color.BLACK);
        mTries.setOpaque(true);
        mTries.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(mTries);
        size = mTries.getPreferredSize();
        mTries.setBounds(10, 10, size.width + 15, size.height + 20);
    }


    /**
     * Read the file 'words.txt'. Add every word from the file to an array.
     */
    private void setWords() {
        words = new ArrayList<>();

        String line = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader("words.txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
                words.add(line);

            // Always close files.
            bufferedReader.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to open file '");
        }
    }


    /**
     * Method invoked when a character is pressed.
     * Checks if the character exists in the random word, and updates the UI.
     *
     * Also checks if the game is over. If yes, lets the user know and resets the game.
     *
     * @param jButton The Button with character was clicked {@link HangmanGUI#setCharButton(int, int, int, boolean)}
     */
    private void guess(JButton jButton)    {
        jButton.setVisible(false);          //Hide the character.
        mTriesCounter++;                    //Increment the counter.
        String guessedWord = "";

        mTries.setText(" " + mTriesCounter + " tries"); //Set the try counter.

        //Loop through the word and check if the clicked character exists in the word
        //and updates the character Array.
        for (int i = 0; i < randomWord.length(); i++)  {
            if (jButton.getText().charAt(0) == randomWord.charAt(i))
                f[i] = jButton.getText().charAt(0);
        }

        for (char aF : f) guessedWord += " " + aF + " ";    //An object to add the character that was guessed, if true.

        mGuessText.setText(guessedWord);    //Update UI.

        if (!guessedWord.contains("-")) {   //End Game and reset the game.
            JOptionPane.showMessageDialog(null, "YOU WINNN!!!");
            jFrame2.dispose();
            setScreen1();
        }
    }
}