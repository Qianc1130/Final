

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
 
import javax.swing.*;
/*Cheng Qian
  1410
  11.5.2019
  Some comments are codes I didn't delete, because I think that both codes can be used, so I didn't delete them, just added '//'
 */
 
public class ChessBoard extends JPanel implements MouseListener {
   public static final int MARGIN=30;//边距  Margin
   public static final int GRID_SPAN=35;//网格间距 Grid spacing
   public static final int ROWS=15;//棋盘行数 rows
   public static final int COLS=15;//棋盘列数 columns
   
   Point[] chessList=new Point[(ROWS+1)*(COLS+1)];
   boolean isBlack=true;//默认开始是黑棋先 start from black
   boolean gameOver=false;//游戏是否结束  to decide is game over
   int chessCount;//当前棋盘棋子的个数  Number of chess pieces
   int xIndex,yIndex;//当前刚下棋子的索引 Index of chess pieces
   
  
   Color colortemp;
   public ChessBoard(){
	  

	   addMouseListener(this);
	   addMouseMotionListener(new MouseMotionListener(){
		   public void mouseDragged(MouseEvent e){
			   
		   }
		   
		   public void mouseMoved(MouseEvent e){
		     int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		   
		     int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		     //游戏已经结束不能下 The game has ended and cannot be played
		     //落在棋盘外不能下 Can't play outside the chessboard
		     //x，y位置已经有棋子存在，不能下 There are already chess pieces in position x and y.
		     if(x1<0||x1>ROWS||y1<0||y1>COLS||gameOver||findChess(x1,y1))
		    	 setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		     //设置成默认状态 make in default
		     else setCursor(new Cursor(Cursor.HAND_CURSOR));
		     
		   }
	   });
   } 
   
  
 
//绘制
   public void paintComponent(Graphics g){
	 
	   super.paintComponent(g);//画棋盘 draw the board
	 
	   
	   int FWidth=getWidth();
	   int FHeight=getHeight();//获得窗口的宽度与高度 Get window width and height
	   int x=(FWidth)/2;
	   int y=(FHeight)/2;
	   g.drawImage(null, x, y, null);
	
	   
	   for(int i=0;i<=ROWS;i++){//画横线 draw rows
		   g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);
	   }
	   for(int i=0;i<=COLS;i++){//画竖线 draw columns
		   g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);
		   
	   }
	   
	   //画棋子 draw chess
	   for(int i=0;i<chessCount;i++){
		   //网格交叉点x，y坐标 Grid intersection x, y coordinates
		   int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
		   int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
		   g.setColor(chessList[i].getColor());//设置颜色set color
		  g.fillOval(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
				           Point.DIAMETER, Point.DIAMETER);
		  g.drawImage(null, xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER, null);
		   colortemp=chessList[i].getColor();
		   if(colortemp==Color.black){
			   RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 20, new float[]{0f, 1f}
               , new Color[]{Color.WHITE, Color.BLACK});
               ((Graphics2D) g).setPaint(paint);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
 
		   }
		   else if(colortemp==Color.white){
			   RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 70, new float[]{0f, 1f}
               , new Color[]{Color.WHITE, Color.BLACK});
               ((Graphics2D) g).setPaint(paint);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
 
		   }
		 
		   Ellipse2D e = new Ellipse2D.Float(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, 34, 35);
		   ((Graphics2D) g).fill(e);
	       //标记最后一个棋子的红矩形框 Mark the last pawn the Red rectangle frame
		   
		   if(i==chessCount-1){//如果是最后一个棋子 Is the last pawn
			   g.setColor(Color.red);
			   g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
				           34, 35);
		   }
	   }
   }
   
   public void mousePressed(MouseEvent e){//鼠标在组件上按下时调用 when mouse pressed on the board
	   
	   //游戏结束时，不再能下 When the game ends, you can no longer play
	   if(gameOver) return;
	   
	   String colorName=isBlack?"Black":"White";
	   
	   //将鼠标点击的坐标位置转换成网格索引 Convert mouse click coordinates to grid index
	   xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   
	   //落在棋盘外不能下 can.t click out of the board
	   if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)
		   return;
	   
	   //如果x，y位置已经有棋子存在，不能下 If there are already have a chess in the x and y positions, cannot be clicked
	   if(findChess(xIndex,yIndex))return; 
	   
	   
	   Point ch=new Point(xIndex,yIndex,isBlack?Color.black:Color.white);
	   chessList[chessCount++]=ch;
	    repaint();
	  
	   
	   //如果胜出则给出提示信息，不能继续下棋 If it wins, it will inform you, you can't continue playing
	   
	   if(isWin()){
		   //String msg=String.format("%s win！", colorName);
		   String msg=colorName+"win!";
		   JOptionPane.showMessageDialog(this, msg);
		   gameOver=true;
	   }
	   isBlack=!isBlack;
	 }
   //覆盖mouseListener的方法 Methods to override mouseListener
   public void mouseClicked(MouseEvent e){
	   //Invoked when a mouse button is clicked on a component
   }
   
   public void mouseEntered(MouseEvent e){
	    
   }
   public void mouseExited(MouseEvent e){
	
   }  
   public void mouseReleased(MouseEvent e){
	   
   }
   //在棋子数组中查找是否有索引为x，y的棋子存在 Find if there are any pawns with index x and y in the pawn array
   private boolean findChess(int x,int y){
	   for(Point c:chessList){
		   if(c!=null&&c.getX()==x&&c.getY()==y)
			   return true;
	   }
	   return false;
   }
   
   
   private boolean isWin(){
	   int continueCount=1;//连续棋子的个数 Number of consecutive pieces
	  
	   //横向向西寻找 Looking westward
	   for(int x=xIndex-1;x>=0;x--){
		   Color c=isBlack?Color.black:Color.white;
		   if(getChess(x,yIndex,c)!=null){
			   continueCount++;
		   }else
			   break;
	   }
      //横向向东寻找 Looking eastward
       for(int x=xIndex+1;x<=COLS;x++){
	      Color c=isBlack?Color.black:Color.white;
	      if(getChess(x,yIndex,c)!=null){
		     continueCount++;
	      }else
		     break;
       }
       if(continueCount>=5){
	         return true;
       }else 
	   continueCount=1;
       
       
       //向上搜索 Looking northward
       for(int y=yIndex-1;y>=0;y--){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(xIndex,y,c)!=null){
    		   continueCount++;
    	   }else
    		   break;
       }
       //纵向向下寻找 looking southward
       for(int y=yIndex+1;y<=ROWS;y++){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(xIndex,y,c)!=null)
    	       continueCount++;
           else
    	      break;
       
       }
       if(continueCount>=5)
    	   return true;
       else
    	   continueCount=1;
       
       
       
       //东北寻找 looking Northeast 
       for(int x=xIndex+1,y=yIndex-1;y>=0&&x<=COLS;x++,y--){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null){
    		   continueCount++;
    	   }
    	   else break;
       }
       //西南寻找 Looking southwest
       for(int x=xIndex-1,y=yIndex+1;x>=0&&y<=ROWS;x--,y++){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null){
    		   continueCount++;
    	   }
    	   else break;
       }
       if(continueCount>=5)
    	   return true;
       else continueCount=1;
       
       
       //西北寻找 Northwest looking
       for(int x=xIndex-1,y=yIndex-1;x>=0&&y>=0;x--,y--){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null)
    		   continueCount++;
    	   else break;
       } 
       //东南寻找 Looking southeast
       for(int x=xIndex+1,y=yIndex+1;x<=COLS&&y<=ROWS;x++,y++){
    	   Color c=isBlack?Color.black:Color.white;
    	   if(getChess(x,y,c)!=null)
    		   continueCount++;
    	   else break;
       }
       if(continueCount>=5)
    	   return true;
       else continueCount=1;
       
       return false;
     }
   
   
   private Point getChess(int xIndex,int yIndex,Color color){
	   for(Point p:chessList){
		   if(p!=null&&p.getX()==xIndex&&p.getY()==yIndex
				   &&p.getColor()==color)
			   return p;
	   }
	   return null;
   }
   
   
   public void restartGame(){
	   //清除棋子 restartGame
	   for(int i=0;i<chessList.length;i++){
		   chessList[i]=null;
	   }
	   
	   isBlack=true;
	   gameOver=false; //游戏是否结束 Whether the game is over
	   chessCount =0; //当前棋盘棋子个数 Number of current chess pieces
	   repaint();
   }
   
   //悔棋 Regret method
   public void goback(){
	   if(chessCount==0)
		   return ;
	   chessList[chessCount-1]=null;
	   chessCount--;
	   if(chessCount>0){
		   xIndex=chessList[chessCount-1].getX();
		   yIndex=chessList[chessCount-1].getY();
	   }
	   isBlack=!isBlack;
	   repaint();
   }
   
   //矩形Dimension
 
   public Dimension getPreferredSize(){
	   return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2
	                        +GRID_SPAN*ROWS);
   }
   
   
   
}