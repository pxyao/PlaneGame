package com.Planegame;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PlaneThread extends Thread {
	private PlanePanel planePanel;
	private int count1 = 0;// 用于我机子弹
	private int count2 = 0;// 用于敌机子弹
	private int count3 = 0;// 用于子弹移动
	private int count4 = 0;// 用于移除无效子弹
	public int enemybulletspeed = 3;
	public int enemybulletmove = 1;
	private boolean iswin = false;// 是否获得胜利
	private boolean islose = false;// 是失败
	private boolean isend = false;// 结束游戏
	private Filereadandwrite filereadandwrite;
	private PlaneFrame planeFrame;
	public static boolean ememyloselife = false; // 敌机是否是掉血状态

	public PlaneThread(PlanePanel planePanel, PlaneFrame planeFrame,
			Filereadandwrite filereadandwrite) {
		this.planePanel = planePanel;
		this.filereadandwrite = filereadandwrite;
		this.planeFrame = planeFrame;
	}

	public void run() {
		while (true) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count3++ == enemybulletmove) { // 5是敌机子弹移动速速
				enemybulletmove();
				count3 = 0;
			}
			if (count2++ == enemybulletspeed) { // 20是敌机发射子弹速速
				addenemybullet();
				count2 = 0;
			}
			removeenemybullet();
			if (count1++ == planePanel.myplane.getBulletspeed()) {
				addmybullet();
				count1 = 0;
			}
			mybulletmove();
			mybullet_hit_enemyplane(); // 我机子弹碰撞敌机
			enemybullets_hit_myplane(); // 敌机子弹碰撞我机
			myplane_hit_enemyplane(); // 我机碰撞敌机
			mybullet_hit_enemybullet(); // 我机子弹碰撞敌机子弹
			boss1_changecolor();
			removeenemy();// 移除无效敌机
			removemybullet();
			enemyplanemove();
			isend();// 判断是否结束
			end(); // 结束处理
		}
	}

	private void end() {
		/**
		 * 游戏结束选择画面
		 */
		if (isend == true) {
			filereadandwrite.writegameinfo();
			if (iswin == true) {
				JOptionPane.showMessageDialog(null, "恭喜您获得胜利！", "胜利",
						JOptionPane.PLAIN_MESSAGE);
			}
			if (islose == true) {
				JOptionPane.showMessageDialog(null, "很遗憾 您失败了。", "失败",
						JOptionPane.PLAIN_MESSAGE);
			}
			Object[] options = { "查看战绩", "关闭游戏！" };
			int m = JOptionPane.showOptionDialog(null, "游戏结束了", "游戏结束",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			if (m == 0) {
				/**
				 * + 查看战绩
				 */
				String string = "得分：" + filereadandwrite.getMycredit();
				JOptionPane.showMessageDialog(null, string, "战绩",
						JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			} else if (m == 1) {
				/**
				 * 结束游戏
				 */
				System.exit(0);
			}
		}
	}

	private void isend() {
		if (planePanel.myplane.getLife() <= 0) {
			isend = true;
			islose = true;
		}
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		for (int i = 0; i < enemyplanes.size(); i++) {
			Enemyplane enemyplane = enemyplanes.get(i);
			if (enemyplane.getType() == "boss_1") {
				if (enemyplane.isDie() == true) {
					enemyplanes.remove(enemyplane);
					isend = true;
					iswin = true;
				}
			}
		}
	}

	private int f_boss1 = 1;
	private int count_boss1 = 0;

	private void boss1_changecolor() {
		/**
		 * boss1改变颜色 如果BOSS1改变颜色开关打开 那么就每隔一段时间改变颜色
		 */
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		for (int i = 0; i < enemyplanes.size(); i++) {
			Enemyplane enemyplane = enemyplanes.get(i);
			if (enemyplane.getType() == "boss_1"
					&& enemyplane.isChangecolor() == true) {
				/**
				 * 如果boss_1开始变色 那么每隔 （count_boss1加到20） 时间就改变一次颜色
				 */
				count_boss1++;
				if (count_boss1++ >= 20) {
					enemyplane.changecolor(f_boss1);
					f_boss1++;
					count_boss1 = 0;
				}
				if (f_boss1 >= enemyplane.getMaxchangecolor()) {
					f_boss1 = 1;
				}
			}
		}
	}

	private void mybullet_hit_enemybullet() {
		/**
		 * 我机子弹碰撞敌机子弹 双方不掉血双方都消失
		 */
		ArrayList<Mybullet> mybullets = planePanel.mybullets;
		ArrayList<EnemyBullet> enemyBullets = planePanel.enemyBullets;
		for (int i = 0; i < mybullets.size(); i++) {
			for (int j = 0; j < enemyBullets.size(); j++) {
				Mybullet mybullet = mybullets.get(i);
				EnemyBullet enemyBullet = enemyBullets.get(j);
				boolean ishit = mybullet_hit_enemyplane(mybullet, enemyBullet);
				if (ishit == true) {
					mybullet.setIseffect(false);
					enemyBullet.setIseffect(false);
					ishit = false;
				}
			}
		}
	}

	private void mybullet_hit_enemyplane() {
		/**
		 * 我方子弹碰撞敌机 敌机掉血
		 */
		ArrayList<Mybullet> mybullets = planePanel.mybullets;
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		for (int i = 0; i < mybullets.size(); i++) {
			for (int j = 0; j < enemyplanes.size(); j++) {
				Mybullet mybullet = mybullets.get(i);
				Enemyplane enemyplane = enemyplanes.get(j);
				boolean ishit = mybullet_hit_enemyplane(mybullet, enemyplane);
				if (ishit == true) {
					mybullet.setIseffect(false);
					mybullet.setX(-100);
					int enemylife = enemyplane.getLife();
					int myatk = planePanel.myplane.getAtk();
					int mypower = planePanel.myplane.getPower();
					enemyplane.setLife(enemylife -= myatk * mypower); // 掉血操作
																		// 公式为:atk*power
					if (enemyplane.getType() == "boss_1"
							&& enemyplane.isIsbomb() == false)
						planePanel.boss_injured = true;
					ishit = false;
				}
			}
		}
	}

	private void myplane_hit_enemyplane() {
		/**
		 * 我机碰撞敌机 敌机掉血我机伤害是平常的5倍 我机掉血
		 */
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		Myplane myplane = planePanel.myplane;
		for (int i = 0; i < enemyplanes.size(); i++) {
			Enemyplane enemyplane = enemyplanes.get(i);
			boolean ishit = mybullet_hit_enemyplane(myplane, enemyplane);
			if (ishit == true && ememyloselife == false) {
				int mylife = planePanel.myplane.getLife();
				int enemyatk = enemyplane.getAtk();
				int myatk = myplane.getAtk() * myplane.getPower();
				enemyplane.setLife(enemyplane.getLife() - myatk * 5);
				myplane.setLife(mylife - enemyatk);
				ememyloselife = true;
				if (enemyplane.getType() == "boss_1"
						&& enemyplane.isIsbomb() == false)
					planePanel.boss_injured = true;
				ishit = false;
			}
		}
	}

	private void enemybullets_hit_myplane() {
		/**
		 * 敌机子弹碰撞我机 我机掉血 子弹固定20点伤害
		 */
		ArrayList<EnemyBullet> enemyBullets = planePanel.enemyBullets;
		Myplane myplane = planePanel.myplane;
		for (int i = 0; i < enemyBullets.size(); i++) {
			EnemyBullet enemyBullet = enemyBullets.get(i);
			boolean ishit = mybullet_hit_enemyplane(myplane, enemyBullet);
			if (ishit == true) {
				enemyBullet.setIseffect(false);
				int mylife = myplane.getLife();
				myplane.setLife(mylife - 20);
				ishit = false;
			}
		}
	}

	private boolean mybullet_hit_enemyplane(Myplane myplane,
			Enemyplane enemyplane) {
		/**
		 * 我方飞机碰撞敌方子弹 供碰撞检测函数供函数调用
		 */
		int x2 = myplane.getX();
		int y2 = myplane.getY();
		int w2 = myplane.getWidth();
		int h2 = myplane.getHeight();
		int x1 = enemyplane.getX();
		int y1 = enemyplane.getY();
		int w1 = enemyplane.getWidth();
		int h1 = enemyplane.getHeight();
		return ishit(x1, y1, w1, h1, x2, y2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2)
				|| ishit(x1, y1, w1, h1, x2, y2 + h2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2 + h2);
	}

	private boolean mybullet_hit_enemyplane(Myplane myplane,
			EnemyBullet enemyBullet) {
		/**
		 * 我方飞机碰撞敌方子弹 供碰撞检测函数供函数调用
		 */
		int x1 = myplane.getX();
		int y1 = myplane.getY();
		int w1 = myplane.getWidth();
		int h1 = myplane.getHeight();
		int x2 = enemyBullet.getX();
		int y2 = enemyBullet.getY();
		int w2 = enemyBullet.getWidth();
		int h2 = enemyBullet.getHeight();
		return ishit(x1, y1, w1, h1, x2, y2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2)
				|| ishit(x1, y1, w1, h1, x2, y2 + h2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2 + h2);
	}

	private boolean mybullet_hit_enemyplane(Mybullet mybullet,
			Enemyplane enemyplane) {
		/**
		 * 我方飞机子弹碰撞敌方飞机 供碰撞检测函数供函数调用
		 */
		int x2 = mybullet.getX();
		int y2 = mybullet.getY();
		int w2 = mybullet.getWidth();
		int h2 = mybullet.getHeight();
		int x1 = enemyplane.getX();
		int y1 = enemyplane.getY();
		int w1 = enemyplane.getWidth();
		int h1 = enemyplane.getHeight();
		return ishit(x1, y1, w1, h1, x2, y2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2)
				|| ishit(x1, y1, w1, h1, x2, y2 + h2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2 + h2);
	}

	private boolean mybullet_hit_enemyplane(Mybullet mybullet,
			EnemyBullet enemyBullet) {
		/**
		 * 我机子弹碰撞敌机子弹 供碰撞检测函数供函数调用
		 */
		int x1 = mybullet.getX();
		int y1 = mybullet.getY();
		int w1 = mybullet.getWidth();
		int h1 = mybullet.getHeight();
		int x2 = enemyBullet.getX();
		int y2 = enemyBullet.getY();
		int w2 = enemyBullet.getWidth();
		int h2 = enemyBullet.getHeight();
		return ishit(x1, y1, w1, h1, x2, y2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2)
				|| ishit(x1, y1, w1, h1, x2, y2 + h2)
				|| ishit(x1, y1, w1, h1, x2 + w2, y2 + h2);
	}

	private boolean ishit(int x1, int y1, int w, int h, int x2, int y2) {
		if (x2 >= x1 && x2 <= x1 + w && y2 >= y1 && y2 <= y1 + h)
			return true;
		else
			return false;
	}

	private void mybulletmove() {
		ArrayList<Mybullet> mybullets = planePanel.mybullets;
		for (int i = 0; i < mybullets.size(); i++)
			mybullets.get(i).move();
	}

	private void removemybullet() {
		ArrayList<Mybullet> mybullets = planePanel.mybullets;
		for (int i = 0; i < mybullets.size(); i++)
			if (mybullets.get(i).isIseffect() == false)
				mybullets.remove(i);
		// for(Mybullet mybullet:mybullets)
		// if(mybullet.isIseffect()==false)
		// mybullets.remove(mybullet);

	}

	int re_count = 1; // 用于每执行4次 爆炸的帧数+1
	int f_bomb = 1; // 用于存储配执行爆炸是第几帧

	private void removeenemy() {
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		for (int i = 0; i < enemyplanes.size(); i++) {
			if (enemyplanes.get(i).isIseffect() == false) {
				/**
				 * 当一个子弹设置为无效时 不会马上移除出子弹数组 要等其爆炸完成后才会移除
				 */
				count4++;
				if (count4 == 1 * re_count) { // 此处4作为 每执行四次该函数就执行一次：爆炸的帧数+1
					enemyplanes.get(i).setIsbomb(true);
					enemyplanes.get(i).bomb(f_bomb); // 调用爆炸函数
					enemyplanes.get(i).setIsbomb(false);
					re_count++;
					f_bomb++;
					if (f_bomb > enemyplanes.get(i).getF_bomb()) {
						/**
						 * 此处的条件意思是入如果爆炸的帧数比敌机爆炸的帧数大 也就是爆炸动画执行完了 就可以移除将该敌机移除出数组了
						 */
						count4 = 0;
						f_bomb = 1;
						/**
						 * 根据死亡的敌机类型来加分
						 */
						if (enemyplanes.get(i).getType() == "normal_") {
							planePanel.myplane.setCredit(planePanel.myplane
									.getCredit() + 50);
							enemyplanes.remove(i);
						} else if (enemyplanes.get(i).getType() == "boss_1") {
							/**
							 * 如果是BOSS则先不移除出数组 因为要给游戏结束做判断 如果移除了就获取不到BOSS对象了
							 * 也就无从判断他是否死亡了
							 */
							planePanel.myplane.setCredit(planePanel.myplane
									.getCredit() + 1000);
							enemyplanes.get(i).setDie(true);// 设置死亡状态
						}
					}
				}
			}
		}
	}

	private void addmybullet() {
		if (planePanel.myplane.isFire() == true) {
			Myplane myplane = planePanel.myplane;
			planePanel.mybullets.add(new Mybullet(myplane.getX() + 2, myplane
					.getY() + 2));
		}
	}

	private void removeenemybullet() {
		ArrayList<EnemyBullet> enemyBullets = planePanel.enemyBullets;
		for (int i = 0; i < enemyBullets.size(); i++)
			if (enemyBullets.get(i).isIseffect() == false)
				enemyBullets.remove(i);
		// for(EnemyBullet enemyBullet:enemyBullets)
		// if(enemyBullet.isIseffect()==false)
		// enemyBullets.remove(enemyBullet);
	}

	private void enemybulletmove() {
		ArrayList<EnemyBullet> enemyPlaneBullets = planePanel.enemyBullets;
		for (int i = 0; i < enemyPlaneBullets.size(); i++)
			enemyPlaneBullets.get(i).move();
	}

	private void addenemybullet() {
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		Myplane myplane = planePanel.myplane;
		for (int i = 0; i < enemyplanes.size(); i++) {
			Enemyplane enemyplane = enemyplanes.get(i);
			if (enemyplane.isfire() == true) {
				if (enemyplane.getType() == "normal_")
					planePanel.enemyBullets.add(new EnemyBullet(enemyplane
							.getX()
							+ enemyplane.getWidth()
							/ 2
							- EnemyBullet.width / 2, enemyplane.getY()
							+ enemyplane.getHeight(), myplane.getX(), myplane
							.getY(), enemyplane.getX(), enemyplane.getY()));
				if (enemyplane.getType() == "boss_1") {
					planePanel.enemyBullets.add(new EnemyBoss_1_Bullet(
							enemyplane.getX() + enemyplane.getWidth() / 2
									- EnemyBullet.width / 2, enemyplane.getY()
									+ enemyplane.getHeight(), myplane,
							(EnemyBoss_1) enemyplane));
				}
			}
		}
	}

	private void enemyplanemove() {
		/**
		 * 敌机移动
		 */
		for (int i = 0; i < planePanel.enemyplanes.size(); i++) {
			planePanel.enemyplanes.get(i).move();
		}
		planePanel.repaint();
	}
}
