package com.anpi.domain;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name="t_credit_charge_response")
@XmlRootElement(name="OrderResponse")
public class CCChargeResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private String id;
	@Column(name="order_id")
	private String orderID;
	@Column(name="gateway_transaction_reference_number")
	private String gatewayTransactionReferenceNumber;
	@Column(name="gateway_transaction_index")
	private String gatewayTransactionIndex;
	@Column(name="authorization_code")
	private String authorizationCode;
	@Column(name="response_dateTime")
	private String responseDateTime;
	@Column(name="approval_status")
	private String approvalStatus;
	@Column(name="response_code")
	private String responseCode;
	@Column(name="response_address_code")
	private String responseAddressCode;
	@Column(name="response_host_address_code")
	private String responseHostAddressCode;
	@Column(name="response_cvv_code")
	private String responseCVVCode;
	@Column(name="response_code_message")
	private String responseCodeMessage;
	@Column(name="response_host_code")
	private String responseHostCode;
	@Column(name="proc_status")
	private String procStatus;
	@Column(name="proc_status_message")
	private String procStatusMessage;
	@Column(name="profile_status_message")
	private String profileStatusMessage;
	@Column(name="profile_status")
	private String profileStatus;
	@Column(name="customer_ref_number")
	private String customerRefNumber;
	@Column(name="remaining_balance")
	private BigDecimal remainingBalance;
	@Column(name="redeemed_amount")
	private BigDecimal redeemedAmount;
	@Column(name="retry_trace")
	private String retryTrace;
	@Column(name="retry_attempt_count")
	private int retryAttemptCount;
	@SerializedName("FaultCode")
	@Column(name="fault_code")
	private String faultCode;
	@SerializedName("ErrorMessage")
	@Column(name="error_message")
	private String errorMessage;
	
	@Column(name="is_success")
	private int isSuccess;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getGatewayTransactionReferenceNumber() {
		return gatewayTransactionReferenceNumber;
	}

	public void setGatewayTransactionReferenceNumber(String gatewayTransactionReferenceNumber) {
		this.gatewayTransactionReferenceNumber = gatewayTransactionReferenceNumber;
	}

	public String getGatewayTransactionIndex() {
		return gatewayTransactionIndex;
	}

	public void setGatewayTransactionIndex(String gatewayTransactionIndex) {
		this.gatewayTransactionIndex = gatewayTransactionIndex;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(String responseDateTime) {
		this.responseDateTime = responseDateTime;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseAddressCode() {
		return responseAddressCode;
	}

	public void setResponseAddressCode(String responseAddressCode) {
		this.responseAddressCode = responseAddressCode;
	}

	public String getResponseHostAddressCode() {
		return responseHostAddressCode;
	}

	public void setResponseHostAddressCode(String responseHostAddressCode) {
		this.responseHostAddressCode = responseHostAddressCode;
	}

	public String getResponseCVVCode() {
		return responseCVVCode;
	}

	public void setResponseCVVCode(String responseCVVCode) {
		this.responseCVVCode = responseCVVCode;
	}

	public String getResponseCodeMessage() {
		return responseCodeMessage;
	}

	public void setResponseCodeMessage(String responseCodeMessage) {
		this.responseCodeMessage = responseCodeMessage;
	}

	public String getResponseHostCode() {
		return responseHostCode;
	}

	public void setResponseHostCode(String responseHostCode) {
		this.responseHostCode = responseHostCode;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getProcStatusMessage() {
		return procStatusMessage;
	}

	public void setProcStatusMessage(String procStatusMessage) {
		this.procStatusMessage = procStatusMessage;
	}

	public String getProfileStatusMessage() {
		return profileStatusMessage;
	}

	public void setProfileStatusMessage(String profileStatusMessage) {
		this.profileStatusMessage = profileStatusMessage;
	}

	public String getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}

	public String getCustomerRefNumber() {
		return customerRefNumber;
	}

	public void setCustomerRefNumber(String customerRefNumber) {
		this.customerRefNumber = customerRefNumber;
	}

	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(BigDecimal remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public String getRetryTrace() {
		return retryTrace;
	}

	public void setRetryTrace(String retryTrace) {
		this.retryTrace = retryTrace;
	}

	public int getRetryAttemptCount() {
		return retryAttemptCount;
	}

	public void setRetryAttemptCount(int retryAttemptCount) {
		this.retryAttemptCount = retryAttemptCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
	
}
