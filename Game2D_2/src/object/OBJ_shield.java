package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_shield extends Entity{

	public OBJ_shield(GamePanel gp) {
		super(gp);
		type = type_shield;
		name="Shield wood";
		down1 = setup("/objects/shield_wood" , gp.tileSize , gp.tileSize);
		defensevalue = 1;
		description = "[" +name+ "]\nMade by wood " ;
	}

}
