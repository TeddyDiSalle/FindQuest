package main;

import javax.swing.JFrame;

/**
 * The fruitless pursuit of wealth in the modern world
 *
 * haha funny trying to sell weed. slowly realize main character is trying to
 * provide for child, then a widow/widower, idk if male or female right now
 * Opening scene with weed van middle scene where snow *whooooooshes* and covers
 * path maybe dies at the end
 * 
 * boy left handed
 *
 * boy took this job for his friend Carl. Boy and carl met in 6th grade and
 * stayed pretty close. Up until sophmore year where they just got dragged apart
 * by different sports. boy played soccer and carl played baseball. They
 * promised that when they had time, they would hangout, they were good friends.
 * They hungout during summer, winter break, off seasons where alright but still
 * had practice. They took their new adventures seriously. Boy could not afford
 * college but carl got a scholarship since he was a smart kid. Carl went of to
 * a distant country to study but when carl came back, they reconnected. Just
 * coffee chats and reminscing over old times. They went to a few house parties
 * together, putt putt with a group, but Boy was pretty busy. They sort of
 * started where they left off but just time commitments took over most of their
 * lives. Eventually boy had basically no time to hangout with carl besides a
 * text message or sending a funny video on the train to work every now and
 * again. Cordial. Maybe out of pity or maybe out of desperation, carl (who
 * seemed to be doing well for himself) texted boy saying he needed help to pick
 * up these mysterious packages. Boy wass strapped for cash and this was from a
 * trusted friend. He was heisatant, wondering who his friend was now. Boy
 * accepted, imagining hiding from like the cops and suspicison would be his
 * biggest worries. He is imagining like walking up to ware house, knock knock,
 * hide it in his jacket.
 * 
 * boy dropped off by van, slides open, kicks him out, slides close
 *
 * There is an old man who already cleared an avlache and is not coming out. He
 * felt no one was thankful for his work
 *
 * There is another avalanche that blocks Boy's path
 *
 * Not much of an idea so far. Not sure where this story is really going, Teddy
 * What do you wish to convey here?
 *
 * @author Teddy DiSalle Last worked on: like 2024? 2023 winter? ITs been awhile
 */
public final class Main {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Main() {
    }

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Find Quest");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }

}
