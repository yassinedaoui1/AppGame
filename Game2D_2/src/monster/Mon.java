package monster;

import java.util.Random;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;
import projet.GamePanel;

public class Mon extends Entity{
GamePanel gp;
	public Mon(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "green slime" ;
		speed = 1;
		maxLife = 20;
		Life = maxLife;
		type = type_monster;
		attack = 5;
		defense = 0;
		exp=2;
		projectile = new OBJ_Rock(gp);
		getImage();
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x ;
		solidAreaDefaultY = solidArea.y ;
		
	}
	
	public void getImage() {
		up1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		up2= setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
	}
	
	public void setAction() {
		actionLockCounter ++; 
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i =random.nextInt(100)+1;	//pick up a number 1-100
			if(i<25) {
				direction = "up";	
			}
			if(i>25 && i <= 50) {
				direction= "down";
			}
			if(i>50 && i<= 75) {
				direction="left";	
			}
			if(i>75 && i<=100) {
				direction = "right";
		}
			actionLockCounter = 0;
		
		
		}
		int i = new Random().nextInt(100) + 1;
		if(i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
			projectile.set(worldX , worldY,direction , true,this);
			gp.projectileList.add(projectile);
			shotAvailableCounter =	0;
		}
	}
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;
		
	}
	//Cast a die
	public void checkDrop() {
		int i = new Random().nextInt(100)+1;
		//Set the monster drop
		if(i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if(i >= 50 && i <75) {
			dropItem(new OBJ_Heart(gp));	
		}
		if(i >= 75	 && i <100) {
			dropItem(new OBJ_ManaCrystal(gp));	
		}
	}

}
