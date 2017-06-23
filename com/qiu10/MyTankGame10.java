/**
 * ���ܣ�̹����Ϸ6.0(�����Ż�)
 * 1.����̹��.
 * 2.�ҵ�̹�˿������������ƶ�
 * 3.���Է����ӵ����ӵ���������(���7��)
 * 4.���ҵ��ӵ����е���̹��ʱ������̹����ʧ����ըЧ����
 * 5.�ұ�����ʱ����ʾ��ըЧ��
 * 6.��ֹ����̹���ص��˶�
 *   6.1 �������ж��Ƿ��ص��˶��ķ���д��EnemyTank��
 * 7.���Էֹ�
 *   7.1 ��һ����ʼ��Panel������һ����
 *   7.2 ��˸Ч��
 * 8.���Լ�¼��ҵĳɼ�
 *   8.1 ���ļ���
 *   8.2 ��дһ����¼�࣬��ɶ���ҵļ�¼
 *   8.3 ����ɱ��湲�����˶���������̹�˵Ĺ���
 *   8.4 �����˳���Ϸ�����Լ�¼��ʱ���˵�̹�����꣬�����Իָ�
 * 9.�ٴ��Ż�
 *   9.1 EnemyTank֮isTouchOther����
 *   9.2 hero�߽�����   
 */
package com.qiu10;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;

public class MyTankGame10 extends JFrame implements ActionListener,Runnable{

	MyPanel mp=null;
	//����һ����ʼ���
	MyStarPanel msp=null;
	//��������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	//��ʼ��Ϸ
	JMenu jm1=null;

	JMenuItem jmi1=null;
	//�˳�ϵͳ
	JMenuItem jmi2=null;
	//�����˳�
	JMenuItem jmi3=null;
	//���Ͼ�
	JMenuItem jmi4=null;
	//��һ��
	JMenuItem jmi5=null;
	
	int bz1=0,bz2=0;
	int getAllHit=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame10 mtg=new MyTankGame10();
		Thread t=new Thread(mtg);
		t.start();

	}
	
	//���췽��
	public MyTankGame10()
	{		
		//�����˵����˵�Զ��
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ(G)");
		
		//���ÿ�ݷ�ʽ
		jm1.setMnemonic('G');
		

		
		jmi1=new JMenuItem("����Ϸ");
		jmi3=new JMenuItem("�����˳�");
		jmi5=new JMenuItem("��һ��");
		jmi2=new JMenuItem("�˳���Ϸ");
		jmi4=new JMenuItem("������һ��");
		jmi1.addActionListener(this);
		jmi1.setActionCommand("new");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("next");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("continue");
		
		jm1.add(jmi1);
		jm1.add(jmi4);
		jm1.add(jmi5);
		jm1.add(jmi3);
		jm1.add(jmi2);
		jmb.add(jm1);		
		
		msp=new MyStarPanel();
		Thread t=new Thread(msp);
		t.start();
		
		this.setJMenuBar(jmb);
		this.add(msp);
		
		this.setSize(600,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	//����������� 
	public  void addPast()
	{
		this.getAllHit=Recorder.getAllEnNum();
		this.remove(mp);
		MyStarPanel.guan++;
		MyStarPanel.bz=0;
		 msp=new MyStarPanel();
		Thread t=new Thread(msp);
		t.start();

		this.add(msp);
		this.setVisible(true);
	}
	//�����������
	public void addDie()
	{
		this.remove(mp);
	//	MyStarPanel.guan++;
		MyStarPanel.bz=1;
		 msp=new MyStarPanel();
		Thread t=new Thread(msp);
		t.start();

		this.add(msp);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//���û���ͬ�ĵ������Ӧ�Ĵ���
		if(arg0.getActionCommand().equals("new"))
		{
			//����ս�����
			mp=new MyPanel("newGame");
			
			MyStarPanel.guan=1;
			Recorder.setMyLife(3);
			Recorder.setEnNum(20);
			this.bz1=0;
			this.bz2=0;
			MyStarPanel.bz=0;
			
			Shot.speed=2;
			EnemyTank.speed=1;
			
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ  ˢ��JFrame
			this.setVisible(true);
		}else if(arg0.getActionCommand().equals("continue")){
			
			Recorder.getRecord();
			
			//����ս�����
			mp=new MyPanel("newGame");
			
			MyStarPanel.guan=1;
			Recorder.setMyLife(3);
			Recorder.setEnNum(20);
			this.bz1=0;
			this.bz2=0;
			MyStarPanel.bz=0;
			
			Shot.speed=2;
			EnemyTank.speed=1;
			
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ  ˢ��JFrame
			this.setVisible(true);
			
		}
		else if(arg0.getActionCommand().equals("exit"))
		{
			//�û�����˳�ϵͳ�˵�
			//������ٵ�������
			File file = new File("d:\\myRecording.txt");
			if (file.exists()){
			 file.delete();
			} 
			
			System.exit(0);
		}else if(arg0.getActionCommand().equals("saveExit"))
		{
			//�������̹������ͻ��ٵ��˵�����
			Recorder.setEts(mp.ets);
			Recorder.keepRecAndEnemy();
			
			System.exit(0);
		}else if(arg0.getActionCommand().equals("next"))
		{
			//����ս�����
			mp=new MyPanel("newGame");
			
			//Recorder.setMyLife(3);
			Recorder.setAllEnNum(this.getAllHit);
			Recorder.setEnNum(20);//20+10*(MyStarPanel.guan-1)
			this.bz1=0;
			this.bz2=0;
			MyStarPanel.bz=0;
			
			Shot.speed++;
			EnemyTank.speed++;
								
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//��ɾ���ɵĿ�ʼ���
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ  ˢ��JFrame
			this.setVisible(true);
		}
		
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			if(this.bz1==0){
				if(Recorder.getEnNum()==0)
				{
					this.bz1++;
					this.addPast();
				}
			}
			if(this.bz2==0){
				if(Recorder.getMyLife()==0)
				{
					this.bz2++;
					this.addDie();
				}
			}

		}
	}


}
//����һ����ʾ����
class MyStarPanel extends JPanel implements Runnable
{
	int times=0;
	static int guan=1;
	static int bz=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0,0,400,300);
		
		if(times%2==0)
		{
			//��ʾ��Ϣ
			g.setColor(Color.yellow);
			Font myfont=new Font("�����п�",Font.BOLD,30);
			g.setFont(myfont);
			if(bz==0)
			{
				g.drawString("stage�� "+guan,150,150);
			}else{
				g.drawString("Ӣ�۰ܱ�",150,150);
			}
			
			
		}
		
		
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			times++;
			//�ػ�
			this.repaint();
		}
		
	}
	
}

//�ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable
{
	//����һ���ҵ�̹��
	Hero hero=null;
	
	//������˵�̹����
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	Vector<Node> nodes=new Vector<Node>();
	
	//����ը���༯��
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	int ensize=4;
	
	//��������ͼƬ������ͼƬ�������һ��ը������ըЧ����
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//���췽��
	public MyPanel(String flag)
	{
		//�ָ���¼
		//Recorder.getRecord();
		
		hero=new Hero(200,250);
		//��ʼ�����˵�̹��
		if(flag.equals("newGame"))
		{
			for(int i=0;i<ensize;i++)
			{
				//��������̹��
				EnemyTank et=new EnemyTank((i+1)*50,30);
				et.setColor(0);
				et.setDirect(2);
				//��MyPanel�ĵ���̹�����������õ���̹��
				et.setEts(ets);
				
				//��������̹��
				Thread t=new Thread(et);
				t.start();
				//������̹�����һ���ӵ�
				Shot s=new Shot(et.x,et.y+15,2);
				//���������̹��
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//����
				ets.add(et);
				
			}
		}else if(flag.equals("continue"))
		{
			this.nodes=Recorder.getNode();
			for(int i=0;i<nodes.size();i++)
			{
				Node node=nodes.get(i);
				//��������̹��
				EnemyTank et=new EnemyTank(node.x,node.y);
				et.setColor(0);
				et.setDirect(node.direct);
				//��MyPanel�ĵ���̹�����������õ���̹��
				et.setEts(ets);
				
				//��������̹��
				Thread t=new Thread(et);
				t.start();
				//������̹�����һ���ӵ�
				Shot s=new Shot(et.x,et.y+15,2);
				//���������̹��
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//����
				ets.add(et);
			}
		}
			
		//��ʼ������ͼƬ
		try {
			image1=ImageIO.read(new File("bomb1.gif"));
			image2=ImageIO.read(new File("bomb2.gif"));
			image3=ImageIO.read(new File("bomb3.gif"));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}
	
	//������ʾ��Ϣ
	public void showInfo(Graphics g)
	{
		//������ʾ��Ϣ̹��
		this.drawTank(80,340,g,0,0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+" ",100,345);
		this.drawTank(140,340,g,0,1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+" ",160,345);
		
		//������ҵ��ܳɼ�
		g.setFont(new Font("����",Font.BOLD,20));
		g.drawString("����ܳɼ�",440,30);
		
		this.drawTank(450,70,g,0,0);
		
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+" ",490,75);
		
		g.drawString("��ǰ�� "+MyStarPanel.guan+" ��",430,150);
	}
		
	//��дpaint
	public void paint(Graphics g)
	{
		//���ø��෽����ɳ�ʼ��
		super.paint(g);
		g.fillRect(0,0,400,300);
		//������ʾ��Ϣ
		this.showInfo(g);
		
		//�����Լ���̹��
		if(hero.isLive)
		{
			this.drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
		}
				
		//��ss��ȡ��ÿ���ӵ���������
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myshot=hero.ss.get(i);
			
			//����һ���ӵ�
			if(myshot!=null&&myshot.isLive==true)
			{
				g.draw3DRect(myshot.x,myshot.y,1,1,false);
			}
			if(myshot.isLive==false)
			{
				//��ss��ɾ�����ӵ�
				hero.ss.remove(myshot);
			}
		}
		
		//����ը��
		for(int i=0;i<bombs.size();i++)
		{
			//ȡ��ը��
			Bomb b=bombs.get(i);
			
			if(b.life>6)
			{
				g.drawImage(image1,b.x,b.y,30,30,this);
			}else if(b.life>3){
				g.drawImage(image2,b.x,b.y,30,30,this);
			}else if(b.life>0){
				g.drawImage(image3,b.x,b.y,30,30,this);
			}
			//��b������ֵ��С
			b.lifeDown();
			//���ը��������ֵΪ0���ʹ�bombs������ɾȥ
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		
		//�������˵�̹��
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive==true)
			{
				this.drawTank(et.getX(),et.getY(),g,et.getDirect(),0);

			}
			//�ٻ������˵��ӵ�
			for(int j=0;j<et.ss.size();j++)
			{
				Shot enemyshot=et.ss.get(j);
				if(enemyshot.isLive)
				{
					g.setColor(Color.cyan);
					g.draw3DRect(enemyshot.x,enemyshot.y,1,1,false);
				}else{
					//��������ӵ������ʹ�Vector��ɾ��
					et.ss.remove(enemyshot);
				}
			}
			
		}
	}
	
	//�����ӵ��Ƿ������
	public void hitMe()
	{
		if(hero.isLive)
		{
			//ȡ�����˵�ÿһ̹��
			for(int i=0;i<ets.size();i++)
			{
				//ȡ��̹��
				EnemyTank et=ets.get(i);
				
				//ȡ��ÿһ���ӵ�
				for(int j=0;j<et.ss.size();j++)
				{
					//ȡ���ӵ�
					Shot enemyshot=et.ss.get(j);
					if(this.hitTank(enemyshot,hero))
					{
						Recorder.reduceMyLife();
						if(Recorder.getMyLife()>0)
						{
							hero=new Hero(200,250);
						}
					}
				}
			}
		}
		
	}
	
	//�ж��ҵ��ӵ��Ƿ���е���̹��
	public void hitEnemyTank()
	{
		//�ж��Ƿ���е���̹��
		for(int i=0;i<hero.ss.size();i++)
		{
			//ȡ���ӵ�
			Shot myshot=hero.ss.get(i);
			//�ж��ӵ��Ƿ���Ч
			if(myshot.isLive==true)
			{
				//ȡ��ÿ������̹�ˣ���֮�ж�
				for(int j=0;j<ets.size();j++)
				{
					//ȡ��̹��
					EnemyTank et=ets.get(j);
					
					if(et.isLive==true)
					{
						if (this.hitTank(myshot,et))
						{
							Recorder.reduceEnNum();
							Recorder.addEnNum();
							if(Recorder.getEnNum()>3)
							{
								//��������̹��
								int x,y,z;
								x=(int)(Math.random()*370)+15;
								y=(int)(Math.random()*270)+15;
								z=(int)(Math.random()*3);
								EnemyTank et1=new EnemyTank(x,y);
								et1.setColor(0);
								et1.setDirect(z);
								
								Shot s1=null;
								switch(z)
								{
								case 0:
									s1=new Shot(et1.x,et1.y-15,0);
									break;
								case 1:
									s1=new Shot(et1.x+15,et1.y,1);
									break;
								case 2:								
									s1=new Shot(et1.x,et1.y+15,2);
									break;
								case 3:
									s1=new Shot(et1.x-15,et1.y,3);
									break;
								}
								
						//		Shot s=new Shot(et1.x,et1.y+15,2);
								//���������̹��
								et1.ss.add(s1);
								Thread t2=new Thread(s1);
								t2.start();
								
								ets.add(et1);
								//��MyPanel�ĵ���̹�����������õ���̹��
								et1.setEts(ets);
								
								
								//��������̹��
								Thread t=new Thread(et1);
								t.start();
								//������̹�����һ���ӵ�

								
							}
						}
					}
				}
			}
		}
	}
	
	//дһ������ר���ж��ӵ��Ƿ���е���̹��
	public boolean hitTank(Shot s,Tank et)
	{
		boolean bl=false;
		//�ж�̹�˷���
		switch(et.direct)
		{
		//���̹�˷��������ϻ�������
		case 0:
		case 2:
			if(s.x>et.x-10&&s.x<et.x+10&&s.y>et.y-15&&s.y<et.y+15)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				//����̹������
				et.isLive =false;
				//����һ��ը��������Vector
				Bomb b=new Bomb(et.x-10,et.y-15);
				bombs.add(b);
				bl=true;
			}
			break;
		case 1:
		case 3:
			if(s.x>et.x-15&&s.x<et.x+15&&s.y>et.y-10&&s.y<et.y+10)
			{
				//�ӵ�����
				s.isLive=false;
				//����̹������
				et.isLive =false;
				//����һ��ը��������Vector
				Bomb b=new Bomb(et.x-15,et.y-10);
				bombs.add(b);
				bl=true;
			}
			break;
		}
		return bl;
	}
	
	//����̹�˵ĺ���
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		//�ж�ʲô���͵�̹��
		switch(type)
		{case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		
		//�жϷ���
		switch(direct)
		{
		//����
		case 0:
			//�����ҵ�̹�ˣ���ʱ�ٷ�װ��һ��������
			//1.������ߵľ���
			g.fill3DRect(x-10,y-15,5,30,false);
			//2.�����ұߵľ���
			g.fill3DRect(x+5,y-15,5,30,false);
			//3.�����м����
			g.fill3DRect(x-5,y-10,10,20,false);
			//4.����Բ��
			g.fillOval(x-5,y-5,10,10);
			//5.������
			g.drawLine(x,y,x,y-15);
			break;
			//����
		case 1:
			//1.�����ϱߵľ���
			g.fill3DRect(x-15,y-10,30,5,false);
			//2.�����±ߵľ���
			g.fill3DRect(x-15,y+5,30,5,false);
			//3.�����м����
			g.fill3DRect(x-10,y-5,20,10,false);
			//4.����Բ��
			g.fillOval(x-5,y-5,10,10);
			//5.������
			g.drawLine(x,y,x+15,y);
			break;
			//����
		case 2:
			//1.������ߵľ���
			g.fill3DRect(x-10,y-15,5,30,false);
			//2.�����ұߵľ���
			g.fill3DRect(x+5,y-15,5,30,false);
			//3.�����м����
			g.fill3DRect(x-5,y-10,10,20,false);
			//4.����Բ��
			g.fillOval(x-5,y-5,10,10);
			//5.������
			g.drawLine(x,y,x,y+15);
			break;
			//����
		case 3:
			//1.�����ϱߵľ���
			g.fill3DRect(x-15,y-10,30,5,false);
			//2.�����±ߵľ���
			g.fill3DRect(x-15,y+5,30,5,false);
			//3.�����м����
			g.fill3DRect(x-10,y-5,20,10,false);
			//4.����Բ��
			g.fillOval(x-5,y-5,10,10);
			//5.������
			g.drawLine(x,y,x-15,y);
			break;
		}
	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	//�����´���a��ʾ����s��ʾ���£�d��ʾ���ң�w��ʾ����
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==KeyEvent.VK_W)
		{
			//�����ҵ�̹�˵ķ���
			this.hero.setDirect(0);
			if(this.hero.y>15)
			{
				this.hero.moveUp();
			}			
		}else if(arg0.getKeyCode()==KeyEvent.VK_D)
		{
			//����
			this.hero.setDirect(1);
			if(this.hero.x<400-15)
			{
				this.hero.moveRight();
			}			
		}else if(arg0.getKeyCode()==KeyEvent.VK_S)
		{
			this.hero.setDirect(2);
			if(this.hero.y<300-15)
			{
				this.hero.moveDown();
			}			
		}else if(arg0.getKeyCode()==KeyEvent.VK_A)
		{
			this.hero.setDirect(3);
			if(this.hero.x>15)
			{
				this.hero.moveLeft();
			}			
		}
		
		if(arg0.getKeyCode()==KeyEvent.VK_J)
		{
			//�ж�����Ƿ���j��
			if(hero.ss.size()<7)
			{
				this.hero.shotEnemy();
			}
			
		}
		
		//�����ػ洰��
		this.repaint();
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		// TODO Auto-generated method stub
		//ÿ��100�����ػ��ӵ�
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
			
			this.hitEnemyTank();
			//���˵�̹���Ƿ������
			this.hitMe();
						
			//�ػ�
			this.repaint();
		}
		
	}
}

//�ָ�����
class Node
{
	int x;
	int y;
	int direct;
	public Node(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}

//��¼��
class Recorder
{
	//��¼ÿ���ж��ٵ���
	private static int enNum=20;
	//�������ж��ٿ���̹��
	private static int myLife=3;
	//��¼�ܹ�������ٵ���̹��
	private static int allEnNum=0;
	//���ļ��лָ���¼��
 	static Vector<Node> nodes=new Vector<Node>();
		
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//��ɶ�ȡ����
	public  static Vector<Node> getNode()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n="";
				n=br.readLine();
			allEnNum=Integer.parseInt(n);
			while((n=br.readLine())!=null)
			{
				String []xyz=n.split(" ");
				Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
				nodes.add(node);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		return nodes;
	}
	
	//������ٵ��������͵��˵����꣬����
	public static void keepRecAndEnemy()
	{
		try {
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
			
			//���浱ǰ��ĵ��˵�����ͷ���
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				
				if(et.isLive)
				{
					String recode=et.x+" "+et.y+" "+et.direct;
					bw.write(recode+"\r\n");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			//�ر��� ���ȹ�
			try {
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	//���ļ��ж�ȡ��¼
	public static void getRecord()
	 {
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n=br.readLine();
			allEnNum=Integer.parseInt(n);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	//����һ��ٵ���̹���������浽�ļ���
	public static void keepRecord()
	{
		try {
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			//�ر��� ���ȹ�
			try {
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	//����������
	public static void reduceEnNum()
	{
		enNum--;
	}
	//�����ҵ�̹��
	public static void reduceMyLife()
	{
		myLife--;
	}
	public static void addEnNum()
	{
		allEnNum++;
	}
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}

	public static Vector<EnemyTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<EnemyTank> ets) {
		Recorder.ets = ets;
	}
	
}

//ը����
class Bomb
{
	//����ը��������
	int x,y;
	//ը��������
	int life=9;
	boolean isLive=true;
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//��������ֵ
	public void lifeDown()
	{
		if(life>0)
		{
			life--;
		}else{
			isLive=false;
		}
	}
	
}

//�ӵ���
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	static int speed=2;
	//�ӵ��Ƿ񻹻���
	boolean isLive=true;
	public Shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			switch(direct)
			{
			case 0:
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			}			
			//�ӵ���ʱ������
			//�ж��ӵ��Ƿ�������Ե
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
		
	}
}

//̹����
class Tank
{
	//��ʾ̹�˵ĺ�����
	int x=0;
	//��ʾ̹�˵�������
	int y=0;
	//̹�˷���0��ʾ�ϣ�1��ʾ�ң�2��ʾ�£�3��ʾ��
	int direct=0;
	int color;
	
	boolean isLive=true;
	
	public Tank(int x,int y)
	{
		this.x =x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
}

//���˵�̹��,�ѵ���̹�������߳���
class EnemyTank extends Tank implements Runnable
{
	 static int speed=1;
	int times=0;
	
	//����һ�������Է��ʵ�MyPanel�����е���̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	
	//����һ����ŵ����ӵ�������
	Vector<Shot> ss=new Vector<Shot>();
	//��������ӵ���Ӧ���ڸոմ���̹�˺͵����ӵ�������
	
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	
	//�õ�MyPanel�ϵĵ���̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//�ж��Ƿ������˱�ĵ���̹��
	public boolean isTouchOther()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this&&et.isLive)
				{
					if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
					{
						return true;
					}
					if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
					{
						return true;
					}
				}
			}
			break;
		case 1:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this&&et.isLive)
				{
					if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
					{
						return true;
					}
					if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
					{
						return true;
					}						
				}
			}
			break;
		case 2:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this&&et.isLive)
				{
					if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
					{
						return true;
					}
					if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
					{
						return true;
					}											
				}
			}
			break;
		case 3:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this&&et.isLive)
				{
					if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
					{
						return true;
					}
					if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
					{
						return true;
					}						
				}
			}
			break;
		}
		
		return b;
	}

	public void run() {
		// TODO Auto-generated method stub
		
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			
			switch(this.direct)
			{
			case 0:
				for(int i=0;i<30;i++)
				{
					//��֤̹�˲����߽�
					if(y>15&&!this.isTouchOther())
					{
						y-=speed;
					}					
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}				
				break;
			case 1:
				for(int i=0;i<30;i++)
				{
					if(x<400-15&&!this.isTouchOther())
					{
						x+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}				
				break;
			case 2:
				for(int i=0;i<30;i++)
				{
					if(y<300-15&&!this.isTouchOther())
					{
						y+=speed;
					}					
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				break;
			case 3:
				for(int i=0;i<30;i++)
				{
					if(x>15&&!this.isTouchOther())
					{
						x-=speed;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
				}
				break;
			}
			
			this.times++;
			if(times%2==0)
			{
				//�ж��Ƿ���Ҫ��̹�˼����µ��ӵ�
					if(isLive)
					{
						if(ss.size()<5)
						{
							Shot s=null;
							//û���ӵ������
							switch(direct)
							{
							case 0:
								//�����ӵ�������������
								s=new Shot(x,y-15,0);
								ss.add(s);
								break;
							case 1:
								s=new Shot(x+15,y,1);
								ss.add(s);
								break;
							case 2:
								s=new Shot(x,y+15,2);
								ss.add(s);
								break;
							case 3:
								s=new Shot(x-15,y,3);
								ss.add(s);
								break;
							}
							//�����ӵ��߳�
							Thread t=new Thread(s);
							t.start();
						}
					}
				
			}
			
			//��̹���������һ���µķ���
			this.direct=(int)(Math.random()*4);
			
			//�жϵ���̹���Ƿ�����
			if(this.isLive==false)
			{
				//��̹���������˳��߳�
				break;
			}
		}
		
	}

	public static int getSpeed() {
		return speed;
	}

	public static void setSpeed(int speed) {
		EnemyTank.speed = speed;
	}
}


//�ҵ�̹��
class Hero extends Tank
{
	int speed=2;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	
	//����
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//�����ӵ�������������
			s=new Shot(x,y-15,0);
			ss.add(s);
			break;
		case 1:
			s=new Shot(x+15,y,1);
			ss.add(s);
			break;
		case 2:
			s=new Shot(x,y+15,2);
			ss.add(s);
			break;
		case 3:
			s=new Shot(x-15,y,3);
			ss.add(s);
			break;
		}
		//�����ӵ��߳�
		Thread t=new Thread(s);
		t.start();
	}
	
	//̹�������ƶ�
	public void moveUp()
	{
		this.y-=this.speed;
	}
	//̹�������ƶ�
	public void moveRight()
	{
		x+=speed;
	}
	//̹�������ƶ�
	public void moveDown()
	{
		y+=speed;
	}
	//̹�������ƶ�
	public void moveLeft()
	{
		x-=speed;
	}
}