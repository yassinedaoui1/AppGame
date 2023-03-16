package projet;

import java.awt.Rectangle;

public class EventHandler {
GamePanel gp ;
Rectangle eventRect ;
int eventRectDefaultX , eventRectDefaultY ;


 public EventHandler(GamePanel gp) {
	this.gp = gp ;	 
	
	eventRect = new Rectangle();
	eventRect.x = 23;
	eventRect.y = 23;
	eventRect.width = 2;
	eventRect.height = 2;	
	eventRectDefaultX = eventRect.x ;
	eventRectDefaultY = eventRect.y ;		
}
 
  public void checkEvent()  {
			if(hit(27,16,"right") == true)   {damagePit(gp.dialogueState);}
			if(hit(23,12,"up") == true) {healingPool(23,12,gp.dialogueState);
	  }
	}
  
 

private void damagePit(int gameState) {
	gp.gameState = gameState ;
	gp.ui.currentDialogue = "You fall into a pit" ;
	gp.playSE(6);
	gp.player.Life -= 1 ;
}

public boolean hit(int eventCol , int eventRow , String reqDirection) {
	
	  boolean hit = false ;
	  gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x ;
	  gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y ;
	  eventRect.x = eventCol * gp.tileSize + eventRect.x ;
	  eventRect.y = eventRow * gp.tileSize + eventRect.y ;
	  
	  if(gp.player.solidArea.intersects(eventRect)) {
		  if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
			  
	       hit = true ;
		  }
		}
	  gp.player.solidArea.x = gp.player.solidAreaDefaultX ;
	  gp.player.solidArea.y = gp.player.solidAreaDefaultY ;
	  eventRect.x =  eventRectDefaultY ;
	  eventRect.y =  eventRectDefaultY ;
	  
		  return hit ; 
   }
public void healingPool(int col , int row ,int gameState)  {
	if(gp.keyH.enterPressed == true) {
		gp.gameState = gameState ;
		gp.ui.currentDialogue = "You drink the water .\nYour life and mana has been recovered" ;
		gp.player.Life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		gp.playSE(2);
		gp.player.attackcanceled = true ;
		
		gp.aSetter.setMonster();
	}
}
}
	

