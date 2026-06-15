import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.File;

public class GamePanel extends JPanel implements MouseListener, ActionListener
{
    GameState gamestate;
   	Player p1;
   	Player p2;

	//font
	Font pixelFont;

    Timer timer;
    int tickCounter = 0;
    //Set Count for deck and also the counter is what tracks the current number of abilites

    //Poison pick
    final int poisonCount = 4;
    int poisonCounter = 0;

    //Extra Heart pick
    final int eHeartCount = 1;
    int eHeartCounter = 0;

    //Extra Pick
    final int ePickCount = 2;
    int ePickCounter = 0;

    //Computer pick
    final int cpuPickCount = 3;
    int cpuPickCounter = 0;

    //Starting deck of cards - default is all null
    Card[][] P1CardList = new Card[4][4];
    Card[][] P2CardList = new Card[4][4];

    //Card Length --- change accordingly
    final int cardWidth = 117;
    final int cardHeight = 188;

    //card images and names
    String[] cardSpritesName = {"backSide_card.png","poison_card.png","safe_card.png","computerpick_card.png","extrapick_card.png","extraHeart_card.png"};
    Image[] cardSprites = new Image[6];

	//Starting images
	Image StartingScreen;
	Image CreditScreen;

	//Buttons
	Rectangle NextButton;
	Rectangle RestartButton;

    Toolkit tk;


    public GamePanel(JFrame frame)
    {
		p1 = new Player("player 1");
   		p2 = new Player("player 2");
        gamestate = new GameState(p1,p2);

        timer = new Timer(16,this);
        timer.start();
        addMouseListener(this);
        tk = Toolkit.getDefaultToolkit();

        //intialize starting screen
        StartingScreen = tk.getImage("START_SCREEN.png");
		CreditScreen = tk.getImage("CREDITS_SCREEN.png");

        //resizing card sprites
		for(int i = 0; i < cardSpritesName.length; i++)
	 	{
			cardSprites[i] = tk.getImage(cardSpritesName[i]).getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
    	}

        //intializing cards - player 1
        for(int row = 0; row < P1CardList.length; row++)
		{
			for(int col = 0; col < P1CardList[0].length; col++)
			{
				P1CardList[row][col] = new Card(50 + 136* row, 100 + 209 * col, cardWidth, cardHeight, cardSprites);
			}
 	    }
        //intializing cards - player 2
		for(int row = 0; row < P2CardList.length; row++)
		{
			for(int col = 0; col < P2CardList[0].length; col++)
			{
				P2CardList[row][col] = new Card(1345 + 136* row, 100 + 209 * col, cardWidth, cardHeight, cardSprites);
			}
 	    }

 	    //intializing font, import file package, and deriveFont needs float paramenter to insure it changes font size
 	    try
 	    {
		    pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P.ttf")).deriveFont(52f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(pixelFont);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}

		//intialize our own button
		NextButton = new Rectangle(830, 650, 250, 80);

    }
    public void mousePressed(MouseEvent e)
 	{
		if(gamestate.getCurrentPhase() == GameState.Phase.START)
		{
			gamestate.nextPhase();
		}

//iterates through each card and checks for contains (player 1 setup_Phase)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1SETUP_POISON)
 	    {
 	        for(int row = 0; row < P1CardList.length; row++)
 	        {
 	            for(int col = 0; col < P1CardList[0].length; col++)
 	            {
 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P1CardList[row][col].getCardType() == Card.cardType.Poison)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P1CardList[row][col].setCardImage(2); //set safe
 	                            poisonCounter--;
 	                        }
 	                        else if(P1CardList[row][col].getCardType() == Card.cardType.Safe && poisonCounter < poisonCount)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Poison);
 	                            P1CardList[row][col].setCardImage(1); //set poison
 	                           	poisonCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the poisonCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && poisonCounter == poisonCount)
			{
				gamestate.nextPhase();
			}
 	    }
 //(player 1 setup_ExtraHeart)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAHEART)
 	    {
 	        for(int row = 0; row < P1CardList.length; row++)
 	        {
 	            for(int col = 0; col < P1CardList[0].length; col++)
 	            {
 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P1CardList[row][col].getCardType() == Card.cardType.E_Heart)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P1CardList[row][col].setCardImage(2); //set safe
 	                            eHeartCounter--;
 	                        }
 	                        else if(P1CardList[row][col].getCardType() == Card.cardType.Safe && eHeartCounter < eHeartCount)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.E_Heart);
 	                            P1CardList[row][col].setCardImage(5); //set extra Hear
 	                           	eHeartCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the poisonCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && eHeartCounter == eHeartCount)
			{
				gamestate.nextPhase();
			}
 	    }
/*
 	    if(gamestate.getCurrentPhase() == Phase.Player1_SetUp_Extra_Pick)
 	    {
 	        for(int row = 0; row < P1CardList.length; row++)
 	        {
 	            for(int col = 0; col < P1CardList[0].length; col++)
 	            {
 	                if(P1CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P1CardList[row][col].getType == Type.ePick)
 	                        {
 	                            P1CardList[row][col].setType(Type.Safe);
 	                        }
 	                        else if(P1CardList[row][col].getType == Type.Safe && ePickCount < 2)
 	                        {
 	                            P1CardList[row][col].setType(Type.ePick);
 	                            ePickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	    }
 	    if(gamestate.getCurrentPhase() == Phase.Player1_SetUp_CPU_Pick)
 	    {
 	        for(int row = 0; row < P1CardList.length; row++)
 	        {
 	            for(int col = 0; col < P1CardList[0].length; col++)
 	            {
 	                if(P1CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P1CardList[row][col].getType == Type.cpuPick)
 	                        {
 	                            P1CardList[row][col].setType(Type.Safe);
 	                        }
 	                        else if(P1CardList[row][col].getType == Type.Safe && cpuPickCount < 4)
 	                        {
 	                            P1CardList[row][col].setType(Type.cpuPick);
 	                            cpuPickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	    }
//iterates through each card and checks for contains (player 2 setup_Phase)
    if(gamestate.getCurrentPhase() == Phase.Player2_SetUp_Poison)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getType == Type.Poison)
 	                        {
 	                            P2CardList[row][col].setType(Type.Safe);
 	                        }
 	                        else if(P2CardList[row][col].getType == Type.Safe && poisonCount < 4)
 	                        {
 	                            P2CardList[row][col].setType(Type.Poison);
 	                            poisonCounter++;
 	                        }
 	                    }

 	            }
 	        }
 	    }
 	    if(gamestate.getCurrentPhase() == Phase.Player2_SetUp_Extra_Heart)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getType == Type.eHeart)
 	                        {
 	                            P2CardList[row][col].setType(Type.Safe);
 	                        }
 	                        else if(P2CardList[row][col].getType == Type.Safe && eHeartCount < 1)
 	                        {
 	                            P2CardList[row][col].setType(Type.eHeart);
 	                            eHeartCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	    }
 	    if(gamestate.getCurrentPhase() == Phase.Player2_SetUp_Extra_Pick)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getType == Type.ePick)
 	                        {
 	                            P2CardList[row][col].setType(Type.Safe);
 	                        }
 	                        else if(P2CardList[row][col].getType == Type.Safe && ePickCount < 2)
 	                        {
 	                            P2CardList[row][col].setType(Type.ePick);
 	                            ePickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	    }
 	    if(gamestate.getCurrentPhase() == Phase.Player2_SetUp_CPU_Pick)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getType == Type.cpuPick)
 	                        {
 	                            P2CardList[row][col].setType(Type.Safe);
 	                        }
 	                        else if(P2CardList[row][col].getType == Type.Safe && cpuPickCount < 4)
 	                        {
 	                            P2CardList[row][col].setType(Type.cpuPick);
 	                            cpuPickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	    }*/

	}
 	public void mouseClicked(MouseEvent e)
 	{

	}

	public void mouseReleased(MouseEvent e)
	{

	}
	public void mouseEntered(MouseEvent e)
	{

	}
	public void mouseExited(MouseEvent e)
	{

	}
    public void actionPerformed(ActionEvent e)
    {
		if(gamestate.getCurrentPhase() == GameState.Phase.CREDITS)
		{
			tickCounter++;
			//16 ms every tick (around 62.5 ticks is 1 second)
			if(tickCounter >=63)
			{
				tickCounter=0;
				gamestate.nextPhase();
			}
		}
        repaint();
    }

    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        //set pixel font
        g.setFont(pixelFont);

        //starting phases
        if(gamestate.getCurrentPhase() == GameState.Phase.START)
        {
			g.drawImage(StartingScreen, 0, 0, null);
		}
		if(gamestate.getCurrentPhase() == GameState.Phase.CREDITS)
        {
			g.drawImage(CreditScreen, 0, 0, null);
		}

		//change to startup phase (P1_POISON)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1SETUP_POISON)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLAYER 1 PICK",620,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(62, 5, 124));//purple poison color
			g.drawString("     POISON!",650,460);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			    }
			}
			if(poisonCounter < poisonCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(62, 5, 124)); //signify that button is workable
			g.fillRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			g.setColor(Color.BLACK);
			g.drawRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			if(poisonCounter < poisonCount)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
    		g.drawString("NEXT", NextButton.x + 27, NextButton.y + 70);
		}
		//change to startup phase (P1_E_HEART)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAHEART)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLAYER 1 PICK",620,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(196, 34, 73));//red heart color
			g.drawString("     EXTRA",650,460);
			g.drawString("   HEART!",650,520);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			    }
			}
			if(eHeartCounter < eHeartCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(196, 34, 73)); //signify that button is workable
			g.fillRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			g.setColor(Color.BLACK);
			g.drawRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			if(eHeartCounter < eHeartCount)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
    		g.drawString("NEXT", NextButton.x + 27, NextButton.y + 70);
		}



    }
}