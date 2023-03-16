package entity;

import java.awt.Color;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import object.OBJ_Axe;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Sword;
import object.OBJ_shield;
import projet.UI;


import projet.GamePanel;
import projet.KeyHandler;
import projet.UtilityTool;

public class Player extends Entity{
	

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public boolean attackcanceled = false ;
	public ArrayList<Entity>inventory = new ArrayList<>();
	public final int maxinventorySize = 20;

	
	public Player(GamePanel gp , KeyHandler keyH) {
		super(gp);
		this.keyH=keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
		//solid Area
		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		//Attack area
		//attackArea.width = 36;
		//attackArea.height = 36;
	}
	private void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
			
	}
	public void setDefaultValues() {
		worldX= gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction ="down";
		
		//player status
		maxLife = 6;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		Life = maxLife ;
		level = 1;
		strength = 1;
		dexterity = 1;
		exp=0;
		nextLevelExp  = 5;
		coin = 0;
		//currentWeapon = new OBJ_Sword(gp);
		currentWeapon = new OBJ_Axe(gp);
		currentShield = new OBJ_shield(gp);
		projectile = new OBJ_Fireball(gp);
		//projectile = new OBJ_Rock(gp);	
		attack = getAttack();
		defense = getDefense();
	}
	public void setDefaultPositions() {
		worldX= gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction ="down";
	}
	public void restoreLifeAndMan() {
		Life = maxLife ;
		mana = maxMana;
		invincible=false;
		
	}
	private int getDefense() {
		return defense = dexterity * currentShield.defensevalue ;
	}
	private int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackvalue;
	}
	public void getPlayerImage() {
		up1=setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2=setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1=setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
		down2=setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
		left1=setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
		left2=setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
		right1=setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
		right2=setup("/player/boy_right_2",gp.tileSize,gp.tileSize);
	}
	public void getPlayerAttackImage()	 {
		
		if(currentWeapon.type == type_sword) {
			aup1 = setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
			aup2 = setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
			adown1 = setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
			adown2 = setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
			aleft1 = setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
			aleft2 = setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
			aright1 = setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
			aright2 = setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);	
		}
		if(currentWeapon.type == type_axe) {
			aup1 = setup("/player/boy_axe_up_1",gp.tileSize,gp.tileSize*2);
			aup2 = setup("/player/boy_axe_up_2",gp.tileSize,gp.tileSize*2);
			adown1 = setup("/player/boy_axe_down_1",gp.tileSize,gp.tileSize*2);
			adown2 = setup("/player/boy_axe_down_2",gp.tileSize,gp.tileSize*2);
			aleft1 = setup("/player/boy_axe_left_1",gp.tileSize*2,gp.tileSize);
			aleft2 = setup("/player/boy_axe_left_2",gp.tileSize*2,gp.tileSize);
			aright1 = setup("/player/boy_axe_right_1",gp.tileSize*2,gp.tileSize);
			aright2 = setup("/player/boy_axe_right_2",gp.tileSize*2,gp.tileSize);	
		}
		
	}
    public void update() {
    	
    	if(attacking == true) {
    		attacking();
    	}
    	else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || 
			keyH.rightPressed == true || keyH.enterPressed == true) {
		if(keyH.upPressed == true) {
			direction = "up";
		}
		else if(keyH.downPressed == true) {
			direction = "down";
		}
		else if(keyH.leftPressed == true) {
			direction = "left";
		}
		else if(keyH.rightPressed == true) {
			direction = "right";
		}
		//Tile Collision
		collisionOn = false ;
		gp.cChecker.checkTile(this);
		//Object Collision
		int objIndex=gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		// CHECK NPC COLLISION 
		int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
		interactNPC(npcIndex);
		//check monster collision
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
		contactMonster(monsterIndex);
		//Check Interactive Tile Colision
		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile );
		//Event Collision
		gp.eHandler.checkEvent();
		
		
		//collision is false
		if(collisionOn == false && gp.keyH.enterPressed == false) {
			switch(direction) {
			case "up":worldY -= speed;break;
			case "down":worldY += speed;break;
			case "left":worldX -= speed;break;
			case "right":worldX += speed;break;
			}
		}
		if(keyH.enterPressed == true && attackcanceled == false) {
			attacking = true ;
			spriteCounter = 0;
		}
		gp.keyH.enterPressed =	false;
		spriteCounter++;
		if(spriteCounter > 10) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter=0;
		}
		
	}
    	
    	if(gp.keyH.shotKeyPressed == true && projectile.alive == false
    			&& shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
    		
    		
    		//set default coordinates , direction and user
    	   projectile.set(worldX , worldY , direction , true , this);
    	   //SUBTRACT THE COST (Mana , Ammo ETc.)
    	   projectile.subtractResource(this);
    	   
    	   
    	   //add it to the list
    	   
    	   gp.projectileList.add(projectile);
    	   
    	   shotAvailableCounter = 0 ;
    	   
    	   gp.playSE(9);
    	}
	if(invincible == true) {
		invinciblecounter ++ ;
		if(invinciblecounter > 60) {
			invincible = false ;
			invinciblecounter = 0;
		}
	}
	if(shotAvailableCounter < 30) {
		shotAvailableCounter++;
	}
	if(Life >maxLife) {
		Life = maxLife;
	}
	if(mana > maxMana) {
		mana = maxMana;
	}
	if(Life<=0) {
		gp.gameState=gp.gameOverstate;
		gp.ui.commandNum=-1;
		gp.stopMusic();
		gp.playSE(12);
	}
}
private void attacking() {
	
		spriteCounter++;
		if(spriteCounter <=5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
		}
		//save the xurrent worldX , worldY , solidArea
		int currentWorldX = worldX;
		int currentWorldY = worldY;
		int solidAreaWidth = solidArea.width;
		int solidAreaHeight = solidArea.height;
		
		switch(direction) {
		case "up": worldY -= attackArea.height;break;
		case "down": worldY += attackArea.height;break;
		case "left": worldX -= attackArea.width;break;
		case "right": worldX += attackArea.width;break;
		}
		//attackArea
		solidArea.width = attackArea.width;
		solidArea.height = attackArea.height;
		//check monster collision
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
		damageMonster(monsterIndex , attack);
		int iTileIndex=gp.cChecker.checkEntity(this, gp.iTile);
		damageInteractiveTile(iTileIndex);
		
		
		
		worldX = currentWorldX;
		worldY = currentWorldY;
		solidArea.width = solidAreaWidth;
		solidArea.height = solidAreaHeight ;
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
void damageMonster(int i , int attack) {
	if(i != 999) {
		if(gp.monster[i].invincible == false) {
			gp.playSE(5);
			int damage = attack - gp.monster[i].defense;
			if(damage < 0) {
				damage = 0;
			}
			gp.monster[i].Life -= damage;
			gp.ui.addMessage(damage + "damage");
			gp.monster[i].invincible = true ;
			gp.monster[i].damageReaction();
			if(gp.monster[i].Life <=0) {
				gp.monster[i].dying = true ;
				gp.ui.addMessage("killed the " + gp.monster[i].name + "!");
				gp.ui.addMessage("Exp" + gp.monster[i].exp);
				exp += gp.monster[i].exp ;
				checkLevel();
			}
		}
	}
	
	
}
	public void damageInteractiveTile(int i) {
		if(i != 999	&& gp.iTile[i].destructible == true  && gp.iTile[i].isCorrectItem(this) == true && gp.iTile[i].invincible == false) {
			gp.iTile[i].playSE();
			gp.iTile[i].Life--;
				gp.iTile[i].invincible = true;
				
				generateParicle(gp.iTile[i],gp.iTile[i]);
			if(gp.iTile[i].Life == 0) {
				gp.iTile[i] = gp.iTile[i].getDestroyedForm();	
			}
				
		}
		
	}
private void checkLevel() {
	if(exp >= nextLevelExp) {
		level++;
		nextLevelExp = nextLevelExp*2;
		maxLife += 2;
		strength++;
		dexterity++;
		attack = getAttack();
		defense = getDefense();
		
		gp.playSE(8);
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You are level" +level+ "now!\n" ;
	}
}
public void selectItems() {
	int itemIndex = gp.ui.getItemIndex();
	if(itemIndex < inventory.size()) {
		Entity selectedItem = inventory.get(itemIndex);
		if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
			currentWeapon = selectedItem;
			attack = getAttack();
			getPlayerAttackImage();
		}
		if(selectedItem.type == type_shield) {
			currentShield = selectedItem;
			defense = getDefense();
		}
		if(selectedItem.type == type_consumable) {
			if(selectedItem.use(this) == true) {
				inventory.remove(itemIndex);

			}
		
		}
	}
}
private void contactMonster(int i) {
		if(i != 999) {
			if(invincible == false && gp.monster[i].dying == false) {
				gp.playSE(6);
				int damage = gp.monster[i].attack - defense;
				Life -= damage ;
				invincible = true ;
			}
		}	
	}
public void pickUpObject(int i) {
	if(i != 999) {
		//pickup ONLY ITEMS
		if(gp.obj[i].type == type_pickupOnly) {
			gp.obj[i].use(this);
			gp.obj[i] = null ;
			
		}
			//OBSTACLE
		else if(gp.obj[i].type == type_obstacle) {
			if(keyH.enterPressed == true) {
				attackcanceled = true;
					gp.obj[i].interact();
			}
		}
		else {
			String text; 
			if(inventory.size() != maxinventorySize) {
				inventory.add(gp.obj[i]);
				gp.playSE(1);
				text = "Got a" + gp.obj[i].name + "!" ;
			}else {
				text = "You cannot carry any more!";
			}
			gp.ui.addMessage(text);
			gp.obj[i] = null;
			
		}
	}		
}
public void interactNPC(int i ) {
	if(gp.keyH.enterPressed == true ) {
		 if(i != 999) {
			 attackcanceled = true;
					gp.gameState = gp.dialogueState;	
					gp.npc[i].speak();	}	
			    
	          }  
         }
public void draw(Graphics2D g2) {
	
	
	BufferedImage image = null;
	
	
	
	switch(direction) {
	case "up":
		if(attacking == false) {
			if(spriteNum == 1) {image = up1;}
			if(spriteNum == 2) {image = up2;}	
		}
		if(attacking == true) {
			if(spriteNum == 1) {image = aup1;}
			if(spriteNum == 2) {image = aup2;}
		}
		break;	
	case "down":
		if(attacking == false) {
			if(spriteNum == 1) {image = down1;}
			if(spriteNum == 2) {image = down2;}	
		}
		 if(attacking == true) {
			if(spriteNum == 1) {image = adown1;}
			if(spriteNum == 2) {image = adown2;}	
		}
		break;
	case "left":
		if(attacking == false) {
			if(spriteNum == 1) {image = left1;}
			if(spriteNum == 2) {image = left2;}	
		}
		 if(attacking == true) {
			if(spriteNum == 1) {image = aleft1;}
			if(spriteNum == 2) {image = aleft2;}
		}
		break;
	case "right":
		if(attacking == false) {
			if(spriteNum == 1) {image = right1;}
			if(spriteNum == 2) {image = right2;}	
		}
		if(attacking == true) {
			if(spriteNum == 1) {image = aright1;}
			if(spriteNum == 2) {image = aright2;}	
			
		}
		break;
	}

	if(invincible == true) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	}
	g2.drawImage(image, screenX, screenY,null);
	
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	
	
}

}
