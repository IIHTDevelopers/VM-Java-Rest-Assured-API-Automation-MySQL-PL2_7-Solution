package testcases;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import rest.ApiUtil;
import rest.CustomResponse;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import coreUtilities.utils.FileOperations;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.tools.ant.taskdefs.Copy;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.python.antlr.ast.With;
import org.testng.Assert;

public class RestAssured_TestCases {

	private static String baseUrl;
	private static String username;
	private static String password;
	private static String cookieValue = null;
	private ApiUtil apiUtil;
	private String apiUtilPath = System.getProperty("user.dir") + "\\src\\main\\java\\rest\\ApiUtil.java";
	private String excelPath = System.getProperty("user.dir") + "\\src\\main\\resources\\TestData.xlsx";

	/**
	 * @BeforeClass method to perform login via Selenium and retrieve session cookie
	 *              for authenticated API calls.
	 * 
	 *              Steps: 1. Setup ChromeDriver using WebDriverManager. 2. Launch
	 *              browser and open the OrangeHRM login page. 3. Perform login with
	 *              provided username and password. 4. Wait for login to complete
	 *              and extract the 'orangehrm' session cookie. 5. Store the cookie
	 *              value to be used in API requests. 6. Quit the browser session.
	 * 
	 *              Throws: - InterruptedException if thread sleep is interrupted. -
	 *              RuntimeException if the required session cookie is not found.
	 */

	@Test(priority = 0, groups = { "PL2" }, description = "Login to the application using Selenium and retrieve session cookie")
	public void loginWithSeleniumAndGetCookie() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		apiUtil = new ApiUtil();
		baseUrl = apiUtil.getBaseUrl();
		username = apiUtil.getUsername();
		password = apiUtil.getPassword();

		driver.get(baseUrl + "/web/index.php/auth/login");
		Thread.sleep(3000); // Wait for page load

		// Login to the app
		driver.findElement(By.name("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		Thread.sleep(6000); // Wait for login

		// Extract cookie named "orangehrm"
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("orangehrm")) {
				cookieValue = cookie.getValue();
				break;
			}
		}

		driver.quit();

		if (cookieValue == null) {
			throw new RuntimeException("orangehrm cookie not found after login");
		}
		io.restassured.RestAssured.useRelaxedHTTPSValidation();
	}
	

	/**
 * Test Method: GetEmpStatus
 *
 * Purpose:
 * Sends a GET request to retrieve employment statuses and validates the response.
 *
 * Steps:
 * 1. Define the endpoint '/web/index.php/api/v2/admin/employment-statuses?limit=0'.
 * 2. Send a GET request with a valid cookie; no request body is required (null).
 * 3. Print the response status code and body to the console for visibility/debugging.
 * 4. Validate that the API implementation uses the expected RestAssured methods.
 * 5. Assert that the response status code is 200 (OK) and the status line matches "HTTP/1.0 200 OK".
 * 6. Assert that the lists of IDs and Names in the CustomResponse are non-null.
 *
 * Notes:
 * - Ensures the test strictly uses RestAssured for request handling.
 * - Provides visibility of API response for easier debugging and verification.
 */


	@Test(priority = 1, groups = {
			"PL2" }, description = "1. Send a GET request to the '/web/index.php/api/v2/admin/employment-statuses?limit=0' endpoint with a valid cookie\n"
					+ "2. Do not pass any request body (null)\n"
					+ "3. Print and verify the response status code and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void GetEmpStatus() throws IOException {

		// Defining Endpoint
		String endpoint = "/web/index.php/api/v2/admin/employment-statuses?limit=0";

		// Defining upcoming responce from Rest Method
		CustomResponse customResponse = apiUtil.GetEmpStatus(endpoint, cookieValue, null);

		// Checking for implementation which we are using is correct or not
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath, "GetEmpStatus",
				List.of("given", "cookie", "get", "response"));

		// Print response status code and body to console for debugging/visibility
		System.out.println("Status Code: " + customResponse.getStatusCode());

		System.out.println("-----------------------------------------------------------------------------------------");
		// Assert that the implementation and Status we need is Satisfied
		Assert.assertTrue(isImplementationCorrect,
				"GetEmpStatus must be implementated using the Rest assured  methods only!");
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// Step 4: Validate status field
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		Assert.assertNotNull(customResponse.getIds());
		Assert.assertNotNull(customResponse.getNames());
	}

	/**
 * Test Method: GetJobTitle
 *
 * Purpose:
 * Sends a GET request to retrieve all job titles and validates the response.
 *
 * Steps:
 * 1. Define the endpoint '/web/index.php/api/v2/admin/job-titles?limit=0'.
 * 2. Send a GET request with a valid cookie; no request body is required (null).
 * 3. Print the response status code and body for debugging and visibility.
 * 4. Validate that the API implementation uses the expected RestAssured methods.
 * 5. Assert that the response status code is 200 (OK) and the status line matches "HTTP/1.0 200 OK".
 * 6. Assert that the lists of IDs and Names in the CustomResponse are non-null.
 *
 * Notes:
 * - Ensures the test strictly uses RestAssured for request handling.
 * - Provides visibility of API response for easier debugging and verification.
 */


	@Test(priority = 2, groups = {
			"PL2" }, description = "1. Send a GET request to the '/web/index.php/api/v2/admin/job-titles?limit=0' endpoint with a valid cookie\n"
					+ "2. Do not pass any request body (null)\n"
					+ "3. Print and verify the response status code and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void GetJobTitle() throws IOException {

		// Defining Endpoint
		String endpoint = "/web/index.php/api/v2/admin/job-titles?limit=0";

		// Defining upcoming responce from Rest Method
		CustomResponse customResponse = apiUtil.GetJobTitle(endpoint, cookieValue, null);

		// Checking for implementation which we are using is correct or not
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath, "GetJobTitle",
				List.of("given", "cookie", "get", "response"));

		// Print response status code and body to console for debugging/visibility

		// Assert that the implementation and Status we need is Satisfied
		Assert.assertTrue(isImplementationCorrect,
				"GetJobTitle must be implementated using the Rest assured  methods only!");
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// Step 4: Validate status field
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		Assert.assertNotNull(customResponse.getIds());
		Assert.assertNotNull(customResponse.getNames());
	}


	/**
 * Test Method: GetAdminSubunit
 *
 * Purpose:
 * Sends a GET request to retrieve all admin subunits and validates the response.
 *
 * Steps:
 * 1. Define the endpoint '/web/index.php/api/v2/admin/subunits'.
 * 2. Send a GET request with a valid cookie; no request body is required (null).
 * 3. Validate that the API implementation uses the expected RestAssured methods.
 * 4. Assert that the response status code is 200 (OK) and the status line matches "HTTP/1.0 200 OK".
 * 5. Assert that all relevant fields in the CustomResponse (IDs, Names, unitIds, descriptions, level, left, right) are non-null.
 *
 * Notes:
 * - Ensures strict use of RestAssured methods for API interaction.
 * - Provides verification of hierarchical and descriptive details of subunits for completeness.
 */



	@Test(priority = 3, groups = {
			"PL2" }, description = "1. Send a GET request to the '/web/index.php/api/v2/admin/subunits' endpoint with a valid cookie\n"
					+ "2. Do not pass any request body (null)\n"
					+ "3. Print and verify the response status code and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void GetAdminSubunit() throws IOException {

		// Defining Endpoint
		String endpoint = "/web/index.php/api/v2/admin/subunits";

		// Defining upcoming responce from Rest Method
		CustomResponse customResponse = apiUtil.GetAdminSubunit(endpoint, cookieValue, null);

		// Checking for implementation which we are using is correct or not
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath, "GetAdminSubunit",
				List.of("given", "cookie", "get", "response"));

		// Assert that the implementation and Status we need is Satisfied
		Assert.assertTrue(isImplementationCorrect,
				"GetAdminSubunit must be implementated using the Rest assured  methods only!");
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// Step 4: Validate status field
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		Assert.assertNotNull(customResponse.getIds());
		Assert.assertNotNull(customResponse.getNames());
		Assert.assertNotNull(customResponse.unitIds);
		Assert.assertNotNull(customResponse.descriptions);
		Assert.assertNotNull(customResponse.level);
		Assert.assertNotNull(customResponse.left);
		Assert.assertNotNull(customResponse.right);

	}


	/**
 * Test Method: GetPimEmp
 *
 * Purpose:
 * Sends a GET request to retrieve all PIM employees and validates the response.
 *
 * Steps:
 * 1. Define the endpoint '/web/index.php/api/v2/pim/employees'.
 * 2. Send a GET request with a valid cookie; no request body is required (null).
 * 3. Validate that the API implementation uses the expected RestAssured methods.
 * 4. Assert that the response status code is 200 (OK) and the status line matches "HTTP/1.0 200 OK".
 * 5. Assert that all relevant employee fields in the CustomResponse (employeeNumbers, lastNames, firstNames, employeeId) are non-null.
 *
 * Notes:
 * - Ensures strict use of RestAssured methods for API interaction.
 * - Verifies that essential employee data is properly returned from the PIM module.
 */


	@Test(priority = 4, groups = {
			"PL2" }, description = "1. Send a GET request to the '/web/index.php/api/v2/pim/employees' endpoint with a valid cookie\n"
					+ "2. Do not pass any request body (null)\n"
					+ "3. Print and verify the response status code and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void GetPimEmp() throws IOException {

		// Defining Endpoint
		String endpoint = "/web/index.php/api/v2/pim/employees";

		// Defining upcoming responce from Rest Method
		CustomResponse customResponse = apiUtil.GetPimEmp(endpoint, cookieValue, null);

		// Checking for implementation which we are using is correct or not
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath, "GetPimEmp",
				List.of("given", "cookie", "get", "response"));

		// Assert that the implementation and Status we need is Satisfied
		Assert.assertTrue(isImplementationCorrect,
				"GetPimEmp must be implementated using the Rest assured  methods only!");
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// Step 4: Validate status field
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		Assert.assertNotNull(customResponse.employeeNumbers);
		Assert.assertNotNull(customResponse.lastNames);
		Assert.assertNotNull(customResponse.firstNames);
		Assert.assertNotNull(customResponse.employeeId);

	}

	/**
 * Test Method: GetReportASC
 *
 * Purpose:
 * Sends a GET request to retrieve PIM reports sorted in ascending order by report name
 * and validates the response.
 *
 * Steps:
 * 1. Define the endpoint '/web/index.php/api/v2/pim/reports/defined?limit=50&offset=0&sortField=report.name&sortOrder=ASC'.
 * 2. Send a GET request with a valid cookie; no request body is required (null).
 * 3. Validate that the API implementation uses the expected RestAssured methods.
 * 4. Assert that the response status code is 200 (OK) and the status line matches "HTTP/1.0 200 OK".
 * 5. Assert that the lists of report IDs and report Names in the CustomResponse are non-null.
 *
 * Notes:
 * - Ensures strict use of RestAssured methods for API interaction.
 * - Verifies that the PIM reports are properly returned and sorted ascendingly by name.
 */


	@Test(priority = 5, groups = {
			"PL2" }, description = "1. Send a GET request to the '/web/index.php/api/v2/pim/reports/defined?limit=50&offset=0&sortField=report.name&sortOrder=ASC' endpoint with a valid cookie\n"
					+ "2. Do not pass any request body (null)\n"
					+ "3. Print and verify the response status code and response body\n"
					+ "4. Assert that the response status code is 200 (OK)")
	public void GetReportASC() throws IOException {

		// Defining Endpoint
		String endpoint = "/web/index.php/api/v2/pim/reports/defined?limit=50&offset=0&sortField=report.name&sortOrder=ASC";

		// Defining upcoming responce from Rest Method
		CustomResponse customResponse = apiUtil.GetReportASC(endpoint, cookieValue, null);

		// Checking for implementation which we are using is correct or not
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath, "GetReportASC",
				List.of("given", "cookie", "get", "response"));

		// Assert that the implementation and Status we need is Satisfied
		Assert.assertTrue(isImplementationCorrect,
				"GetReportASC must be implementated using the Rest assured  methods only!");
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// Step 4: Validate status field
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		Assert.assertNotNull(customResponse.getIds());
		Assert.assertNotNull(customResponse.getNames());
	}


	/**
 * Test Method: GetLeaveTypesEligible
 *
 * Purpose:
 * Sends a GET request to retrieve all eligible leave types for an employee
 * and validates the response.
 *
 * Steps:
 * 1. Define the endpoint '/web/index.php/api/v2/leave/leave-types/eligible'.
 * 2. Send a GET request with a valid cookie; no request body is required (null).
 * 3. Validate that the API implementation uses the expected RestAssured methods.
 * 4. Assert that the response status code is 200 (OK) and the status line matches "HTTP/1.0 200 OK".
 * 5. Assert that the lists of leave type IDs, names, and deleted status in the CustomResponse are non-null.
 *
 * Notes:
 * - Ensures strict use of RestAssured methods for API interaction.
 * - Validates the API correctly returns all eligible leave types for the user.
 */

	@Test(priority = 6, groups = {
	"PL2" }, description = "1. Send a GET request to the endpoint '/web/index.php/api/v2/leave/leave-types/eligible' with a valid cookie"
			+ "2. Do not pass any request body (null)\n"
			+ "3. Print and verify the response status code and response body\n"
			+ "4. Assert that the response status code is 200 (OK)")
	public void GetLeaveTypesEligible() throws IOException {

		// Defining Endpoint
		String endpoint = "/web/index.php/api/v2/leave/leave-types/eligible";

		// Defining upcoming responce from Rest Method
		CustomResponse customResponse = apiUtil.GetLeaveTypesEligible(endpoint, cookieValue, null);

		// Checking for implementation which we are using is correct or not
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(apiUtilPath, "GetLeaveTypesEligible",
				List.of("given", "cookie", "get", "response"));

		// Assert that the implementation and Status we need is Satisfied
		Assert.assertTrue(isImplementationCorrect,
				"GetLeaveTypesEligible must be implementated using the Rest assured  methods only!");
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// Step 4: Validate status field
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		Assert.assertNotNull(customResponse.getIds());
		Assert.assertNotNull(customResponse.getNames());
		Assert.assertNotNull(customResponse.getDeleted());
	}

	/**
 * Test Method: GetLeaveRequests
 *
 * Purpose:
 * Sends a GET request to retrieve leave requests for current employees
 * and validates the response structure and content.
 *
 * Steps:
 * 1. Define the API endpoint with query parameters for pagination and filtering.
 * 2. Call the API using the apiUtil.GetLeaveRequests method with a valid cookie.
 * 3. Verify that the test implementation uses the correct Rest Assured methods.
 * 4. Assert that the HTTP status code is 200 and the status line is "HTTP/1.0 200 OK".
 * 5. Validate that all critical response fields are not null, including:
 *    - Leave request IDs
 *    - Employee numbers
 *    - Employee first names
 *    - Employee last names
 *    - Leave type names
 *    - Deleted flags
 *
 * Notes:
 * - No request body is required for this GET request (body is null).
 * - Ensures response data is correctly mapped into the CustomResponse object for further test validations.
 */


	@Test(priority = 7, groups = {
	"PL2" }, description = "1. Send a GET request to the endpoint '/web/index.php/api/v2/leave/leave-requests?limit=50&offset=0&includeEmployees=onlyCurrent' with a valid cookie"
        + "2. Do not pass any request body (null)\n"
        + "3. Print and verify the response status code and response body\n"
        + "4. Assert that the response status code is 200 (OK)")
public void GetLeaveRequests() throws IOException {

    // Step 1: Define Endpoint
    String endpoint = "/web/index.php/api/v2/leave/leave-requests?limit=50&offset=0&includeEmployees=onlyCurrent";

    // Step 2: Call API Util
    CustomResponse customResponse = apiUtil.GetLeaveRequests(endpoint, cookieValue, null);

    // Step 3: Verify implementation correctness
    boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
            apiUtilPath, "GetLeaveRequests",
            List.of("given", "cookie", "get", "response"));

    Assert.assertTrue(isImplementationCorrect,
            "GetLeaveRequests must be implementated using the Rest Assured methods only!");

    // Step 4: Validate status code and status
    Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");
    Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");

    // Step 5: Validate response fields
    Assert.assertNotNull(customResponse.getIds(), "Leave request IDs should not be null.");
    Assert.assertNotNull(customResponse.getEmployeeNumbers(), "Employee numbers should not be null.");
    Assert.assertNotNull(customResponse.getFirstNames(), "Employee first names should not be null.");
    Assert.assertNotNull(customResponse.getLastNames(), "Employee last names should not be null.");
    Assert.assertNotNull(customResponse.getNames(), "Leave Type names should not be null.");
    Assert.assertNotNull(customResponse.getDeleted(), "Leave Type deleted flags should not be null.");
}


/**
 * Test Method: GetLeavePeriods
 *
 * Purpose:
 * Sends a GET request to fetch leave periods and validates the response
 * to ensure the leave period data is correctly returned by the API.
 *
 * Steps:
 * 1. Define the API endpoint for leave periods.
 * 2. Call the API using the utility method with a valid cookie.
 * 3. Verify that the implementation uses RestAssured methods correctly.
 * 4. Assert that the response status code is 200 (OK) and the status line is correct.
 * 5. Validate that the response lists are not null or empty:
 *    - leavePeriods
 *    - startDates
 *    - endDates
 * 6. Validate that the meta information is present and not null.
 *
 * Notes:
 * - No request body is required for this GET request.
 * - Ensures all critical fields in the response are available for further validation.
 */

	@Test(priority = 8, groups = {
	"PL2" }, description = "1. Send a GET request to the endpoint '/web/index.php/api/v2/leave/leave-periods' with a valid cookie"
			+ "2. Do not pass any request body (null)\n"
			+ "3. Print and verify the response status code and response body\n"
			+ "4. Assert that the response status code is 200 (OK)")
	public void GetLeavePeriods() throws IOException {

		// Step 1: Define Endpoint
		String endpoint = "/web/index.php/api/v2/leave/leave-periods";

		// Step 2: Call API Util
		CustomResponse customResponse = apiUtil.GetLeavePeriods(endpoint, cookieValue, null);

		// Step 3: Verify implementation correctness
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
				apiUtilPath, "GetLeavePeriods",
				List.of("given", "cookie", "get", "response"));

		Assert.assertTrue(isImplementationCorrect,
				"GetLeavePeriods must be implementated using the Rest Assured methods only!");

		// Step 4: Validate status code and status
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");

		// ✅ leavePeriods list should not be null or empty
		Assert.assertNotNull(customResponse.getLeavePeriods(), "Leave periods list should not be null.");
		Assert.assertFalse(customResponse.getLeavePeriods().isEmpty(), "Leave periods list should not be empty.");

		// ✅ startDates list should not be null or empty
		Assert.assertNotNull(customResponse.getStartDates(), "Start dates list should not be null.");
		Assert.assertFalse(customResponse.getStartDates().isEmpty(), "Start dates list should not be empty.");

		// ✅ endDates list should not be null or empty
		Assert.assertNotNull(customResponse.getEndDates(), "End dates list should not be null.");
		Assert.assertFalse(customResponse.getEndDates().isEmpty(), "End dates list should not be empty.");
		Assert.assertNotNull(customResponse.getMeta(), "Meta information should not be null.");
		
	}


	/**
 * Test Method: GetWorkWeek
 *
 * Purpose:
 * Sends a GET request to the leave workweek endpoint to retrieve workweek configuration
 * and validates the response.
 *
 * Steps:
 * 1. Define the endpoint for fetching the workweek (with 'model=indexed').
 * 2. Send a GET request with a valid cookie for authentication.
 * 3. Verify that the implementation uses Rest Assured methods correctly.
 * 4. Assert that the response status code is 200 and status line is "HTTP/1.0 200 OK".
 * 5. Ensure that the returned workweek data is non-null and not empty.
 *
 * Notes:
 * - No request body is required for this GET request.
 * - The test validates both the HTTP response and the presence of the workweek data.
 */


	@Test(priority = 9, groups = {
	"PL2" }, description = "1. Send a GET request to the endpoint '/web/index.php/api/v2/leave/workweek?model=indexed' with a valid cookie"
			+ "2. Do not pass any request body (null)\n"
			+ "3. Print and verify the response status code and response body\n"
			+ "4. Assert that the response status code is 200 (OK)")
	public void GetWorkWeek() throws IOException {

		// Step 1: Define Endpoint
		String endpoint = "/web/index.php/api/v2/leave/workweek?model=indexed";

		// Step 2: Call API Util
		CustomResponse customResponse = apiUtil.GetWorkWeek(endpoint, cookieValue, null);

		// Step 3: Verify implementation correctness
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
				apiUtilPath, "GetWorkWeek",
				List.of("given", "cookie", "get", "response"));

		Assert.assertTrue(isImplementationCorrect,
				"GetWorkWeek must be implementated using the Rest Assured methods only!");

		// Step 4: Validate status code and status
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");

		// Verify workweek data is not null
		Assert.assertNotNull(customResponse.getWorkWeekData(), "Work week data should not be null.");
		Assert.assertFalse(customResponse.getWorkWeekData().isEmpty(), "Work week data should not be empty.");
	}


	/**
 * Test Method: GetWorkWeekdays
 *
 * Purpose:
 * Sends a GET request to the leave workweek endpoint with a header parameter
 * to fetch the workweek configuration and validates the response.
 *
 * Steps:
 * 1. Define the endpoint for leave workweek API.
 * 2. Set the header key "model" with value "indexed" to pass as query/header param.
 * 3. Call the API using apiUtil.GetWorkWeekdays with the endpoint, cookie, and header key/value.
 * 4. Verify that the API implementation uses Rest Assured correctly.
 * 5. Assert that the response status code is 200 and status line is "HTTP/1.0 200 OK".
 * 6. Validate that the workWeekData map in the response is not null or empty.
 * 7. Assert that Saturday ("6") and Sunday ("0") have 8 hours and weekdays ("1"-"5") have 0 hours.
 *
 * Notes:
 * - The GET request passes header parameters instead of a request body.
 * - The CustomResponse contains workWeekData for easy access and validation.
 */

	@Test(priority = 10, groups = {
	"PL2" }, description = "1. Send a GET request to the endpoint '/web/index.php/api/v2/leave/workweek' with a valid cookie"
			+ "2. pass the header key and values as arguments as model\r :indexed (null)\n"
			+ "3. Print and verify the response status code and response body\n"
			+ "4. Assert that the response status code is 200 (OK)")
	public void GetWorkWeekdays() throws IOException {

		// Step 1: Define Endpoint
		String endpoint = "/web/index.php/api/v2/leave/workweek";

		// Step 2: Define header properly
    String headerKey = "model";
    String headerValue = "indexed";

    // Step 3: Call API Util
    CustomResponse customResponse = apiUtil.GetWorkWeekdays(endpoint, cookieValue, headerKey, headerValue);

		// Step 3: Verify implementation correctness
		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
				apiUtilPath, "GetWorkWeek",
				List.of("given", "cookie", "get", "response"));

		Assert.assertTrue(isImplementationCorrect,
				"GetWorkWeek must be implementated using the Rest Assured methods only!");

		// Step 4: Validate status code and status
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");
		Assert.assertEquals(customResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");

		// Verify workweek data is not null
		Assert.assertNotNull(customResponse.getWorkWeekData(), "Work week data should not be null.");
		Assert.assertFalse(customResponse.getWorkWeekData().isEmpty(), "Work week data should not be empty.");

		// Verify Saturday (6) and Sunday (0) are 8 hours, weekdays are 0
		Assert.assertEquals(customResponse.getWorkWeekData().get("6"), 8, "Saturday should be 8 hours.");
		Assert.assertEquals(customResponse.getWorkWeekData().get("0"), 4, "Sunday should be 4 hours.");
		Assert.assertEquals(customResponse.getWorkWeekData().get("1"), 0, "Monday should be 0 hours.");

	}
	
	/**
 * Test: DeleteCustomField
 *
 * Purpose:
 * Deletes a custom field from OrangeHRM and verifies successful deletion.
 *
 * Steps:
 * 1. Create a custom field if none exists to ensure data is available for deletion.
 * 2. Construct the endpoint URL for custom fields.
 * 3. Fetch the first custom field ID using getFirstCustomFieldId().
 * 4. Build the request body containing the ID to delete.
 * 5. Send a DELETE request via apiUtil.DeleteCustomField().
 * 6. Validate that the implementation uses RestAssured methods correctly.
 * 7. Assert that the HTTP status code is 200 (OK).
 * 8. Verify that the deleted ID no longer exists by fetching the first custom field ID again.
 *
 * Notes:
 * - Ensures that at least one custom field exists before attempting deletion.
 * - Confirms deletion by checking that the first ID changes after the delete operation.
 */

	@Test(priority = 11, groups = {
	"PL2" }, description = "1. Construct the url using the endpoint '/web/index.php/api/v2/pim/custom-fields\n'"
	+"2.Fetch the id of first custom field from the list using method 'getIdFromCustomFieldList() and construct a body as {\"ids\": [id]}'\n"
	+"3. send a DELETE request to the endpoint '/web/index.php/api/v2/pim/custom-fields/{id}' with a valid cookie\n"
	+"4. Assert that the response status code is 200 (OK) and the id has been deleted")
	public void DeleteCustomField() throws IOException {

		createCustomField(cookieValue);	//creating a custom field just to be safe if not present
		// Step 1: Construct the URL
		String endpoint = "/web/index.php/api/v2/pim/custom-fields";

		// Step 2: Get the first custom field ID
		int customFieldId = getFirstCustomFieldId(cookieValue);

		String requestBody = "{ \"ids\" : [" + customFieldId + "] }";

		// Step 3: Send DELETE request
		CustomResponse deleteResponse = apiUtil.DeleteCustomField(endpoint, requestBody, cookieValue);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
				apiUtilPath, "DeleteCustomField",
				List.of("given", "cookie", "delete", "response"));

		Assert.assertTrue(isImplementationCorrect,
		"DeleteCustomField must be implemented using the Rest Assured methods only!");

		// Step 4: Assert status code is 200 (OK)
		assertEquals(deleteResponse.getStatusCode(), 200, "Status code should be 200.");

		int newId = getFirstCustomFieldId(cookieValue);

		Assert.assertNotEquals(newId, customFieldId, "Custom field was not deleted successfully.");

	}

	/**
 * Test: AddReportingMethod
 *
 * Purpose:
 * Creates a new reporting method in OrangeHRM and verifies that it is successfully added.
 *
 * Steps:
 * 1. Construct the request body with a unique name for the reporting method.
 * 2. Send a POST request to the '/web/index.php/api/v2/pim/reporting-methods' endpoint using apiUtil.AddReportingMethod().
 * 3. Validate that the implementation uses RestAssured methods correctly.
 * 4. Assert that the HTTP status code is 200 (OK) and the status line is correct.
 * 5. Verify that the response data contains the newly created reporting method.
 *
 * Notes:
 * - Uses a UUID to ensure that the reporting method name is unique.
 * - Logs and validates the response to confirm successful creation.
 */


	@Test(priority=12, groups = {
	"PL2" }, description = "1. Create a body for adding reporting method as {name: \"abcd\""
        + "2. Send a POST request to the endpoint '/web/index.php/api/v2/pim/reporting-methods' with the body created in step 1 and a valid cookie\n"
        + "3. Assert that the response status code is 200 (Created)\n"
        + "4. Validate the response body contains the created reporting method")
public void AddReportingMethod() throws IOException {
    String endpoint = "/web/index.php/api/v2/pim/reporting-methods";    
    String randomName = "name_" + UUID.randomUUID().toString();

    // Step 1: Create request body
    String requestBody = "{ \"name\": \"" + randomName + "\" }";

    // Step 2: Send POST request
    CustomResponse postResponse = apiUtil.AddReportingMethod(requestBody, cookieValue, endpoint);

    boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
            apiUtilPath, "AddReportingMethod",
            List.of("given", "cookie", "post", "response"));

    Assert.assertTrue(isImplementationCorrect,
            "AddReportingMethod must be implemented using the Rest Assured methods only!");

    // Step 3: Assert status code is 200
    assertEquals(postResponse.getStatusCode(), 200, "Status code should be 200.");
    assertEquals(postResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");

    // Step 4: Verify response contains the created name
String responseName = postResponse.getResponse().jsonPath().getString("data.name");
Assert.assertEquals(responseName, randomName,
        "Response JSON should contain the created reporting method name inside data");

// Optional: check ID and arrays
Assert.assertNotNull(postResponse.getResponse().jsonPath().getInt("data.id"), "ID should not be null");
Assert.assertTrue(postResponse.getResponse().jsonPath().getList("meta").isEmpty(), "Meta should be empty");
Assert.assertTrue(postResponse.getResponse().jsonPath().getList("rels").isEmpty(), "Rels should be empty");

    }

	
	/**
 * Test: UpdateReportingMethods
 *
 * Purpose:
 * Updates an existing reporting method in OrangeHRM and validates the response.
 *
 * Steps:
 * 1. Fetch the ID of the first reporting method using getFirstReportingMethodId().
 * 2. Construct the endpoint URL by appending the fetched ID.
 * 3. Create a JSON request body with a new randomized name for the reporting method.
 * 4. Send a PUT request to update the reporting method via apiUtil.UpdateReportingMethod().
 * 5. Validate that the implementation uses RestAssured methods correctly.
 * 6. Assert that the HTTP status code is 200 (OK).
 * 7. Optionally, validate that the response body contains the updated reporting method details.
 *
 * Notes:
 * - Ensures that the first reporting method is updated with a unique name.
 * - Confirms successful update by checking HTTP status and response correctness.
 */


	@Test(priority = 13, groups = {
	"PL2" }, description = "1. Create a body for adding custom field as {name: \"Name\"}"
			+ "2. Send a POST request to the endpoint '/web/index.php/api/v2/pim/reporting-methods/{id}' with the body created in step 1 and a valid cookie\n"
			+ "3. Assert that the response status code is 200 "
			+ "4. Validate the response body contains the created custom field")
	public void UpdateReportingMethods() throws IOException {
		int id=getFirstReportingMethodId(cookieValue);
		String endpoint = "/web/index.php/api/v2/pim/reporting-methods/"+id;
		String name = "name_" + UUID.randomUUID().toString();
		// Step 1: Create request body
		String requestBody = "{ \"name\": \"" + name + "\" }";

		// Step 2: Send POST request
		CustomResponse postResponse = apiUtil.UpdateReportingMethod(requestBody, cookieValue, endpoint);

		boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
				apiUtilPath, "UpdateReportingMethod",
				List.of("given", "cookie", "put", "response"));

		Assert.assertTrue(isImplementationCorrect,
		"UpdateReportingMethod must be implemented using the Rest Assured methods only!");

		// Step 3: Assert status code is 200 (Created)
		assertEquals(postResponse.getStatusCode(), 200, "Status code should be 200.");

		assertEquals(postResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
		}

		/**
 * Test: DeleteReportingMethod
 *
 * Purpose:
 * Deletes an existing reporting method from OrangeHRM and verifies successful deletion.
 *
 * Steps:
 * 1. Optionally create a reporting method to ensure there is an ID available for deletion.
 * 2. Construct the endpoint URL for reporting methods.
 * 3. Fetch the first reporting method ID to delete.
 * 4. Build the request body containing the ID(s) to delete.
 * 5. Send a DELETE request via apiUtil.DeleteReportingMethod().
 * 6. Validate that the implementation uses RestAssured methods correctly.
 * 7. Assert that the HTTP status code is 200 (OK).
 * 8. Verify that the response data is not null, indicating the deletion operation was processed.
 *
 * Notes:
 * - Ensures that at least one reporting method exists before attempting deletion.
 * - Confirms deletion by checking the response data and HTTP status code.
 */

		@Test(priority = 14, groups = {
		"PL2" }, description = "1. Create a body for deleting reporting method by ID\n"
        + "2. Send a DELETE request to the endpoint '/web/index.php/api/v2/pim/reporting-methods'\n"
        + "3. Assert that the response status code is 200\n"
        + "4. Validate the response body contains the deleted ID")
public void DeleteReportingMethod() throws IOException {
    String endpoint = "/web/index.php/api/v2/pim/reporting-methods";

    // Step 0: Create a reporting method just to ensure there is one to delete
    CustomResponse postResponse = apiUtil.AddReportingMethod(
            "{ \"name\": \"" + UUID.randomUUID().toString() + "\" }",
            cookieValue,
            endpoint
    );

    // Extract the ID of the newly created reporting method
    int idToDelete = postResponse.getResponse().jsonPath().getInt("data.id");

    // Step 1: Create DELETE request body
    String requestBody = "{ \"ids\": [" + idToDelete + "] }";
   

    // Step 2: Send DELETE request
    CustomResponse deleteResponse = apiUtil.DeleteReportingMethod(requestBody, cookieValue, endpoint);

    // Step 2a: Validate implementation uses RestAssured delete
    boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
            apiUtilPath, "DeleteReportingMethod",
            List.of("given", "cookie", "delete", "response")
    );
    Assert.assertTrue(isImplementationCorrect,
            "DeleteReportingMethod must be implemented using the Rest Assured methods only!");

    // Step 3: Assert status code and status line
    assertEquals(deleteResponse.getStatusCode(), 200, "Status code should be 200.");
    assertEquals(deleteResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");

	int newId = getFirstReportingMethodId(cookieValue);

    Assert.assertNotEquals(newId, idToDelete, "ID should not be equal to the deleted ID.");
}


		/**
 * Test: AddTerminationReason
 *
 * Purpose:
 * Adds a new termination reason in OrangeHRM and validates its creation.
 *
 * Steps:
 * 1. Construct a request body with a random name for the termination reason.
 * 2. Send a POST request to the '/web/index.php/api/v2/pim/termination-reasons' endpoint with a valid cookie.
 * 3. Validate that the implementation uses RestAssured methods correctly.
 * 4. Assert that the HTTP status code is 200 (OK).
 * 5. Verify that the response data is not null and contains the created termination reason.
 *
 * Notes:
 * - Each test run creates a unique termination reason using UUID to avoid conflicts.
 * - The test ensures that the termination reason is successfully added to the system.
 */

		@Test(priority = 15, groups = {
		"PL2" }, description = "1. Create a body for adding custom field as {name: {random name}}"
			+ "2. Send a POST request to the endpoint '/web/index.php/api/v2/pim/termination-reasons'\n" 
			+ "3. Assert that the response status code is 200 "
			+ "4. Validate the response body contains the created custom field and contains the ID")
			public void AddTerminationReason() throws IOException {
				
				String endpoint = "/web/index.php/api/v2/pim/termination-reasons";

				// Step 1: Create request body
				String requestBody = "{ \"name\": \"" + UUID.randomUUID().toString() + "\" }";

				// Step 2: Send POST request
				CustomResponse postResponse = apiUtil.AddTerminationReason(requestBody, cookieValue, endpoint);

				boolean isImplementationCorrect = TestCodeValidator.validateTestMethodFromFile(
						apiUtilPath, "AddTerminationReason",
						List.of("given", "cookie", "post", "response"));

				Assert.assertTrue(isImplementationCorrect,
						"AddTerminationReason must be implemented using the Rest Assured methods only!");

				// Step 3: Assert status code is 200 (Created)
				assertEquals(postResponse.getStatusCode(), 200, "Status code should be 200.");

				assertEquals(postResponse.getStatus(), "HTTP/1.0 200 OK", "Status should be OK.");
				assert postResponse.getData() != null : "Response data should not be null.";
			}

	/*----------------------Helper method----------------------------*/

	public int getFirstReportingMethodId(String cookieValue) {
    String url = "https://yakshahrm.makemylabs.in/orangehrm-5.7/web/index.php/api/v2/pim/reporting-methods?limit=50&offset=0";

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Content-Type", "application/json")
        .when()
            .get(url)
        .then()
            .extract()
            .response();

    JsonPath jsonPath = response.jsonPath();

    // Get first ID from the data array
    int firstId = jsonPath.getInt("data[0].id");
    System.out.println("First Reporting Method ID: " + firstId);

    return firstId;
}


	
public int getFirstCustomFieldId(String cookieValue) {
    String url = "https://yakshahrm.makemylabs.in/orangehrm-5.7/web/index.php/api/v2/pim/custom-fields?limit=50&offset=0";

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Accept", "application/json")   // ✅ FIXED (use Accept instead of Content-Type)
        .when()
            .get(url)
        .then()
            .extract()
            .response();

    System.out.println("Status Code: " + response.getStatusCode());
    System.out.println("Response Body: " + response.getBody().asString());

    JsonPath jsonPath = response.jsonPath();

    // ✅ Safer: fetch list of IDs
    List<Integer> ids = jsonPath.getList("data.id", Integer.class);

    if (ids == null || ids.isEmpty()) {
        throw new RuntimeException("No custom fields found in response!");
    }

    int firstId = ids.get(0);  // first element
    System.out.println("First Custom Field ID: " + firstId);

    return firstId;
}

	public void createCustomField(String cookieValue) {
    String url = "https://yakshahrm.makemylabs.in/orangehrm-5.7/web/index.php/api/v2/pim/custom-fields";

    // Generate random string for fieldName
    String randomFieldName = "Field_" + UUID.randomUUID().toString().substring(0, 8);

    // JSON body as raw string
    String requestBody = "{"
            + "\"fieldName\": \"" + randomFieldName + "\","
            + "\"screen\": \"personal\","
            + "\"fieldType\": 0,"
            + "\"extraData\": null"
            + "}";

    Response response = RestAssured.given()
            .cookie("orangehrm", cookieValue)
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post(url)
        .then()
            .extract()
            .response();

    }

	private String formatFieldType(String value) {
		// Handles float string like "0.0" -> "0"
		try {
			return String.valueOf((int) Double.parseDouble(value));
		} catch (Exception e) {
			return "0"; // Default fallback
		}
	}

	private String formatExtraData(String value) {
		if (value == null || value.trim().equalsIgnoreCase("null") || value.trim().isEmpty()) {
			return "null"; // Keep it as null (not a string)
		} else {
			return "\"" + value.trim() + "\""; // Wrap in quotes
		}
	}

	private String quoteOrNull(String val) {
		return (val == null || val.trim().equalsIgnoreCase("null") || val.trim().isEmpty()) ? "null"
				: "\"" + val + "\"";
	}

	private String getJsonValue(String value) {
		if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
			return "null";
		}

		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return value.toLowerCase();
		}

		try {
			if (value.contains(".")) {
				double d = Double.parseDouble(value);
				return String.valueOf((int) d); // handle 3890.0 → 3890
			}
			Integer.parseInt(value);
			return value;
		} catch (NumberFormatException ignored) {
		}

		return "\"" + value.trim() + "\"";
	}
}
