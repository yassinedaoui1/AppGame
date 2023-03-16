package projet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;  
public boolean upPressed , downPressed, leftPressed,rightPressed,enterPressed , shotKeyPressed;
public KeyHandler(GamePanel gp) {
	this.gp = gp;
}
	@Override
	public void keyTyped(KeyEvent e) {
			
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//TitleState
		if(gp.gameState == gp.titleState) {	
			titlestate(code);
		}
		//PlayState
		else if(gp.gameState == gp.playState) {	
			playstate(code);
		}
		//PauseState
		else if(gp.gameState == gp.pauseState) {
			pausedstate(code);
		}
		//DialogueState
			 else if(gp.gameState == gp.dialogueState) {
				 dialoguestate(code);
			 }
		//characterstate
			 else if( gp.gameState == gp.characterstate) {
				 characterstate(code);
			 }
			 else if( gp.gameState == gp.gameOverstate) {
				 gameOverstate(code);
			 }
			 }
	private void gameOverstate(int code) {
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum=1;
			}
			gp.playSE(9);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>1) {
				gp.ui.commandNum=0;
			}
			gp.playSE(9);
		}
		if(code == KeyEvent.VK_ENTER) {
			
			if(gp.ui.commandNum==0) {
				gp.gameState=gp.playState;
				gp.retry();
				gp.playMusic(0);
				
			}
			else if(gp.ui.commandNum==1) {
				gp.gameState=gp.titleState;
				gp.restart();
				
			}
			
		}
		
		
	}
	public void titlestate(int code) {
		if(gp.ui.titleScreenState == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
					
				}
				if(gp.ui.commandNum == 1) {
					
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}	
		}
		else if(gp.ui.titleScreenState == 1) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.gameState = gp.playState ;
					gp.playMusic(0);
					
				}
				if(gp.ui.commandNum == 1) {
					gp.gameState = gp.playState ;
					gp.playMusic(0);
				
				}
				if(gp.ui.commandNum == 2) {
					gp.gameState = gp.playState ;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0 ;
				}
			}	
		}
	}
	public void playstate(int code) {
		if(code == KeyEvent.VK_W){
			 upPressed = true ;
		}
		else if(code == KeyEvent.VK_S){
	    	downPressed = true ;
	    }
		else if(code == KeyEvent.VK_A){
       	leftPressed = true ;
       }
		else if(code == KeyEvent.VK_D){
       	rightPressed = true ;
       }
		else if(code == KeyEvent.VK_P){
			gp.gameState = gp.pauseState;
		}
		else if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterstate ;
		}
		else if(code == KeyEvent.VK_ENTER){
			   enterPressed = 	true;	
			}
		else if(code == KeyEvent.VK_F){
			   shotKeyPressed = 	true;	
			}
	}
	public void pausedstate(int code) {
		 if(code == KeyEvent.VK_P){
				gp.gameState = gp.playState;
	}	
	}
	public void dialoguestate(int code) {
		if(code == KeyEvent.VK_ENTER) {
		 	gp.gameState = gp.playState; 
	 }	
	}
	public void characterstate(int code) {
		if(code == KeyEvent.VK_C) {
			 gp.gameState = gp.playState ;
		 }
		if(code == KeyEvent.VK_W) {
			if(gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSE(8);	
			}	
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSE(8);
			}
		}
		if(code == KeyEvent.VK_S) {
            if(gp.ui.slotRow != 3) {
            	gp.ui.slotRow++;
    			gp.playSE(8);	
			}
		}
		if(code == KeyEvent.VK_D) {
              if(gp.ui.slotCol != 4) {
            	  gp.ui.slotCol++;
      			  gp.playSE(8);
			}
		}
		if(code == KeyEvent.VK_ENTER) {
    			gp.player.selectItems();	
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W){
			 upPressed = false ;
		}
		else if(code == KeyEvent.VK_S){
	    	downPressed = false ;
	    }
		else if(code == KeyEvent.VK_A){
        	leftPressed = false ;
        }
		else if(code == KeyEvent.VK_D){
        	rightPressed = false ;
        }
		else if(code == KeyEvent.VK_F){
			   shotKeyPressed = false;	
			}
	}

}
