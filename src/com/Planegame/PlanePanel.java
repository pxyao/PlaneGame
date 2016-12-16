package com.Planegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

import org.w3c.dom.events.MouseEvent;

public class PlanePanel extends JPanel implements MouseMotionListener,MouseListener{
	String backpath;
	Myplane myplane;
	ArrayList<Enemyplane> enemyplanes;
	ArrayList<EnemyBullet> enemyBullets;//�л��ӵ�����
	ArrayList<Mybullet> mybullets;//�һ��ӵ�����
	private int count=0;// ���ڻ����˺���
	public boolean boss_injured=false;//���ڻ�boss���˺���
	
	
	public PlanePanel(){
		backpath="/background_1.gif";
		myplane=new Myplane();
		addMouseMotionListener(this);
		addMouseListener(this);
		enemyplanes=new ArrayList<Enemyplane>();
		enemyBullets=new ArrayList<EnemyBullet>();
		mybullets=new ArrayList<Mybullet>();
	}
	public void paint(Graphics g){
		super.paint(g);
		drawback(g);
		drawmyplane(g);
		drawenemyplane(g);
		drawenemybullet(g);
		drawmybullet(g);
		drawmyplanelife(g);//��Ѫ��
		drawenemyplanelife(g);//��BOSSѪ��
		drawenemyinjured(g);//���л�����
		drawcredit(g); //������
	}

	private void drawcredit(Graphics g) {
		/**
		 * �����ֻ������½�
		 */
		g.setColor(Color.white);
		g.drawString("���֣�"+" "+myplane.getCredit(), 50, 550);
	}
	private void drawenemyinjured(Graphics g) {
		/**
		 * ���л�����
		 * ���һ��ӵ���ײ�л�ʱ boss_injured��������Ϊtrue
		 * ֻҪ�ڱ�������boss_injured����Ϊtrue�ͻ���Ӧ���˵�ͼƬ
		 */
		for (int i = 0; i < enemyplanes.size(); i++) {
			Enemyplane enemyplane = enemyplanes.get(i);
			if (enemyplane.getType() == "boss_1"&&boss_injured) {
				g.drawImage(new ImageIcon(Enemyplane.class.getResource("/boss1/enemy_boss_1_injured.png"))
				.getImage(),enemyplane.getX(),enemyplane.getY(),enemyplane.getWidth(),
				enemyplane.getHeight(),this);
				if(count++==20)
				{
					boss_injured=false;
					count=0;
				}
			}
		}
	}
	private void drawenemyplanelife(Graphics g) {
		/**
		 * ��BOSSѪ��
		 */
		for(int i=0;i<enemyplanes.size();i++){
			Enemyplane enemyplane=enemyplanes.get(i);
			if(enemyplane.getType()=="boss_1"){
				g.setColor(Color.RED);
				g.setFont(new Font("����",Font.PLAIN,18));
				g.drawString("B", 22, 50);
				g.drawString("O",22, 70);
				g.drawString("O",22, 90);
				g.drawString("S",22, 110);
				if(enemyplane.getLife()>=enemyplane.getMaxlife()*4/5+10)
					g.setColor(Color.blue);
				else if (enemyplane.getLife()>=enemyplane.getMaxlife()*3/5+10) {
					g.setColor(Color.orange);
				}else if (enemyplane.getLife()>=enemyplane.getMaxlife()*2/5+10){
					g.setColor(Color.green);
				}else if (enemyplane.getLife()>=enemyplane.getMaxlife()/5+10){
					g.setColor(Color.pink);
				}
				g.drawRect(23, 120, 6,(enemyplane.getMaxlife()/5)/5);
				g.fillRect(23, 120, 6,((enemyplane.getLife()-1)%2000)/5);
			}
		}
	}
	private void drawmyplanelife(Graphics g){
		/**
		 * /���һ���Ѫ��
		 */
		if(myplane.getLife()>50)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);
		g.drawRect(450, 50, 10, myplane.getLife());
		g.fillRect(450, 50, 10, myplane.getLife());
	}
	private void drawmybullet(Graphics g) {
		for(int i=0;i<mybullets.size();i++)
		g.drawImage(new ImageIcon(Mybullet.class.getResource(mybullets.get(i).getPath())).getImage()
		, mybullets.get(i).getX(), mybullets.get(i).getY(), mybullets.get(i).getWidth(),mybullets.get(i)
		.getHeight(),this);
	}
	private void drawenemybullet(Graphics g) {
		for(int i=0;i<enemyBullets.size();i++)
			g.drawImage(new ImageIcon(EnemyBullet.class.getResource(enemyBullets.get(i).getPath())).getImage(),
					enemyBullets.get(i).getX(), enemyBullets.get(i).getY(), enemyBullets.get(i).getWidth()
			, enemyBullets.get(i).getHeight(), this);
	}
	private void drawmyplane(Graphics g) {
		g.drawImage(new ImageIcon(Myplane.class.getResource(myplane.getPath())).
				getImage(), myplane.getX(), myplane.getY(), myplane.getWidth(), myplane.getHeight(),this);
	}
	private void drawenemyplane(Graphics g) {
		for(int i=0;i<enemyplanes.size();i++){
		g.drawImage(new ImageIcon(Enemyplane.class.getResource(enemyplanes.get(i).getPath())).
				getImage(), enemyplanes.get(i).getX(), enemyplanes.get(i).getY(), enemyplanes.get(i).
				getWidth(), enemyplanes.get(i).getHeight(),this);
		}
	}
	private void drawback(Graphics g) {
		g.drawImage(new ImageIcon(PlanePanel.class.getResource(backpath)).
				getImage(), 0, 0, PlaneFrame.width, PlaneFrame.height,this);
	}
	public void mouseDragged(java.awt.event.MouseEvent e) {
		myplane.setX(e.getX()-20);
		myplane.setY(e.getY()-20);
	}
	public void mouseMoved(java.awt.event.MouseEvent e) {
		myplane.setX(e.getX()-20);
		myplane.setY(e.getY()-20);
	}
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	public void mousePressed(java.awt.event.MouseEvent e) {
		myplane.setFire(true);
	}
	public void mouseReleased(java.awt.event.MouseEvent e) {
		myplane.setFire(false);
	}
}
