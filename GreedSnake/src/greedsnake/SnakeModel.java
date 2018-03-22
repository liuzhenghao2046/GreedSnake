package greedsnake;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

public class SnakeModel implements Runnable {
	GreedSnake gs;
	
	boolean[][]matrix;
	LinkedList nodeArray = new LinkedList();
	Node food;
	
	int maxX;
	int maxY;
	int direction = 2;
	boolean running = false;
	int timeInterval = 200;
	double speedChangeRate = 0.75;
	boolean paused = false;
	int score =0;
	int countMove = 0;
	
	//UP��Down��ż���� RIGHT �� LEFT������
	public static final int UP =2;
	public static final int Down = 4;
	public static final int LEFT = 1;
	public static final int RIGHT = 3;

//---------------------------------------
//GreedModel()�������ʼ��
//---------------------------------------
public SnakeModel(GreedSnake gs, int maxX, int maxY){
	this.gs = gs;
	this.maxX = maxX;
	this.maxY = maxY;
	matrix = new boolean[maxX][];
	for(int i = 0; i<maxX; ++i){
		matrix[i] = new boolean[maxY];
		Arrays.fill(matrix[i], false);//û���ߺ�ʳ��ĵ�����false
	}
	//��ʼ����
	int initArrayLength = maxX > 20 ? 10 : maxX/2;
	for(int i = 0; i <initArrayLength; i++){
		int x = maxX/2 +i;
		int y = maxY/2;
		nodeArray.addLast(new Node(x,y));
		matrix[x][y] = true;//�ߵ�λ��
	}
	food = createFood();
	matrix[food.x][food.y] = true;//���ӵ�λ��
	}



//-------------------------------------------
//changeDirection():�ı��˶�����
//-------------------------------------------
public void changeDirection(int newDirection){
if(direction %2 != newDirection %2){	//�����ͻ
	direction = newDirection;
	}
}

//--------------------------------------------
//moveOn():̰�����˶�����
//--------------------------------------------
public boolean moveOn(){
	Node n = (Node) nodeArray.getFirst();
	int x = n.x;
	int y = n.y;
	switch(direction){
		case UP:
		y--;
		break;
		
		case Down:
			y++;
			break;
			
		case LEFT:
			x--;
			break;
			
		case RIGHT:
			x++;
			break;
	}
	if((0 <= x && x < maxX) && (0 <= y && y < maxY) ){
		if(matrix[x][y])//�Ե����ӻ���ײ������
		{
			if(x== food.x && y == food.y)//�Ե�ʳ��
			{
			nodeArray.addFirst(food);//��ͷ������һ�����
			//�Ʒֹ������ƶ����Ⱥ��ٶ��й�
			int scoreGet = (10000 - 200 * countMove) / timeInterval;
			score += scoreGet > 0 ? scoreGet : 10;
			countMove = 0;
			food = createFood();
			matrix[food.x][food.y] = true;
			return true;
			} else
				return false; //ײ������
			} else   //ʲô��û����
				
			{
				
			
			nodeArray.addFirst(new Node(x,y));//����ͷ��
			matrix[x][y] = true;
			n= (Node) nodeArray.removeLast();//ȥ��β��
			matrix[n.x][n.y] = false;
			countMove++;
			return true;
			}
		}
			return false;//Խ��
			}

//---------------------------------
//run():̰�����˶��߳�
//---------------------------------
public void run(){
	running = true;
	while(running){
		try{
			Thread.sleep(timeInterval);
		}catch (Exception e)
		{
			break;
		}
		if(!paused){
			if(moveOn()){
				gs.repaint();
			}else
			{
				JOptionPane.showMessageDialog(null, "GAME OVER", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				break;
		}
		}
		}
	running = false;
	}

//--------------------------------------------
//createFood():����ʳ�Ｐ���õص�
//--------------------------------------------
private Node createFood(){
	int x = 0;
	int y = 0;
	do{
		Random r = new Random();
		x = r.nextInt(maxX);
		y = r.nextInt(maxY);
	}
	while(matrix[x][y]);
	return new Node(x,y);
}

//---------------------------------------------
//speedUP():�ӿ��ߵ��ٶ�
//---------------------------------------------
public void speedUp(){
	timeInterval *= speedChangeRate;
}

//---------------------------------------------
//speedDown():�����ߵ��ٶ�
//---------------------------------------------
public void speedDown(){
	timeInterval /= speedChangeRate;
}

//------------------------------------------------
//changePauseState():�ı���Ϸ״̬����ͣ���߼�����Ϸ��
//------------------------------------------------
public void changePauseState(){
	paused = !paused;
}
}
//end








































































