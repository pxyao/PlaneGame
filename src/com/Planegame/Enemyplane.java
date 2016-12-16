package com.Planegame;

import java.util.Random;


public class Enemyplane {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected String path;
	private boolean iseffect=true;
	private int life=50; //敌机 默认生命
	private int atk=10; //敌机，默认攻击
	private int f_bomb=10;//该种飞机爆炸动画的帧数
	private boolean isbomb;//是否爆炸
	private int firespeed=20; //越小越快
	private boolean changecolor;
	private int maxchangecolor;
	private int enemyspeed=1;//敌机Y轴移动速度
	protected String type="normal_"; //敌机类型  格式为XXX_num
	private boolean die=false;
	private int maxlife;
	
	public int getMaxlife() {
		return maxlife;
	}

	public void setMaxlife(int maxlife) {
		this.maxlife = maxlife;
	}

	public boolean isDie() {
		return die;
	}

	public void setDie(boolean die) {
		this.die = die;
	}

	public int getMaxchangecolor() {
		return maxchangecolor;
	}

	public void setMaxchangecolor(int maxchangecolor) {
		this.maxchangecolor = maxchangecolor;
	}
	
	public int getFirespeed() {
		return firespeed;
	}

	public void setFirespeed(int firespeed) {
		this.firespeed = firespeed;
	}

	public boolean isIsbomb() {
		return isbomb;
	}

	public void setIsbomb(boolean isbomb) {
		this.isbomb = isbomb;
	}

	public int getF_bomb() {
		return f_bomb;
	}

	public void setF_bomb(int f_bomb) {
		this.f_bomb = f_bomb;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getEnemyspeed() {
		return enemyspeed;
	}

	public boolean isIseffect() {
		return iseffect;
	}

	public void setIseffect(boolean iseffect) {
		this.iseffect = iseffect;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if (x < 0)
			x = 0;
		if (x > PlaneFrame.width - 50)
			x = PlaneFrame.width - 50;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if (y > PlaneFrame.height)
			y = 0;
		this.y = y;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life=life;
		if (getLife() <= 0) {
			setIseffect(false);
		}
	}

	public Enemyplane() {
		/**
		 * 随机位置创建普通随机敌机
		 */
		Random random = new Random();
		setX(random.nextInt(PlaneFrame.width));
		setY(-random.nextInt(PlaneFrame.height/2));
		width = 50;
		height = 50;
		choosePlane();
	}
	
	public Enemyplane(int x,int y) {
		/**
		 * 固定位置创建随机普通敌机
		 */
		setX(x);
		setY(y);
		width = 50;
		height = 50;
		choosePlane();
	}
	

	private void choosePlane() {
		Random random = new Random();
		int chooseplane = random.nextInt(9) + 1;
		path = "/enemy_" + chooseplane + ".png";
		setLife(100);
	}


	public void move() {
		/**
		 * 普通敌机移动方法
		 */
		setY(y + enemyspeed);
	}

	
	private int fire_count=0;
	public boolean isfire() {
		/**
		 * 普通敌机三之一的概率开火
		 */
		fire_count++;
		if(fire_count>=getFirespeed()){
			fire_count=0;
			return true;
		}else{
			return false;
		}
	}
	public void bomb(int c){
		if(c==1)
			setPath("/bomb_1.png");
		if(c==2)
			setPath("/bomb_1.gif");
	}
	
	public void changecolor(int type){
		/**
		 * 普通敌机不用 这个函数是给子类 enemy_boss1用的
		 */
	}
	public boolean isChangecolor() {
		return changecolor;
	}

	public void setChangecolor(boolean changecolor) {
		this.changecolor = changecolor;
	}
}
