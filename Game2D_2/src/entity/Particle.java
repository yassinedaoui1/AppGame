package entity;

import java.awt.Color;
import java.awt.Graphics;

import projet.GamePanel;

public class Particle extends Entity {
Entity generator;
Color color;
int size;
int speed;
int maxLife;
	
//int xd;
//int yd;
	public Particle(GamePanel gp,Entity generator,Color color,int size,int speed,int maxLife,int xd,int yd) {
		super(gp);
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;	
		this.maxLife = maxLife;
		//this.xd = xd;
		//yd = yd;
		Life =maxLife;
		worldX = generator.worldX;
		worldY = generator.worldY;
		}
	//public void update() {
		//worldX += xd * speed;
		//worldY += yd*speed;
	//}
	public void draw(Graphics g2) {
		int screenX = worldX - gp.player.worldX +gp.player.screenX;
		int screenY = worldY - gp.player.worldY +gp.player.screenY;
		g2.setColor(color);
		g2.fillRect(screenX,screenY,size,size);
	}

}
