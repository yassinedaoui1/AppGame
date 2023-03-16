package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_Boots extends Entity {
	
	public OBJ_Boots(GamePanel gp) {
		super(gp);
		name="boots";
		down1 = setup("/objects/boots",gp.tileSize,gp.tileSize);
		
	}

}
