### Development environment

Project is based on JDK 17 and Spring Boot 3.1.6

Dependencies including lombok(for tools), fastjson(for json parsing), mockito for testing, jpa and H2 for in-memory DB.

### Apis

Two apis were provided, including get request weatherData and get request weatherDataFromDB.

Get request weatherData is for user to query data from OpenWeatherMap with city, country and api key, both 3 params is required. there are 5 api keys are available:

```
28df14d70bb0feda6142c41395e2b9da
ddb564a6b676fdbc071edd3c11ec677f
066ff147559dca3cc44ba189d233d6e8
b7c2595eb89920da5fee614fda445f4b
18e06f7692492a6533ddde2e07f026a6
```

Each of them are available for 5 times query in one hour. Other keys will be treated as Invalid api keys and are not accepted.

weatherDataFromDB is to extract data from H2 which were already stored in H2 with previous queries, two params needed: city and country
