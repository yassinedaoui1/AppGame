package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_Potion  extends Entity{
GamePanel gp;	


	public OBJ_Potion(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "Red Potion";
		value = 5;
		down1 = setup("/objects/potion_red" , gp.tileSize , gp.tileSize) ;
		description = "[Red potion]\nHeals your life by " +value+ ".";	
	}
public boolean use(Entity entity) {
	 gp.gameState = gp.dialogueState;
	 gp.ui.currentDialogue = "You drink the " +name+ "!\n" + "Your life has been recovered by " +value+ ";" ;
	 entity.Life += value;

	 gp.playSE(2);
	return true;
}
}
