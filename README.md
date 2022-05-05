## Welcome to the Crypto Recommendation
### Crypto Recommendation helps invest their salaries on cryptos 

In the CRYPTO_NAME_values.csv (e.g. BTC_values.csv) you can find one month’s prices for one crypto in USD.
All files are located in the src/main/resources/data/ directory. Initially the cryptos are only six, but when new files with prices are added to the resources/data/ directory, 
they will be included.
***
Detailed API documentation implemented using **Swagger**, it can be found at http://localhost:8080/swagger-ui.html.
This version of the application has six endpoints.
* **GET /price/{symbol}/oldest** - Returns the oldest values for the requested crypto in a month. The name of the crypto is passed through the {symbol} parameter, 
 for example http://localhost:8080/price/BTC/oldest. 
 
 Example of response body: 
 ```
    {
  "date": "2022-01-01",
  "symbol": "BTC",
  "price": 46813.21,
  "normalizedRange": null
}
 ```

* **GET /price/{symbol}/newest** - Returns the newest values for the requested crypto in a month. The name of the crypto is passed through the {symbol} parameter, 
 for example http://localhost:8080/price/BTC/newest. 
 
 Example of response body:
 ```
{
  "date": "2022-01-31",
  "symbol": "BTC",
  "price": 36823.19,
  "normalizedRange": null
}
 ```

* **GET /price/{symbol}/min** - Returns the min values for the requested crypto in a month. The name of the crypto is passed through the {symbol} parameter, 
 for example http://localhost:8080/price/XRP/min. 
 
 Example of response body:
 ```
  {
  "date": "2022-01-24",
  "symbol": "XRP",
  "price": 0.5616,
  "normalizedRange": null
}
 ```

* **ET /price/{symbol}/max** - Returns the max values for the requested crypto in a month. The name of the crypto is passed through the {symbol} parameter, 
 for example http://localhost:8080/price/XRP/max.
 
 
 Example of response body:
 ```
 {
  "date": "2022-01-01",
  "symbol": "XRP",
  "price": 0.8458,
  "normalizedRange": null
}
 ```

* **GET /price/range** - Return a descending sorted list of all the cryptos, comparing the normalized range (i.e. (max-min)/min). 
Request URL  http://localhost:8080/price/range.

 Example of response body:
 ```
  [
  {
    "date": null,
    "symbol": "ETH",
    "price": null,
    "normalizedRange": 0.638
  },
  {
    "date": null,
    "symbol": "XRP",
    "price": null,
    "normalizedRange": 0.506
  },
  {
    "date": null,
    "symbol": "DOGE",
    "price": null,
    "normalizedRange": 0.505
  },
  {
    "date": null,
    "symbol": "LTC",
    "price": null,
    "normalizedRange": 0.465
  },
  {
    "date": null,
    "symbol": "BTC",
    "price": null,
    "normalizedRange": 0.434
  },
  {
    "date": null,
    "symbol": "ABC",
    "price": null,
    "normalizedRange": 0.0193
  }
]
 ```


* **GET /price/{date}/highest-range** - Return the crypto with the highest normalized range for a specific day.
The day is passed through the {date} parameter, 
 for example  http://localhost:8080/price/2022-01-01/highest-range.
 
 Example of response body:
 ```
 {
  "date": "2022-01-01",
  "symbol": "ETH",
  "price": 3715.32,
  "normalizedRange": 0.638
}
 ```
 
