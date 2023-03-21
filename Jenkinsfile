pipeline {

    agent any

    options {
        gitLabConnection('GitLab')
    }

    post {
      failure {
        updateGitlabCommitStatus name: 'jenkins-pipeline', state: 'failed'
      }
      success {
        updateGitlabCommitStatus name: 'jenkins-pipeline', state: 'success'
      }
      aborted {
        updateGitlabCommitStatus name: 'jenkins-pipeline', state: 'canceled'
      }
    }

    stages {

        stage('Main Pull') {
            when{
                branch 'main'
            }
            steps {
                git branch: 'main', credentialsId: 'sdyszews', url: 'https://gitlab-stud.elka.pw.edu.pl/sdyszews/inz23'
            }
        }

        stage('Develop Pull') {
            when{
                branch 'develop'
            }
            steps {
                git branch: 'develop', credentialsId: 'sdyszews', url: 'https://gitlab-stud.elka.pw.edu.pl/sdyszews/inz23'
            }
        }

        //stage('Gradle Build') {
        //    steps {
        //        updateGitlabCommitStatus name: 'jenkins-pipeline', state: 'running'
        //        sh '/home/sdyszews/.sdkman/candidates/gradle/current/bin/gradle wrapper build'
        //    }
        //}


        stage('Docker') {
           steps {
               sh 'docker compose down'
               sh 'docker compose build'
               sh 'docker compose up -d'
           }
        }

        //stage('Test') {
        //  steps {
        //        sh './groovy clean test'
        //        sh './groovy clean integration'
        //  }
        //}

        // stage('SonarQube Analysis') {
        //     steps{
        //         withSonarQubeEnv(installationName: 'inz23-sq') {
        //             sh './groovy test jacocoTestReport sonarqube'
        //         }
        //     }
        // }

        // stage('Publish snapshot to nexus') {
        //     when{
        //         branch 'develop'
        //     }
        //     steps {
        //         sh './gradlew incrementVersion --versionIncrementType=PATCH -Psnapshot'
        //         sh './gradlew publish'
        //     }
        // }

        // stage('Publish release to nexus') {
        //     when{
        //         branch 'main'
        //     }
        //     steps {
        //         sh './gradlew incrementVersion --versionIncrementType=MINOR'
        //         sh './gradlew publish -Prelease'
        //     }
        // }

        stage('Deploy to Server'){
            when{
                branch 'main'
            }
            steps{
                echo "OK"
            }
        }

        //stage('Cleanup') {
        //  steps {
        //        sh 'docker compose down'
        //  }
        //}
    }
}
