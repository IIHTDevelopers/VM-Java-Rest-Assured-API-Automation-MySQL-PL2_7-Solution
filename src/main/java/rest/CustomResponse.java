package rest;

import java.util.List;
import java.util.Map;

import io.restassured.response.Response;

public class CustomResponse {
	private Response response;
	private List<Map<String, Object>> listResults;
	private String resultMessage;
	private Map<String, Object> mapResults;
	private List<Object> genericNames;
	private Object storeId;
	private Object category;
	private Object isActive;
	private List<Object> itemIds;
	private List<Object> itemNames;
	private int statusCode;
	private String pimEmployeeId;
	private String status;
	private List<Object> counts;
	private int empCount;
	private List<Object> deletes;
	private List<Object> situationals;
	private List<Object> props;
	private List<Object> sizes;
	private List<Object> pins;
	private List<Object> cellProperties;
	private List<Object> deleted;
	private Map<String, Object> workWeekData;
	
	private List<Map<String, Object>> leavePeriods;
	private Map<String, Object> meta;
	private List<Object> rels;
	private List<Object> startDates;
	private List<Object> endDates;
		
	private List<Object> leaveTypeNames;

	@SuppressWarnings("unused")
	private List<Object> Ids;

	private Object data;

	private List<Object> ids;
	private List<Object> names;

	private List<Object> dates;
	private List<Object> recurrings;
	private List<Object> lengths;
	private List<Object> lengthNames;
	public List<Object> unitIds;
	public List<Object> descriptions;
	public List<Object> level;
	public List<Object> left;
	public List<Object> right;
	public List<Object> employeeNumbers;
	public List<Object> lastNames;
	public List<Object> firstNames;
	public List<Object> employeeIds;
	public List<Object> employeeId;
	public List<Object> enable;
	public List<Object> hostname;
	public List<Object> port;
	public List<Object> encryption;
	public List<Object> ldapImplementation;
	public List<Object> bindAnonymously;
	public List<Object> bindUserDN;
	public List<Object> hasBindUserPassword;
	public List<Object> userLookupSettings;
	public List<Object> dataMapping;
	public List<Object> mergeLDAPUsersWithExistingSystemUsers;
	public List<Object> syncInterval;
	public List<Object> fieldName;
	public List<Object> fieldType;
	public List<Object> extraData;
	public List<Object> screen;
	private Object name;
	private Object lastName;
	@SuppressWarnings("unused")
	private Object id;



	public CustomResponse(Response response, int statusCode, String status, Object id, Object name) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.setId(id);
		this.name = name;
	}

	public CustomResponse(Response response, int statusCode, String status, Object id, Object name, Object lastName) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.setId(id);
		this.name = name;
		this.lastName = lastName;
	}

	public CustomResponse(Response response, int statusCode, String status, Object data) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.data = data;
	}
	
	

	public CustomResponse(Response response, int statusCode, String status, int empCount) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.empCount = empCount;
	}

	public CustomResponse(Response response, int statusCode, String status,
                      List<Object> employeeNumbers,
                      List<Object> lastNames,
                      List<Object> firstNames,
                      List<Object> employeeId) {

    this.response = response;
    this.statusCode = statusCode;
    this.status = status;

    this.employeeNumbers = employeeNumbers;
    this.lastNames = lastNames;
    this.firstNames = firstNames;
    this.employeeId = employeeId;
}


	// Getters

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public CustomResponse(Response response, int statusCode, String status, Map<String, Object> mapResults) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.mapResults = mapResults;
	}

	public CustomResponse(Response response, int statusCode, String status, String resultMessage) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.resultMessage = resultMessage;
	}

	public CustomResponse(Response response, int statusCode, String status, List<Map<String, Object>> listResults) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.listResults = listResults;
	}

	public CustomResponse(Response response, int statusCode, String status, List<Object> itemIds,
			List<Object> itemNames, List<Object> genericNames) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.itemIds = itemIds;
		this.itemNames = itemNames;
		this.genericNames = genericNames;
	}

	

	public CustomResponse(Response response, int statusCode, String status, List<Object> ids, List<Object> names,
			List<Object> dates, List<Object> recurrings, List<Object> lengths, List<Object> lengthNames) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.ids = ids;
		this.names = names;
		this.dates = dates;
		this.recurrings = recurrings;
		this.lengths = lengths;
		this.lengthNames = lengthNames;
	}

	public CustomResponse(Response response, int statusCode, String status, List<Object> ids, List<Object> names) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.ids = ids;
		this.names = names;
	}

	public CustomResponse(Response response, int statusCode, String status, List<Object> ids, List<Object> names,
			List<Object> unitIds, List<Object> descriptions, List<Object> level, List<Object> left,
			List<Object> right) {
		this.response = response;
		this.statusCode = statusCode;
		this.status = status;
		this.ids = ids;
		this.names = names;
		this.unitIds = unitIds;
		this.descriptions = descriptions;
		this.level = level;
		this.left = left;
		this.right = right;
	}

	public List<Object> getEmployeeNumbers() {
		return employeeNumbers;
	}

	public void setEmployeeNumbers(List<Object> employeeNumbers) {
		this.employeeNumbers = employeeNumbers;
	}

	public List<Object> getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(List<Object> firstNames) {
		this.firstNames = firstNames;
	}

	public List<Object> getLastNames() {
		return lastNames;
	}

	public void setLastNames(List<Object> lastNames) {
		this.lastNames = lastNames;
	}

	public void setEmployeeIds(List<Object> employeeIds) {
    this.employeeIds = employeeIds;
}

public void setWorkWeekData(Map<String, Object> workWeekData) {
    this.workWeekData = workWeekData;
}

public CustomResponse(Response response, int statusCode, String status,
                      List<Map<String, Object>> leavePeriods,
                      Map<String, Object> meta,
                      List<Object> rels,
                      List<Object> startDates,
                      List<Object> endDates) {
    this.response = response;
    this.statusCode = statusCode;
    this.status = status;
    this.leavePeriods = leavePeriods;
    this.meta = meta;
    this.rels = rels;
    this.startDates = startDates;
    this.endDates = endDates;
}

public List<Map<String, Object>> getLeavePeriods() {
    return leavePeriods;
}

public Map<String, Object> getMeta() {
    return meta;
}

public List<Object> getRels() {
    return rels;
}

public List<Object> getStartDates() {
    return startDates;
}

public List<Object> getEndDates() {
    return endDates;
}

public Map<String, Object> getWorkWeekData() {
    return workWeekData;
}

private String rawResponse;

public String getRawResponse() {
        return rawResponse;
    }

	private Map<String, Object> responseMap;

	public Map<String, Object> getResponseMap() {
        return responseMap;
    }

	public void setResponseMap(Map<String, Object> responseMap) {
        this.responseMap = responseMap;
    }

public void setLeaveTypeNames(List<Object> leaveTypeNames) {
    this.leaveTypeNames = leaveTypeNames;
}

public List<Object> getEmployeeIds() {
    return employeeIds;
}

public List<Object> getLeaveTypeNames() {
    return leaveTypeNames;
}






	public Object getId() {
		return id;
	}

	public Object getName() {
		return name;
	}

	public Object getLastName() {
		return lastName;
	}

// Getters and Setters
	public List<Object> getCounts() {
		return counts;
	}

	public List<Object> setCounts(List<Object> counts) {
		return this.counts = counts;
	}

	public int setEmpCount(int count) {
		return this.empCount = count;
	}

	public int getEmpCount() {
		return this.empCount;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getEmployeeId() {
		return pimEmployeeId;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getIds() {
		return ids;
	}

	public List<Object> getDeleted() {
		return deleted;
	}

	public void setIds(List<Object> ids) {
		this.ids = ids;
	}

	public List<Object> getNames() {
		return names;
	}

	public void setNames(List<Object> names) {
		this.names = names;
	}

	public List<Object> getDates() {
		return dates;
	}

	public void setDates(List<Object> dates) {
		this.dates = dates;
	}

	public List<Object> getRecurrings() {
		return recurrings;
	}

	public void setRecurrings(List<Object> recurrings) {
		this.recurrings = recurrings;
	}

	public List<Object> getLengths() {
		return lengths;
	}

	public void setLengths(List<Object> lengths) {
		this.lengths = lengths;
	}

	public List<Object> getLengthNames() {
		return lengthNames;
	}

	public void setLengthNames(List<Object> lengthNames) {
		this.lengthNames = lengthNames;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<Map<String, Object>> getListResults() {
		return listResults;
	}

	public void setListResults(List<Map<String, Object>> listResults) {
		this.listResults = listResults;
	}

	public Map<String, Object> getMapResults() {
		return mapResults;
	}

	public void setMapResults(Map<String, Object> mapResults) {
		this.mapResults = mapResults;
	}

	public Object getStoreId() {
		return storeId;
	}

	public void setStoreId(Object storeId) {
		this.storeId = storeId;
	}

	public Object getCategory() {
		return category;
	}

	public void setCategory(Object category) {
		this.category = category;
	}

	public Object getIsActive() {
		return isActive;
	}

	public void setIsActive(Object isActive) {
		this.isActive = isActive;
	}

	public List<Object> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Object> itemIds) {
		this.itemIds = itemIds;
	}

	// Getter and Setter for itemNames
	public List<Object> getItemNames() {
		return itemNames;
	}

	public void setItemNames(List<Object> itemNames) {
		this.itemNames = itemNames;
	}

	// Getter and Setter for genericNames
	public List<Object> getGenericNames() {
		return genericNames;
	}

	public void setGenericNames(List<Object> genericNames) {
		this.genericNames = genericNames;
	}
	

	public void setSituationals(List<Object> situationals) {
		this.situationals = situationals;
	}

	public void setPimEmployeeId(String pimEmployeeId) {
		this.pimEmployeeId = pimEmployeeId;
	}

	public List<Object> getSituationals() {
		return situationals;
	}

	public void setDeletes(List<Object> deletes) {
		this.deletes = deletes;
	}

	public void setDeleted(List<Object> deleted) {
		this.deleted = deleted;
	}

	public List<Object> getDeletes() {
		return deletes;
	}

	public List<Object> getProps() {
		return props;
	}

	public void setProps(List<Object> props) {
		this.props = props;
	}

	public List<Object> getSizes() {
		return sizes;
	}

	public void setSizes(List<Object> sizes) {
		this.sizes = sizes;
	}

	public List<Object> getPins() {
		return pins;
	}

	public void setPins(List<Object> pins) {
		this.pins = pins;
	}

	public List<Object> getCellProperties() {
		return cellProperties;
	}

	public void setCellProperties(List<Object> cellProperties) {
		this.cellProperties = cellProperties;
	}

	public void setId(Object id) {
		this.id = id;
	}

	// ✅ Add these getters

}
