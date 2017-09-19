
package customer;

import java.util.Vector;

public class AllTempCustomers {

    public static Vector<TempCustomers> allTempCustomers = new Vector<TempCustomers>();

    /**
     * @return the allState
     */
    public static Vector<TempCustomers> getAllData() {
        return AllTempCustomers.allTempCustomers;
    }

    /**
     * @param allState the allState to set
     */
    public static void setAllData(final Vector<TempCustomers> NewData1) {
        // allRadioData.clear();
        AllTempCustomers.allTempCustomers = NewData1;
    }

    /**
     * @return the allState
     */
    public static TempCustomers getData(final int pos) {
        return AllTempCustomers.allTempCustomers.get(pos);
    }

    /**
     * @param allState the allState to set
     */
    public static void setData(final TempCustomers allTempCustomers1) {
        AllTempCustomers.allTempCustomers.addElement(allTempCustomers1);
    }

    public static void removeAllData() {

        AllTempCustomers.allTempCustomers.removeAllElements();
    }

    public static Vector<String> getAllDataBytitle() {
        final Vector<String> temp = new Vector<String>();
        // temp.addElement("Select  ");

        for (final TempCustomers st : AllTempCustomers.allTempCustomers) {
            temp.addElement(st.getCustName());

        }

        return temp;
    }

    /*
     * public static int getPositionInAllData(String stationName) { final
     * Vector<String> temp = new Vector<String>(); temp.addElement("Select  ");
     * for (final TempDrafts st : AllTempDrafts.allRadioData) {
     * temp.elementAt(st.getStation_name()); } return temp; }
     */

}
