package com.example.WeatherDataAggregationSystem.client;

import lombok.experimental.UtilityClass;

@UtilityClass
class ExternalApiResponses {
    String getJsonResponseOpenWeatherMap() {
        return """
                {
                    "coord": {
                        "lon": -0.1257,
                        "lat": 51.5085
                    },
                    "weather": [
                        {
                            "id": 804,
                            "main": "Clouds",
                            "description": "overcast clouds",
                            "icon": "04d"
                        }
                    ],
                    "base": "stations",
                    "main": {
                        "temp": 23.48,
                        "feels_like": 23.24,
                        "temp_min": 21.95,
                        "temp_max": 24.44,
                        "pressure": 1019,
                        "humidity": 52,
                        "sea_level": 1019,
                        "grnd_level": 1014
                    },
                    "visibility": 10000,
                    "wind": {
                        "speed": 3.13,
                        "deg": 276,
                        "gust": 5.36
                    },
                    "clouds": {
                        "all": 100
                    },
                    "dt": 1754656283,
                    "sys": {
                        "type": 2,
                        "id": 2075535,
                        "country": "GB",
                        "sunrise": 1754627707,
                        "sunset": 1754681834
                    },
                    "timezone": 3600,
                    "id": 2643743,
                    "name": "London",
                    "cod": 200
                }
                """;
    }

    String getJsonResponseWeatherApi() {
        return """
                {
                    "location": {
                        "name": "London",
                        "region": "City of London, Greater London",
                        "country": "United Kingdom",
                        "lat": 51.5171,
                        "lon": -0.1062,
                        "tz_id": "Europe/London",
                        "localtime_epoch": 1754656828,
                        "localtime": "2025-08-08 13:40"
                    },
                    "current": {
                        "last_updated_epoch": 1754656200,
                        "last_updated": "2025-08-08 13:30",
                        "temp_c": 23.1,
                        "temp_f": 73.6,
                        "is_day": 1,
                        "condition": {
                            "text": "Partly Cloudy",
                            "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png",
                            "code": 1003
                        },
                        "wind_mph": 6.5,
                        "wind_kph": 10.4,
                        "wind_degree": 254,
                        "wind_dir": "WSW",
                        "pressure_mb": 1019.0,
                        "pressure_in": 30.09,
                        "precip_mm": 0.0,
                        "precip_in": 0.0,
                        "humidity": 47,
                        "cloud": 25,
                        "feelslike_c": 24.2,
                        "feelslike_f": 75.6,
                        "windchill_c": 23.4,
                        "windchill_f": 74.1,
                        "heatindex_c": 24.3,
                        "heatindex_f": 75.8,
                        "dewpoint_c": 7.9,
                        "dewpoint_f": 46.2,
                        "vis_km": 10.0,
                        "vis_miles": 6.0,
                        "uv": 5.5,
                        "gust_mph": 7.5,
                        "gust_kph": 12.0,
                        "short_rad": 413.73,
                        "diff_rad": 202.72,
                        "dni": 267.42,
                        "gti": 196.2
                    }
                }
                """;
    }
}
