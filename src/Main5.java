import java.util.*;
import java.util.stream.Collectors;

public class Main5 {


    private static final String CARD_NAME_ROYAL = "royal straight flush";

    private static final String CARD_NAME_STRAIGHT_FLUSH = "straight flush";


    private static final String CARD_NAME_FOUR_KIND = "four of a kind";


    private static final String CARD_NAME_FULL_HOUSE = "full house";

    private static final String CARD_NAME_FLUSH = "flush";


    private static final String CARD_NAME_STRAIGHT = "straight";

    private static final String CARD_NAME_THREE_KIND = "three of a kind";

    private static final String CARD_NAME_TWO_PAIR = "two pair";

    private static final String CARD_NAME_ONE_PAIR = "one pair";

    private static final String CARD_NAME_HIGH_CARD = "high card";


    private static final Map<String, Integer> CARD_SCORE;

    static {
        Map<String, Integer> temp = new HashMap<>();
        temp.put(CARD_NAME_ROYAL, 10);
        temp.put(CARD_NAME_STRAIGHT_FLUSH, 9);
        temp.put(CARD_NAME_FOUR_KIND, 8);
        temp.put(CARD_NAME_FULL_HOUSE, 7);
        temp.put(CARD_NAME_FLUSH, 6);
        temp.put(CARD_NAME_STRAIGHT, 5);
        temp.put(CARD_NAME_THREE_KIND, 4);
        temp.put(CARD_NAME_TWO_PAIR, 3);
        temp.put(CARD_NAME_ONE_PAIR, 2);
        temp.put(CARD_NAME_HIGH_CARD, 1);

        CARD_SCORE = Collections.unmodifiableMap(temp);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        String[] placeCardStrs = sc.nextLine().split(" ");
        Card[] placeCards = new Card[placeCardStrs.length];
        for (int i = 0; i < placeCardStrs.length; i++) {
            placeCards[i] = new Card(placeCardStrs[i]);
        }

        String[] handCardStrs = sc.nextLine().split(" ");
        Card[] handCards = new Card[handCardStrs.length];
        for (int i = 0; i < handCardStrs.length; i++) {
            handCards[i] = new Card(handCardStrs[i]);
        }

        System.out.println(getResult(placeCards, handCards));
    }

    private static String getResult(Card[] placeCards, Card[] handCards) {
        String[] maxCardName = {CARD_NAME_HIGH_CARD};

        doGetMaxResult(placeCards, handCards, new CardList(), maxCardName, 0);
        return maxCardName[0];
    }


    private static void doGetMaxResult(Card[] placeCards, Card[] handCards, CardList curCards, String[] maxCardName, int curPlaceIndex) {
        if (curCards.getSize() == 3) {
            doGetMaxResultFromHand(handCards, curCards, maxCardName, 0);
            return;
        }

        for (int i = curPlaceIndex; i < placeCards.length; i++) {
            Card placeCard = placeCards[i];
            curCards.addCard(placeCard);
            doGetMaxResult(placeCards, handCards, curCards, maxCardName, i + 1);
            curCards.removeCard(placeCard);
        }
    }

    private static void doGetMaxResultFromHand(Card[] handCards, CardList curCards, String[] maxCardName, int curHandIndex) {
        if (curCards.getSize() == 5) {
            String cardsName = curCards.getCardsName();
            if (getScore(cardsName) > getScore(maxCardName[0])) {
                maxCardName[0] = cardsName;
            }

            return;
        }

        for (int i = curHandIndex; i < handCards.length; i++) {
            Card handCard = handCards[i];
            curCards.addCard(handCard);
            doGetMaxResultFromHand(handCards, curCards, maxCardName, i + 1);
            curCards.removeCard(handCard);
        }
    }

    private static int getScore(String cardName) {
        return CARD_SCORE.get(cardName);
    }

    private static class CardList {


        private Set<Card> cards = new HashSet<>();

        private Map<String, Integer> shapeCounter = new HashMap<>();


        private Map<Integer, Integer> numCounter = new HashMap<>();

        public void addCard(Card card) {
            String shape = card.getShape();
            shapeCounter.put(shape, shapeCounter.getOrDefault(shape, 0) + 1);

            Integer num = card.getNum();
            numCounter.put(num, numCounter.getOrDefault(num, 0) + 1);

            cards.add(card);
        }

        public void removeCard(Card card) {
            String shape = card.getShape();
            shapeCounter.put(shape, shapeCounter.get(shape) - 1);

            Integer num = card.getNum();
            numCounter.put(num, numCounter.get(num) - 1);

            cards.remove(card);
        }

        public int getSize() {
            return cards.size();
        }

        private String getCardsName() {
            if (getSize() < 5) {
                return CARD_NAME_HIGH_CARD;
            }

            boolean sameShape = isSameShape();
            boolean sort = isSort();
            List<Card> sortedCard = getSortedCard();


            if (sameShape
                    && sort
                    && sortedCard.get(0).getNum() == 1
                    && sortedCard.get(1).getNum() == 10
                    && sortedCard.get(sortedCard.size() - 1).getNum() == 13) {
                return CARD_NAME_ROYAL;
            }

            if (sameShape && sort) {
                return CARD_NAME_STRAIGHT_FLUSH;
            }

            if (checkCardCount(4)) {
                return CARD_NAME_FOUR_KIND;
            }

            boolean threeCard = checkCardCount(3);
            boolean twoCard = checkCardCount(2);
            if (threeCard && twoCard) {
                return CARD_NAME_FULL_HOUSE;
            }

            if (sameShape) {
                return CARD_NAME_FLUSH;
            }

            if (sort) {
                return CARD_NAME_STRAIGHT;
            }

            if (threeCard) {
                return CARD_NAME_THREE_KIND;
            }

            if (twoCard && checkTwoPair()) {
                return CARD_NAME_TWO_PAIR;
            }

            if (twoCard) {
                return CARD_NAME_ONE_PAIR;
            }

            return CARD_NAME_HIGH_CARD;
        }


        private boolean checkTwoPair() {
            int counter = 0;
            for (Integer value : numCounter.values()) {
                if (value == null || value != 2) {
                    continue;
                }

                ++counter;
                if (counter == 2) {
                    return true;
                }
            }

            return false;
        }


        private boolean checkCardCount(int tarCount) {
            for (Integer value : numCounter.values()) {
                if (value == null) {
                    continue;
                }

                if (value == tarCount) {
                    return true;
                }
            }

            return false;
        }


        private boolean isSameShape() {
            int counter = 0;
            for (Integer value : shapeCounter.values()) {
                if (value == null || value < 1) {
                    continue;
                }

                ++counter;
                if (counter > 1) {
                    return false;
                }
            }

            return true;
        }

        private List<Card> getSortedCard() {
            List<Card> list = cards.stream().collect(Collectors.toList());
            if (getSize() <= 1) {
                return list;
            }

            list.sort(Comparator.comparingInt(Card::getNum));
            return list;
        }

        private boolean isSort() {
            int size = getSize();
            if (size <= 1) {
                return true;
            }


            List<Card> list = getSortedCard();

            int stopIndex = -1;
            for (int i = 1; i < size; i++) {
                Card card = list.get(i);
                Card preCard = list.get(i - 1);
                if (card.getNum() - 1 != preCard.getNum()) {
                    stopIndex = i;
                    break;
                }
            }

            if (stopIndex < 0) {
                return true;
            }

            int startIndex = stopIndex + 1;
            stopIndex = -1;
            for (int i = startIndex; i < size; i++) {
                Card card = list.get(i);
                Card preCard = list.get(i - 1);
                if (card.getNum() - 1 != preCard.getNum()) {
                    stopIndex = i;
                    break;
                }
            }

            if (stopIndex >= 0) {
                return false;
            }


            if (list.get(size - 1).getNum() == 13 && list.get(0).getNum() == 1) {
                return true;
            }

            return false;
        }

    }


    private static class Card {
        private String shape;

        private Integer num;

        public Card(String cardInfoStr) {
            shape = cardInfoStr.substring(0, 1);
            String numStr = cardInfoStr.substring(1, cardInfoStr.length());
            if ("A".equals(numStr)) {
                num = 1;
            } else if ("J".equals(numStr)) {
                num = 11;
            } else if ("Q".equals(numStr)) {
                num = 12;
            } else if ("K".equals(numStr)) {
                num = 13;
            } else {
                num = Integer.parseInt(numStr);
            }

        }

        public String getShape() {
            return shape;
        }

        public Integer getNum() {
            return num;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "shape='" + shape + '\'' +
                    ", num=" + num +
                    '}';
        }
    }

}


