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
                        script {
                            currentBuild.getRawBuild().getExecutor().interrupt(Result.SUCCESS)
                            sleep(1)   // Interrupt is not blocking and does not take effect immediately.
                        }                      
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