package com.demo2.model;

/**
 * 电脑水平算法
 * 棋盘为19*19
 * 初级：随机下
 * 初级人机：根据棋子点的分数来判断威胁最大的坐标完成下棋，计分时不考虑是否两边有连子，中间有空格的情况
 * 中级人机：根据棋子点的分数来判断威胁最大的坐标完成下棋,在计分的时候会考虑两边有连子，中间有空格的情况
 * @author admin
 */

import com.demo2.view.ChooseLevel;

public class Computer {
    private int scores; //分数
    private Coord coord=new Coord();//Coord用来保存分数最大点的坐标
    private int everyPlayerPointScore[][]=new int[19][19]; //用来保存玩家已有的分数值
    private int everyComputerPointScore[][]=new int[19][19]; //用来保存电脑已有的分数值
    
    /**
	 * 生成棋子算法
	 * @param role 电脑执的棋
	 * @param chess 二维数组
	 * @param level 电脑水平
	 * @return 坐标对象
	 */
    public Coord computePos(int role, int chess[][], int level){
        int x, y, posX, posY;
        //选择初级
        if(level == ChooseLevel.PRIMARY) {
        	//随机找到的位置可能有棋，所以用循环
            while(true){
            	//随机生成坐标
                posX = (int) (Math.random() * 19);
                posY = (int) (Math.random() * 19);
                //如果随机找到的位置原来无棋，则生成棋子
                if (chess[posX][posY] == Chess.BLANK) {
                    coord.setX(posX);
                    coord.setY(posY);
                    return coord;
                }
            }
        }
        //选择初级人机
        else if(level== ChooseLevel.MEDIUM)
        {
        	//加载初级人机函数
            countMaxLines_primary(chess, Chess.WHITE);
            return coord;

        }
        //选择中级人机
        else if(level== ChooseLevel.SENIOR)
        {
        	//加载中级人机函数
            countMaxLines_medium(chess, Chess.WHITE);
            return coord;
        }
        return coord;
    }


    /**********************下面是中级人机*************************************/
    /******************初级人机类似，这里详细注释中级人机**************************/
    /**
     * 找出分数最大的坐标
     * @param chess 棋盘数组
     * @param role  白棋还是黑棋
     */
    public void countMaxLines_medium(int chess[][],int role)
    {
    	/*
    	 * 创建一个坐标对象playerCoord，通过它将最大点的坐标赋值给coord
    	 * 创建一个坐标对象computerCoord，通过它将最大点的坐标赋值给coord
    	 */
    	Coord playerCoord=new Coord();
        Coord computerCoord=new Coord();
        int x,y;
       
        for(x=0;x<19;x++)
        {
            for(y=0;y<19;y++)
            {
            	if(chess[x][y]== Chess.BLANK) {
            	/*
            	 * 判断一个坐标处有没有棋子
            	 * 创建一个计算分数的函数countEveryPos
            	 * 从而得到每一个坐标点（未落子）的分数        
            	 */    
            		countEveryPos_medium(x, y, chess, role);//countEveryPos判断连子，并且得到分数
                    everyPlayerPointScore[x][y] = scores;
                    countEveryPos_medium(x, y, chess, Chess.BLACK);
                    everyComputerPointScore[x][y] = scores;
                }
                else
                //若该点处已有棋子，则记分数为0
                {
                    everyPlayerPointScore[x][y]=0;
                    everyComputerPointScore[x][y]=0;
                }
            }
        }
       //如果玩家棋子点处的分数值大于或等于机器人分数，则将最大分数赋值给coord
        if(findBestPos_medium(everyPlayerPointScore, playerCoord)>=findBestPos(everyComputerPointScore,computerCoord))
        {
            coord=playerCoord;
        }
        else
        {
            coord=computerCoord;
        }
    }

    /**
     * 找到最大分数点的坐标
     * @param a 数组 存储每个点的分数
     * @param c 保存最大分数点的坐标
     * @return 最大分数
     */
    public int findBestPos_medium(int a[][], Coord c)
    {
        int i,j,max=0;

        for(i=0;i<19;i++)
        {
            for(j=0;j<19;j++)
            {
                if(a[i][j]>max)
                {
                    max=a[i][j];
                    c.setX(i);
                    c.setY(j);
                }
            }
        }
        return max;
    }

    /**
     * 估分函数
     * * 算分方法：（为防止棋子多之后的分数计算出现小危险情况分数大于大危险情况分数，在选取时选取更大的值作为大危险分数值）
     * 一颗棋子，分数最低为10分
     * 两颗连子，被阻挡其次加50分
     * 两颗连子，位于单方向，且无阻挡，黑棋（电脑）分数大于白棋（玩家）分数  黑加400分 白加200分
     * 两颗连子，在多个方向都成立，分数更高加60000分
     * 三颗连子，且无阻挡，黑棋（电脑）分数大于白棋（玩家）分数  黑加80000分 白加70000分
     * 三颗连子，一边被阻挡，若为黑子，则选择连成4颗加85000分，若是白子，则可先进行其他落子，可以先选择两连黑的成三连加300分
     * 四颗连子，且无阻挡,同理，黑棋（电脑）分数大于白棋（玩家）分数  黑加150000分 白加100000分
     * 四颗连子，一边被阻挡，不论黑子白子，都在另一边落子90000分
     * @param count 每一个方向上的相同颜色的总个数
     * @param i 不同颜色的个数
     * @param countTwo 代表有一个坐标向八个方向，有两个连一起的个数
     * @param role 棋子种类
     * 
     */
    public void mark_medium(int count,int i,int countTwo,int role)
    {
    	/*
    	 * 冲X：     只有一个点可以连成X+1的X个子，即有一边已经被堵
    	 * 活X：     已有X子连成串，并且两头均没有被堵        
    	 */
    	
    	if(count==1)
        {
            scores=scores+10;
        }
        //活二
        else if(count==2&&i==0&&role== Chess.WHITE&&countTwo<=1)//有两个白色棋子，并且没有不同颜色的棋子堵，八个方向上，两个颜色一样的连在一起的只有一个方向的
        {
            scores=scores+200;
            //System.out.println("白子活二"+scores);
        }
        else if(count==2&&i==0&&role== Chess.BLACK&&countTwo<=1)//有两个黑色棋子，并且没有不同颜色的棋子堵，八个方向，两个颜色一样的连在一起的只有一个方向的
        {
            scores=scores+400;
           // System.out.println("黑子活二"+scores);
        }
        //冲二
        else if(count==2&&i==1)//有两个颜色相同的棋子，但其中一头已经被堵
        {
            scores=scores+50;
           // System.out.println("冲二"+scores);
        }
        //双活二
        else if(count==2&&i==0&&countTwo>1)//有两个颜色相同的棋子，并且没有不同颜色的棋子堵，八个方向，两个颜色一样的连在一起的个数>1
        {
            scores=scores+60000;
            System.out.println("双活二"+scores);
        }
        //活三
        else if(count==3&&i==0&&role== Chess.WHITE)//有三个白色棋子，并且没有不同颜色的棋子堵
        {
            scores=scores+70000;
            //System.out.println("白子活三"+scores);
        }
        else if(count==3&&i==0&&role== Chess.BLACK)//有三个黑色棋子，并且没有不同颜色的棋子堵
        {
            scores=scores+80000;//黑子和白子同时活三的时候选择黑子成，所以分数高
            //System.out.println("黑子活三"+scores);
        }
        //冲三
        else if(count==3&&i==1&&role== Chess.WHITE)//有三个黑色棋子，但其中一头已经被堵
        {
            scores=scores+300;
            System.out.println("白子冲三"+scores);
        }
        else if(count==3&&i==1&&role== Chess.BLACK)//同理，首先选择黑子先连成4子
        {
            scores=scores+85000;
            System.out.println("黑子冲三"+scores);
        }
        //白子活四
        else if(count==4&&i==0&&role== Chess.WHITE)//有四个白色棋子，并且没有不同颜色的棋子堵
        {
            scores=scores+100000;
            //System.out.println("活四"+scores);
        }
        //黑子活四
        else if(count==4&&i==0&&role== Chess.BLACK)//有四个黑色棋子，并且没有不同颜色的棋子堵
        {
            scores=scores+150000;
        }
        //冲四
        else if(count==4&&i==1)//有四个任意棋子，但其中一头已经被堵，都在另外一边落子
        {
            scores=scores+90000;
            //System.out.println("冲四"+scores);
        }
        else if(count==5)
        {
            scores=scores+200000;
        }
    }
    
    /**
     * 初始分数
     * @param x 横坐标
     * @param y 纵坐标
     */
    public void basicScore(int x,int y)
    {
        if(x==0||y==0)
        {
            scores=scores+3;
        }
        else
        {
            scores=scores+5;
        }
    }
    /**
     * 计算每个坐标有几个连子
     * @param x 横坐标
     * @param y 纵坐标
     * @param chess 棋盘数组
     * @param role  棋子种类
     */
    public void countEveryPos_medium(int x,int y,int chess[][],int role)
    {
        scores=0;
        basicScore(x,y);
        /*
         * countTwo代表有一个坐标向八个方向，有两个连一起的个数
         * count代表颜色相同的棋子个数
         * up，down，···表示在某个方向上颜色不同的棋子个数
         * up+down，left+right，···表示在这一条线上不同颜色棋子的个数，即mark_medium函数中的i
         */
        int matchRole,startX=x,startY=y,
                count=0,up=0,down=0,left=0,right=0,
                  leftUp=0,leftDown=0,rightUp=0,rightDown=0,countTwo=0;
        //分别计算黑子和白子的连子情况
        if(role== Chess.BLACK)
        {
            matchRole= Chess.WHITE;
        }
        else
        {
            matchRole= Chess.BLACK;
        }
        //竖直方向上判断
        while (true)
        {
            //向上判断
            y--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;//坐标处都为同样颜色的棋子，count+1
            } else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||y<0){
                up++;//位于上方的不同颜色的棋子个数+1
                break;
            }
            else
            {
                break;
            }
        }
        y = startY;
        while (true) {
            //向下判断
            y++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }  else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||y>19){
                down++;//位于下方的不同颜色的棋子个数+1
                break;
            }
            else
            {
                break;
            }
        }
        //此处代表在上下方向有二连 或者 夹了一个空子两边各有一个棋子，并且两端没有其他子挡住
        if(count==2&&(up+down==0))
        {
            countTwo++;//八个方向上，有两个连在一起
            //System.out.println("出现二连"+countTwo);
        }
        mark_medium(count, up + down, countTwo,role);

        //水平方向判断
        x = startX;
        y = startY;
        count = 0;
        while (true) {
            //向左判断
            x--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||x==0){
                left++;
                break;
            }
            else
            {
                break;
            }

        }
        x = startX;
        while (true) {
            //向右判断
            x++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||x>19){
                right++;
                break;
            }
            else
            {
                break;
            }
        }
        //同理，此处代表在左右方向有二连 或者 夹了一个空子两边各有一个棋子 并且两端没有其他子挡住
        if(count==2&&(left+right==0))
        {
            countTwo++;
            //System.out.println("出现二连"+countTwo);
        }
        mark_medium(count, left + right, countTwo,role);

        //右倾斜方向判断
        x = startX;
        y = startY;
        count = 0;
        while (true) {
            //向左上判断
            y--;
            x--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||x<0||y<0){
                leftUp++;
                break;
            }
            else
            {
                break;
            }
        }
        x = startX;
        y = startY;
        while (true) {
            //向右下判断
            x++;
            y++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||x>19||y>19){
                rightDown++;
                break;
            }
            else
            {
                break;
            }
        }
        //同上
        if(count==2&&(leftUp+rightDown==0))
        {
            countTwo++;
           // System.out.println("出现二连"+countTwo);
        }
        mark_medium(count, leftUp + rightDown, countTwo,role);

        //左倾斜方向判断
        x = startX;
        y = startY;
        count = 0;
        while (true) {
            //向左下判断
            x--;
            y++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||x<0||y>19){
                leftDown++;
                break;
            }
            else
            {
                break;
            }
        }
        x = startX;
        y = startY;
        while (true) {
            //向右上判断
            x++;
            y--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }else if((x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole)||x>19||y<0){
                rightUp++;
                break;
            }
            else
            {
                break;
            }
        }
        //同上
        if(count==2&&(leftDown+rightUp==0))
        {
            countTwo++;
           // System.out.println("出现二连"+countTwo);
        }
        mark_medium(count, leftDown + rightUp, countTwo,role);
    }



    /*********************下面是初级人机*************************************/
    /**
     * 找出分数最大的坐标
     * @param chess 棋盘数组
     * @param role  白棋还是黑棋
     */
    public void countMaxLines_primary(int chess[][],int role)
    {
    	/*
    	 * 创建一个坐标对象playerCoord，通过它将最大点的坐标赋值给coord
    	 * 创建一个坐标对象computerCoord，通过它将最大点的坐标赋值给coord
    	 */
    	Coord playerCoord=new Coord();
        Coord computerCoord=new Coord();
        int x,y;
        for(x=0;x<19;x++)
        {
            for(y=0;y<19;y++)
            {
                if(chess[x][y]== Chess.BLANK) {
                    countEveryPos_primary(x, y, chess, role);
                    everyPlayerPointScore[x][y] = scores;
                    countEveryPos_primary(x, y, chess, Chess.BLACK);
                    everyComputerPointScore[x][y] = scores;
                }
                else
                {
                    everyPlayerPointScore[x][y]=0;
                    everyComputerPointScore[x][y]=0;
                }
            }
        }
        if(findBestPos(everyPlayerPointScore,playerCoord)>=findBestPos(everyComputerPointScore,computerCoord))
        {
            coord=playerCoord;
        }
        else
        {
            coord=computerCoord;
        }
    }

    /**
     * 找到最大分数点的坐标
     * @param a 数组 存储每个点的分数
     * @param c 保存最大分数点的坐标
     * @return
     */
    public int findBestPos(int a[][], Coord c)
    {
        int i,j,max=0;

        for(i=0;i<19;i++)
        {
            for(j=0;j<19;j++)
            {
                if(a[i][j]>max)
                {
                    max=a[i][j];
                    c.setX(i);
                    c.setY(j);
                }
            }
        }
        return max;
    }

    /**
     * 估分函数
     * @param count 每一个方向上的相同颜色的总个数
     * @param i 不同颜色的个数
     * 
     *  算分方法：（为防止棋子多之后的分数计算出现小危险情况分数大于大危险情况分数，在选取时选取更大的值作为大危险分数值）
     * 一颗棋子，分数最低10
     * 两颗连子，被阻挡其次加50分
     * 两颗连子，位于单方向，且无阻挡加400分
     * 三颗连子，且无阻挡加70000分
     * 三颗连子，一边被阻挡加300分
     * 四颗连子，且无阻挡加100000分
     * 四颗连子，一边被阻挡，不论黑子白子，都在另一边落子加90000分
     */
    public void mark_primary(int count,int i)
    {
        if(count==1)
        {
            scores=scores+10;
        }
        //活二
        else if(count==2&&i==0)
        {
            scores=scores+400;
        }
        //冲二
        else if(count==2&&i==1)
        {
            scores=scores+50;
        }
        //活三
        else if(count==3&&i==0)
        {
            scores=scores+70000;
            System.out.println("分数"+scores);
        }
        //冲三
        else if(count==3&&i==1)
        {
            scores=scores+300;
            System.out.println("分数"+scores);
        }
        //活四
        else if(count==4&&i==0)
        {
            scores=scores+100000;
            System.out.println("分数"+scores);
        }
        //冲四
        else if(count==4&&i==1)
        {
            scores=scores+90000;
            System.out.println("分数"+scores);
        }
        else if(count==5)
        {
            scores=scores+200000;
        }
    }

    /**
     * 计算每个坐标的分数
     * @param x
     * @param y
     * @param chess
     * @param role
     */
    public void countEveryPos_primary(int x,int y,int chess[][],int role)
    {
        scores=0;
        int matchRole,startX=x,startY=y,count=0,up=0,down=0,left=0,right=0,leftUp=0,leftDown=0,rightUp=0,rightDown=0;
        if(role== Chess.BLACK)
        {
            matchRole= Chess.WHITE;
        }
        else
        {
            matchRole= Chess.BLACK;
        }
        //竖直方向上判断
        while (true)
        {
            //向上判断
            y--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                up++;
                break;
            }
            else
            {
                break;
            }
        }
        y = startY;
        while (true) {
            //向下判断
            y = y + 1;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }  else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                down++;
                break;
            }
            else
            {
                break;
            }
        }
        mark_primary(count, up + down);

        //水平方向判断
        x = startX;
        y = startY;
        count = 0;
        while (true) {
            //向左判断
            x--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                down++;
                break;
            }
            else
            {
                break;
            }

        }
        x = startX;
        while (true) {
            //向右判断
            x++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                right++;
                break;
            }
            else
            {
                break;
            }
        }
        mark_primary(count, left + right);

        //右倾斜方向判断
        x = startX;
        y = startY;
        count = 0;
        while (true) {
            //向左上判断
            y--;
            x--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                leftUp++;
                break;
            }
            else
            {
                break;
            }
        }
        x = startX;
        y = startY;
        while (true) {
            //向右下判断
            x++;
            y++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                rightDown++;
                break;
            }
            else
            {
                break;
            }
        }
        mark_primary(count, leftUp + rightDown);

        //左倾斜方向判断
        x = startX;
        y = startY;
        count = 0;
        while (true) {
            //向左下判断
            x--;
            y++;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            } else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                leftDown++;
                break;
            }
            else
            {
                break;
            }
        }
        x = startX;
        y = startY;
        while (true) {
            //向右上判断
            x++;
            y--;
            if (x >= 0 && x < 19 && y >= 0 && y < 19 && chess[x][y] == role) {
                count++;
            }else if(x >= 0 && x < 19 && y >= 0 && y < 19 &&chess[x][y]==matchRole){
                rightUp++;
                break;
            }
            else
            {
                break;
            }
        }
        mark_primary(count, leftDown +rightUp);
    }

}






