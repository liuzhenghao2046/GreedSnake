package greedsnake;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GreedSnake implements KeyListener {
	JFrame mainFrame;
	Canvas paintCanvas;
	JLabel labelScore;	//记分牌

	SnakeModel snakeModel = null;//蛇
	

	public static final int canavasWidth = 500;
	public static final int canavasHeight = 500;
	public static final int nodeWidth = 30;
	public static final int nodeHeidth = 30;

	// --------------------------------------------
	// greedsnake(): the beginning desktop
	// --------------------------------------------

	public GreedSnake() {
		// 设置界面
		mainFrame = new JFrame("GreedSanke");
		Container a = mainFrame.getContentPane();
		labelScore = new JLabel("Score: ");
		a.add(labelScore, BorderLayout.NORTH);

		paintCanvas = new Canvas();
		paintCanvas.setSize(canavasWidth + 1, canavasHeight +1);
		paintCanvas.addKeyListener(this);
		a.add(paintCanvas, BorderLayout.CENTER);

		JPanel panelButtom = new JPanel();
		panelButtom.setLayout(new BorderLayout());

		JLabel labelHelp; // help message
		labelHelp = new JLabel("PageUp, PageDown for speed;", JLabel.CENTER);
		panelButtom.add(labelHelp, BorderLayout.NORTH);
		labelHelp = new JLabel("Enter or R or S for start the game;", JLabel.CENTER);
		panelButtom.add(labelHelp, BorderLayout.CENTER);
		labelHelp = new JLabel("Space or P for Pause the game;", JLabel.CENTER);
		panelButtom.add(labelHelp, BorderLayout.SOUTH);
		a.add(panelButtom, BorderLayout.SOUTH);

		mainFrame.addKeyListener(this);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	// -----------------------------------
	// keyPressed():按键
	// -----------------------------------
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (snakeModel.running)
			switch (keyCode) {
			case KeyEvent.VK_UP:
				snakeModel.changeDirection(SnakeModel.UP);
				break;

			case KeyEvent.VK_DOWN:
				snakeModel.changeDirection(SnakeModel.Down);
				break;

			case KeyEvent.VK_LEFT:
				snakeModel.changeDirection(SnakeModel.LEFT);
				break;

			case KeyEvent.VK_RIGHT:
				snakeModel.changeDirection(SnakeModel.RIGHT);
				break;

			case KeyEvent.VK_ADD:
			case KeyEvent.VK_PAGE_UP:
				snakeModel.speedUp();
				break;

			case KeyEvent.VK_SUBTRACT:
			case KeyEvent.VK_PAGE_DOWN:
				snakeModel.speedDown();
				break;

			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_P:
				snakeModel.changePauseState();
				break;
			default:
			}
		// restart
		if (keyCode == KeyEvent.VK_R || keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_ENTER) {
			snakeModel.running = false;
//		 begin();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
}
	
//------------------------------------------
//keyTyped():空函数
//------------------------------------------
public void keyTyped(KeyEvent e){
}

//------------------------------------------
//repaint():创造游戏界面
//------------------------------------------
void repaint(){
	Graphics g = paintCanvas.getGraphics();
	//background
	g.setColor(Color.white);
	g.fillRect(0, 0, canavasWidth, canavasHeight);
	//the snake
	g.setColor(Color.black);
	LinkedList na = snakeModel.nodeArray;
	Iterator it = na.iterator();
	while(it.hasNext()){
		Node n = (Node)it.next();
		drawNode(g,n);
	}
	//draw the food
	g.setColor(Color.red);
	Node n = snakeModel.food;
	drawNode(g,n);
	updateScore();
}

//--------------------------------------------
//drawNode():画某一个节点
//--------------------------------------------
private void drawNode(Graphics g, Node n){
	g.fillRect(n.x*nodeWidth, n.y*nodeHeidth, nodeWidth - 1, nodeHeidth - 1);
}

//--------------------------------------------
//updateScore():改变记分牌
//--------------------------------------------
public void updateScore(){
	String s = "Score: " +snakeModel.score;
	labelScore.setText(s);
}

//-------------------------------------------
//begin()
//-------------------------------------------
void begin(GreedSnake gs){
	if(snakeModel == null || !snakeModel.running){
		snakeModel = new SnakeModel(gs, this.canavasWidth/nodeWidth, this.canavasHeight/nodeHeidth);
		(new Thread(snakeModel)).start();
	}
}

//--------------------------------------------
//main():主程序
//--------------------------------------------
public static void main(String[] args){
	GreedSnake gs = new GreedSnake();
	gs.begin(gs);
}
}





















