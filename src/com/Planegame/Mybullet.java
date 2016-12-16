package com.Planegame;

public class Mybullet {
	private int x;
	private int y;
	/**
	 * 注意 子弹不能动态改大小  应为在画子弹的时候需要调用
	 * 而画子弹的时候不一定有该类的实例 所以只能定义成静态的
	 */
	public static int width=40;
	public static int height=40;
	private String path;
	private boolean iseffect;//当前子弹是否有效
	
	private int bulletspeed=6;
	public Mybullet(int x,int y){
		this.x=x;
		this.y=y;
		width=40;
		height=40;
		path="/mybullet_1.gif";
		iseffect=true;
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
		if(y<0||y>PlaneFrame.height)
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
	public boolean isIseffect() {
		return iseffect;
	}
	public void setIseffect(boolean iseffect) {
		this.iseffect = iseffect;
	}
	public void move() {
		y=y-bulletspeed;
	}
}
