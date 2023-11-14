package utils;

import static utils.ReadCSV.ReadCSV;

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
        }
        float[] hf = ReadCSV(path);
        for (int i = 0; i < 24; i++)
            hourlyFactors[i] = new HourlyFactor(hf[i], i);
    }

    public void updateHourlyFactor(HourlyFactor hf) {
        switch (mode) {
            case BASAL:
                if (hf.units() > 0.1 && hf.units() < 5)
                    hourlyFactors[hf.hour()] = hf;
                else System.out.println("Invalid basal profile value");
                break;
            case IC:
                if (hf.units() >= 1 && hf.units() <= 15)
                    hourlyFactors[hf.hour()] = hf;
                else System.out.println("Invalid carb ratio value");
                break;
            case IG:
                if (hf.units() >= 20 && hf.units() <= 50)
                    hourlyFactors[hf.hour()] = hf;
                else System.out.println("Invalid insulin sensitivity value");
                break;
        }
    }
}
