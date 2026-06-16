import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GameState
{
    public enum Phase
    {
		START, CREDITS, //starting phase
        PLAYER1SETUP_POISON, PLAYER2SETUP_POISON, //placing the poison
        PLAYER1_EXTRAHEART, PLAYER2_EXTRAHEART, //players picking extra heart card
        PLAYER1_EXTRAPICK, PLAYER2_EXTRAPICK, // players picking extra pick crd
        PLAYER1_COMPUTERPICK, PLAYER2_COMPUTERPICK, //players picking computer pick card
        PLAYER1PICK_POISON, PLAYER2PICK_POISON, //players picking poison
        RESULT;
    }
	private Player player1;
	public Player player2;
	public Phase currentPhase;

    public int p1Poisonchoice = 0;
    public int p2Poisonchoice = 0;

    public GameState(Player p1, Player p2)
    {
        player1 = p1;
        player2 = p2;

        currentPhase = Phase.START;
    }

        public void handleCardpicks(Card card)
        {
            Player currentPlayer = returnCurrentPlayer();
            Player otherPlayer;

            if(currentPlayer == player1)
            {
                otherPlayer = player2;
            }
            else
            {
                otherPlayer = player1;
            }

            //extra heart
            if(card.getCardType() == Card.cardType.E_Heart)
            {
               /* currentPlayer.addHeart(1);*/
                nextPhase();
            }

            //extra pick
            else if(card.getCardType()== Card.cardType.E_Pick)
            {
                if(currentPlayer == player1)
                {
                    currentPhase = Phase.PLAYER1PICK_POISON;
                }
                else
                {
                    currentPhase = Phase.PLAYER2PICK_POISON;
                }
            }

            //computer picks
            else if(card.getCardType()== Card.cardType.Cpu_Pick)
            {
                //random card selected

            }

            //picking the poison card
            else if(card.getCardType()== Card.cardType.Poison)
            {
                if(currentPlayer == player1)
                {
                    currentPhase = Phase.PLAYER1PICK_POISON;
                   /* currentPlayer.loseHeart(1); */
                }
                else
                {
                    currentPhase = Phase.PLAYER2PICK_POISON;
                   /* currentPlayer.loseHeart(1); */
                }
            }
            if (player1.isDead() || player2.isDead())
            {
                currentPhase = Phase.RESULT;
            }

        }


        public void nextPhase() //switching phases
        {
			if(currentPhase == Phase.START)
			{
				currentPhase = Phase.CREDITS;
			}
			else if(currentPhase == Phase.CREDITS)
			{
				currentPhase = Phase.PLAYER1SETUP_POISON;
			}
            else if(currentPhase == Phase.PLAYER1SETUP_POISON)
            {
                 currentPhase = Phase.PLAYER1_EXTRAHEART;
            }
            else if(currentPhase == Phase.PLAYER1_EXTRAHEART)
            {
                currentPhase = Phase.PLAYER1_EXTRAPICK;
            }
            else if(currentPhase == Phase.PLAYER1_EXTRAPICK)
            {
                currentPhase = Phase.PLAYER1_COMPUTERPICK;
            }
            else if(currentPhase == Phase.PLAYER1_COMPUTERPICK)
            {
                currentPhase = Phase.PLAYER2SETUP_POISON;
            }
            else if(currentPhase == Phase.PLAYER2SETUP_POISON)
            {
                currentPhase = Phase.PLAYER2_EXTRAHEART;
            }
            else if(currentPhase == Phase.PLAYER2_EXTRAHEART)
            {
                currentPhase = Phase.PLAYER2_EXTRAPICK;
            }
            else if(currentPhase == Phase.PLAYER2_EXTRAPICK)
			{
				currentPhase = Phase.PLAYER2_COMPUTERPICK;
            }
            else if(currentPhase == Phase.PLAYER2_COMPUTERPICK)
			{
				currentPhase = Phase.PLAYER1PICK_POISON;
            }
            else if(currentPhase == Phase.PLAYER1PICK_POISON)
			{
				currentPhase = Phase.PLAYER2PICK_POISON;
            }
            else if(currentPhase == Phase.PLAYER2PICK_POISON)
			{
				currentPhase = Phase.PLAYER1PICK_POISON;
            }
        }


        public Player returnCurrentPlayer()
        {
            if(currentPhase == Phase.PLAYER1SETUP_POISON ||
            currentPhase == Phase.PLAYER1_EXTRAHEART ||
            currentPhase == Phase.PLAYER1_EXTRAPICK ||
            currentPhase == Phase.PLAYER1_COMPUTERPICK ||
            currentPhase == Phase.PLAYER1PICK_POISON)
            {
                return player1;
            }
            else if(currentPhase == Phase.PLAYER2SETUP_POISON ||
            currentPhase == Phase.PLAYER2_EXTRAHEART ||
            currentPhase == Phase.PLAYER2_EXTRAPICK ||
            currentPhase == Phase.PLAYER2_COMPUTERPICK ||
            currentPhase == Phase.PLAYER2PICK_POISON)
            {
                return player2;
            }
            else
				return null;
        }

        public Player getPlayer1()
        {
            return player1;
        }

        public Player getPlayer2()
        {
            return player2;
        }

        public Phase getCurrentPhase()
        {
            return currentPhase;
        }

        public void setCurrentPhase(Phase phase)
        {
            currentPhase = phase;
        }


}