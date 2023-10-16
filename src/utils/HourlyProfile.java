package utils;

import static utils.ReadCSV.ReadFloatCSV;
import static utils.ReadCSV.ReadIntCSV;

public class HourlyProfile {
    public HourlyFactor[] hourlyFactors;
    public ProfileMode mode;

    public HourlyProfile(ProfileMode mode) {
        hourlyFactors = new HourlyFactor[24];
        this.mode = mode;
        switch (mode) {
            case BASAL:
                float[] bf = ReadFloatCSV("csv/basalProfile.csv");
                for (int i = 0; i < 24; i++)
                    hourlyFactors[i] = new HourlyFactor(bf[i], i);
                break;
            case IC:
                int[] cb = ReadIntCSV("csv/carbRatio.csv");
                for (int i = 0; i < 24; i++)
                    hourlyFactors[i] = new HourlyFactor(cb[i], i);
                break;
            case IG:
                int[] is = ReadIntCSV("csv/insulinSensitivity.csv");
                for (int i = 0; i < 24; i++)
                    hourlyFactors[i] = new HourlyFactor(is[i], i);
                break;
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
