package tile_interactive;

import entity.Entity;
import projet.GamePanel;

public class InteractiveTile extends Entity {
GamePanel gp;
public boolean destructible = false;
	public InteractiveTile(GamePanel gp, int col,int row) {
		super(gp);
		this.gp = gp;
	}
public void update() {
	if(invincible == true) {
		invinciblecounter ++ ;
		if(invinciblecounter > 20) {
			invincible = false ;
			invinciblecounter = 0;
			
		}
		
	} 
}
public boolean isCorrectItem(Entity entity){
	boolean isCorrectItem = false;
	return isCorrectItem;
}
public void playSE() {
	
}
public InteractiveTile getDestroyedForm() {
	InteractiveTile tile = null;
	return tile;
}
}
