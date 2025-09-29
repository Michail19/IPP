# OrdersApi

All URIs are relative to *http://localhost:4010*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**ordersGet**](OrdersApi.md#ordersGet) | **GET** /orders | Get all orders (Admin only) |
| [**ordersOrderIdGet**](OrdersApi.md#ordersOrderIdGet) | **GET** /orders/{orderId} | Get order by ID |
| [**ordersPost**](OrdersApi.md#ordersPost) | **POST** /orders | Create a new order |
| [**usersUserIdOrdersGet**](OrdersApi.md#usersUserIdOrdersGet) | **GET** /users/{userId}/orders | Get user&#39;s order history |


<a id="ordersGet"></a>
# **ordersGet**
> List&lt;Order&gt; ordersGet(status, userId)

Get all orders (Admin only)

Returns a list of all orders in the system. Requires administrator privileges. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OrdersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    OrdersApi apiInstance = new OrdersApi(defaultClient);
    String status = "pending"; // String | Filter orders by status
    Long userId = 56L; // Long | Filter orders by user ID
    try {
      List<Order> result = apiInstance.ordersGet(status, userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrdersApi#ordersGet");
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
| **status** | **String**| Filter orders by status | [optional] [enum: pending, completed, cancelled] |
| **userId** | **Long**| Filter orders by user ID | [optional] |

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
| **403** | Forbidden - User is not an admin |  -  |
| **500** | Internal server error |  -  |

<a id="ordersOrderIdGet"></a>
# **ordersOrderIdGet**
> Order ordersOrderIdGet(orderId)

Get order by ID

Returns detailed information about a specific order. Users can only view their own orders. Administrators can view any order. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OrdersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    OrdersApi apiInstance = new OrdersApi(defaultClient);
    Long orderId = 1001L; // Long | ID of the order to retrieve
    try {
      Order result = apiInstance.ordersOrderIdGet(orderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrdersApi#ordersOrderIdGet");
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
| **orderId** | **Long**| ID of the order to retrieve | |

### Return type

[**Order**](Order.md)

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
| **403** | Forbidden - User tried to access another user&#39;s order without admin rights |  -  |
| **404** | Order not found |  -  |
| **500** | Internal server error |  -  |

<a id="ordersPost"></a>
# **ordersPost**
> Order ordersPost(orderCreate)

Create a new order

Creates a new order for the authenticated user. The user will be automatically determined from the JWT token. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.OrdersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    OrdersApi apiInstance = new OrdersApi(defaultClient);
    OrderCreate orderCreate = new OrderCreate(); // OrderCreate | Order creation data
    try {
      Order result = apiInstance.ordersPost(orderCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrdersApi#ordersPost");
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
| **orderCreate** | [**OrderCreate**](OrderCreate.md)| Order creation data | |

### Return type

[**Order**](Order.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Order successfully created |  * Location - URL of the created order <br>  |
| **400** | Invalid input - e.g., empty game list, invalid game IDs |  -  |
| **401** | Unauthorized - Missing or invalid token |  -  |
| **404** | One or more games not found |  -  |
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
import org.openapitools.client.api.OrdersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    OrdersApi apiInstance = new OrdersApi(defaultClient);
    Long userId = 10L; // Long | ID of the user whose orders to retrieve
    try {
      List<Order> result = apiInstance.usersUserIdOrdersGet(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrdersApi#usersUserIdOrdersGet");
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

