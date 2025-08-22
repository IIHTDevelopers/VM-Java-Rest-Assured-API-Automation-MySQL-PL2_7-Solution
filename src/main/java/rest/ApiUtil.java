package rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

public class ApiUtil {
	private static String BASE_URL;
	Properties prop;

	/**
	 * Retrieves the base URL from the configuration properties file.
	 *
	 * <p>
	 * This method loads the properties from the file located at
	 * <code>{user.dir}/src/main/resources/config.properties</code> and extracts the
	 * value associated with the key <code>base.url</code>. The value is stored in
	 * the static variable <code>BASE_URL</code> and returned.
	 *
	 * @return the base URL string if successfully read from the properties file;
	 *         {@code null} if an I/O error occurs while reading the file.
	 */
	public String getBaseUrl() {
		prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties")) {
			prop.load(fis);
			BASE_URL = prop.getProperty("base.url");
			return BASE_URL;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves the username from the configuration properties file.
	 *
	 * <p>
	 * This method reads the properties from the file located at
	 * <code>{user.dir}/src/main/resources/config.properties</code> and returns the
	 * value associated with the key <code>username</code>.
	 *
	 * @return the username as a {@code String} if found in the properties file;
	 *         {@code null} if an I/O error occurs while reading the file.
	 */
	public String getUsername() {
		prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties")) {
			prop.load(fis);
			return prop.getProperty("username");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPassword() {
		prop = new Properties();
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties")) {
			prop.load(fis);
			return prop.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Test1 - This method sends a GET request to fetch a list of employment status
	 *        entries from the system.
	 * 
	 * @description This method constructs and executes a GET API call to the
	 *              provided endpoint. It uses a session cookie ("orangehrm") for
	 *              authentication and optionally includes a request body (though
	 *              GET typically does not require one). The method extracts the
	 *              HTTP status, response content, and specifically parses the "id"
	 *              and "name" fields from the "data" array in the response. These
	 *              values are returned encapsulated in a CustomResponse object.
	 *
	 * @param endpoint    - The specific API endpoint to hit (relative to the base
	 *                    URL).
	 * @param cookieValue - The session cookie value to authenticate the request.
	 * @param body        - Optional request body as a Map (can be null since GET
	 *                    does not typically include a body).
	 *
	 * @return CustomResponse - A custom response object containing the full HTTP
	 *         response, status code, status line, and lists of extracted IDs and
	 *         names from the response "data" array.
	 */

	public CustomResponse GetEmpStatus(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		if (body != null) {
			request.body(body);
		}

		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		int statusCode = response.getStatusCode();
		String status = response.getStatusLine();

		List<Object> ids = new ArrayList<>();
		List<Object> names = new ArrayList<>();

		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> data = jsonPath.getList("data");

		if (data != null) {
			for (Map<String, Object> holiday : data) {
				ids.add(holiday.get("id"));
				names.add(holiday.get("name"));
			}
		} else {
			System.out.println("⚠️ 'data' field is null in response. Status code: " + statusCode);
		}

		return new CustomResponse(response, statusCode, status, ids, names);
	}

	/**
	 * @Test2 - This method sends a GET request to retrieve job title information
	 *        from the system.
	 * 
	 * @description This method constructs and executes a GET API call to the
	 *              specified endpoint. It uses a session cookie ("orangehrm") for
	 *              authentication and can optionally include a request body (though
	 *              not typical for GET). The response is parsed to extract the HTTP
	 *              status, and from the "data" array, it collects "id" and "title"
	 *              fields. These are returned encapsulated in a CustomResponse
	 *              object for further validation or use.
	 *
	 * @param endpoint    - The specific API endpoint to hit (relative to the base
	 *                    URL).
	 * @param cookieValue - The session cookie value to authenticate the request.
	 * @param body        - Optional request body as a Map (can be null since GET
	 *                    does not typically include a body).
	 *
	 * @return CustomResponse - A custom response object containing the full HTTP
	 *         response, status code, status line, and lists of extracted job title
	 *         IDs and titles from the response "data" array.
	 */
	public CustomResponse GetJobTitle(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		if (body != null) {
			request.body(body);
		}

		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		int statusCode = response.getStatusCode();
		String status = response.getStatusLine();

		List<Object> ids = new ArrayList<>();
		List<Object> titles = new ArrayList<>();

		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> data = jsonPath.getList("data");

		if (data != null) {
			for (Map<String, Object> holiday : data) {
				ids.add(holiday.get("id"));
				titles.add(holiday.get("title"));
			}
		} else {
			System.out.println("⚠️ 'data' field is null in response. Status code: " + statusCode);
		}

		return new CustomResponse(response, statusCode, status, ids, titles);
	}

	/**
	 * @Test3 - This method sends a GET request to fetch administrative subunit data
	 *        from the system.
	 * 
	 * @description This method constructs and executes a GET API call to the
	 *              specified endpoint. It uses a session cookie ("orangehrm") for
	 *              authentication and optionally includes a request body (though
	 *              GET typically does not require one). The method parses the
	 *              response to extract HTTP status details and subunit-related
	 *              fields such as "id", "title", "unitId", "description", "level",
	 *              "left", and "right" from the "data" array. These are returned in
	 *              a CustomResponse object for validation or further processing.
	 *
	 * @param endpoint    - The specific API endpoint to hit (relative to the base
	 *                    URL).
	 * @param cookieValue - The session cookie value to authenticate the request.
	 * @param body        - Optional request body as a Map (can be null since GET
	 *                    does not typically include a body).
	 *
	 * @return CustomResponse - A custom response object containing the full HTTP
	 *         response, status code, status line, and lists of extracted subunit
	 *         attributes including IDs, titles, unit IDs, descriptions, levels,
	 *         left, and right values.
	 */
	public CustomResponse GetAdminSubunit(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		if (body != null) {
			request.body(body);
		}

		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		int statusCode = response.getStatusCode();
		String status = response.getStatusLine();

		List<Object> ids = new ArrayList<>();
		List<Object> names = new ArrayList<>();
		List<Object> unitIds = new ArrayList<>();
		List<Object> descriptions = new ArrayList<>();
		List<Object> level = new ArrayList<>();
		List<Object> left = new ArrayList<>();
		List<Object> right = new ArrayList<>();

		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> data = jsonPath.getList("data");

		if (data != null) {
			for (Map<String, Object> holiday : data) {
				ids.add(holiday.get("id"));
				names.add(holiday.get("title"));
				unitIds.add(holiday.get("unitId"));
				descriptions.add(holiday.get("description"));
				level.add(holiday.get("level"));
				left.add(holiday.get("left"));
				right.add(holiday.get("right"));
			}
		} else {
			System.out.println("⚠️ 'data' field is null in response. Status code: " + statusCode);
		}

		return new CustomResponse(response, statusCode, status, ids, names, unitIds, descriptions, level, left, right);
	}

	/**
	 * @Test4 - This method sends a GET request to retrieve employee data from the
	 *        PIM (Personal Information Management) module.
	 * 
	 * @description This method constructs and executes a GET API call to the
	 *              specified endpoint using a session cookie ("orangehrm") for
	 *              authentication. It can optionally include a request body,
	 *              although GET requests typically do not require one. The response
	 *              is parsed to extract the HTTP status and employee-related fields
	 *              from the "data" array, including "empNumber", "lastName",
	 *              "firstName", and "employeeId". These extracted values are
	 *              returned in a CustomResponse object for further validation or
	 *              usage.
	 *
	 * @param endpoint    - The specific API endpoint to hit (relative to the base
	 *                    URL).
	 * @param cookieValue - The session cookie value to authenticate the request.
	 * @param body        - Optional request body as a Map (can be null since GET
	 *                    does not typically include a body).
	 *
	 * @return CustomResponse - A custom response object containing the full HTTP
	 *         response, status code, status line, and lists of employee numbers,
	 *         last names, first names, and employee IDs extracted from the "data"
	 *         array.
	 */
	public CustomResponse GetPimEmp(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		if (body != null) {
			request.body(body);
		}

		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		int statusCode = response.getStatusCode();
		String status = response.getStatusLine();

		List<Object> employeeNumbers = new ArrayList<>();
		List<Object> lastNames = new ArrayList<>();
		List<Object> firstNames = new ArrayList<>();
		List<Object> employeeId = new ArrayList<>();

		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> data = jsonPath.getList("data");

		if (data != null) {
			for (Map<String, Object> holiday : data) {
				employeeNumbers.add(holiday.get("empNumber"));
				lastNames.add(holiday.get("lastName"));
				firstNames.add(holiday.get("firstName"));
				employeeId.add(holiday.get("employeeId"));
			}
		} else {
			System.out.println("⚠️ 'data' field is null in response. Status code: " + statusCode);
		}

		return new CustomResponse(response, statusCode, status, employeeNumbers, lastNames, firstNames, employeeId);
	}

	/**
	 * @Test5 - This method sends a GET request to retrieve report data in ascending
	 *        order from the system.
	 * 
	 * @description This method constructs and executes a GET API call to the
	 *              specified endpoint using a session cookie ("orangehrm") for
	 *              authentication. It optionally includes a request body, although
	 *              this is uncommon for GET requests. The response is parsed to
	 *              extract the HTTP status and report-related fields from the
	 *              "data" array, specifically the "id" and "name" of each item.
	 *              These values are returned in a CustomResponse object for
	 *              validation or further use.
	 *
	 * @param endpoint    - The specific API endpoint to hit (relative to the base
	 *                    URL).
	 * @param cookieValue - The session cookie value to authenticate the request.
	 * @param body        - Optional request body as a Map (can be null since GET
	 *                    does not typically include a body).
	 *
	 * @return CustomResponse - A custom response object containing the full HTTP
	 *         response, status code, status line, and lists of IDs and names
	 *         extracted from the response "data" array.
	 */
	public CustomResponse GetReportASC(String endpoint, String cookieValue, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().cookie("orangehrm", cookieValue).header("Content-Type",
				"application/json");

		if (body != null) {
			request.body(body);
		}

		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		int statusCode = response.getStatusCode();
		String status = response.getStatusLine();

		List<Object> ids = new ArrayList<>();
		List<Object> names = new ArrayList<>();

		JsonPath jsonPath = response.jsonPath();
		List<Map<String, Object>> data = jsonPath.getList("data");

		if (data != null) {
			for (Map<String, Object> holiday : data) {
				ids.add(holiday.get("id"));
				names.add(holiday.get("name"));
			}
		} else {
			System.out.println("⚠️ 'data' field is null in response. Status code: " + statusCode);
		}

		return new CustomResponse(response, statusCode, status, ids, names);
	}
	
	/**
 * Method: GetLeaveTypesEligible
 *
 * Purpose:
 * Sends a GET request to fetch all eligible leave types for an employee
 * and parses the response into a CustomResponse object.
 *
 * Steps:
 * 1. Construct the full URL by appending the endpoint to the base URL.
 * 2. Send a GET request with the provided cookie and 'Accept: application/json' header.
 * 3. Extract the response and capture the HTTP status code and status line.
 * 4. Parse the JSON response to extract lists of:
 *    - IDs of leave types
 *    - Names of leave types
 *    - Deleted flags for leave types
 * 5. Create a CustomResponse object using the extracted lists and HTTP response details.
 * 6. Explicitly set IDs, names, and deleted flags using setters to ensure consistency.
 * 7. Return the populated CustomResponse object.
 *
 * Notes:
 * - This method does not require a request body (body parameter can be null).
 * - Ensures the response data is safely mapped to CustomResponse fields for further validation in tests.
 */


	public CustomResponse GetLeaveTypesEligible(String endpoint, String cookieValue, String body) {
    String url = BASE_URL + endpoint;

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
        .when()
            .get(url)
        .then()
            .extract().response();

    int statusCode = response.getStatusCode();
    String status = response.getStatusLine();

    // Parse response JSON
    JsonPath jsonPath = response.jsonPath();
    List<Object> ids = jsonPath.getList("data.id");
    List<Object> names = jsonPath.getList("data.name");
    List<Object> deleted = jsonPath.getList("data.deleted");

    // Use your existing constructor (ids + names + genericNames)
    CustomResponse customResponse = new CustomResponse(response, statusCode, status, ids, names, deleted);

    // Explicitly set deletes if you have a setter
	customResponse.setIds(ids);
	customResponse.setNames(names);
    customResponse.setDeleted(deleted);

    return customResponse;
}

/**
 * Method: GetLeaveRequests
 *
 * Purpose:
 * Sends a GET request to retrieve leave requests for current employees and
 * maps the response JSON into a CustomResponse object for test validation.
 *
 * Steps:
 * 1. Construct the full URL by combining the BASE_URL with the provided endpoint.
 * 2. Send a GET request with the provided cookie for authentication.
 * 3. Extract the HTTP status code and status line from the response.
 * 4. Parse the JSON response to extract relevant fields, including:
 *    - Leave request IDs
 *    - Employee numbers
 *    - Employee first names
 *    - Employee last names
 *    - Employee IDs
 *    - Leave type names
 *    - Deleted flags for leave types
 * 5. Create a CustomResponse object with the extracted data.
 * 6. Explicitly set the parsed lists into the CustomResponse object using setters.
 * 7. Return the populated CustomResponse object for assertions in test methods.
 *
 * Notes:
 * - No request body is required for this GET request (body parameter is ignored).
 * - This method ensures that all important leave request and employee data is captured
 *   in a structured format for further validation.
 */


public CustomResponse GetLeaveRequests(String endpoint, String cookieValue, String body) {
    String url = BASE_URL + endpoint;

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
        .when()
            .get(url)
        .then()
            .extract().response();

    int statusCode = response.getStatusCode();
    String status = response.getStatusLine();

    JsonPath jsonPath = response.jsonPath();

    List<Object> ids = jsonPath.getList("data.id");
    List<Object> empNumbers = jsonPath.getList("data.employee.empNumber");
    List<Object> firstNames = jsonPath.getList("data.employee.firstName");
    List<Object> lastNames = jsonPath.getList("data.employee.lastName");
    List<Object> employeeIds = jsonPath.getList("data.employee.employeeId");
    List<Object> leaveTypeNames = jsonPath.getList("data.leaveType.name");
    List<Object> deletedFlags = jsonPath.getList("data.leaveType.deleted");

    // Create CustomResponse with basic constructor
    CustomResponse customResponse = new CustomResponse(response, statusCode, status, ids, empNumbers, firstNames, lastNames, employeeIds, leaveTypeNames, deletedFlags);

    // Explicitly set lists using setters
    customResponse.setIds(ids);
    customResponse.setEmployeeNumbers(empNumbers);
    customResponse.setFirstNames(firstNames);
    customResponse.setLastNames(lastNames);
    customResponse.setEmployeeIds(employeeIds);
    customResponse.setLeaveTypeNames(leaveTypeNames);
    customResponse.setDeleted(deletedFlags);

    return customResponse;
}

/**
 * Method: GetLeavePeriods
 *
 * Purpose:
 * Sends a GET request to the leave periods endpoint and parses the response
 * into a CustomResponse object containing leave period details, meta information,
 * relationships, and date lists.
 *
 * Steps:
 * 1. Construct the full URL using BASE_URL and the endpoint.
 * 2. Send a GET request with the provided cookie for authentication.
 * 3. Extract the JSON response.
 * 4. Parse the response to retrieve:
 *    - The full list of leave periods (data array)
 *    - Meta information (meta object)
 *    - Relationships (rels array)
 *    - Start dates (data.startDate)
 *    - End dates (data.endDate)
 * 5. Wrap all extracted data in a CustomResponse object and return it.
 *
 * Notes:
 * - No request body is required for this GET request.
 * - The method ensures all key fields from the leave periods API response
 *   are available for further validation in tests.
 */

public CustomResponse GetLeavePeriods(String endpoint, String cookieValue, String requestBody) {
    String url = BASE_URL + endpoint;

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
        .when()
            .get(url)
        .then()
            .extract().response();

    // Parse JSON
    JsonPath jsonPath = response.jsonPath();

    // Extract leave periods (data array)
    List<Map<String, Object>> leavePeriods = jsonPath.getList("data");

    // Extract meta info
    Map<String, Object> meta = jsonPath.getMap("meta");

    // Extract rels (array)
    List<Object> rels = jsonPath.getList("rels");

    // Extract startDates and endDates separately
    List<Object> startDates = jsonPath.getList("data.startDate");
    List<Object> endDates   = jsonPath.getList("data.endDate");

    // Return wrapped in CustomResponse
    return new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            leavePeriods,   
            meta,                       
			rels,                       
			startDates,     
            endDates        
    );
}

/**
 * Method: GetWorkWeek
 *
 * Purpose:
 * Sends a GET request to the leave workweek endpoint and parses the response
 * into a CustomResponse object for validation in tests.
 *
 * Steps:
 * 1. Construct the full URL by appending the endpoint to the base URL.
 * 2. Send a GET request with the provided cookie for authentication.
 * 3. Parse the JSON response using JsonPath.
 * 4. Extract the workweek data (mapping of days to hours) from the "data" field.
 * 5. Extract meta information from the "meta" field.
 * 6. Extract rels information from the "rels" field.
 * 7. Wrap all extracted data in a CustomResponse object.
 * 8. Set the workWeekData field in the CustomResponse for easier access in tests.
 *
 * Notes:
 * - The GET request does not require a request body.
 * - The returned CustomResponse contains the raw Response, status code, status line,
 *   and structured data for workweek, meta, and rels.
 */


public CustomResponse GetWorkWeek(String endpoint, String cookieValue, String requestBody) {
    String url = BASE_URL + endpoint;

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
        .when()
            .get(url)
        .then()
            .extract().response();

    // Parse JSON
    JsonPath jsonPath = response.jsonPath();

    // Extract data (workweek mapping of days to hours)
    Map<String, Object> workWeekData = jsonPath.getMap("data");

    // Extract meta
    List<Object> meta = jsonPath.getList("meta");

    // Extract rels
    List<Object> rels = jsonPath.getList("rels");

    // Wrap in CustomResponse
    CustomResponse customResponse = new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            workWeekData,   // store workweek mapping
            meta,           // meta list
            rels            // rels list
    );
	customResponse.setWorkWeekData(workWeekData);
	return customResponse;
}


/**
 * Method: GetWorkWeekdays
 *
 * Purpose:
 * Sends a GET request to the leave workweek endpoint with a query parameter 
 * (e.g., model=indexed) and parses the response into a CustomResponse object.
 *
 * Steps:
 * 1. Build the GET request with the provided endpoint, cookie, and query parameter.
 * 2. Send the request and extract the raw Response.
 * 3. Parse the JSON response to extract:
 *    - workWeekData: mapping of days to hours
 *    - meta: metadata list
 *    - rels: related links or objects
 * 4. Create a CustomResponse object and populate workWeekData.
 * 5. Return the populated CustomResponse for further validation in tests.
 *
 * Notes:
 * - Query parameter is used to filter or modify the returned data.
 * - workWeekData is stored in CustomResponse for easy access.
 */

public CustomResponse GetWorkWeekdays(String endpoint, String cookieValue, String paramKey, String paramValue) {
    String url = BASE_URL + endpoint;

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
            .queryParam(paramKey, paramValue)   // ✅ use query param, not header
        .when()
            .get(url)
        .then()
            .extract().response();

    // Parse JSON
    JsonPath jsonPath = response.jsonPath();

    // Extract data (workweek mapping of days to hours)
    Map<String, Object> workWeekData = jsonPath.getMap("data");
    List<Object> meta = jsonPath.getList("meta");
    List<Object> rels = jsonPath.getList("rels");

    CustomResponse customResponse = new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            workWeekData,
            meta,
            rels
    );
    customResponse.setWorkWeekData(workWeekData);
    return customResponse;
}

/**
 * Deletes one or more custom fields via the OrangeHRM API.
 *
 * @param endpoint    The API endpoint for custom fields (e.g., "/web/index.php/api/v2/pim/custom-fields")
 * @param requestBody JSON body containing the IDs to delete (e.g., {"ids": [1]})
 * @param cookieValue Session cookie for authentication
 * @return CustomResponse containing the HTTP response, status code, status line, and list of deleted IDs
 *
 * Steps:
 * 1. Send a DELETE request to the full endpoint with the provided JSON body.
 * 2. Log the request and response for debugging.
 * 3. Extract the "data" array from the response JSON, which contains deleted IDs.
 * 4. Wrap response details and deleted IDs into a CustomResponse object and return it.
 *
 * Notes:
 * - Ensures content-type is set to "application/json" for proper request handling.
 * - Logs both request and response for easier debugging of API failures.
 */

public CustomResponse DeleteCustomField(String endpoint, String requestBody, String cookieValue) {

    Response response = RestAssured.given()
            .log().all()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
            .contentType("application/json")   // ✅ FIXED: Send JSON
            .body(requestBody)                 // JSON body from test
        .when()
            .delete(BASE_URL + endpoint)
        .then()
            .log().all()
            .extract().response();

    JsonPath jsonPath = response.jsonPath();
    List<Object> data = jsonPath.getList("data");

    return new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            data
    );
}
/**
 * Method: AddReportingMethod
 *
 * Purpose:
 * Sends a POST request to create a new reporting method in OrangeHRM and wraps the response
 * into a CustomResponse object for further validation in tests.
 *
 * Steps:
 * 1. Construct the POST request with the provided endpoint, cookie, and JSON body.
 * 2. Set the "Content-Type" header to "application/json".
 * 3. Send the POST request to the specified endpoint.
 * 4. Extract the raw Response object from RestAssured.
 * 5. Wrap the response, status code, status line, and full JSON body into a CustomResponse object.
 * 6. Return the CustomResponse for test assertions.
 *
 * Notes:
 * - The full response is captured as a Map using jsonPath().getMap("$").
 * - Ensures that all response details are available for downstream validation.
 */
public CustomResponse AddReportingMethod(String requestBody, String cookieValue, String endpoint) {
    Response response = RestAssured.given()
            .baseUri("https://opensource-demo.orangehrmlive.com")
            .cookie("orangehrm", cookieValue)
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post(endpoint)
        .then()
            .extract()
            .response();

		String rawResponse = response.getBody().asString();

		CustomResponse customResponse = new CustomResponse(
				response,
				response.getStatusCode(),
				response.getStatusLine(),
				response.jsonPath().getMap("$"),
				rawResponse
		);
		customResponse.setResponseMap(response.jsonPath().getMap("$"));
		return customResponse;
}

public CustomResponse GetReportingMethods(String cookieValue) {
    String endpoint = "/web/index.php/api/v2/pim/reporting-methods";

    Response response = RestAssured.given()
            .baseUri("https://opensource-demo.orangehrmlive.com")
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
        .when()
            .get(endpoint)
        .then()
            .extract()
            .response();

    return new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
			response.jsonPath().getList("data.id") // IDs
    );
}

/**
 * Method: UpdateReportingMethod
 *
 * Purpose:
 * Sends a PUT request to update an existing reporting method in OrangeHRM and wraps the response.
 *
 * Steps:
 * 1. Construct the PUT request with the given endpoint, cookie, and JSON request body.
 * 2. Send the request and extract the raw Response object.
 * 3. Log or extract the updated reporting method ID from the response JSON.
 * 4. Wrap the HTTP response, status code, status line, and updated ID in a CustomResponse object.
 * 5. Return the populated CustomResponse for further validation in tests.
 *
 * Notes:
 * - Ensures that the reporting method is updated with the provided JSON body.
 * - The returned CustomResponse contains the updated reporting method ID for assertion.
 */

public CustomResponse UpdateReportingMethod(String requestBody, String cookieValue, String endpoint) {
    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(requestBody)
        .when()
            .put(BASE_URL + endpoint)
        .then()
            .extract()
            .response();

    // Extract the id from the response JSON
    int updatedId = response.jsonPath().getInt("data.id");
    System.out.println("Updated Reporting Method ID: " + updatedId);

    // Wrap in CustomResponse
    CustomResponse customResponse = new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            updatedId // store the updated id directly
    );

    return customResponse;
}

/**
 * Method: DeleteReportingMethod
 *
 * Purpose:
 * Sends a DELETE request to remove one or more reporting methods from OrangeHRM.
 *
 * Steps:
 * 1. Build the DELETE request with the provided endpoint, cookie, and request body (containing IDs to delete).
 * 2. Send the request and extract the raw Response.
 * 3. Wrap the response in a CustomResponse object.
 * 4. Extract the list of deleted IDs from the response JSON and store in CustomResponse.
 *
 * Notes:
 * - Assumes the request body contains a JSON object with an "ids" array.
 * - Returns the HTTP status code, status line, and deleted IDs for validation.
 */

public CustomResponse DeleteReportingMethod(String requestBody, String cookieValue, String endpoint) {
    // Send DELETE request
    Response response = RestAssured.given()
            .baseUri("https://opensource-demo.orangehrmlive.com")
            .cookie("orangehrm", cookieValue)
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .delete(endpoint)
        .then()
            .extract()
            .response();

    // Safe raw response (never null)
    String rawResponse = response.getBody() != null ? response.getBody().asString() : "";

    // Safe response map: empty if body is null/empty or JSON parsing fails
    Map<String, Object> responseMap;
    try {
        responseMap = rawResponse.isEmpty() ? new HashMap<>() : response.jsonPath().getMap("$");
    } catch (Exception e) {
        responseMap = new HashMap<>();
    }

    // Construct CustomResponse
    CustomResponse customResponse = new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            responseMap,
            rawResponse
    );

    return customResponse;
}


/**
 * API Method: AddTerminationReason
 *
 * Purpose:
 * Sends a POST request to create a new termination reason in OrangeHRM and wraps the response in a CustomResponse object.
 *
 * Steps:
 * 1. Construct the full URL by appending the endpoint to BASE_URL.
 * 2. Send a POST request with:
 *    - Cookie for authentication
 *    - Headers for Accept and Content-Type as 'application/json'
 *    - Request body containing the termination reason details
 * 3. Extract the raw Response from RestAssured.
 * 4. Parse the JSON response to retrieve the 'data' field, which contains the created object or ID.
 * 5. Wrap the response, status code, status line, and extracted data into a CustomResponse object.
 *
 * Notes:
 * - Logs the request and response for debugging purposes.
 * - The 'data' field can be an integer, string, or map depending on API behavior.
 * - Ensures standardized response handling for downstream test assertions.
 */

 
public CustomResponse AddTerminationReason(String requestBody, String cookieValue, String endpoint) {
    // Construct full URL
    String url = BASE_URL + endpoint;

    // Send POST request
    Response response = RestAssured.given()
            .log().all()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post(url)
        .then()
            .log().all()
            .extract().response();

    // Parse JSON response
    JsonPath jsonPath = response.jsonPath();

    // Extract data (ID or created object info)
    Object data = jsonPath.get("data");  // can be int, string, or map depending on API response

    // Wrap in CustomResponse
    CustomResponse customResponse = new CustomResponse(
            response,
            response.getStatusCode(),
            response.getStatusLine(),
            data
    );

    return customResponse;
}






	

}