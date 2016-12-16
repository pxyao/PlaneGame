package com.Planegame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filereadandwrite {
	private PlanePanel planePanel;

	private int mymaxlife; // �ҵ�����ֵ
	private int myatk; // �ҵĹ�����
	private int bulletspeed;// �ӵ��ٶ�
	private int mycredit;// �ҵĻ���
	private File file;

	public Filereadandwrite(PlanePanel planePanel) {
		this.planePanel = planePanel;
		file = new File("gameinfo");
	}

	public void writegameinfo() {
		/**
		 * ������Ϸʱ���ô˺���
		 *������Ϸʱ  �����ݶ�д��TXT��
		 */
		mycredit = planePanel.myplane.getCredit();
		mymaxlife = planePanel.myplane.getMaxlife();
		myatk = planePanel.myplane.getAtk();
		bulletspeed = planePanel.myplane.getBulletspeed();
		try {
			if (file.exists()) {  //������ļ����ھ�ɾ���ٴ���һ�� �������ݵ���
				file.delete();
				file.createNewFile();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("�÷�:" + mycredit);
			bufferWritter.newLine();
			bufferWritter.write("������:" + mymaxlife);
			bufferWritter.newLine();
			bufferWritter.write("������:" + myatk);
			bufferWritter.newLine();
			bufferWritter.write("�����ٶ�:" + bulletspeed);
			bufferWritter.newLine();
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	public static void writegameinfo(File file,int mycredit,int mymaxlife,int myatk,int bulletspeed) {
		/**
		 * ������Ϸʱ���ô˺���
		 *������Ϸʱ  �����ݶ�д��TXT��
		 */
		try {
			if (file.exists()) {  //������ļ����ھ�ɾ���ٴ���һ�� �������ݵ���
				file.delete();
				file.createNewFile();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("�÷�:" + mycredit);
			bufferWritter.newLine();
			bufferWritter.write("������:" + mymaxlife);
			bufferWritter.newLine();
			bufferWritter.write("������:" + myatk);
			bufferWritter.newLine();
			bufferWritter.write("�����ٶ�:" + bulletspeed);
			bufferWritter.newLine();
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
	
	public int getMycredit() {
		return mycredit;
	}

	public void readgameinfo() {
		BufferedReader reader = null;
		try {
			if(!file.exists()){  //��������ھʹ���һ���µ�txt�ļ� ��д������
				writegameinfo();
			}
			FileReader fileReader=new FileReader(file);
			reader = new BufferedReader(fileReader);
			String tempString = null;
			String gameinfo = null;
			int line = 1;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				// ��ȡ����
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(tempString);
				gameinfo = m.replaceAll("\n").trim();
				int putdata=Integer.parseInt(gameinfo);
				switch (line) {
				case 1:
					planePanel.myplane.setCredit(putdata);
					break;
				case 2:
					planePanel.myplane.setMaxlife(putdata);
					planePanel.myplane.setLife(putdata);
					break;
				case 3:
					planePanel.myplane.setAtk(putdata);
					break;
				case 4:
					planePanel.myplane.setBulletspeed(putdata);
					break;
				default:
					break;
				}
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
