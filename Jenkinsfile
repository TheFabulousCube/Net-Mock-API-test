pipeline {
    agent any
    stages {
        stage ('Git Checkout') {
            steps {
                checkout scm
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
        stage('Run test') {
			parallel {
				stage('Start API'){
					steps {
						// start the API in the background
						dir("${WORKSPACE}/Net Mock API test") {
							bat 'dotnet run &' 
						}
					}
				}
				stage('Run Tests'){
					steps {
						sleep 30
						// Run Karate tests against the API
						dir("${WORKSPACE}/KarateTests") {
						bat 'mvn clean test'
						}
                        echo 'Tests complete'
                        bat 'taskkill/F /FI "IMAGENAME eq dotnet.exe"'
					}
				}
			}
		}
	}

    post {
        success {
            // Send reports to Dashboard
            dir("${WORKSPACE}/KarateTests") {
            junit 'target/karate-reports/*.xml'
            }
        }
    }
}