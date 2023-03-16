/*package projet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;
	public Config(GamePanel gp) {
		this.gp=gp;
	}
	
	public void saveConfig() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			if(gp.fullScreenOn == true) {
				bw.write("On");
			}
			if(gp.fullScreenOn == false) {
				bw.write("Off");
			}
			bw.newLine();
			bw.write(String.valueOf(gp.music.volumeScale));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void loadConfig() {
		
	}

}*/
