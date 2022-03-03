package frc.robot.CustomUtil;
import java.util.LinkedList;
import java.util.function.Consumer;
/*
    Custom class to contain and analyze all data within a certain timeframe
    @author WilliamBruce
*/
public class Timeframe<N extends Number> {
    private LinkedList<N> frame;
    private double maxLen;
    public Timeframe(double seconds, double updatesPerSecond) {
        frame = new LinkedList<>();
        maxLen = seconds * updatesPerSecond;
    }

    /*
        Updates the Timeframe object with a new input
        @param value the value to update the Timeframe with
    */
    public void update(N value) {
        if(frame.size() >= maxLen) { //If the current data does not take up a whole timeframe, then simply add this datapoint, else remove a point first
            frame.remove();
        }
        frame.add(value);
    }

    /*
        Get a percentage of existing entries that are greater than a target number
        @param target the target to compare other data points to
        @return returns the percentage of matching entries in decimal form (0 - 1.0)
    */
    public double percentGreaterThan(N target, boolean includeEqual) {
        if(frame.size() == 0) return 0.0;
        int matches = 0;
        for(int i = 0; i < frame.size(); i++) {
            if(includeEqual && frame.get(i).doubleValue() >= target.doubleValue()) {
                matches++;
            } else if(!includeEqual && frame.get(i).doubleValue() > target.doubleValue()) {
                matches++;
            }
        }
        return ((double)matches) / maxLen;
    }

    /*
        Get a percentage of existing entries that are less than a target number
        @param target the target to compare other data points to
        @return returns the percentage of matching entries in decimal form (0 - 1.0)
    */
    public double percentLessThan(N target, boolean includeEqual) {
        if(frame.size() == 0) return 0.0;
        return 1.0 - percentGreaterThan(target, !includeEqual);
    }

    /*
        Get a percentage of existing entries that are equal to a target number
        @param target the target to compare other data points to
        @return returns the percentage of matching entries in decimal form (0 - 1.0)
    */
    public double percentEqual(N target) {
        if(frame.size() == 0) return 0.0;
        int matches = 0;
        for(int i = 0; i < frame.size(); i++) {
            if(frame.get(i).doubleValue() == target.doubleValue()) matches++;
        }
        return ((double)matches) / maxLen;
    }

    public void forEach(Consumer<? super N> action) {
        frame.forEach(action);
    }
}
