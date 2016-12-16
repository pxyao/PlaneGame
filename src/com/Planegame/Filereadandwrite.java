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

	private int mymaxlife; // 我的生命值
	private int myatk; // 我的攻击力
	private int bulletspeed;// 子弹速度
	private int mycredit;// 我的积分
	private File file;

	public Filereadandwrite(PlanePanel planePanel) {
		this.planePanel = planePanel;
		file = new File("gameinfo");
	}

	public void writegameinfo() {
		/**
		 * 结束游戏时调用此函数
		 *结束游戏时  把数据都写入TXT中
		 */
		mycredit = planePanel.myplane.getCredit();
		mymaxlife = planePanel.myplane.getMaxlife();
		myatk = planePanel.myplane.getAtk();
		bulletspeed = planePanel.myplane.getBulletspeed();
		try {
			if (file.exists()) {  //如果该文件存在就删除再创建一个 避免数据叠加
				file.delete();
				file.createNewFile();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("得分:" + mycredit);
			bufferWritter.newLine();
			bufferWritter.write("生命力:" + mymaxlife);
			bufferWritter.newLine();
			bufferWritter.write("攻击力:" + myatk);
			bufferWritter.newLine();
			bufferWritter.write("发射速度:" + bulletspeed);
			bufferWritter.newLine();
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	public static void writegameinfo(File file,int mycredit,int mymaxlife,int myatk,int bulletspeed) {
		/**
		 * 结束游戏时调用此函数
		 *结束游戏时  把数据都写入TXT中
		 */
		try {
			if (file.exists()) {  //如果该文件存在就删除再创建一个 避免数据叠加
				file.delete();
				file.createNewFile();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write("得分:" + mycredit);
			bufferWritter.newLine();
			bufferWritter.write("生命力:" + mymaxlife);
			bufferWritter.newLine();
			bufferWritter.write("攻击力:" + myatk);
			bufferWritter.newLine();
			bufferWritter.write("发射速度:" + bulletspeed);
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
			if(!file.exists()){  //如果不存在就创建一个新的txt文件 并写入数据
				writegameinfo();
			}
			FileReader fileReader=new FileReader(file);
			reader = new BufferedReader(fileReader);
			String tempString = null;
			String gameinfo = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 提取数字
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
