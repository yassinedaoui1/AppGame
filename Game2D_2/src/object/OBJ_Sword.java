package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_Sword extends Entity {

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		type = type_sword;
		name="Normal sword";
		down1 = setup("/objects/sword_normal" , gp.tileSize , gp.tileSize);
		attackvalue = 1;
		description = "[" +name+ "]\n An old sword " ;
		attackArea.width = 36;
		attackArea.height = 36;
	}

}
