package com.Planegame;


import javax.swing.JFrame;

public class PlaneFrame extends JFrame{
	public static int width=500;
	public static int height=600;
	public PlaneFrame(PlanePanel planePanel){
		setVisible(true);
		setSize(width,height);
		setTitle("ø’’Ω");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(planePanel);
	}
}
