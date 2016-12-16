package com.Planegame;

public class EnemyBoss_1_Bullet extends EnemyBullet{
	/**
	 * myplane_x 和myplane_y 只会记录一次当前我机位置 
	 */
	private EnemyBoss_1 enemyBoss_1; //敌机BOSS1
	private Myplane myplane; //我机
	private int bulletmovespeed;
	public EnemyBoss_1_Bullet(int x, int y, Myplane myplane,
			EnemyBoss_1 enemyBoss_1) {
		super(x, y, myplane.getX(), myplane.getY(), enemyBoss_1.getX(), enemyBoss_1.getY());
		path="/efire_2.gif";
		this.enemyBoss_1=enemyBoss_1;
		this.myplane=myplane;
		this.bulletmovespeed=enemyBoss_1.getBulletmovespeed(); //获取子弹移动速度
	}
	public void move() {
		if(enemyBoss_1.getLife_state()==1||enemyBoss_1.getLife_state()==2||enemyBoss_1.getLife_state()==4){
			/**
			 * 1,2,4阶段移动方案
			 */
			if (isfirst == true) {
				toward(myplane_x, myplane_y);
				isfirst = false;
			}
			if(isleft&&istop){
				int d_x=myplane_x-enemy_x;
				int d_y=myplane_y-enemy_y;
				int m_x=(int)(d_x*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				int m_y=(int)(d_y*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				setX(getX()+m_x);
				setY(getY()+m_y);
			}
			if(isright&&istop){
				int d_x=myplane_x-enemy_x;
				int d_y=myplane_y-enemy_y;
				int m_x=(int)(d_x*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				int m_y=(int)(d_y*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				setX(getX()+m_x);
				setY(getY()+m_y);
			}
			if(isleft&&isbotton){
				int d_x=myplane_x-enemy_x;
				int d_y=myplane_y-enemy_y;
				int m_x=(int)(d_x*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				int m_y=(int)(d_y*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				setX(getX()+m_x);
				setY(getY()+m_y);
			}
			if(isright&&isbotton){
				int d_x=myplane_x-enemy_x;
				int d_y=myplane_y-enemy_y;
				int m_x=(int)(d_x*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				int m_y=(int)(d_y*bulletmovespeed)/(int)Math.sqrt(d_x*d_x+d_y*d_y);
				setX(getX()+m_x);
				setY(getY()+m_y);
			}
		}
		
		if(enemyBoss_1.getLife_state()==3){
			/**
			 * 3阶段移动方案
			 */
			if (getX() > myplane.getX()) {
				setX(getX() - bulletmovespeed);
			}
			if (getX() < myplane.getX()) {
				setX(getX() + bulletmovespeed);
			}
			if (getY() < myplane.getY()) {
				setY(getY() + bulletmovespeed);
			}
			if (getY() > myplane.getY()) {
				setY(getY() - bulletmovespeed);
			}
		}
	}
	private void toward(int myplane_x,int myplane_y) {
		if(enemy_x<myplane_x){
			isleft=true;
		}else {
			isright=true;
		}
		if(enemy_y<myplane_y){
			istop=true;
		}else {
			isbotton=true;
		}
	}
}
