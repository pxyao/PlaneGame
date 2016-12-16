package com.Planegame;


public class EnemyBoss_1 extends Enemyplane{
	private boolean getinjured=false; //是否受伤 
	private int maxlife=10000;

	private boolean changecolor=false;
	private int maxchangecolor=5;
	private int firespeed=5; //越小越快
	private int f_bomb=90;//该种飞机爆炸动画的帧数
	private boolean invincible=false; //是否是无敌状态
	private boolean openfire=true;//boss开火开关
	
	private int bulletmovespeed=5;
	
	
	public int getMaxlife() {
		return maxlife;
	}

	public void setMaxlife(int maxlife) {
		this.maxlife = maxlife;
	}
	public int getBulletmovespeed() {
		return bulletmovespeed;
	}

	public void setBulletmovespeed(int bulletmovespeed) {
		this.bulletmovespeed = bulletmovespeed;
	}

	public int getFirespeed() {
		return firespeed;
	}

	public void setFirespeed(int firespeed) {
		this.firespeed = firespeed;
	}

	
	public int getMaxchangecolor() {
		return maxchangecolor;
	}

	public void setMaxchangecolor(int maxchangecolor) {
		this.maxchangecolor = maxchangecolor;
	}

	public int getF_bomb() {
		return f_bomb;
	}

	public void setF_bomb(int f_bomb) {
		this.f_bomb = f_bomb;
	}

	public boolean isChangecolor() {
		return changecolor;
	}

	public void setChangecolor(boolean changecolor) {
		this.changecolor = changecolor;
	}

	public boolean isGetinjured() {
		return getinjured;
	}

	public void setGetinjured(boolean getinjured) {
		this.getinjured = getinjured;
	}
	public EnemyBoss_1(int x,int y,int w,int h) {
		/**
		 * 固定位置创建固定敌机
		 * w:宽度 h:高度 全都要大于45
		 */
		setX(x);
		setY(y);
		width = w;
		height = h;
		choosePlane();
		type="boss_1";
	}
	private void choosePlane() {
		/**
		 * type：敌机类型+编号 
		 * 如： boss_1
		 */
		path = "/boss1/enemy_boss_1.png";
		setLife(maxlife);
		setAtk(50);
	}
	private int step=1;
	
	private int move_count=0;
	public void move() {
		 // boss_1的的移动方法
		if (type == "boss_1") {// BOSS1移动方法
			if(step==1){
				setX(getX() + 4);
				if(getX()>=PlaneFrame.width/2)
					step=2;
			}
			if(step==2){
				setX(getX()+8);
				if(getX()>=PlaneFrame.width-getWidth())
					step=3;
			}
			if(step==3){
				setX(getX() - 4);
				if(getX()<PlaneFrame.width/2)
					step=4;
			}
			if(step==4){
				setX(getX()-8);
				if(getX()<=0)
					step=1;
			}
			if (step == 5) {
				setInvincible(true); // 无敌阶段
				openfire = false; // 移动阶段不开火
				if (getX() < PlaneFrame.width / 2 - getWidth() / 2) {
					setX(getX() + 1);
				}
				if (getX() > PlaneFrame.width / 2 - getWidth() / 2) {
					setX(getX() - 1);
				}
				if (getY() < 150) {
					setY(getY() + 1);
				}
				if (getY() > 150) {
					setY(getY() - 1);
				}
				if (getX() == PlaneFrame.width / 2 - getWidth() / 2
						&& getY() == 150) {
					openfire = true; // 打开开火开关
					setChangecolor(false);
					setFirespeed(10);
					setBulletmovespeed(10);
					step = 6;
				
				}
			}
			if (step == 6) {
				move_count++;
				if (move_count == 800) {
					setFirespeed(2);
					step = 1;  //回到 1-2-3-4 移动方案
					setInvincible(false);
					life_state = 4;     
				}
			}
			if(step==99){ //BOSS爆炸时不移动
				
			}
		}
	}
	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	private int fire_count=0;
	public boolean isfire() {
		/**
		 * fire_count每加到firespeed时  开一次火
		 */
		if(fire_count++>=getFirespeed()&&openfire){
			fire_count=0;
			return true;
		}else{
			return false;
		}
	}

	private int life_state=1;
	public void setLife(int life){
		if(invincible==false)
			super.setLife(life); //不是无敌状态才掉血
		if(super.getLife()<=maxlife*0.75&&life_state==1){  //2阶段
			setGetinjured(false);
			changecolor=true;
			this.firespeed=2;
			life_state=2;
		}
		if(super.getLife()<=maxlife*0.5&&life_state==2){   //3阶段
			step=5;  //boss移动方案 变为step5
			changecolor=true;
			setFirespeed(1); //将子弹速速发射设置为最快
			life_state=3;
		}
	}
	public int getLife_state() {
		return life_state;
	}

	public void setLife_state(int life_state) {
		this.life_state = life_state;
	}

	public void changecolor(int type){
		/**
		 * type 是显示第几种颜色 1 是第一个
		 */
		setPath("/boss1/enemy_boss_1_"+type+".png");
	}
	public void bomb(int c){
		step=99;
		if(c==1){
			setPath("/boss1/enemy_boss_bomb_1.png");
		}
		if(c==7){
			setPath("/boss1/enemy_boss_bomb_2.png");
		}
		if(c==14){
			setPath("/boss1/enemy_boss_bomb_3.png");
		}
		if(c==21){
			setPath("/boss1/enemy_boss_bomb_4.png");
		}
		if(c==43){
			setPath("/bomb_1.gif");
		}
		if(c==50){
			setPath("/bomb_1.png");
		}
		if(c==58){
			setPath("/bomb_1.gif");
		}
		if(c==65){
			setPath("/bomb_1.png");
		}
		if(c==73){
			setPath("/bomb_1.gif");
		}
		if(c==80){
			setPath("/bomb_1.png");
		}
	}
}
