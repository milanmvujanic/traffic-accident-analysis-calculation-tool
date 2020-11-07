properties([pipelineTriggers([githubPush()])])

pipeline {
    environment {
        registry = 'milan79/traffic-accident-analysis-calculation-tool'
        dockerhubCredentialId = 'dockerhub_id'
        dockerImage = ''
        githubCredentialId = 'github_id'
    }
    agent any
    stages {
        stage('Cloning Git') {
		      steps {
		        git 'https://github.com/milanmvujanic/traffic-accident-analysis-calculation-tool.git'
		      }
   	 	}
        stage ('Build jar') {
        	steps {
        		sh "mvn clean install"        		
        	}
        }
        stage ('Building image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":latest"
                }
            }
        }
        stage ('Deploy image') {
            steps {
                script {
                    docker.withRegistry('https://' + registry, dockerhubCredentialId) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage ('Remove unused docker image') {
            steps {
            	sh "docker rmi $registry:latest"
            }
        }
    }
    
}