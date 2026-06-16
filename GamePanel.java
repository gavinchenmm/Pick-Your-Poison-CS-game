import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.File;

public class GamePanel extends JPanel implements MouseListener, ActionListener, MouseMotionListener
{
    GameState gamestate;
   	Player p1;
   	Player p2;

	//font
	Font pixelFont;
	Font pixelFont_larger;

    Timer timer;
    int tickCounter = 0;
    int countdownTimer = 1250;
	final int maxTime = 1250; // 10 seconds (62.5 * 10)

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

	//player health bar
	String[] healthBarString = {"HealthBar0.png","HealthBar1.png","HealthBar2.png","HealthBar3.png","HealthBar4.png"};
	Image[] healthBarImage = new Image[5];

	//Starting images
	Image StartingScreen;
	Image CreditScreen;

	//Buttons
	Rectangle NextButton;
	Rectangle RestartButton;

    Toolkit tk;


    public GamePanel(JFrame frame)
    {

        tk = Toolkit.getDefaultToolkit();

        //intialize starting screen
        StartingScreen = tk.getImage("START_SCREEN.png");
		CreditScreen = tk.getImage("CREDITS_SCREEN.png");

        //resizing card sprites
		for(int i = 0; i < cardSpritesName.length; i++)
	 	{
			cardSprites[i] = tk.getImage(cardSpritesName[i]).getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
    	}
    	//resizing healthbar sprites
		for(int i = 0; i < healthBarString.length; i++)
	 	{
			healthBarImage[i] = tk.getImage(healthBarString[i]).getScaledInstance(489, 72, Image.SCALE_DEFAULT);
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
		    pixelFont_larger = Font.createFont(Font.TRUETYPE_FONT, new File("PressStart2P.ttf")).deriveFont(100f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(pixelFont);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
		//intialize game state and players
		p1 = new Player("Gavin",healthBarImage);
		p2 = new Player("Computer",healthBarImage);
        gamestate = new GameState(p1,p2);


		//intialize our own button
		NextButton = new Rectangle(830, 650, 250, 80);
        timer = new Timer(16,this);
        timer.start();
        addMouseListener(this);
        addMouseMotionListener(this);

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
 	                            P2CardList[row][col].resetPicked();
 	                            poisonCounter--;
 	                        }
 	                        else if(P1CardList[row][col].getCardType() == Card.cardType.Safe && poisonCounter < poisonCount)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Poison);
 	                            P1CardList[row][col].setCardImage(1); //set poison
 	                            P2CardList[row][col].madePicked();
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
 	                            P2CardList[row][col].resetPicked();
 	                            eHeartCounter--;
 	                        }
 	                        else if(P1CardList[row][col].getCardType() == Card.cardType.Safe && eHeartCounter < eHeartCount)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.E_Heart);
 	                            P1CardList[row][col].setCardImage(5); //set extra Heart
 	                            P2CardList[row][col].madePicked();
 	                           	eHeartCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the eHeartCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && eHeartCounter == eHeartCount)
			{
				gamestate.nextPhase();
			}
 	    }
//(player 1 setup_ExtraPick)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAPICK)
 	    {
 	        for(int row = 0; row < P1CardList.length; row++)
 	        {
 	            for(int col = 0; col < P1CardList[0].length; col++)
 	            {
 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P1CardList[row][col].getCardType() == Card.cardType.E_Pick)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P1CardList[row][col].setCardImage(2); //set safe
 	                            P2CardList[row][col].resetPicked();
 	                            ePickCounter--;
 	                        }
 	                        else if(P1CardList[row][col].getCardType() == Card.cardType.Safe && ePickCounter < ePickCount)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.E_Pick);
 	                            P1CardList[row][col].setCardImage(4); //set extra Pick
 	                            P2CardList[row][col].madePicked();
 	                           	ePickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the ePickCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && ePickCounter == ePickCount)
			{
				gamestate.nextPhase();
			}
 	    }
//(player 1 setup_CPUick)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_COMPUTERPICK)
 	    {
 	        for(int row = 0; row < P1CardList.length; row++)
 	        {
 	            for(int col = 0; col < P1CardList[0].length; col++)
 	            {
 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P1CardList[row][col].getCardType() == Card.cardType.Cpu_Pick)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P1CardList[row][col].setCardImage(2); //set safe
 	                            P2CardList[row][col].resetPicked();
 	                            cpuPickCounter--;
 	                        }
 	                        else if(P1CardList[row][col].getCardType() == Card.cardType.Safe && cpuPickCounter < cpuPickCount)
 	                        {
 	                            P1CardList[row][col].setCardType(Card.cardType.Cpu_Pick);
 	                            P1CardList[row][col].setCardImage(3); //set CPU Pick
 	                            P2CardList[row][col].madePicked();
 	                           	cpuPickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the ePickCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && cpuPickCounter == cpuPickCount)
			{
				gamestate.nextPhase();
				for(int row = 0; row < P1CardList.length; row++)
				{
					for(int col = 0; col < P1CardList[0].length; col++)
 	            	{
						P1CardList[row][col].setCardImage(0); //set P1 Card back
						P2CardList[row][col].resetPicked();
					}
				}
				cpuPickCounter = 0;
				ePickCounter = 0;
				eHeartCounter = 0;
				poisonCounter = 0;
			}
 	    }
//iterates through each card and checks for contains (player 2 setup_Phase)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2SETUP_POISON)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getCardType() == Card.cardType.Poison)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P2CardList[row][col].setCardImage(2); //set safe
 	                            P2CardList[row][col].resetPicked();
 	                            poisonCounter--;
 	                        }
 	                        else if(P2CardList[row][col].getCardType() == Card.cardType.Safe && poisonCounter < poisonCount)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.Poison);
 	                            P2CardList[row][col].setCardImage(1); //set poison
 	                            P2CardList[row][col].madePicked();
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
 //(player 2 setup_ExtraHeart)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAHEART)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getCardType() == Card.cardType.E_Heart)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P2CardList[row][col].setCardImage(2); //set safe
 	                            P2CardList[row][col].resetPicked();
 	                            eHeartCounter--;
 	                        }
 	                        else if(P2CardList[row][col].getCardType() == Card.cardType.Safe && eHeartCounter < eHeartCount)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.E_Heart);
 	                            P2CardList[row][col].setCardImage(5); //set extra Hear
 	                            P2CardList[row][col].madePicked();
 	                           	eHeartCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the eHeartCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && eHeartCounter == eHeartCount)
			{
				gamestate.nextPhase();
			}
 	    }
//(player 2 setup_ExtraPick)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAPICK)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getCardType() == Card.cardType.E_Pick)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P2CardList[row][col].setCardImage(2); //set safe
 	                            P2CardList[row][col].resetPicked();
 	                            ePickCounter--;
 	                        }
 	                        else if(P2CardList[row][col].getCardType() == Card.cardType.Safe && ePickCounter < ePickCount)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.E_Pick);
 	                            P2CardList[row][col].setCardImage(4); //set extra Pick
 	                            P2CardList[row][col].madePicked();
 	                           	ePickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the ePickCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && ePickCounter == ePickCount)
			{
				gamestate.nextPhase();
			}
 	    }
//(player 2 setup_CPUick)
 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_COMPUTERPICK)
 	    {
 	        for(int row = 0; row < P2CardList.length; row++)
 	        {
 	            for(int col = 0; col < P2CardList[0].length; col++)
 	            {
 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true)
 	                    {
 	                        if(P2CardList[row][col].getCardType() == Card.cardType.Cpu_Pick)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.Safe);
 	                            P2CardList[row][col].setCardImage(2); //set safe
 	                            P2CardList[row][col].resetPicked();
 	                            cpuPickCounter--;
 	                        }
 	                        else if(P2CardList[row][col].getCardType() == Card.cardType.Safe && cpuPickCounter < cpuPickCount)
 	                        {
 	                            P2CardList[row][col].setCardType(Card.cardType.Cpu_Pick);
 	                            P2CardList[row][col].setCardImage(3); //set CPU Pick
 	                            P2CardList[row][col].madePicked();
 	                           	cpuPickCounter++;
 	                        }
 	                    }
 	            }
 	        }
 	        //checks if the mouse hits the nextbutton and if the ePickCounter is enough
 	        if(NextButton.contains(e.getX(), e.getY()) && cpuPickCounter == cpuPickCount)
			{
				gamestate.nextPhase();
				for(int row = 0; row < P2CardList.length; row++)
				{
					for(int col = 0; col < P2CardList[0].length; col++)
 	            	{
						P2CardList[row][col].setCardImage(0); //set P2 Card back
						P2CardList[row][col].resetPicked();
					}
				}
			}
 	    }
 	//turn based playing
 	 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1PICK_POISON)
			{
	 	        for(int row = 0; row < P2CardList.length; row++)
	 	        {
	 	            for(int col = 0; col < P2CardList[0].length; col++)
	 	            {
	 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true && P2CardList[row][col].isPicked() == false)
	 	                    {
	 	                        P2CardList[row][col].madePicked();
	 	                        P2CardList[row][col].revealCardImage();
	 	                        gamestate.nextPhase();
	 	                        countdownTimer = maxTime;
	 	                    }
	 	            }
	 	        }
			}
 	 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2PICK_POISON)
			{
	 	        for(int row = 0; row < P1CardList.length; row++)
	 	        {
	 	            for(int col = 0; col < P1CardList[0].length; col++)
	 	            {
	 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true && P1CardList[row][col].isPicked() == false)
	 	                    {
	 	                        P1CardList[row][col].madePicked();
	 	                        P1CardList[row][col].revealCardImage();
	 	                        gamestate.nextPhase();
	 	                        countdownTimer = maxTime;
	 	                    }
	 	            }
	 	        }
			}


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

	public void	mouseDragged(MouseEvent e)
	{

	}
	public void	mouseMoved(MouseEvent e)
	{
		//made for highlighting the picks
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1PICK_POISON)
			{
	 	        for(int row = 0; row < P2CardList.length; row++)
	 	        {
	 	            for(int col = 0; col < P2CardList[0].length; col++)
	 	            {
						P2CardList[row][col].setHovered(false);
	 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true && P2CardList[row][col].isPicked() == false)
	 	                    {
								P2CardList[row][col].setHovered(true);
	 	                    }
	 	            }
	 	        }
			}
 	 	    if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2PICK_POISON)
			{
	 	        for(int row = 0; row < P1CardList.length; row++)
	 	        {
	 	            for(int col = 0; col < P1CardList[0].length; col++)
	 	            {
						P1CardList[row][col].setHovered(false);
	 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true && P1CardList[row][col].isPicked() == false)
							{
								P1CardList[row][col].setHovered(true);
	 	                    }
	 	            }
	 	        }
			}
			//hover over cards
			if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1SETUP_POISON || gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAHEART
			||gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAPICK ||gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_COMPUTERPICK)
			{
	 	        for(int row = 0; row < P1CardList.length; row++)
	 	        {
	 	            for(int col = 0; col < P1CardList[0].length; col++)
	 	            {
						P1CardList[row][col].setHovered(false);
	 	                    if(P1CardList[row][col].contains(e.getX(),e.getY()) == true && P1CardList[row][col].isPicked() == false)
							{
								P1CardList[row][col].setHovered(true);
	 	                    }
	 	            }
	 	        }
			}
			else if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2SETUP_POISON || gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAHEART
			||gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAPICK ||gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_COMPUTERPICK)
			{
	 	        for(int row = 0; row < P2CardList.length; row++)
	 	        {
	 	            for(int col = 0; col < P2CardList[0].length; col++)
	 	            {
						P2CardList[row][col].setHovered(false);
	 	                    if(P2CardList[row][col].contains(e.getX(),e.getY()) == true && P1CardList[row][col].isPicked() == false)
							{
								P2CardList[row][col].setHovered(true);
	 	                    }
	 	            }
	 	        }
			}
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
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1PICK_POISON || gamestate.getCurrentPhase() == GameState.Phase.PLAYER2PICK_POISON)
		{
		    countdownTimer--;
		    if(countdownTimer <= 0)
		    {
		        countdownTimer = maxTime; // reset timer
		        gamestate.nextPhase();     // force next player's turn
		    }
		}
        repaint();
    }
    //formats the timer and uses x:xx seconds then milliseconds
	public String formatTimer()
	{
		int totalSeconds = (int)(countdownTimer / 62.5);
		int milliSeconds = (int)((countdownTimer % 62.5) / 62.5 * 100);
		return String.format("%02d", totalSeconds) + ":" + String.format("%02d", milliSeconds);
	}

    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        //set pixel font
        g.setFont(pixelFont);

        //drawing healthbars
		p1.draw(g,50,10);
		p2.draw(g,1345,10);

        //starting phases
        if(gamestate.getCurrentPhase() == GameState.Phase.START)
        {
			g.drawImage(StartingScreen, 0, 0, null);
		}
		if(gamestate.getCurrentPhase() == GameState.Phase.CREDITS)
        {
			g.drawImage(CreditScreen, 0, 0, null);
		}
		//player name items
			if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1SETUP_POISON || gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAHEART
			||gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAPICK ||gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_COMPUTERPICK)
			{
				g.setColor(new Color(152, 9, 19)); //player 1
				g.drawString(gamestate.returnCurrentPlayer().getPlayerName(),800,340);
			}
			else if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2SETUP_POISON || gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAHEART
			||gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAPICK ||gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_COMPUTERPICK)
			{
				g.setColor(new Color(4, 7, 121)); //player 2
				g.drawString(gamestate.returnCurrentPlayer().getPlayerName(),800,340);
			}

		//change to startup phase (P1_POISON)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1SETUP_POISON)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(62, 5, 124));//purple poison color
			g.drawString("     POISON!",650,460);
			if(poisonCounter < poisonCount)
				g.setColor(new Color(75, 71, 80));
			else
				g.setColor(new Color(62, 5, 124));
			g.drawString("(" + poisonCounter +"/" + poisonCount +")",820,640);

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
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(196, 34, 73));//red heart color
			g.drawString("     EXTRA",650,460);
			g.drawString("   HEART!",650,520);
			if(eHeartCounter < eHeartCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(196, 34, 73)); //signify that button is workable
			g.drawString("(" + eHeartCounter +"/" + eHeartCount +")",820,640);

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
		//change to startup phase (P1_E_PICK)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_EXTRAPICK)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(66, 185, 245));//blue extra pick color
			g.drawString("     EXTRA",650,460);
			g.drawString("   PICK!",650,520);
			if(ePickCounter < ePickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(6, 105, 156)); //signify that button is workable (dark blue)
			g.drawString("(" + ePickCounter +"/" + ePickCount +")",820,640);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			    }
			}
			if(ePickCounter < ePickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(6, 105, 156)); //signify that button is workable (dark blue)
			g.fillRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			g.setColor(Color.BLACK);
			g.drawRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			if(ePickCounter < ePickCount)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
    		g.drawString("NEXT", NextButton.x + 27, NextButton.y + 70);
		}
		//change to startup phase (P1_CPU_pick)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1_COMPUTERPICK)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",630,460);
			g.setColor(new Color(4, 196, 36));//green cpu pick color
			g.drawString("     COMPUTER",630,460);
			g.drawString("   PICK!",650,520);
			if(cpuPickCounter < cpuPickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(3, 84, 16)); //signify that button is workable (dark green)
			g.drawString("(" + cpuPickCounter +"/" + cpuPickCount +")",820,640);


 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			    }
			}
			if(cpuPickCounter < cpuPickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(3, 84, 16)); //signify that button is workable (dark green)
			g.fillRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			g.setColor(Color.BLACK);
			g.drawRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			if(cpuPickCounter < cpuPickCount)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
    		g.drawString("NEXT", NextButton.x + 27, NextButton.y + 70);
		}

		//player 2 setup poison
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2SETUP_POISON)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(62, 5, 124));//purple poison color
			g.drawString("     POISON!",650,460);
			if(poisonCounter < poisonCount)
				g.setColor(new Color(75, 71, 80));
			else
				g.setColor(new Color(62, 5, 124));
			g.drawString("(" + poisonCounter +"/" + poisonCount +")",820,640);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
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
		//change to startup phase (P2_E_HEART)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAHEART)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(196, 34, 73));//red heart color
			g.drawString("     EXTRA",650,460);
			g.drawString("   HEART!",650,520);
			if(eHeartCounter < eHeartCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(196, 34, 73)); //signify that button is workable
			g.drawString("(" + eHeartCounter +"/" + eHeartCount +")",820,640);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
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
		//change to startup phase (P2_E_PICK)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_EXTRAPICK)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",650,460);
			g.setColor(new Color(66, 185, 245));//blue extra pick color
			g.drawString("     EXTRA",650,460);
			g.drawString("   PICK!",650,520);
			if(ePickCounter < ePickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(6, 105, 156)); //signify that button is workable (dark blue)
			g.drawString("(" + ePickCounter +"/" + ePickCount +")",820,640);


 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			    }
			}
			if(ePickCounter < ePickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(6, 105, 156)); //signify that button is workable (dark blue)
			g.fillRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			g.setColor(Color.BLACK);
			g.drawRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			if(ePickCounter < ePickCount)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
    		g.drawString("NEXT", NextButton.x + 27, NextButton.y + 70);
		}
		//change to startup phase (P2_CPU_pick)
		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER2_COMPUTERPICK)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawString("PLACE",810,400);
			g.drawString("YOUR ",630,460);
			g.setColor(new Color(4, 196, 36));//green cpu pick color
			g.drawString("     COMPUTER",630,460);
			g.drawString("   PICK!",650,520);
			if(cpuPickCounter < cpuPickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(3, 84, 16)); //signify that button is workable (dark green)
			g.drawString("(" + cpuPickCounter +"/" + cpuPickCount +")",820,640);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), false);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			    }
			}
			if(cpuPickCounter < cpuPickCount)
				g.setColor(new Color(75, 71, 80)); //signify that more poison needs to be placed
			else
				g.setColor(new Color(3, 84, 16)); //signify that button is workable (dark green)
			g.fillRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			g.setColor(Color.BLACK);
			g.drawRect(NextButton.x, NextButton.y, NextButton.width, NextButton.height);
			if(cpuPickCounter < cpuPickCount)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.WHITE);
    		g.drawString("NEXT", NextButton.x + 27, NextButton.y + 70);
		}

		if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1PICK_POISON || gamestate.getCurrentPhase() == GameState.Phase.PLAYER2PICK_POISON)
		{
			if(gamestate.getCurrentPhase() == GameState.Phase.PLAYER1PICK_POISON)
				g.setColor(new Color(152, 9, 19)); //player 1
			else
				g.setColor(new Color(4, 7, 121)); //player 2

			g.drawString(gamestate.returnCurrentPlayer().getPlayerName(),670,400);
			g.setColor(Color.BLACK);
			g.drawString("PICK A CARD!!",630,460);

			//borrowing cords of next button FOR DRAWING THE TIMER
			g.setColor(Color.BLACK);
			g.fillRect(NextButton.x - 155, NextButton.y - 610, NextButton.width + 310, NextButton.height + 70);
			g.drawRect(NextButton.x - 155, NextButton.y - 610, NextButton.width + 310, NextButton.height + 70);
			g.setColor(Color.WHITE);
			g.setFont(pixelFont_larger);
			g.drawString(formatTimer(), NextButton.x - 125, NextButton.y - 480);

 			for(int row = 0; row < P1CardList.length; row++)
			{
			    for(int col = 0; col < P1CardList[0].length; col++)
			    {
			        P1CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			        P2CardList[row][col].draw(g, gamestate.getCurrentPhase(), true);
			    }
			}

		}

    }
}