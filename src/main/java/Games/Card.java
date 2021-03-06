package Games;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Card implements Comparable<Card> {
    Color color;
    Number number;
    Suit suit;
    @Id
    private String id = new ObjectId().toString();

    public Card(Color color, Number number, Suit suit) {
        this.color = color;
        this.number = number;
        this.suit = suit;
    }

    @Override
    public int compareTo(Card o) {
        return this.number.value - o.number.value;
    }
}
