pipeline {
  agent any
  stages {
    stage('Dev') {
      steps {
        echo 'Hello'
      }
    }
    stage('GIT') {
      steps {
        echo "Getting project from git"
        git branch: 'Aya', url: 'https://github.com/Aya-Bs/Devops-project'
      }
    }
    stage('MVN CLEAN') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('MVN COMPILE') {
      steps {
        sh 'mvn compile'
      }
    }
    stage('MVN PACKAGE') {
      steps {
        sh 'mvn package'
      }
    }

            stage('SonarQube Analysis') {
                steps {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_AUTH_TOKEN')]) {
                        withSonarQubeEnv('SonarQube') {
                            sh "mvn sonar:sonar -Dsonar.projectKey=khaddem -Dsonar.host.url=http://localhost:9000 -Dsonar.login=${SONAR_AUTH_TOKEN}"
                        }
                    }
                }

  }
}