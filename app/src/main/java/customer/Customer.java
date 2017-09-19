
package customer;

public class Customer {
    private String custCode;
    private String custStatus;
    private String custVersion;

    private String custName;

    private String custAddress;

    private String discPercent;

    public Customer(String custCode, String custName, String custAddress, String discPercent,String custStatus,String custVersion) {
        this.custCode = custCode;
        this.custName = custName;
        this.custAddress = custAddress;
        this.discPercent = discPercent;
        this.custStatus = custStatus;
        this.custVersion = custVersion;
    }

    /**
	 * @return the custStatus
	 */
	public String getCustStatus() {
		return custStatus;
	}

	/**
	 * @param custStatus the custStatus to set
	 */
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	/**
	 * @return the custVersion
	 */
	public String getCustVersion() {
		return custVersion;
	}

	/**
	 * @param custVersion the custVersion to set
	 */
	public void setCustVersion(String custVersion) {
		this.custVersion = custVersion;
	}

	public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getDiscPercent() {
        return discPercent;
    }

    public void setDiscPercent(String discPercent) {
        this.discPercent = discPercent;
    }

}
