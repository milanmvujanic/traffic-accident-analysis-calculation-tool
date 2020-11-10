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
   	 	stage ('Building jar') {
   	 		steps {
   	 			sh 'mvn clean package'
   	 		}
   	 	} 
   	 	stage ('Copying jar') {
   	 		steps {
   	 			sh 'chmod +x ./scripts/copy.sh'
				sh './copy.sh'
   	 		}   	 		
   	 	}       
    }
    
}