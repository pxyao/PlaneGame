package com.Planegame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Initframe extends JFrame {
	/**
	 * ��ʼ����
	 */
	private static final long serialVersionUID = -868534413563071615L;
	private int mycredit = 0;
	private int mymaxlife = 0;
	private int myatk = 0;
	private int mybulletspeed = 0;

	public Initframe() {
		File file = new File("gameinfo");
		BufferedReader bufferedReader;
		try {
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String tempString = null;// ÿһ�е�����
			String gameinfo = null;// ��ȡ���� ��Ϊ��Ϸ����
			int line = 1; // �ӵ�һ�п�ʼ��ȡ
			while ((tempString = bufferedReader.readLine()) != null) {
				// ��ȡ����
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(tempString);
				gameinfo = m.replaceAll("\n").trim();
				int outdata = Integer.parseInt(gameinfo);
				switch (line) {
				case 1:
					mycredit = outdata;
					break;
				case 2:
					mymaxlife = outdata;
					break;
				case 3:
					myatk = outdata;
					break;
				case 4:
					mybulletspeed = outdata;
					break;
				default:
					break;
				}
				line++;
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		setResizable(false);
		setSize(300, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		JLabel jl_welcome = new JLabel("��ӭ����ɻ���ս,��ѡ�������������Ļ��֣�");
		jl_welcome.setForeground(Color.red);
		jl_welcome.setBounds(20, 20, 300, 30);
		add(jl_welcome);

		JLabel jl_yourcredit = new JLabel("���֣�");
		jl_yourcredit.setBounds(120, 50, 100, 30);
		add(jl_yourcredit);

		JLabel jl_credit = new JLabel(String.valueOf(mycredit));
		jl_credit.setBounds(160, 50, 100, 30);
		add(jl_credit);

		JLabel jl_yourmaxlife = new JLabel("����ֵ��");
		jl_yourmaxlife.setBounds(105, 80, 100, 30);
		add(jl_yourmaxlife);

		JLabel jl_maxlife = new JLabel(String.valueOf(mymaxlife));
		jl_maxlife.setBounds(160, 80, 100, 30);
		add(jl_maxlife);
		
		JLabel jl_youratk = new JLabel("��������");
		jl_youratk.setBounds(105, 110, 100, 30);
		add(jl_youratk);

		JLabel jl_atk = new JLabel(String.valueOf(myatk));
		jl_atk.setBounds(160, 110, 100, 30);
		add(jl_atk);
		
		JLabel jl_yourbulletspeed = new JLabel("�ӵ����٣�");
		jl_yourbulletspeed.setBounds(95, 140, 100, 30);
		add(jl_yourbulletspeed);

		JLabel jl_bulletspeed = new JLabel(String.valueOf(mybulletspeed));
		jl_bulletspeed.setBounds(160, 140, 100, 30);
		add(jl_bulletspeed);
		
		JButton btn_boast_life = new JButton("����10������(500����)");
		btn_boast_life.setBounds(60, 200, 180, 40);
		add(btn_boast_life);
		btn_boast_life.addActionListener(new ActionListener() {
			/**
			 * ��������ֵ��ť
			 */
			public void actionPerformed(ActionEvent e) {
				if (mycredit >= 500&&mymaxlife<500) {
					mycredit = mycredit - 500;
					mymaxlife+=10;
					jl_credit.setText(String.valueOf(mycredit));
					jl_maxlife.setText(String.valueOf(mymaxlife));
				}else if(mymaxlife>=500){
					JOptionPane.showMessageDialog(null, "�������ֵ�Ѿ��ﵽ���ֵ��500");
				}else{
					JOptionPane.showMessageDialog(null, "�������Ļ����Ƿ��㹻");
				}
			}
		});

		JButton btn_boast_myatk = new JButton("����10����(1000����)");
		btn_boast_myatk.setBounds(60, 300, 180, 40);
		add(btn_boast_myatk);
		btn_boast_myatk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mycredit >= 1000&&myatk<200) {
					mycredit = mycredit - 1000;
					myatk+=10;
					jl_credit.setText(String.valueOf(mycredit));
					jl_atk.setText(String.valueOf(myatk));
				}else if(myatk>=200){
					JOptionPane.showMessageDialog(null, "��󹥻����Ѿ��ﵽ���ֵ��200");
				}else{
					JOptionPane.showMessageDialog(null, "�������Ļ����Ƿ��㹻");
				}
			}
		});
		
		
		JButton btn_boast_myatkspeed = new JButton("����1�㹥���ٶ�(2500����)");
		btn_boast_myatkspeed.setBounds(50, 400, 200, 40);
		add(btn_boast_myatkspeed);
		btn_boast_myatkspeed.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mycredit >= 2500&&mybulletspeed>10) {
					mycredit = mycredit - 2500;
					mybulletspeed-=1;
					jl_credit.setText(String.valueOf(mycredit));
					jl_bulletspeed.setText(String.valueOf(mybulletspeed));
				}else if(mybulletspeed<=10){
					JOptionPane.showMessageDialog(null, "�����ٶ��Ѿ��ﵽ���ֵ��10");
				}else{
					JOptionPane.showMessageDialog(null, "�������Ļ����Ƿ��㹻");
				}
			}
		});
		
		
		JButton btn_sava = new JButton("���湺��");
		btn_sava.setBounds(35, 500, 100, 30);
		add(btn_sava);
		btn_sava.addActionListener(new ActionListener() {
			/**
			 * ��������
			 */
			public void actionPerformed(ActionEvent e) {
				File file=new File("gameinfo.txt");
				Filereadandwrite.writegameinfo(file, mycredit, mymaxlife, myatk, mybulletspeed);
				JOptionPane.showMessageDialog(null, "����ɹ�");
			}
		});

		JButton btn_start = new JButton("��ʼ��Ϸ");
		btn_start.setBounds(155, 500, 100, 30);
		add(btn_start);
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlanePanel planePanel = new PlanePanel();
				Filereadandwrite filereadandwrite = new Filereadandwrite(planePanel);
				PlaneFrame planeFrame = new PlaneFrame(planePanel);
				filereadandwrite.readgameinfo(); //�����ʼ��Ϸ���ٶ�ȡ����
				PlaneThread planeThread = new PlaneThread(planePanel,
						planeFrame, filereadandwrite);
				EnemyScript enemyScript = new EnemyScript(
						planePanel.enemyplanes);
				enemyScript.start();
				EnemyLoseLife enemyLoseLife = new EnemyLoseLife();
				enemyLoseLife.start();
				planeThread.start();
				dispose();
			}
		});
		setVisible(true);
	}
}
