package Games;
import Users.NoMuchMoney;

import java.util.ArrayList;

/**
 * Created by tamir on 16/04/2017.
 */
public class LimitHoldem extends Policy{

    public LimitHoldem(IGame policy)
    {
        this.policy = policy;
        this.id = this.policy.getId();
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount {
        if(super.getTurn() < 2)
            policy.raise(policy.getMinimumBet(), player);
        else
            policy.raise(amount, player);
    }

    @Override
    public void bet(int amount, Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        if(super.getTurn() < 2)
            policy.bet(policy.getMinimumBet(), player);
        else
            policy.bet(amount, player);
    }

    @Override
    public String getType() {
        return "LimitHoldem";
    }

}
