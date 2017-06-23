/**
 * 功能：坦克游戏6.0(代码优化)
 * 1.画出坦克.
 * 2.我的坦克可以上下左右移动
 * 3.可以发射子弹，子弹可以连发(最多7颗)
 * 4.当我的子弹击中敌人坦克时，敌人坦克消失（爆炸效果）
 * 5.我被击中时，显示爆炸效果
 * 6.防止敌人坦克重叠运动
 *   6.1 决定把判断是否重叠运动的方法写到EnemyTank类
 * 7.可以分关
 *   7.1 做一个开始的Panel，它是一个空
 *   7.2 闪烁效果
 * 8.可以记录玩家的成绩
 *   8.1 用文件流
 *   8.2 单写一个记录类，完成对玩家的记录
 *   8.3 先完成保存共击毁了多少辆敌人坦克的功能
 *   8.4 存盘退出游戏，可以记录当时敌人的坦克坐标，并可以恢复
 * 9.再次优化
 *   9.1 EnemyTank之isTouchOther方法
 *   9.2 hero边界问题   
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
	//定义一个开始面板
	MyStarPanel msp=null;
	//作出我需要的菜单
	JMenuBar jmb=null;
	//开始游戏
	JMenu jm1=null;

	JMenuItem jmi1=null;
	//退出系统
	JMenuItem jmi2=null;
	//存盘退出
	JMenuItem jmi3=null;
	//续上局
	JMenuItem jmi4=null;
	//下一关
	JMenuItem jmi5=null;
	
	int bz1=0,bz2=0;
	int getAllHit=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame10 mtg=new MyTankGame10();
		Thread t=new Thread(mtg);
		t.start();

	}
	
	//构造方法
	public MyTankGame10()
	{		
		//创建菜单及菜单远项
		jmb=new JMenuBar();
		jm1=new JMenu("游戏(G)");
		
		//设置快捷方式
		jm1.setMnemonic('G');
		

		
		jmi1=new JMenuItem("新游戏");
		jmi3=new JMenuItem("存盘退出");
		jmi5=new JMenuItem("下一关");
		jmi2=new JMenuItem("退出游戏");
		jmi4=new JMenuItem("继续上一局");
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
	//创建过关面板 
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
	//创建死亡面板
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
		//对用户不同的点击做相应的处理
		if(arg0.getActionCommand().equals("new"))
		{
			//创建战场面板
			mp=new MyPanel("newGame");
			
			MyStarPanel.guan=1;
			Recorder.setMyLife(3);
			Recorder.setEnNum(20);
			this.bz1=0;
			this.bz2=0;
			MyStarPanel.bz=0;
			
			Shot.speed=2;
			EnemyTank.speed=1;
			
			//启动mp线程
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示  刷新JFrame
			this.setVisible(true);
		}else if(arg0.getActionCommand().equals("continue")){
			
			Recorder.getRecord();
			
			//创建战场面板
			mp=new MyPanel("newGame");
			
			MyStarPanel.guan=1;
			Recorder.setMyLife(3);
			Recorder.setEnNum(20);
			this.bz1=0;
			this.bz2=0;
			MyStarPanel.bz=0;
			
			Shot.speed=2;
			EnemyTank.speed=1;
			
			//启动mp线程
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示  刷新JFrame
			this.setVisible(true);
			
		}
		else if(arg0.getActionCommand().equals("exit"))
		{
			//用户点击退出系统菜单
			//保存击毁敌人数量
			File file = new File("d:\\myRecording.txt");
			if (file.exists()){
			 file.delete();
			} 
			
			System.exit(0);
		}else if(arg0.getActionCommand().equals("saveExit"))
		{
			//保存敌人坦克坐标和击毁敌人的数量
			Recorder.setEts(mp.ets);
			Recorder.keepRecAndEnemy();
			
			System.exit(0);
		}else if(arg0.getActionCommand().equals("next"))
		{
			//创建战场面板
			mp=new MyPanel("newGame");
			
			//Recorder.setMyLife(3);
			Recorder.setAllEnNum(this.getAllHit);
			Recorder.setEnNum(20);//20+10*(MyStarPanel.guan-1)
			this.bz1=0;
			this.bz2=0;
			MyStarPanel.bz=0;
			
			Shot.speed++;
			EnemyTank.speed++;
								
			//启动mp线程
			Thread t=new Thread(mp);
			t.start();
			//先删除旧的开始面板
			this.remove(msp);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示  刷新JFrame
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
//就是一个提示作用
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
			//提示信息
			g.setColor(Color.yellow);
			Font myfont=new Font("华文行楷",Font.BOLD,30);
			g.setFont(myfont);
			if(bz==0)
			{
				g.drawString("stage： "+guan,150,150);
			}else{
				g.drawString("英雄败北",150,150);
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
			//重绘
			this.repaint();
		}
		
	}
	
}

//我的面板
class MyPanel extends JPanel implements KeyListener,Runnable
{
	//定义一个我的坦克
	Hero hero=null;
	
	//定义敌人的坦克组
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	Vector<Node> nodes=new Vector<Node>();
	
	//定义炸弹类集合
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	int ensize=4;
	
	//定义三张图片，三张图片才能组成一颗炸弹（爆炸效果）
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//构造方法
	public MyPanel(String flag)
	{
		//恢复记录
		//Recorder.getRecord();
		
		hero=new Hero(200,250);
		//初始化敌人的坦克
		if(flag.equals("newGame"))
		{
			for(int i=0;i<ensize;i++)
			{
				//创建敌人坦克
				EnemyTank et=new EnemyTank((i+1)*50,30);
				et.setColor(0);
				et.setDirect(2);
				//将MyPanel的敌人坦克向量交给该敌人坦克
				et.setEts(ets);
				
				//启动敌人坦克
				Thread t=new Thread(et);
				t.start();
				//给敌人坦克添加一颗子弹
				Shot s=new Shot(et.x,et.y+15,2);
				//加入给敌人坦克
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//加入
				ets.add(et);
				
			}
		}else if(flag.equals("continue"))
		{
			this.nodes=Recorder.getNode();
			for(int i=0;i<nodes.size();i++)
			{
				Node node=nodes.get(i);
				//创建敌人坦克
				EnemyTank et=new EnemyTank(node.x,node.y);
				et.setColor(0);
				et.setDirect(node.direct);
				//将MyPanel的敌人坦克向量交给该敌人坦克
				et.setEts(ets);
				
				//启动敌人坦克
				Thread t=new Thread(et);
				t.start();
				//给敌人坦克添加一颗子弹
				Shot s=new Shot(et.x,et.y+15,2);
				//加入给敌人坦克
				et.ss.add(s);
				Thread t2=new Thread(s);
				t2.start();
				//加入
				ets.add(et);
			}
		}
			
		//初始化三张图片
		try {
			image1=ImageIO.read(new File("bomb1.gif"));
			image2=ImageIO.read(new File("bomb2.gif"));
			image3=ImageIO.read(new File("bomb3.gif"));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}
	
	//画出提示信息
	public void showInfo(Graphics g)
	{
		//画出提示信息坦克
		this.drawTank(80,340,g,0,0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+" ",100,345);
		this.drawTank(140,340,g,0,1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+" ",160,345);
		
		//画出玩家的总成绩
		g.setFont(new Font("宋体",Font.BOLD,20));
		g.drawString("你的总成绩",440,30);
		
		this.drawTank(450,70,g,0,0);
		
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+" ",490,75);
		
		g.drawString("当前第 "+MyStarPanel.guan+" 关",430,150);
	}
		
	//重写paint
	public void paint(Graphics g)
	{
		//调用父类方法完成初始化
		super.paint(g);
		g.fillRect(0,0,400,300);
		//画出提示信息
		this.showInfo(g);
		
		//画出自己的坦克
		if(hero.isLive)
		{
			this.drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
		}
				
		//从ss中取出每颗子弹，并绘制
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myshot=hero.ss.get(i);
			
			//画出一颗子弹
			if(myshot!=null&&myshot.isLive==true)
			{
				g.draw3DRect(myshot.x,myshot.y,1,1,false);
			}
			if(myshot.isLive==false)
			{
				//从ss中删除该子弹
				hero.ss.remove(myshot);
			}
		}
		
		//画出炸弹
		for(int i=0;i<bombs.size();i++)
		{
			//取出炸弹
			Bomb b=bombs.get(i);
			
			if(b.life>6)
			{
				g.drawImage(image1,b.x,b.y,30,30,this);
			}else if(b.life>3){
				g.drawImage(image2,b.x,b.y,30,30,this);
			}else if(b.life>0){
				g.drawImage(image3,b.x,b.y,30,30,this);
			}
			//让b的生命值减小
			b.lifeDown();
			//如果炸弹的生命值为0，就从bombs向量中删去
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		
		//画出敌人的坦克
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive==true)
			{
				this.drawTank(et.getX(),et.getY(),g,et.getDirect(),0);

			}
			//再画出敌人的子弹
			for(int j=0;j<et.ss.size();j++)
			{
				Shot enemyshot=et.ss.get(j);
				if(enemyshot.isLive)
				{
					g.setColor(Color.cyan);
					g.draw3DRect(enemyshot.x,enemyshot.y,1,1,false);
				}else{
					//如果敌人子弹死亡就从Vector中删除
					et.ss.remove(enemyshot);
				}
			}
			
		}
	}
	
	//敌人子弹是否击中我
	public void hitMe()
	{
		if(hero.isLive)
		{
			//取出敌人的每一坦克
			for(int i=0;i<ets.size();i++)
			{
				//取出坦克
				EnemyTank et=ets.get(i);
				
				//取出每一颗子弹
				for(int j=0;j<et.ss.size();j++)
				{
					//取出子弹
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
	
	//判断我的子弹是否击中敌人坦克
	public void hitEnemyTank()
	{
		//判断是否击中敌人坦克
		for(int i=0;i<hero.ss.size();i++)
		{
			//取出子弹
			Shot myshot=hero.ss.get(i);
			//判断子弹是否有效
			if(myshot.isLive==true)
			{
				//取出每个敌人坦克，与之判断
				for(int j=0;j<ets.size();j++)
				{
					//取出坦克
					EnemyTank et=ets.get(j);
					
					if(et.isLive==true)
					{
						if (this.hitTank(myshot,et))
						{
							Recorder.reduceEnNum();
							Recorder.addEnNum();
							if(Recorder.getEnNum()>3)
							{
								//创建敌人坦克
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
								//加入给敌人坦克
								et1.ss.add(s1);
								Thread t2=new Thread(s1);
								t2.start();
								
								ets.add(et1);
								//将MyPanel的敌人坦克向量交给该敌人坦克
								et1.setEts(ets);
								
								
								//启动敌人坦克
								Thread t=new Thread(et1);
								t.start();
								//给敌人坦克添加一颗子弹

								
							}
						}
					}
				}
			}
		}
	}
	
	//写一个方法专门判断子弹是否击中敌人坦克
	public boolean hitTank(Shot s,Tank et)
	{
		boolean bl=false;
		//判断坦克方向
		switch(et.direct)
		{
		//如果坦克方向是向上或者向下
		case 0:
		case 2:
			if(s.x>et.x-10&&s.x<et.x+10&&s.y>et.y-15&&s.y<et.y+15)
			{
				//击中
				//子弹死亡
				s.isLive=false;
				//敌人坦克死亡
				et.isLive =false;
				//创建一颗炸弹，放入Vector
				Bomb b=new Bomb(et.x-10,et.y-15);
				bombs.add(b);
				bl=true;
			}
			break;
		case 1:
		case 3:
			if(s.x>et.x-15&&s.x<et.x+15&&s.y>et.y-10&&s.y<et.y+10)
			{
				//子弹死亡
				s.isLive=false;
				//敌人坦克死亡
				et.isLive =false;
				//创建一颗炸弹，放入Vector
				Bomb b=new Bomb(et.x-15,et.y-10);
				bombs.add(b);
				bl=true;
			}
			break;
		}
		return bl;
	}
	
	//画出坦克的函数
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		//判断什么类型的坦克
		switch(type)
		{case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		
		//判断方向
		switch(direct)
		{
		//向上
		case 0:
			//画出我的坦克（到时再封装成一个方法）
			//1.画出左边的矩形
			g.fill3DRect(x-10,y-15,5,30,false);
			//2.画出右边的矩形
			g.fill3DRect(x+5,y-15,5,30,false);
			//3.画出中间矩形
			g.fill3DRect(x-5,y-10,10,20,false);
			//4.画出圆形
			g.fillOval(x-5,y-5,10,10);
			//5.画出线
			g.drawLine(x,y,x,y-15);
			break;
			//向右
		case 1:
			//1.画出上边的矩形
			g.fill3DRect(x-15,y-10,30,5,false);
			//2.画出下边的矩形
			g.fill3DRect(x-15,y+5,30,5,false);
			//3.画出中间矩形
			g.fill3DRect(x-10,y-5,20,10,false);
			//4.画出圆形
			g.fillOval(x-5,y-5,10,10);
			//5.画出线
			g.drawLine(x,y,x+15,y);
			break;
			//向下
		case 2:
			//1.画出左边的矩形
			g.fill3DRect(x-10,y-15,5,30,false);
			//2.画出右边的矩形
			g.fill3DRect(x+5,y-15,5,30,false);
			//3.画出中间矩形
			g.fill3DRect(x-5,y-10,10,20,false);
			//4.画出圆形
			g.fillOval(x-5,y-5,10,10);
			//5.画出线
			g.drawLine(x,y,x,y+15);
			break;
			//向左
		case 3:
			//1.画出上边的矩形
			g.fill3DRect(x-15,y-10,30,5,false);
			//2.画出下边的矩形
			g.fill3DRect(x-15,y+5,30,5,false);
			//3.画出中间矩形
			g.fill3DRect(x-10,y-5,20,10,false);
			//4.画出圆形
			g.fillOval(x-5,y-5,10,10);
			//5.画出线
			g.drawLine(x,y,x-15,y);
			break;
		}
	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	//键按下处理，a表示向左，s表示向下，d表示向右，w表示向上
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==KeyEvent.VK_W)
		{
			//设置我的坦克的方向
			this.hero.setDirect(0);
			if(this.hero.y>15)
			{
				this.hero.moveUp();
			}			
		}else if(arg0.getKeyCode()==KeyEvent.VK_D)
		{
			//向右
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
			//判断玩家是否按下j键
			if(hero.ss.size()<7)
			{
				this.hero.shotEnemy();
			}
			
		}
		
		//必须重绘窗口
		this.repaint();
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		// TODO Auto-generated method stub
		//每隔100毫秒重绘子弹
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
			}
			
			this.hitEnemyTank();
			//敌人的坦克是否击中我
			this.hitMe();
						
			//重绘
			this.repaint();
		}
		
	}
}

//恢复坐标
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

//记录类
class Recorder
{
	//记录每关有多少敌人
	private static int enNum=20;
	//设置我有多少可用坦克
	private static int myLife=3;
	//记录总共消灭多少敌人坦克
	private static int allEnNum=0;
	//从文件中恢复记录点
 	static Vector<Node> nodes=new Vector<Node>();
		
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	//完成读取任务
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
	
	//保存击毁敌人数量和敌人的坐标，方向
	public static void keepRecAndEnemy()
	{
		try {
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(allEnNum+"\r\n");
			
			//保存当前活的敌人的坐标和方向
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
			//关闭流 后开先关
			try {
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	
	//从文件中读取记录
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
	
	//把玩家击毁敌人坦克数量保存到文件中
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
			//关闭流 后开先关
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
	//减掉敌人数
	public static void reduceEnNum()
	{
		enNum--;
	}
	//减掉我的坦克
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

//炸弹类
class Bomb
{
	//定义炸弹的坐标
	int x,y;
	//炸弹的生命
	int life=9;
	boolean isLive=true;
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//减少生命值
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

//子弹类
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	static int speed=2;
	//子弹是否还活着
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
			//子弹何时死亡？
			//判断子弹是否碰到边缘
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
		
	}
}

//坦克类
class Tank
{
	//表示坦克的横坐标
	int x=0;
	//表示坦克的纵坐标
	int y=0;
	//坦克方向：0表示上，1表示右，2表示下，3表示左
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

//敌人的坦克,把敌人坦克做成线程类
class EnemyTank extends Tank implements Runnable
{
	 static int speed=1;
	int times=0;
	
	//定义一向量可以访问到MyPanel上所有敌人坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	
	//定义一个存放敌人子弹的向量
	Vector<Shot> ss=new Vector<Shot>();
	//敌人添加子弹，应当在刚刚创建坦克和敌人子弹死亡后
	
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
	
	//得到MyPanel上的敌人坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//判断是否碰到了别的敌人坦克
	public boolean isTouchOther()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//如果不是自己
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
				//如果不是自己
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
				//如果不是自己
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
				//如果不是自己
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
					//保证坦克不出边界
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
				//判断是否需要给坦克加入新的子弹
					if(isLive)
					{
						if(ss.size()<5)
						{
							Shot s=null;
							//没有子弹，添加
							switch(direct)
							{
							case 0:
								//创建子弹，并加入向量
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
							//启动子弹线程
							Thread t=new Thread(s);
							t.start();
						}
					}
				
			}
			
			//让坦克随机产生一个新的方向
			this.direct=(int)(Math.random()*4);
			
			//判断敌人坦克是否死亡
			if(this.isLive==false)
			{
				//让坦克死亡后退出线程
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


//我的坦克
class Hero extends Tank
{
	int speed=2;
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	
	//开火
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//创建子弹，并加入向量
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
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
	}
	
	//坦克向上移动
	public void moveUp()
	{
		this.y-=this.speed;
	}
	//坦克向右移动
	public void moveRight()
	{
		x+=speed;
	}
	//坦克向下移动
	public void moveDown()
	{
		y+=speed;
	}
	//坦克向左移动
	public void moveLeft()
	{
		x-=speed;
	}
}