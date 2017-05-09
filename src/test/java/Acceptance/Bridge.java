package Acceptance;

import java.util.ArrayList;

import ServiceLayer.ServiceUser;
import Users.AlreadyLoggedIn;
import Users.AlreadyLoggedOut;
import Users.EmailNotValid;
import Users.LeagueNotExists;
import Users.NegativeValue;
import Users.PasswordNotValid;
import Users.UserAlreadyExists;
import Users.UserAlreadyInLeague;
import Users.UserNotExists;
import Users.UserNotInLeague;
import Users.UserNotLoggedIn;
import Users.UsernameAndPasswordNotMatch;
import Users.UsernameNotValid;

public interface Bridge {

    ServiceUser register(String username, String password, String email, int wallet) throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid;

    ServiceUser login(String username, String password) throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserNotExists;

    void logout(String username) throws UserNotExists, AlreadyLoggedOut;

    ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists;

    int createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists;

    void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists;

    void spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists;

    ArrayList<String> viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists;

    void saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists;

    void setDefaultLeague(String username, int defaultLeague) throws UserNotLoggedIn, UserNotExists, NegativeValue;

    void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue;

    void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists, LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague;

    void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists;

    void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists;

    void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists;

    void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists;

    void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists;
}
