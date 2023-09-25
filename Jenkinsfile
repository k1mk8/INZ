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
                git branch: 'main', credentialsId: '01a1c8b4-d615-4c5e-9c1f-b59dced8f672	', url: 'https://gitlab-stud.elka.pw.edu.pl/sdyszews/inz23'
            }
        }

        stage('Develop Pull') {
            when{
                branch 'develop'
            }
            steps {
                git branch: 'develop', credentialsId: '01a1c8b4-d615-4c5e-9c1f-b59dced8f672	', url: 'https://gitlab-stud.elka.pw.edu.pl/sdyszews/inz23'
            }
        }

        stage('Gradle Build') {
            when{
                anyOf { branch 'main'; branch 'develop' }
            }
            steps {
                updateGitlabCommitStatus name: 'jenkins-pipeline', state: 'running'
                sh '/opt/gradle/gradle-7.6.1/bin/gradle wrapper build'
            }
        }

        stage('Docker') {
            when{
                anyOf { branch 'main'; branch 'develop' }
            }
            steps {
                sh 'docker compose build'
            }
        }


        stage('Deploy') {
            when{
                branch 'main'
            }
            steps {
                sh 'docker compose down'
                sh 'docker compose up'
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

        //stage('Cleanup') {
        //  steps {
        //        sh 'docker compose down'
        //  }
        //}
    }
}
