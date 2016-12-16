package com.Planegame;

//敌机子弹类
public class EnemyBullet {
	protected int x;
	protected int y;
	/**
	 * 注意 子弹不能动态改大小  应为在画子弹的时候需要调用
	 * 而画子弹的时候不一定有该类的实例 所以只能定义成静态的
	 */
	protected int myplane_x;
	protected int myplane_y;
	protected int enemy_x;
	protected int enemy_y;
	protected static int width=20;
	protected static int height=20;
	protected String path;
	private boolean iseffect;//当前子弹是否有效
	public EnemyBullet(int x,int y,int myplane_x,int myplane_y,int enemy_x,int enemy_y){
		this.x=x;
		this.y=y;
		path="/efire_1.gif";
		iseffect=true;
		this.myplane_x=myplane_x;
		this.myplane_y=myplane_y;
		this.enemy_x=enemy_x;
		this.enemy_y=enemy_y;
	}
	public void move(){
		setY(getY()+10);
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
	public int getHeight() {
		return height;
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
	boolean isleft=false;
	boolean isright=false;
	boolean istop=false;
	boolean isbotton=false;
	boolean isfirst=true;
	
}
