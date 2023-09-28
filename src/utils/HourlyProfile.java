package utils;

public class HourlyProfile {
    public HourlyFactor[] hourlyFactors;
    public ProfileMode mode;

    public HourlyProfile(ProfileMode mode) {
        hourlyFactors = new HourlyFactor[24];
        this.mode = mode;
        for (int i = 0; i < 24; i++) {
            switch (mode) {
                case BASAL:
                    hourlyFactors[i] = new HourlyFactor(1.5F, i);
                    break;
                case IC:
                    hourlyFactors[i] = new HourlyFactor(10, i);
                    break;
                case IG:
                    hourlyFactors[i] = new HourlyFactor(40, i);
                    break;
            }
        }
    }

    public void updateHourlyFactor(HourlyFactor hf) {
        switch (mode) {
            case BASAL:
                if (hf.units() > 0.1 && hf.units() < 5)
                    hourlyFactors[hf.hour()] = hf;
                break;
            case IC:
                if (hf.units() >= 1 && hf.units() <= 15)
                    hourlyFactors[hf.hour()] = hf;
                break;
            case IG:
                if (hf.units() >= 20 && hf.units() <= 50)
                    hourlyFactors[hf.hour()] = hf;
                break;
        }
    }
}
