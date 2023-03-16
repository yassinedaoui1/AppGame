package projet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;


public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, arial_80B;
	BufferedImage heart_full , heart_half , heart_blank,crystal_full,crystal_blank ;
	public boolean messageOn;
	ArrayList<String>message = new ArrayList<>();
	ArrayList<Integer>messageCounter = new ArrayList<>();
	public boolean gameFinished=false;
	public String currentDialogue = "";
	public int commandNum = 0 ;
	public int titleScreenState = 0 ;//0 : the first screen 
	public int slotCol = 0;
	public int slotRow = 0;

	public UI(GamePanel gp) {
		this.gp=gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT,is);
			arial_80B=new Font("Arial",Font.BOLD,80);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image ;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		
		
	}
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}
	
	
	public void draw(Graphics2D g2) {
		
	this.g2 =g2;
	g2.setFont(maruMonica);
	g2.setColor(Color.white);
	//TITLE state
	if(gp.gameState == gp.titleState) {
		drawTitleScreen();
	}
	//play state	
	if(gp.gameState == gp.playState) {
		drawPlayLife()	;
		drawMessage();
	}
	//PauseState
	if(gp.gameState == gp.pauseState) {
		drawPauseScreen();
	}
	//Dialogue State
	if(gp.gameState == gp.dialogueState) {
		drawPlayLife()	;
		drawDialogueScreen();
	}
	//character screen
	if(gp.gameState == gp.characterstate) {
		
		drawCharacterScreen();
		drawInventory();
	}
	if(gp.gameState == gp.gameOverstate) {
		drawGameOverScreen();
	}
	}
	private void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		text="Game Over";
		g2.setColor(Color.black);
		x= getXforCenteredText(text);
		y= gp.tileSize*4;
		g2.drawString(text, x, y);
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		g2.setFont(g2.getFont().deriveFont(50f));
		text="retry";
		x= getXforCenteredText(text);
		y+= gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y-4);	
		}
		
		text="Quit";
		x= getXforCenteredText(text);
		y+= 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y-4);	
		}
		
		
		
		
		
		
	}
	private void drawInventory() {
		int frameX = gp.tileSize * 9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		drawSubWindow(frameX , frameY , frameWidth , frameHeight);
		//slot
		final int slotXStart = frameX +20;
		final int slotYStart = frameY + 20;
		int slotX =  slotXStart;
		int slotY = slotYStart ;
		//draw player's items
				for(int i = 0  ; i < gp.player.inventory.size(); i++) {
					//equip cursor
					
					if(gp.player.inventory.get(i) == gp.player.currentWeapon 
							|| gp.player.inventory.get(i) == gp.player.currentShield) {
						g2.setColor(new Color(240,190,90));
						g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize,10, 10);
					}
					g2.drawImage(gp.player.inventory.get(i).down1 , slotX, slotY , null);
					slotX +=gp.tileSize;
					if(i == 4 || i == 9 || i == 14) {
						slotX = slotXStart ;
						slotY += gp.tileSize;}}		
		//cursor
		int cursorX = slotXStart + (gp.tileSize * slotCol);
		int cursorY = slotYStart + (gp.tileSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		//draw cursor
		g2.setColor(Color.white);
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		g2.setStroke(new BasicStroke(3));
		
		// description frame
		int dframeX = frameX;
		int dframeY = frameY + frameHeight;
		int dframeWidth = frameWidth;
		int dframeHeight = gp.tileSize*3;
		
		
		//descsription text
		int textX = dframeX + 20;
		int textY = dframeY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		int itemIndex  = getItemIndex();
		
		if(itemIndex < gp.player.inventory.size()) {
			
			drawSubWindow(dframeX , dframeY,dframeWidth,dframeHeight);
			for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
					
		}
	}
	
	public int getItemIndex() {
		int itemIndex = slotCol + (slotRow*5) ;
		return itemIndex;
	}
	private void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD , 32F));
		
		for(int i = 0 ; i < message.size() ; i++) {
			if(message.get(i) != null) {
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				int counter = messageCounter.get(i) + 1; //messageCounter++ but it is not possible for the array case
				messageCounter.set(i, counter); //set the counter to the array
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
				
			}
		}
		
	}
	private void drawCharacterScreen() {
		//create frame
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		//text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize ;
		final int lineHeight = 35 ;
		//Names
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Atack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("NextLevelExp", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight +10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight +15;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		//values
		int tailX = (frameX +frameWidth)-30;
		//Reset Text
		textY = frameY + gp.tileSize ; 
		String value;
		value = String.valueOf(gp.player.level);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.Life + "/" + gp.player.maxLife);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		
		
		
		value = String.valueOf(gp.player.strength);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.dexterity);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.attack);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.defense);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.exp);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		value = String.valueOf(gp.player.coin);
		textX = getXAlignToRightText(value , tailX);
		g2.drawString(value,textX,textY);
		textY += lineHeight;
		//weapon
		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-24, null);
		textY += gp.tileSize;
		//sword
		g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY-24, null);
		
		
	}
	private void drawPlayLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		//Draw blank heart
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null); 
			i++;
			x += gp.tileSize;
		}
		//Reset
	     x = gp.tileSize/2 ;
		 y = gp.tileSize/2;
		 i = 0;
		 
		 //Draw CurrentLife
		 while(i < gp.player.Life) {
				g2.drawImage(heart_half, x, y, null); 
				i++;
				if(i < gp.player.Life) {
					g2.drawImage(heart_full, x, y, null); 	
				}
				i++;
				x += gp.tileSize;
			}
		 //DRAW MAX MANA
		 x= (gp.tileSize/2) -5;
		 y = (int) (gp.tileSize*1.5);
		 i=0;
		 while(i < gp.player.maxMana) {
			 g2.drawImage(crystal_blank, x, y ,null);
			 i++;
			 x += 35;
		 }
		 //DRaw Mana
		 x= (gp.tileSize/2) -5;
		 y = (int) (gp.tileSize*1.5);
		 i=0;
		 while(i <gp.player.mana) {
			 g2.drawImage(crystal_full, x, y, null);
			 i++;
			 x += 35;

		 }
		 
		
		
	}
	private void drawTitleScreen() {
		if(titleScreenState == 0) {
			g2.setColor(new Color( 0,0,0));
			g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
	        //TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,85F));
			String text = "Blue Boy Adventure" ;
			int x=getXforCenteredText(text);
			int y=gp.tileSize*3 ;
			//shadow
			g2.setColor(Color.black);
			g2.drawString(text, x+5, y+5);
			//Main color
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			//Blue boy image
			
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2 ;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			//Menu
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,39F));
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*3.5;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}	
		}
		else if(titleScreenState  == 1) {
			//Class selection screen
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select your Level!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Level 1";
			x = getXforCenteredText(text);
			y += gp.tileSize*3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text = "Level 2";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text = "Level 3";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.tileSize*2;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
		}
		
		
		
	}
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x=getXforCenteredText(text);
		
		int y=gp.screenHeight/2;
		g2.drawString(text,x,y);
		
		
	}
	public void drawDialogueScreen() {
		//Window Dialogue
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width= gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		drawSubWindow(x,y,width,height);
		//text inside rectangle
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		x += gp.tileSize;
		y += gp.tileSize;
		//trj3 lster dakhl rectangle
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line,x,y);
			y += 40;

		}
	}
	public void drawSubWindow(int x,int y,int width, int height) {
		Color c = new Color(0,0,0,210); //200 transparence back ground
		g2.setColor(c);
		//Rectangle
		g2.fillRoundRect(x,y,width,height,35,35);
		c= new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5,y + 5,width - 10,height - 10,25,25);
		
	}
	public int getXforCenteredText(String text) {
	
		int length= (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x= gp.screenWidth/2 - length/2;
		return x;
		
	}public int getXAlignToRightText(String text , int tailX) {
	
		int length= (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		int x= tailX - length;
		return x;
		
	}
}
