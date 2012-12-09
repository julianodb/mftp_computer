package tcc_gui;

public class CommReturn {

    public int[] value = null;
    public boolean commSuccess = false;
    public boolean executionSuccess = false;
    public int exceptionCode = 0;
    public int function = 0;
    public byte[] data = null;
    public int size = 0;

    public String toString() {
        String toBeReturned = "commSuccess=" + commSuccess + " executionSuccess=" + executionSuccess + " Function=" + function + " Value=";
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                toBeReturned += value[i] + " ";
            }
        } else {
            toBeReturned += "null";
        }
        toBeReturned += " Size=" + size + " Exception=" + exceptionCode;
        return toBeReturned;
    } // toString
} // CommReturn
