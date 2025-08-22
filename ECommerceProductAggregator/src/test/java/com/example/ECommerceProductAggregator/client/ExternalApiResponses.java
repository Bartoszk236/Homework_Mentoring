package com.example.ECommerceProductAggregator.client;

import lombok.experimental.UtilityClass;

@UtilityClass
class ExternalApiResponses {
    String getValidResponseFromEuroNet() {
        return """
                [
                    {
                        "id": 2,
                        "name": "Xbox",
                        "description": "Świetna konsola do gier i streamowania wideo",
                        "price": 859.99,
                        "currency": "EUR",
                        "inStock": false,
                        "shopName": "EuroNet",
                        "reviews": [
                            {
                                "id": 4,
                                "username": "janek_eu",
                                "comment": "W Euro dobra cena, ale brak kabla hdmi w zestawie."
                            },
                            {
                                "id": 5,
                                "username": "ania_eu",
                                "comment": "Płynność gier robi wrażenie."
                            },
                            {
                                "id": 6,
                                "username": "mario_eu",
                                "comment": "Szybka realizacja, polecam."
                            }
                        ]
                    }
                ]
                """;
    }

    String getValidResponseFromMediaExpert() {
        return """
                [
                    {
                        "id": 1,
                        "name": "Xbox",
                        "description": "Świetna konsola do gier i streamowania wideo",
                        "price": 3999.99,
                        "currency": "PLN",
                        "inStock": true,
                        "shopName": "MediaExpert",
                        "reviews": [
                            {
                                "id": 1,
                                "username": "janek_me",
                                "comment": "Szybki, świetny dysk. Polecam MediaExpert."
                            },
                            {
                                "id": 2,
                                "username": "ania_me",
                                "comment": "Bardzo dobry procesor, cena w porządku."
                            },
                            {
                                "id": 3,
                                "username": "mario_me",
                                "comment": "Dostawa następnego dnia, super obsługa."
                            }
                        ]
                    }
                ]
                """;
    }

    String getValidResponseFromMediaMarkt() {
        return """
                [
                    {
                        "id": 3,
                        "name": "Xbox",
                        "description": "Świetna konsola do gier i streamowania wideo",
                        "price": 920.00,
                        "currency": "USD",
                        "inStock": true,
                        "shopName": "MediaMarkt",
                        "reviews": [
                            {
                                "id": 7,
                                "username": "janek_mm",
                                "comment": "Najtaniej znalazłem w MediaMarkt."
                            },
                            {
                                "id": 8,
                                "username": "ania_mm",
                                "comment": "Bateria kontrolera trzyma 1–2 dni przy normalnym użyciu."
                            },
                            {
                                "id": 9,
                                "username": "mario_mm",
                                "comment": "Bezproblemowy odbiór w sklepie."
                            }
                        ]
                    }
                ]
                """;
    }

    String getValidResponseFromNbpApi() {
        return """
                {
                    "table": "A",
                    "currency": "dolar amerykański",
                    "code": "USD",
                    "rates": [
                        {
                            "no": "162/A/NBP/2025",
                            "effectiveDate": "2025-08-22",
                            "mid": 3.6786
                        }
                    ]
                }
                """;
    }
}
