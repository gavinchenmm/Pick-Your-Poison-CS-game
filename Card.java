import javax.swing.*;
import java.awt.*;


public class Card
{
    public enum cardType
    {
        Poison, Safe, Cpu_Pick, E_Pick, E_Heart;
    }
    private boolean picked;
    private boolean hovered;
    private cardType currentType;
    private int posX, posY, width, height;
    private Image sprite;
    private Image[] spriteList; //0 - back, 1 - poison, 2 - safe, 3 - cpu, 4 - e_pick, 5 - e_heart
    int borderSize = 5;


    public Card(int posX, int posY, int width, int height, Image[] spriteNames)
    {
		this.hovered = false;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.currentType = cardType.Safe;
        this.picked = false;
        spriteList = spriteNames;
        sprite = spriteList[2];
    }


    public boolean contains(int mouseX, int mouseY)
    {
        Rectangle box = new Rectangle(posX, posY, width, height);
            return box.contains(mouseX, mouseY);
    }

    public void madePicked()
    {
        picked = true;
    }
    public void resetPicked()
    {
        picked = false;
    }

    public boolean isPicked()
    {
        return picked;
    }

    public cardType getCardType()
    {
        return currentType;
    }

    public void setCardType(cardType type)
    {
        currentType = type;
    }
    public void setCardImage(int num)
	{
		sprite = spriteList[num];
    }
	public void setHovered(boolean hovered)
	{
    	this.hovered = hovered;
	}
    public void revealCardImage()
	{
		if(picked)
		{
			if(currentType == cardType.Poison)
				sprite = spriteList[1]; //poison
			if(currentType == cardType.Safe)
				sprite = spriteList[2]; //safe
			if(currentType == cardType.Cpu_Pick)
				sprite = spriteList[3]; //cpu
			if(currentType == cardType. E_Pick)
				sprite = spriteList[4]; //e_pick
			if(currentType == cardType.E_Heart)
				sprite = spriteList[5]; //e_heart
		}
    }

    public int getX()
    {
        return posX;
    }

    public int getY()
    {
        return posY;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }

    public void draw(Graphics g, GameState.Phase phase, boolean showHiglight)
	{
	    Graphics2D g2 = (Graphics2D) g;

	    if(currentType == cardType.Poison)
	    {
	        if(phase == GameState.Phase.PLAYER1SETUP_POISON && showHiglight)
	        {
				g2.setColor(new Color(128, 0, 128)); //purple
        		g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	        else if(phase == GameState.Phase.PLAYER2SETUP_POISON && showHiglight)
	        {
	        	g2.setColor(new Color(128, 0, 128)); //purple
        		g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	    }

	    if(currentType == cardType.E_Heart)
	    {
	        if(phase == GameState.Phase.PLAYER1_EXTRAHEART && showHiglight)
	        {
	            g2.setColor(new Color(119, 4, 31)); //red
	            g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	        else if(phase == GameState.Phase.PLAYER2_EXTRAHEART && showHiglight)
			{
				g2.setColor(new Color(119, 4, 31)); //red
				g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	    }
	    if(currentType == cardType.E_Pick)
	    {
	        if(phase == GameState.Phase.PLAYER1_EXTRAPICK && showHiglight)
	        {
	            g2.setColor(new Color(66, 185, 245)); //blue
	            g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	        else if(phase == GameState.Phase.PLAYER2_EXTRAPICK && showHiglight)
			{
				g2.setColor(new Color(66, 185, 245)); //blue
				g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	    }
	    if(sprite == spriteList[0])
	    {
	        if(phase == GameState.Phase.PLAYER1PICK_POISON && showHiglight && hovered)
	        {
	            g2.setColor(new Color(152, 9, 19)); //player 1 color
	            g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	        else if(phase == GameState.Phase.PLAYER2PICK_POISON && showHiglight && hovered)
			{
				g2.setColor(new Color(4, 7, 121)); //player 2 color
				g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
	        }
	    }
	    if(sprite == spriteList[2])
	    {
	        if(phase == GameState.Phase.PLAYER1SETUP_POISON && hovered || phase == GameState.Phase.PLAYER1_EXTRAHEART && hovered
			||phase == GameState.Phase.PLAYER1_EXTRAPICK && hovered ||
			phase == GameState.Phase.PLAYER2SETUP_POISON && hovered || phase == GameState.Phase.PLAYER2_EXTRAHEART && hovered
			||phase == GameState.Phase.PLAYER2_EXTRAPICK && hovered)
			{
				g2.setColor(new Color(0, 0, 0)); //black
				g2.fillRect(posX - borderSize, posY - borderSize, width + borderSize*2, height + borderSize*2);
			}
		}


	    g2.drawImage(sprite, posX, posY, null);
	}
}