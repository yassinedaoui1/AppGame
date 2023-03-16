package projet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.Tile;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16 ;
	final int scale = 3 ; 
	public final int tileSize = originalTileSize * scale ;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12 ;
	public final int screenHeight = tileSize * maxScreenRow ;
	public final int screenWidth = tileSize * maxScreenCol;
	
	public  final int maxWorldCol = 50;
	public  final int maxWorldRow = 50;
	public final int maxMap=10;
	
	//FPS
	int FPS = 60;
	
	//system
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music=new Sound();
	Sound se=new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this) ;
	public UI ui=new UI(this);
	public EventHandler eHandler  = new EventHandler(this) ;
	public AssetSetter aSetter=new AssetSetter(this);
	Thread gameThread;
	
	//Entity and object
	public Player player  = new Player(this,keyH);
	public Entity obj[]=new Entity[20];
	public Entity npc[] = new Entity[10];
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public Entity monster[] = new Entity[20];
	public InteractiveTile iTile[] =new InteractiveTile[50];
	public ArrayList<Entity> particleList = new ArrayList<>();
	//Game state
	public int gameState;
	public final int titleState = 0 ;
	public final int playState = 1;
	public final int pauseState =2;
	public final int dialogueState = 3;
	public final int characterstate = 4;
	public final int gameOverstate = 6;
	
	
	
	
	
	GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		//playMusic(0);
		gameState=titleState;
		
	}
	public void retry() {
		player.restoreLifeAndMan();
		player.setDefaultPositions();
		aSetter.setNPC();
		aSetter.setMonster();
		
	}
	public void restart() {
		player.setDefaultValues();
		player.selectItems();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	
		
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount=0;
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval ; 
			timer += (currentTime - lastTime);
			lastTime = currentTime ;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				drawCount = 0 ;
				timer = 0 ;
			}
			
		}
	}
	public void update() {
		if(gameState == playState) {
			
		//player 	
	    player.update();
	//npc
	for(int i=0; i<npc.length;i++) {
		if(npc[i] != null) {
			npc[i].update();
		}
	}
		for(int i = 0 ; i < monster.length ; i++) {
			if(monster[i] != null) {
				if(monster[i].alive == true && monster[i].dying == false) {
					monster[i].update();	
				}
				if(monster[i].alive == false) {
					monster[i].checkDrop();
					monster[i] = null;
				}
				
			}
		}
		
		for(int i = 0 ; i < projectileList.size() ; i++) {
			if(projectileList.get(i) != null) {
				if(projectileList.get(i).alive == true) {
					projectileList.get(i).update();	
				}
				if(projectileList.get(i).alive == false) {
					projectileList.remove(i) ;
				}
				
			}
		}
		
		for(int i = 0 ; i < particleList.size() ; i++) {
			if(particleList.get(i) != null) {
				if(particleList.get(i).alive == true) {
					particleList.get(i).update();	
				}
				if(particleList.get(i).alive == false) {
					particleList.remove(i) ;
				}
				
			}
		}
		for(int i = 0; i<iTile.length;i++) {
			if(iTile[i] != null) {
				iTile[i].update();
			}
		}
	}
		if(gameState== pauseState) {
			//nothing
		}
	
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//Title screen
		if(gameState == titleState) {
		ui.draw(g2);	
		}
		//Others
		else {
			//Title
			tileM.draw(g2);
			//interactive Tile
			for(int i = 0; i < iTile.length;i++) {
				if(iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}
			
			//Add entities to the list
			entityList.add(player);
			
			for(int i = 0 ; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			for(int i = 0 ; i < obj.length ; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i = 0 ; i < monster.length ; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			for(int i = 0 ; i < projectileList.size() ; i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			for(int i = 0 ; i < particleList.size() ; i++) {
				if( particleList.get(i) != null) {
					entityList.add( particleList.get(i));
				}
			}
			
			//Sort the order of the ArrayList
			Collections.sort(entityList , new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldX, e2.worldY);
					return result;
				}
				
			});
			
			//Draw Entities
			for(int i = 0 ; i < entityList.size() ; i++) {
				entityList.get(i).draw(g2);
			}
			//Empty list
			entityList.clear();
			//UI
			ui.draw(g2);
		}
		g2.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}

}
