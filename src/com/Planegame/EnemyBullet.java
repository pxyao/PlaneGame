package com.Planegame;

//�л��ӵ���
public class EnemyBullet {
	protected int x;
	protected int y;
	/**
	 * ע�� �ӵ����ܶ�̬�Ĵ�С  ӦΪ�ڻ��ӵ���ʱ����Ҫ����
	 * �����ӵ���ʱ��һ���и����ʵ�� ����ֻ�ܶ���ɾ�̬��
	 */
	protected int myplane_x;
	protected int myplane_y;
	protected int enemy_x;
	protected int enemy_y;
	protected static int width=20;
	protected static int height=20;
	protected String path;
	private boolean iseffect;//��ǰ�ӵ��Ƿ���Ч
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
