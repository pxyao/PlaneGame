package com.Planegame;

import java.util.ArrayList;

public class EnemyScript extends Thread{
	private ArrayList<Enemyplane> enemyplanes;
	public static boolean bosswarming;
	public EnemyScript(ArrayList<Enemyplane> enemyplanes) {
		this.enemyplanes=enemyplanes;
	}
	public void run(){
		Enemyplane enemy_1 = new Enemyplane(100,-50);
		Enemyplane enemy_2 = new Enemyplane(200,-50);
		Enemyplane enemy_3 = new Enemyplane(300,-50);
		enemyplanes.add(enemy_1);
		enemyplanes.add(enemy_2);
		enemyplanes.add(enemy_3);
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Enemyplane enemy_4 = new Enemyplane(100,-50);
		Enemyplane enemy_5 = new Enemyplane(200,-50);
		Enemyplane enemy_6 = new Enemyplane(300,-50);
		enemyplanes.add(enemy_4);
		enemyplanes.add(enemy_5);
		enemyplanes.add(enemy_6);
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Enemyplane enemy_7 = new Enemyplane(100,-50);
		Enemyplane enemy_8 = new Enemyplane(300,-50);
		enemyplanes.add(enemy_7);
		enemyplanes.add(enemy_8);
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Enemyplane enemy_9 = new Enemyplane(100,-50);
		Enemyplane enemy_10 = new Enemyplane(300,-50);
		enemyplanes.add(enemy_9);
		enemyplanes.add(enemy_10);
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Enemyplane enemy_11 = new Enemyplane(200,-50);
		enemyplanes.add(enemy_11);
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Enemyplane enemy_12 = new Enemyplane(50,-50);
		Enemyplane enemy_13 = new Enemyplane(120,-50);
		Enemyplane enemy_14 = new Enemyplane(190,-50);
		Enemyplane enemy_15 = new Enemyplane(300,-50);
		Enemyplane enemy_16 = new Enemyplane(380,-50);
		Enemyplane enemy_17 = new Enemyplane(450,-50);
		enemyplanes.add(enemy_12);
		enemyplanes.add(enemy_13);
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enemyplanes.add(enemy_14);
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enemyplanes.add(enemy_15);
		enemyplanes.add(enemy_16);
		try {
			sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enemyplanes.add(enemy_17);
		try {
			sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Enemyplane enemy_18 = new Enemyplane(200,-50);
		enemyplanes.add(enemy_18);
		try {
			sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		EnemyBoss_1 enemy_boss_1=new EnemyBoss_1(0, 0, 125, 150);
		enemyplanes.add(enemy_boss_1);
	}
}
