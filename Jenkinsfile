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
                        script {
                        // Try Catch is not allowed in Declaritive, insert a Script block to use Groovy commands
                            try {
                                dir("${WORKSPACE}/Net Mock API test") {
                                bat 'dotnet run' 
                                }
                            } catch (e) {
                                echo 'Stopped API'
                                echo currentBuild.result
                                echo currentStage.result
                            }
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
        always {
            // Send reports to Dashboard
            dir("${WORKSPACE}/KarateTests") {
            junit 'target/karate-reports/*.xml'
            }
        }
    }
}