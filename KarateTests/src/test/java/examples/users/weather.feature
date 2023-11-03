Feature: sample karate test script
  for help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * url 'https://localhost:7196'
    * configure ssl = true
    * def start = () => karate.start({mock: 'cat-mock.feature', port: 8081})
    * callonce start

  Scenario: get the weather forcast, this will always be
    Given path 'WeatherForecast'
    When method get
    Then status 200

    * def first = response[0]
    * print first

  Scenario: get a quick cat fact.  This is mocked
    Given path 'Cats/GetCatFact'
    When method get
    Then status 200
    * def fact = response
    * print fact
    And match fact == '#regex ^SUT - .*'




  