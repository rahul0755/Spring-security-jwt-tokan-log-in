package com.intellinum.ebs.inventory.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the xxfnd_user database table.
 * 
 */
@Entity
@Table(name = "xxfnd_user")
@NamedQuery(name = "XxfndUser.findAll", query = "SELECT x FROM XxfndUser x")
public class XxfndUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private int userId;

	@Column(name = "CREATED_BY")
	private int createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "CUSTOMER_ID")
	private String customerId;

	private String description;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "EMPLOYEE_ID")
	private int employeeId;

	@Column(name = "ENCRYPTED_FOUNDATION_PASSWORD")
	private String encryptedFoundationPassword;

	@Column(name = "ENCRYPTED_USER_PASSWORD")
	private String encryptedUserPassword;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE")
	private Date endDate;

	private String fax;

	@Column(name = "GCN_CODE_COMBINATION_ID")
	private String gcnCodeCombinationId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_LOGON_DATE")
	private Date lastLogonDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "LAST_UPDATE_LOGIN")
	private int lastUpdateLogin;

	@Column(name = "LAST_UPDATED_BY")
	private int lastUpdatedBy;

	@Column(name = "PASSWORD_ACCESSES_LEFT")
	private String passwordAccessesLeft;

	@Temporal(TemporalType.DATE)
	@Column(name = "PASSWORD_DATE")
	private Date passwordDate;

	@Column(name = "PASSWORD_LIFESPAN_ACCESSES")
	private Integer passwordLifespanAccesses;

	@Column(name = "PASSWORD_LIFESPAN_DAYS")
	private String passwordLifespanDays;

	@Column(name = "PERSON_PARTY_ID")
	private int personPartyId;

	@Column(name = "SECURITY_GROUP_ID")
	private Integer securityGroupId;

	@Column(name = "SESSION_NUMBER")
	private int sessionNumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "SUPPLIER_ID")
	private Integer supplierId;

	@Column(name = "USER_GUID")
	private String userGuid;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "WEB_PASSWORD")
	private String webPassword;

	public XxfndUser() {
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEncryptedFoundationPassword() {
		return this.encryptedFoundationPassword;
	}

	public void setEncryptedFoundationPassword(String encryptedFoundationPassword) {
		this.encryptedFoundationPassword = encryptedFoundationPassword;
	}

	public String getEncryptedUserPassword() {
		return this.encryptedUserPassword;
	}

	public void setEncryptedUserPassword(String encryptedUserPassword) {
		this.encryptedUserPassword = encryptedUserPassword;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getGcnCodeCombinationId() {
		return gcnCodeCombinationId;
	}

	public void setGcnCodeCombinationId(String gcnCodeCombinationId) {
		this.gcnCodeCombinationId = gcnCodeCombinationId;
	}

	public Date getLastLogonDate() {
		return this.lastLogonDate;
	}

	public void setLastLogonDate(Date lastLogonDate) {
		this.lastLogonDate = lastLogonDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(int lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public int getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getPasswordDate() {
		return this.passwordDate;
	}

	public void setPasswordDate(Date passwordDate) {
		this.passwordDate = passwordDate;
	}

	

	public String getPasswordAccessesLeft() {
		return passwordAccessesLeft;
	}

	public void setPasswordAccessesLeft(String passwordAccessesLeft) {
		this.passwordAccessesLeft = passwordAccessesLeft;
	}

	public String getPasswordLifespanDays() {
		return passwordLifespanDays;
	}

	public void setPasswordLifespanDays(String passwordLifespanDays) {
		this.passwordLifespanDays = passwordLifespanDays;
	}

	public int getPersonPartyId() {
		return this.personPartyId;
	}

	public void setPersonPartyId(int personPartyId) {
		this.personPartyId = personPartyId;
	}

	public int getSecurityGroupId() {
		return this.securityGroupId;
	}

	public void setSecurityGroupId(int securityGroupId) {
		this.securityGroupId = securityGroupId;
	}

	public int getSessionNumber() {
		return this.sessionNumber;
	}

	public void setSessionNumber(int sessionNumber) {
		this.sessionNumber = sessionNumber;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getUserGuid() {
		return this.userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWebPassword() {
		return this.webPassword;
	}

	public void setWebPassword(String webPassword) {
		this.webPassword = webPassword;
	}

}