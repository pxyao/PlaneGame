package com.Planegame;

public class Myplane {
	/**
	 * @param args
	 */
	private int x;
	private int y;
	private int width;
	private int height;
	private String path;
	private boolean isFire;
	private int atk=40; //我方初始攻击
	private int power=1;//炮弹初始威力
	private  int maxlife=200;//我方最大生命值
	public int getMaxlife() {
		return maxlife;
	}

	public void setMaxlife(int maxlife) {
		this.maxlife = maxlife;
	}

	private int life=0; //我方生命
	private boolean iseffect=true;
	private int bulletspeed=15; //越小越快
	private int credit=0;//我的积分
	
	
	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getBulletspeed() {
		return bulletspeed;
	}

	public void setBulletspeed(int bulletspeed) {
		this.bulletspeed = bulletspeed;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		if(life<=0){
			setIseffect(false);
		}
		this.life = life;
	}

	public boolean isIseffect() {
		return iseffect;
	}

	public void setIseffect(boolean iseffect) {
		this.iseffect = iseffect;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public Myplane(){
		x=PlaneFrame.width/2-40;
		y=PlaneFrame.height-80;
		width=45;
		height=45;
		path="/myplane_02.png";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		if(x<0||x>PlaneFrame.width)
			setIseffect(false);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		if(y<0||y<PlaneFrame.height)
			setIseffect(false);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}
	
}
