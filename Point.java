

import java.awt.Color;
/**
 * 棋子类
 */
public class Point {
  private int x;//棋盘中的x索引 X index in chessboard
  private int y;//棋盘中的y索引 Y index in the chessboard
  private Color color;//颜色 color
  public static final int DIAMETER=30;//直径 diameter
  
  public Point(int x,int y,Color color){
	  this.x=x;
	  this.y=y;
	  this.color=color;
  } 
  
  public int getX(){//拿到棋盘中x的索引 get x index
	  return x;
  }
  public int getY(){//get y index
	  return y;
  }
  public Color getColor(){//获得棋子的颜色 Get color of chess pieces
	  return color;
  }
}