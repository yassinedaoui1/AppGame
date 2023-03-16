package object;

import entity.Entity;
import projet.GamePanel;

public class OBJ_Axe  extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		type = type_axe;
		name = "Wood";
		down1 = setup("/objects/axe" , gp.tileSize , gp.tileSize);
		attackvalue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[woodcutter's Axe]\nTo cut some trees" ;
	}
	

}
