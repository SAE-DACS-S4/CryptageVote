package Cryptage;

import java.math.BigInteger;

public class Proof {
    private BigInteger[] challenges, answers;

    public void setChallenges(BigInteger[] challenges) {
        this.challenges = challenges;
    }

    public void setAnswers(BigInteger[] answers) {
        this.answers = answers;
    }

    public BigInteger[] getChallenges() {
        return challenges;
    }

    public BigInteger[] getAnswers() {
        return answers;
    }
}
