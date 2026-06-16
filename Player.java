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
        sprite = spriteList[3];
    }

    public void Effect(Card card)
    {
        Card.cardType type = card.getCardType();
        if(type.equals("damage"))
        {
            hearts--;
        }
        else if(type.equals("heal"))
        {
            hearts++;
        }
        else if(type.equals("extraTurn"))
        {
            extraTurns++;
        }
        else if(type.equals("doubleDamage"))
        {
            hearts -= 2;
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
	public void draw(Graphics g, int posX, int posY)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(sprite, posX, posY, null);
	}
}


