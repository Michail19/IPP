# UsersApi

All URIs are relative to *http://localhost:4010*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**usersLoginPost**](UsersApi.md#usersLoginPost) | **POST** /users/login | Authenticate a user and receive a JWT token |
| [**usersRegisterPost**](UsersApi.md#usersRegisterPost) | **POST** /users/register | Register a new user |
| [**usersUserIdGet**](UsersApi.md#usersUserIdGet) | **GET** /users/{userId} | Get user profile by ID |
| [**usersUserIdOrdersGet**](UsersApi.md#usersUserIdOrdersGet) | **GET** /users/{userId}/orders | Get user&#39;s order history |


<a id="usersLoginPost"></a>
# **usersLoginPost**
> UsersLoginPost200Response usersLoginPost(usersLoginPostRequest)

Authenticate a user and receive a JWT token

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");

    UsersApi apiInstance = new UsersApi(defaultClient);
    UsersLoginPostRequest usersLoginPostRequest = new UsersLoginPostRequest(); // UsersLoginPostRequest | 
    try {
      UsersLoginPost200Response result = apiInstance.usersLoginPost(usersLoginPostRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersLoginPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **usersLoginPostRequest** | [**UsersLoginPostRequest**](UsersLoginPostRequest.md)|  | |

### Return type

[**UsersLoginPost200Response**](UsersLoginPost200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful login |  -  |
| **401** | Unauthorized - Invalid email or password |  -  |
| **500** | Internal server error |  -  |

<a id="usersRegisterPost"></a>
# **usersRegisterPost**
> User usersRegisterPost(userCreate)

Register a new user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");

    UsersApi apiInstance = new UsersApi(defaultClient);
    UserCreate userCreate = new UserCreate(); // UserCreate | 
    try {
      User result = apiInstance.usersRegisterPost(userCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersRegisterPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userCreate** | [**UserCreate**](UserCreate.md)|  | |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | User successfully created |  -  |
| **400** | Invalid input (e.g., email already exists, username taken) |  -  |
| **500** | Internal server error |  -  |

<a id="usersUserIdGet"></a>
# **usersUserIdGet**
> User usersUserIdGet(userId)

Get user profile by ID

Returns user profile information.  Regular users can only retrieve their own profile.  Administrators can retrieve any user&#39;s profile. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Long userId = 10L; // Long | ID of the user to retrieve
    try {
      User result = apiInstance.usersUserIdGet(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersUserIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**| ID of the user to retrieve | |

### Return type

[**User**](User.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation |  -  |
| **401** | Unauthorized - Missing or invalid token |  -  |
| **403** | Forbidden - User tried to access another user&#39;s profile without admin rights |  -  |
| **404** | User not found |  -  |
| **500** | Internal server error |  -  |

<a id="usersUserIdOrdersGet"></a>
# **usersUserIdOrdersGet**
> List&lt;Order&gt; usersUserIdOrdersGet(userId)

Get user&#39;s order history

Returns the order history for a specific user. Regular users can only view their own orders. Administrators can view any user&#39;s orders. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UsersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    UsersApi apiInstance = new UsersApi(defaultClient);
    Long userId = 10L; // Long | ID of the user whose orders to retrieve
    try {
      List<Order> result = apiInstance.usersUserIdOrdersGet(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#usersUserIdOrdersGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**| ID of the user whose orders to retrieve | |

### Return type

[**List&lt;Order&gt;**](Order.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation |  -  |
| **401** | Unauthorized - Missing or invalid token |  -  |
| **403** | Forbidden - User tried to access another user&#39;s orders without admin rights |  -  |
| **404** | User not found |  -  |
| **500** | Internal server error |  -  |

