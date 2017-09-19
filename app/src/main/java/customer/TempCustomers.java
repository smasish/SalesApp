/**
 * 
 */

package customer;

public class TempCustomers {

    private String custCode;

    private String custName;

    private String custAddress;

    private String discPercent;

    /**
     * @return the custCode
     */
    public String getCustCode() {
        return custCode;
    }

    /**
     * @param custCode the custCode to set
     */
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    /**
     * @return the custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the custAddress
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     * @param custAddress the custAddress to set
     */
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    /**
     * @return the discPercent
     */
    public String getDiscPercent() {
        return discPercent;
    }

    /**
     * @param discPercent the discPercent to set
     */
    public void setDiscPercent(String discPercent) {
        this.discPercent = discPercent;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TempDrafts [custAddress=" + custAddress + ", custCode=" + custCode + ", custName="
                + custName + ", discPercent=" + discPercent + "]";
    }

}
