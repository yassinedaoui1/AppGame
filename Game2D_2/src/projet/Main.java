package projet;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Aventure");
		GamePanel gameP = new GamePanel();
		window.add(gameP);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gameP.setupGame();
		gameP.startGameThread();
		
			
		
		
		
	}

}
