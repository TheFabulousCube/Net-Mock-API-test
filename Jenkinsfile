pipeline {
    agent any
    stages {
        stage ('Git Checkout') {
            steps {
                git branch: '*/master', url: 'https://github.com/TheFabulousCube/Net-Mock-API-test'
                }
  }
  stage('Restore packages') {
  steps {
    bat "dotnet restore ${workspace}/Net Mock API test.sln"
  }
}
        stage('Build API') {
            steps {
                // build and run SUT api
                dir("${WORKSPACE}") {
                    bat 'dotnet build' 
                }
            }
        }
        stage('Start API') {
            steps {
                // start the API in the background
                dir("${WORKSPACE}/Net Mock API test") {
                    bat 'dotnet run' 
                }
                sleep 30
            }
        }
        stage('Run Tests'){
            steps {
                // Run Karate tests against the API
                dir("${WORKSPACE}/KarateTests") {
                    bat 'mvn test'
                }
            }
        }
    }

    post {
        always {
            // Stop the API process
            bat 'taskkill/F /FI "IMAGENAME eq dotnet.exe"'
        }
    }
}