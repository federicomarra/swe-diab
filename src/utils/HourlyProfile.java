package utils;

import static utils.DBManager.read;

public class HourlyProfile {
    public HourlyFactor[] hourlyFactors;
    public ProfileMode mode;

    public HourlyProfile(ProfileMode mode) {
        hourlyFactors = new HourlyFactor[24];
        this.mode = mode;

        String file = "";
        switch (mode) {
            case BASAL:
                file += "BasalProfile";
                break;
            case CR:
                file += "CarbRatio";
                break;
            case IS:
                file += "InsulinSensitivity";
                break;
        }
        float[] hf = read(file);
        for (int i = 0; i < 24; i++)
            hourlyFactors[i] = new HourlyFactor(hf[i], i);
    }

    public void updateHourlyFactor(HourlyFactor hf) {
        boolean success = false;
        String modeString = "";
        if (hf.getHour() < 0 || hf.getHour() > 23)
            hf.setHour(hf.getHour());
        switch (mode) {
            case BASAL:
                modeString = "basal profile";
                if (hf.getUnits() >= 0.1 && hf.getUnits() <= 5)
                    success = true;
                break;
            case CR:
                modeString = "carb ratio";
                if (hf.getUnits() >= 1 && hf.getUnits() <= 15)
                    success = true;
                break;
            case IS:
                modeString = "insulin sensitivity";
                if (hf.getUnits() >= 20 && hf.getUnits() <= 50)
                    success = true;
                break;
        }
        if (success) {
            System.out.println("Updating " + modeString + " h=" + hf.getHour() + " from "
                    + String.format("%.2f", hourlyFactors[hf.getHour()].getUnits()).replace(",", ".") + " to "
                    + String.format("%.2f", hf.getUnits()).replace(",", ".") + "...");
            hourlyFactors[hf.getHour()] = hf;
        } else {
            System.out.println("Invalid " + modeString + " value");
        }
    }
}
