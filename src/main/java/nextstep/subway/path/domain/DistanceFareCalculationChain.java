package nextstep.subway.path.domain;

public class DistanceFareCalculationChain implements FareCalculationChain {

    private static final int DEFAULT_FARE_DISTANCE = 10;
    private static final int ADD_100_FARE_DISTANCE = 50;

    private FareCalculationChain chain;

    @Override
    public void setNextChain(FareCalculationChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public Fare calculate(FareCalculationCriteria criteria, Fare fare) {
        int distance = criteria.getDistance();

        if (distance <= DEFAULT_FARE_DISTANCE) {
            fare = new Fare(DEFAULT_FARE);
        } else if (distance <= ADD_100_FARE_DISTANCE) {
            fare = new Fare(DEFAULT_FARE + getFareOver10Under50km(distance));
        } else {
            fare = new Fare(DEFAULT_FARE + getFareOver10Under50km(distance) +
                    + getFareOver50km(distance));
        }

        return this.chain.calculate(criteria, fare);
    }

    private int getFareOver10Under50km(int distance) {
        return (int) ((Math.ceil((distance - DEFAULT_FARE_DISTANCE - 1) / 5) + 1) * 100);
    }

    private int getFareOver50km(int distance) {
        return (int) ((Math.ceil((distance - ADD_100_FARE_DISTANCE - 1) / 8) + 1) * 100);
    }
}
