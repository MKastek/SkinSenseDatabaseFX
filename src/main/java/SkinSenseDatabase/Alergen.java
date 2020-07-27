package SkinSenseDatabase;

public class Alergen {
    int bubble;
    int evaluation;
    double probabilityNN;
    int decison;

    public Alergen() { }

    public int getBubble() {
        return bubble;
    }

    public void setBubble(int bubble) {
        this.bubble = bubble;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public double getProbabilityNN() {
        return probabilityNN;
    }

    public void setProbabilityNN(double probabilityNN) {
        this.probabilityNN = probabilityNN;
    }

    public int getDecison() {
        return decison;
    }

    public void setDecison(int decison) {
        this.decison = decison;
    }

    public Alergen(int bubble, int evaluation, double probabilityNN, int decison) {
        this.bubble = bubble;
        this.evaluation = evaluation;
        this.probabilityNN = probabilityNN;
        this.decison = decison;
    }

    @Override
    public String toString() {
        return "Alergen{" +
                "bubble=" + bubble +
                ", evaluation=" + evaluation +
                ", probabilityNN=" + probabilityNN +
                ", decison=" + decison +
                '}';
    }
}
