package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projet.GamePanel;
import projet.UtilityTool;
import java.awt.*;



public class Entity {
	GamePanel gp;
  public int x,y;
  
  
  public BufferedImage up1 , up2 , down1 , down2 , right1 , right2 , left1 , left2;
  public BufferedImage aup1 , aup2 , adown1 , adown2 , aright1 , aright2 , aleft1 , aleft2;
  public Rectangle solidArea = new Rectangle(0 , 0 , 48, 48); 
  public Rectangle attackArea = new Rectangle(0,0,0,0);
  public int solidAreaDefaultX,solidAreaDefaultY;
  public boolean  collision=false;
  public String dialogues[] = new String[20];
  public boolean alive  = true;
  public boolean dying = false;
  public boolean hpbarOn = false;
  public final int type_obstacle = 8;
  
  
  public BufferedImage image , image2 , image3;
  //counter
  public int spriteCounter = 0;
  public int actionLockCounter = 0;
  public int invinciblecounter = 0;
  public int dyingcounter = 0;
  public int hpbarcounter = 0;
  public int shotAvailableCounter = 0;


	
  //character status
  public int maxLife ;
  public int mana;
  public int Life ;
 public  int maxMana;
 public int ammo;
  public String name;
  public int speed;
  public int value;
  public int attackvalue ;
  public int defensevalue ;
  public int level ;
  public int strength;
  public int dexterity ;
  public int exp;
  public int nextLevelExp ;
  public int coin ;
  public Entity currentWeapon ;
  public Entity currentShield ;
  public Projectile projectile;
  public int attack;
  public int defense ;
  public String description = "" ;
  public int useCost ;
//Type
  public int type; //0 for player  , 1 for npc , 2 for monster
  public final int type_player = 0;
  public final int type_npc = 1;
  public final int type_monster=2;
  public final int type_sword = 3;
  public final int type_axe = 4;
  public final int type_shield = 5;
  public final int type_consumable = 6;
  public final int type_pickupOnly = 7;
  //state
  public boolean collisionOn =  false ;
  public String direction = "down";
  public int spriteNum = 1 ;
  public int worldX , worldY ;
  int dialogueindex = 0;
  public boolean invincible = false ;
  public boolean attacking = false ; 
  
  
  
  
  public Entity(GamePanel gp) {
	  this.gp = gp;
  }
  public void setAction() {}
  public void damageReaction() {}
  public void speak() {
	  if(dialogues[dialogueindex]== null ) {
			dialogueindex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueindex];	
		dialogueindex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	  
  }
  public void interact() {
	  
  }
  public boolean  use(Entity entity) {return false;}
  public void update() {
	  setAction();
	  collisionOn= false;
	  gp.cChecker.checkTile(this);//aymchi l npc
	  gp.cChecker.checkObject(this,false);
	  gp.cChecker.checkPlayer(this);
	  gp.cChecker.checkEntity(this, gp.npc);
	  gp.cChecker.checkEntity(this, gp.monster);
	  gp.cChecker.checkEntity(this, gp.iTile);
	  boolean contactPlayer = gp.cChecker.checkPlayer(this);
	  
	  if(this.type == type_monster && contactPlayer == true) {
		  damagePlayer(attack);
		  
	  }
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
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
		if(invincible == true) {
			invinciblecounter ++ ;
			if(invinciblecounter > 40) {
				invincible = false ;
				invinciblecounter = 0;
				
			}
			
		} 
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
  }
  public void damagePlayer(int attack) {
	  if(gp.player.invincible == false) {
		  gp.playSE(6);
		  int damage = attack - gp.player.defense ;
		  if(damage < 0) {
			  damage = 0;
		  }
		  gp.player.Life -= damage;
		  gp.player.invincible = true ;
	  }
  }
  public void draw(Graphics2D g2) {
	  BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX ;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
        		worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
        		worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && 
        		worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        	switch(direction) {
        	case "up":
        		if(spriteNum == 1) {image = up1;
                }
        		if(spriteNum == 2) {image = up2;
        		}
        		break;	
        	case "down":
        		if(spriteNum == 1) {image = down1;}
        		if(spriteNum == 2) {image = down2;}
        		break;
        	case "left":
        		if(spriteNum == 1) {image = left1;}
        		if(spriteNum == 2) {image = left2;}
        		break;
        	case "right":
        		if(spriteNum == 1) {image = right1;}
        		if(spriteNum == 2) {image = right2;}
        		break;
        	}
        	//Monster HP bar
        	if(type == 2 && hpbarOn == true) {
        	    hpbarcounter++;
        	    if(hpbarcounter > 600) {
        	    	hpbarcounter = 0 ;
        	    	hpbarOn = false;
        	    }
        		double onescale = (double)gp.tileSize/maxLife;
        		double hpbarvalue = onescale*Life;
        		g2.setColor(new Color(35,35,35));
        		g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
        		
        		g2.setColor(new Color(255,0,0));
        		g2.fillRect(screenX, screenY - 15, (int)hpbarvalue, 10);
        	}
        	if(invincible == true) {
        		hpbarOn = true;
        		hpbarcounter = 0;
        		changeAlpha(g2,0.4f);
        	}
        	if(dying == true) {
        		dyingAnimation(g2);
        	}

        	g2.drawImage(image, screenX, screenY, null);
        	changeAlpha(g2 , 1f);
        	
        }
        	
        }
  
	private void dyingAnimation(Graphics2D g2) {
		
	dyingcounter++;
	
	int i = 5;
	
	if(dyingcounter <= i) {  changeAlpha(g2 , 0f) ;}
	if(dyingcounter > i && dyingcounter <= i*2 ) { changeAlpha(g2 , 1f);}
	if(dyingcounter > i*2 && dyingcounter <= i*3 ) { changeAlpha(g2 , 0f);}
	if(dyingcounter > i*3 && dyingcounter <= i*4 ) { changeAlpha(g2 , 1f);}
	if(dyingcounter > i*4 && dyingcounter <= i*5 ) { changeAlpha(g2 , 0f);}
	if(dyingcounter > i*5 && dyingcounter <= i*6 ) { changeAlpha(g2 , 1f);}
	if(dyingcounter > i*6 && dyingcounter <= i*7 ) { changeAlpha(g2 , 0f);}
	if(dyingcounter > i*7 && dyingcounter <= i*8 ) { changeAlpha(g2 , 1f);}
	if(dyingcounter > i*8) {
		alive = false;
	}
	
}
	
	
	private void changeAlpha(Graphics2D g2, float alphavalue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER , alphavalue));
		
	}
	public BufferedImage setup(String imagePath , int width , int height) {
		UtilityTool uTool=new UtilityTool();
		BufferedImage image=null;
		
		try {
			image=ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image=uTool.scaleImage(image, width, height);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
		
	}
	public void checkDrop() {
		
	}
	public void dropItem(Entity droppedItem) {
		for(int i=0 ;i<gp.obj.length; i++) {
			if(gp.obj[i]== null ) {
				gp.obj[i] =droppedItem;
				gp.obj[i].worldX = worldX;//the dead monster's worldX 
				gp.obj[i].worldY = worldY;
				break;
			}
		}
		
	}
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	public int getParticleSpeed() {
			int speed = 0;
			return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParicle(Entity generator , Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();	
		int speed = generator.getParticleSpeed();
		int maxLife =generator.getParticleMaxLife();
		Particle p1 = new Particle(gp,generator ,color,size,speed,maxLife,-1,-1);
		gp.particleList.add(p1);
		
	}
	public int getDetected(Entity user,Entity[] obj,String targetName) {
		int index = 999;
		int nextWorldX =user.getLeftX();
		int nextWorldY = user.getTopY();
		switch(user.direction)	{
		case "up" :nextWorldY = user.getTopY()-1;break;
		case "down" : nextWorldY =user.getBottomY() +1;break;	
		case "left" : nextWorldX = user.getLeftX()-1;break;
		case "right" :nextWorldX = user.getRightX()+1;break;
		}
		int col = nextWorldX/gp.tileSize;
		int row =nextWorldY/gp.tileSize;
		for(int i = 0;i<obj.length;i++) {
			if(obj[i] != null) {
				if(obj[i].getCol() == col && 
						obj[i].getRow() == row &&
						obj[i].name.equals(targetName)) {
					
					index = i;
					break;
					
				}
						
			}
		}
		return index;
	}
	public int getLeftX() {
		return worldX + solidArea.x;
	}
	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
		
	}
	public int getTopY() {
		return worldY + solidArea.y;
	}
	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}
	public int getCol() {
			  return (worldX + solidArea.x)/gp.tileSize;
	}
	public int getRow() {
		return (worldY + solidArea.y)/gp.tileSize;
	}
	
}
