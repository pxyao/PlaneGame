package com.Planegame;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PlaneThread extends Thread {
	private PlanePanel planePanel;
	private int count1 = 0;// �����һ��ӵ�
	private int count2 = 0;// ���ڵл��ӵ�
	private int count3 = 0;// �����ӵ��ƶ�
	private int count4 = 0;// �����Ƴ���Ч�ӵ�
	public int enemybulletspeed = 3;
	public int enemybulletmove = 1;
	private boolean iswin = false;// �Ƿ���ʤ��
	private boolean islose = false;// ��ʧ��
	private boolean isend = false;// ������Ϸ
	private Filereadandwrite filereadandwrite;
	private PlaneFrame planeFrame;
	public static boolean ememyloselife = false; // �л��Ƿ��ǵ�Ѫ״̬

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
			if (count3++ == enemybulletmove) { // 5�ǵл��ӵ��ƶ�����
				enemybulletmove();
				count3 = 0;
			}
			if (count2++ == enemybulletspeed) { // 20�ǵл������ӵ�����
				addenemybullet();
				count2 = 0;
			}
			removeenemybullet();
			if (count1++ == planePanel.myplane.getBulletspeed()) {
				addmybullet();
				count1 = 0;
			}
			mybulletmove();
			mybullet_hit_enemyplane(); // �һ��ӵ���ײ�л�
			enemybullets_hit_myplane(); // �л��ӵ���ײ�һ�
			myplane_hit_enemyplane(); // �һ���ײ�л�
			mybullet_hit_enemybullet(); // �һ��ӵ���ײ�л��ӵ�
			boss1_changecolor();
			removeenemy();// �Ƴ���Ч�л�
			removemybullet();
			enemyplanemove();
			isend();// �ж��Ƿ����
			end(); // ��������
		}
	}

	private void end() {
		/**
		 * ��Ϸ����ѡ����
		 */
		if (isend == true) {
			filereadandwrite.writegameinfo();
			if (iswin == true) {
				JOptionPane.showMessageDialog(null, "��ϲ�����ʤ����", "ʤ��",
						JOptionPane.PLAIN_MESSAGE);
			}
			if (islose == true) {
				JOptionPane.showMessageDialog(null, "���ź� ��ʧ���ˡ�", "ʧ��",
						JOptionPane.PLAIN_MESSAGE);
			}
			Object[] options = { "�鿴ս��", "�ر���Ϸ��" };
			int m = JOptionPane.showOptionDialog(null, "��Ϸ������", "��Ϸ����",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			if (m == 0) {
				/**
				 * + �鿴ս��
				 */
				String string = "�÷֣�" + filereadandwrite.getMycredit();
				JOptionPane.showMessageDialog(null, string, "ս��",
						JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			} else if (m == 1) {
				/**
				 * ������Ϸ
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
		 * boss1�ı���ɫ ���BOSS1�ı���ɫ���ش� ��ô��ÿ��һ��ʱ��ı���ɫ
		 */
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		for (int i = 0; i < enemyplanes.size(); i++) {
			Enemyplane enemyplane = enemyplanes.get(i);
			if (enemyplane.getType() == "boss_1"
					&& enemyplane.isChangecolor() == true) {
				/**
				 * ���boss_1��ʼ��ɫ ��ôÿ�� ��count_boss1�ӵ�20�� ʱ��͸ı�һ����ɫ
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
		 * �һ��ӵ���ײ�л��ӵ� ˫������Ѫ˫������ʧ
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
		 * �ҷ��ӵ���ײ�л� �л���Ѫ
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
					enemyplane.setLife(enemylife -= myatk * mypower); // ��Ѫ����
																		// ��ʽΪ:atk*power
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
		 * �һ���ײ�л� �л���Ѫ�һ��˺���ƽ����5�� �һ���Ѫ
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
		 * �л��ӵ���ײ�һ� �һ���Ѫ �ӵ��̶�20���˺�
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
		 * �ҷ��ɻ���ײ�з��ӵ� ����ײ��⺯������������
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
		 * �ҷ��ɻ���ײ�з��ӵ� ����ײ��⺯������������
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
		 * �ҷ��ɻ��ӵ���ײ�з��ɻ� ����ײ��⺯������������
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
		 * �һ��ӵ���ײ�л��ӵ� ����ײ��⺯������������
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

	int re_count = 1; // ����ÿִ��4�� ��ը��֡��+1
	int f_bomb = 1; // ���ڴ洢��ִ�б�ը�ǵڼ�֡

	private void removeenemy() {
		ArrayList<Enemyplane> enemyplanes = planePanel.enemyplanes;
		for (int i = 0; i < enemyplanes.size(); i++) {
			if (enemyplanes.get(i).isIseffect() == false) {
				/**
				 * ��һ���ӵ�����Ϊ��Чʱ ���������Ƴ����ӵ����� Ҫ���䱬ը��ɺ�Ż��Ƴ�
				 */
				count4++;
				if (count4 == 1 * re_count) { // �˴�4��Ϊ ÿִ���Ĵθú�����ִ��һ�Σ���ը��֡��+1
					enemyplanes.get(i).setIsbomb(true);
					enemyplanes.get(i).bomb(f_bomb); // ���ñ�ը����
					enemyplanes.get(i).setIsbomb(false);
					re_count++;
					f_bomb++;
					if (f_bomb > enemyplanes.get(i).getF_bomb()) {
						/**
						 * �˴���������˼���������ը��֡���ȵл���ը��֡���� Ҳ���Ǳ�ը����ִ������ �Ϳ����Ƴ����õл��Ƴ���������
						 */
						count4 = 0;
						f_bomb = 1;
						/**
						 * ���������ĵл��������ӷ�
						 */
						if (enemyplanes.get(i).getType() == "normal_") {
							planePanel.myplane.setCredit(planePanel.myplane
									.getCredit() + 50);
							enemyplanes.remove(i);
						} else if (enemyplanes.get(i).getType() == "boss_1") {
							/**
							 * �����BOSS���Ȳ��Ƴ������� ��ΪҪ����Ϸ�������ж� ����Ƴ��˾ͻ�ȡ����BOSS������
							 * Ҳ���޴��ж����Ƿ�������
							 */
							planePanel.myplane.setCredit(planePanel.myplane
									.getCredit() + 1000);
							enemyplanes.get(i).setDie(true);// ��������״̬
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
		 * �л��ƶ�
		 */
		for (int i = 0; i < planePanel.enemyplanes.size(); i++) {
			planePanel.enemyplanes.get(i).move();
		}
		planePanel.repaint();
	}
}
