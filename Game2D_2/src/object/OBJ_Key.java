package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_Key extends Entity {
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name="key";
		down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
		description = "[" +name+ "]\nOpen a door" ;
		
		
	}
	public boolean use(Entity entity) {
		gp.gameState = gp.dialogueState;
		int objIndex = getDetected(entity,gp.obj, "door");
		if(objIndex != 999) {
			gp.ui.currentDialogue = "You use the " + name + "and open the door";
			gp.playSE(3);
			gp.obj[objIndex] = null;
			return true;
		}
	
		else {
			gp.ui.currentDialogue = "What are you doing ?"; 
			return false;
		}
		
	}

}
