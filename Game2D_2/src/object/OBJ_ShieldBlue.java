package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_ShieldBlue extends Entity{

	public OBJ_ShieldBlue(GamePanel gp) {
		super(gp);
		type = type_shield;
		name=" Blue Shield wood";
		down1 = setup("/objects/shield_blue" , gp.tileSize , gp.tileSize);
		defensevalue = 2;
		description = "[" +name+ "]\nA shiny blue shield " ;
	}

}
