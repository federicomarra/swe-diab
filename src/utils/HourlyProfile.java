package utils;

import static utils.ReadCSV.ReadFloatCSV;
import static utils.ReadCSV.ReadIntCSV;

public class HourlyProfile {
    public HourlyFactor[] hourlyFactors;
    public ProfileMode mode;

    public HourlyProfile(ProfileMode mode) {
        hourlyFactors = new HourlyFactor[24];
        this.mode = mode;
        
        String path = "";
        switch (mode) {
            case BASAL:
                path = "csv/basalProfile.csv";
                break;
            case IC:
                path = "csv/carbRatio.csv";
                break;
            case IG:
                path = "csv/insulinSensitivity.csv";
                break;
        };

        switch (mode) {
            case BASAL:
                float[] fhf = ReadFloatCSV(path);
                for (int i = 0; i < 24; i++)
                    hourlyFactors[i] = new HourlyFactor(fhf[i], i);
                break;
            case IC:
            case IG:
                int[] ihf = ReadIntCSV(path);
                for (int i = 0; i < 24; i++)
                    hourlyFactors[i] = new HourlyFactor(ihf[i], i);
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
