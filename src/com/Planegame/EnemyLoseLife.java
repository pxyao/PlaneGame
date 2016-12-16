package com.Planegame;

public class EnemyLoseLife extends Thread{
	public void run(){
		while(true){
			PlaneThread.ememyloselife=false;
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
