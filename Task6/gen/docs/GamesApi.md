# GamesApi

All URIs are relative to *http://localhost:4010*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**gamesGameIdDelete**](GamesApi.md#gamesGameIdDelete) | **DELETE** /games/{gameId} | Delete a game from the catalog (Admin only) |
| [**gamesGameIdGet**](GamesApi.md#gamesGameIdGet) | **GET** /games/{gameId} | Get a game by its ID |
| [**gamesGameIdPut**](GamesApi.md#gamesGameIdPut) | **PUT** /games/{gameId} | Update an existing game (Admin only) |
| [**gamesGet**](GamesApi.md#gamesGet) | **GET** /games | Get a list of all games |
| [**gamesPost**](GamesApi.md#gamesPost) | **POST** /games | Add a new game to the store (Admin only) |


<a id="gamesGameIdDelete"></a>
# **gamesGameIdDelete**
> gamesGameIdDelete(gameId)

Delete a game from the catalog (Admin only)

Deletes a specific game by ID. Requires admin privileges.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GamesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    GamesApi apiInstance = new GamesApi(defaultClient);
    Long gameId = 42L; // Long | ID of the game to delete
    try {
      apiInstance.gamesGameIdDelete(gameId);
    } catch (ApiException e) {
      System.err.println("Exception when calling GamesApi#gamesGameIdDelete");
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
| **gameId** | **Long**| ID of the game to delete | |

### Return type

null (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Game successfully deleted (no content returned) |  -  |
| **401** | Unauthorized - Missing or invalid token |  -  |
| **403** | Forbidden - User is not an admin |  -  |
| **404** | Game not found |  -  |
| **500** | Internal server error |  -  |

<a id="gamesGameIdGet"></a>
# **gamesGameIdGet**
> Game gamesGameIdGet(gameId)

Get a game by its ID

Returns detailed information about a single game.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GamesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");

    GamesApi apiInstance = new GamesApi(defaultClient);
    Long gameId = 56L; // Long | Numeric ID of the game to retrieve.
    try {
      Game result = apiInstance.gamesGameIdGet(gameId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GamesApi#gamesGameIdGet");
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
| **gameId** | **Long**| Numeric ID of the game to retrieve. | |

### Return type

[**Game**](Game.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation. Game found and returned. |  -  |
| **400** | Bad Request - Invalid ID supplied (e.g., not a number). |  -  |
| **404** | Game not found. No game exists with the provided ID. |  -  |
| **500** | Internal Server Error |  -  |

<a id="gamesGameIdPut"></a>
# **gamesGameIdPut**
> Game gamesGameIdPut(gameId, gameUpdate)

Update an existing game (Admin only)

Updates the details of a specific game by ID. Requires admin privileges.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GamesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    GamesApi apiInstance = new GamesApi(defaultClient);
    Long gameId = 42L; // Long | ID of the game to update
    GameUpdate gameUpdate = new GameUpdate(); // GameUpdate | Updated game object
    try {
      Game result = apiInstance.gamesGameIdPut(gameId, gameUpdate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GamesApi#gamesGameIdPut");
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
| **gameId** | **Long**| ID of the game to update | |
| **gameUpdate** | [**GameUpdate**](GameUpdate.md)| Updated game object | |

### Return type

[**Game**](Game.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Game successfully updated |  -  |
| **400** | Invalid input or ID |  -  |
| **401** | Unauthorized - Missing or invalid token |  -  |
| **403** | Forbidden - User is not an admin |  -  |
| **404** | Game not found |  -  |
| **500** | Internal server error |  -  |

<a id="gamesGet"></a>
# **gamesGet**
> List&lt;Game&gt; gamesGet(genre, developer)

Get a list of all games

Returns a list of games, optionally filtered by genre or developer.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GamesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");

    GamesApi apiInstance = new GamesApi(defaultClient);
    String genre = "genre_example"; // String | Filter games by genre (e.g., 'RPG', 'Action')
    String developer = "developer_example"; // String | Filter games by developer name
    try {
      List<Game> result = apiInstance.gamesGet(genre, developer);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GamesApi#gamesGet");
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
| **genre** | **String**| Filter games by genre (e.g., &#39;RPG&#39;, &#39;Action&#39;) | [optional] |
| **developer** | **String**| Filter games by developer name | [optional] |

### Return type

[**List&lt;Game&gt;**](Game.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation |  -  |
| **500** | Internal server error |  -  |

<a id="gamesPost"></a>
# **gamesPost**
> Game gamesPost(gameCreate)

Add a new game to the store (Admin only)

Creates a new game in the catalog. Requires admin privileges. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.GamesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:4010");
    
    // Configure HTTP bearer authorization: BearerAuth
    HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
    BearerAuth.setBearerToken("BEARER TOKEN");

    GamesApi apiInstance = new GamesApi(defaultClient);
    GameCreate gameCreate = new GameCreate(); // GameCreate | Game object that needs to be added to the store
    try {
      Game result = apiInstance.gamesPost(gameCreate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling GamesApi#gamesPost");
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
| **gameCreate** | [**GameCreate**](GameCreate.md)| Game object that needs to be added to the store | |

### Return type

[**Game**](Game.md)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Game successfully created |  * Location - URL of the created game <br>  |
| **400** | Invalid input |  -  |
| **401** | Unauthorized - Missing or invalid token |  -  |
| **403** | Forbidden - User is not an admin |  -  |
| **500** | Internal server error |  -  |

