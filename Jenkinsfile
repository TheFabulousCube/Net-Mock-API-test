// Product Information
productName = "poc"
buildName = "system-tests-karate"
cmBuildNumber = currentBuild.number

pipeline {
    agent any
    stages {
		stage('Version'){
			agent any
			steps{
				script{
					//Set build name to make build number/branch clear
					currentBuild.displayName = "${cmBuildNumber}${" Cat Fact Test"}"
					stepEnvironment = [
						"BUILD_NAME=${buildName}",
						"PRODUCT=${productName}",
					]
				}
			}
		}
        stage('Build API') {
            steps {
                // build SUT api
                dir("${WORKSPACE}") {
                    bat 'dotnet build' 
                }
            }
        }
        stage('Run test') {
			steps {
				// Run Karate tests against the API
				dir("${WORKSPACE}/KarateTests") {
				bat 'mvn clean test'
				}
				echo 'Tests complete'
			}
		}
	}

    post {
        always {
            // Send reports to Dashboard
            dir("${WORKSPACE}/KarateTests") {
            junit 'target/karate-reports/*.xml'
            cucumber 'target/karate-reports/*.json'
            }
        }
    }
}