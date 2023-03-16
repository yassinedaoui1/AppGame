package object;

import entity.Entity;
import entity.Projectile;
import projet.GamePanel;

public class OBJ_Rock extends Projectile {
	GamePanel gp;
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp=gp;
		
		name="Rock";
		speed = 8;
		maxLife = 80;
		Life = maxLife ;
		attack = 2;	
		useCost = 1;
		alive = false;
		getImage();
	}
public void getImage() {
	up1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize*2);
	up2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize*2);
	down1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize*2);
	down2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize*2);
	left1 = setup("/projectile/rock_down_1",gp.tileSize*2,gp.tileSize);
	left2 = setup("/projectile/rock_down_1",gp.tileSize*2,gp.tileSize);
	right1 = setup("/projectile/rock_down_1",gp.tileSize*2,gp.tileSize);
	right2 = setup("/projectile/rock_down_1",gp.tileSize*2,gp.tileSize);
}
public boolean haveResource(Entity user) {
	boolean haveResource = false;
	if(user.ammo >= useCost) {
		haveResource = true;
		
	}
	return haveResource;
}
public void subtractResource(Entity user) {
	user.ammo -=  useCost;
	
}

}
