@debug
Feature: sample karate test script
  for help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * url 'https://localhost:7196'
    * configure ssl = true

  Scenario: get the weather forcast, this should always work
    Given path 'WeatherForecast'
    When method get
    Then status 200