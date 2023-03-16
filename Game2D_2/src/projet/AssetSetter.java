	
package projet;

import entity.NPC_OldMan;
import monster.Mon;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion;
import object.OBJ_ShieldBlue;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp=gp;
	}
	public void setObject() {
		int i = 0; 
		gp.obj[i] = new OBJ_Door(gp);
		gp.obj[i].worldX = gp.tileSize*12;
		gp.obj[i].worldY = gp.tileSize*12;
		i++;
		gp.obj[i] = new OBJ_Door(gp);
		gp.obj[i].worldX = gp.tileSize*14;
		gp.obj[i].worldY = gp.tileSize*28;
		i++;
		gp.obj[i] = new OBJ_Chest(gp,new OBJ_Key(gp));
		gp.obj[i].worldX = gp.tileSize*13;
		gp.obj[i].worldY = gp.tileSize*10;
		i++;
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*23;
		gp.obj[i].worldY = gp.tileSize*27;
		i++;
		
		
	gp.obj[i] = new OBJ_Coin_Bronze(gp);
	gp.obj[i].worldX = gp.tileSize*25;
	gp.obj[i].worldY = gp.tileSize*23;
	i++;
	gp.obj[i] = new OBJ_Coin_Bronze(gp);
	gp.obj[i].worldX = gp.tileSize*21;
	gp.obj[i].worldY = gp.tileSize*19;
	i++;
	gp.obj[i] = new OBJ_Coin_Bronze(gp);
	gp.obj[i].worldX = gp.tileSize*26;
	gp.obj[i].worldY = gp.tileSize*21;
	i++;
	gp.obj[i] = new OBJ_Axe(gp);
	gp.obj[i].worldX = gp.tileSize*33;
	gp.obj[i].worldY = gp.tileSize*21;
	i++;
	gp.obj[i] = new OBJ_ShieldBlue(gp);
	gp.obj[i].worldX = gp.tileSize*37;
	gp.obj[i].worldY = gp.tileSize*21;
	i++;
	gp.obj[i] = new OBJ_Heart(gp);
	gp.obj[i].worldX = gp.tileSize*20;
	gp.obj[i].worldY = gp.tileSize*21;
	i++;
	gp.obj[i] = new OBJ_ManaCrystal(gp);
	gp.obj[i].worldX = gp.tileSize*22;
	gp.obj[i].worldY = gp.tileSize*31 ;
	i++;
	gp.obj[i] = new OBJ_Potion(gp);
	gp.obj[i].worldX = gp.tileSize*22;
	gp.obj[i].worldY = gp.tileSize*27;
	i++;
}
	public void setNPC() {
		int mapNum=0; 
		int i = 0 ;
		gp.npc[i]=new NPC_OldMan(gp);
	    gp.npc[i].worldX =gp.tileSize*21;
	    gp.npc[i].worldY =gp.tileSize*21;    
	}
	public void setMonster() {
		int mapNum=0; 
		int i = 0 ;
		gp.monster[i] = new Mon(gp) ;
		gp.monster[i].worldX = gp.tileSize*21 ;
		gp.monster[i].worldY = gp.tileSize*38 ;
		i++;
		gp.monster[i] = new Mon(gp) ;
		gp.monster[i].worldX = gp.tileSize*23 ;
		gp.monster[i].worldY = gp.tileSize*42 ;
		i++;
		gp.monster[i] = new Mon(gp) ;
		gp.monster[i].worldX = gp.tileSize*24 ;
		gp.monster[i].worldY = gp.tileSize*37 ;
		i++;
		gp.monster[i] = new Mon(gp) ;
		gp.monster[i].worldX = gp.tileSize*34 ;
		gp.monster[i].worldY = gp.tileSize*42 ;
		i++;
		gp.monster[i] = new Mon(gp) ;
		gp.monster[i].worldX = gp.tileSize*38 ;
		gp.monster[i].worldY = gp.tileSize*42 ;
		i++;
		
	}
	public void setInteractiveTile() {
		int mapNum=0; 
		int i = 0;
		gp.iTile[i] =new IT_DryTree(gp,27,12);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,28,12);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,29,12);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,30,12);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,31,12);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,32,12);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,33,12);
		i++;
		
		
		
		gp.iTile[i] =new IT_DryTree(gp,30,20);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,30,21);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,30,22);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,20,20);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,20,21);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,20,22);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,22,24);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,23,24);
		i++;
		gp.iTile[i] =new IT_DryTree(gp,24,24);
		i++;
	}
}
