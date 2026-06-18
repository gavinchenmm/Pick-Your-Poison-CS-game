import javax.swing.*;
import java.awt.*;

public class Player
{
    private int hearts;
    private int extraTurns;
    private String playerName;
    private Image[] spriteList;
	private Image sprite;


    public Player(String playerName, Image[] spriteNames)
    {
        this.playerName = playerName;
        this.hearts = 3;
        this.extraTurns = 0;
        spriteList = spriteNames;
    }

    public void Effect(Card card)
    {
        Card.cardType type = card.getCardType();
        if(type == Card.cardType.Poison)
        {
            hearts--;
        }
        else if(type == Card.cardType.E_Heart)
        {
            hearts++;
        }
        else if(type == Card.cardType.E_Pick)
        {
            extraTurns++;
        }
        else if(type == Card.cardType.Cpu_Pick)
        {
            //random
        }
    }
    public void loseHeart()
    {
        hearts--;
    }
    public void gainHeart()
    {
        hearts++;
    }
    public void resetHearts()
    {
		hearts = 3;
	}
    public void addExtraTurn()
    {
        extraTurns++;
    }
    public void useExtraTurn()
    {
        if(extraTurns > 0)
        {
            extraTurns--;
        }
    }

    public boolean isDead()
    {
        return hearts <= 0;
    }

    public int getHearts()
    {
        return hearts;
    }

    public int getExtaTurns()
    {
        return extraTurns;
    }

    public String getPlayerName()
    {
        return playerName;
    }
    public void changePlayerName(String newName)
    {
    	playerName = newName;
    }
	public void draw(Graphics g, int posX, int posY)
	{
		Graphics2D g2 = (Graphics2D) g;
		if(hearts == 4)
			sprite = spriteList[4];
		else if(hearts == 3)
			sprite = spriteList[3];
		else if(hearts == 2)
			sprite = spriteList[2];
		else if(hearts == 1)
			sprite = spriteList[1];
		else if(hearts == 0)
			sprite = spriteList[0];
		else
			sprite = null;
		g2.drawImage(sprite, posX, posY, null);
	}
}


