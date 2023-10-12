package utils;

import java.util.List;

public interface Observer {
    public void update(List<Measurement> m);
}
