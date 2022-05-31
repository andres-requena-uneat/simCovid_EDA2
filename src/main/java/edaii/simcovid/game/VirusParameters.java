package edaii.simcovid.game;

public class VirusParameters {
    final int transmissionPercent;
    final int lifetimeInDays;
    final int mortalityRate;
    final int surrounding;
    final int maskedTransmissionPercent;


    /**
     * Virus parameters
     *
     * @param transmissionPercent Percentage of transmissibility
     * @param lifetimeInDays      Life-time in a host until get immunity
     */
    public VirusParameters(int transmissionPercent, int lifetimeInDays, int mortalityRate, int surrounding, int maskedTransmissionPercent) {
        this.transmissionPercent = transmissionPercent;
        this.lifetimeInDays = lifetimeInDays;
        this.mortalityRate = mortalityRate;
        this.surrounding = surrounding;
        this.maskedTransmissionPercent = maskedTransmissionPercent;
    }

}
