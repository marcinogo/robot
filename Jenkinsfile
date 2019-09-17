pipeline {
    agent any
    options {
       buildDiscarder(
          logRotator(
            daysToKeepStr: '5',
            numToKeepStr: '10',
            artifactNumToKeepStr: '3'
          )
       )
        timestamps ()
    }
    environment {
        def scannerHome = tool name: 'SonarQubeScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    }
    stages {
        stage('Compile') {
            steps {
                echo 'Compiling...'
                sh 'mvn compile'
            }
        }
        stage('Unit tests') {
            steps {
                echo 'Unit testing...'
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
            }
        }
        stage('Integration tests') {
           steps {
              echo 'Integration testing...'
              sh 'mvn pre-integration-test failsafe:integration-test -DskipSurefire=true'
           }
        }
        stage('Performance test') {
            steps {
                echo 'Performance testing...'
                sh 'mvn gatling:test -Dgatling.useOldJenkinsJUnitSupport=true'
                gatlingArchive()
            }
        }
        stage('Performance gate') {
            steps {
                echo 'Checking Gatling Performance gate...'
//                 Skipped due to problem with Heroku deployed application / Heroku's Dynos hours limit
//                 gatlingCheck(metrics: [
//                     'global.okRate = 60',
//                 ])
            }
        }
        stage('Generate Site reports') {
            steps {
                echo 'Generating reports...'
                sh 'mvn install -DskipTests -Dgatling.skip=true site'
            }
        }
        stage('SonarQube analysis') {
            steps {
                echo 'Perform SonarQube analysis...'
                withSonarQubeEnv('Sonar') {
//                 TODO: Pass sonar-jenkins.properties in other way
                    sh "${scannerHome}/bin/sonar-scanner -Dproject.settings=/opt/sonarqube/conf/sonar-jenkins.properties"
                }
            }
        }
        stage("Quality gate") {
            steps {
                echo 'Checking SonarQube Quality gate...'
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy to test server') {
            when {
//              Change to develop after tests
                branch 'develop'
            }
            steps {
                echo 'Deploying to test server....'
//              Here should go configuration to deploy on a test server
            }
        }
        stage('Client acceptance test') {
            when {
//              Change to develop after tests
                branch 'develop'
            }
            steps {
                echo 'Acceptance test....'
//              Change to appropriate content and email to client representative one.
                emailext body: '''Dear Client

                If the newest version of the Robot application meets your requirements, please approve  Build # $BUILD_NUMBER on $JOB_URL page.

                Sincerely
                Your Dev Team''', subject: 'Robot application - Acceptance test - Build # $BUILD_NUMBER', to: 'marcin.grzegorz.ogorzalek@gmail.com'
                timeout(time:5, unit:'DAYS') {
                    input message:'Do you want to proceed this build?', submitter: 'Client'
                }
            }
        }
        stage('Deploy to production server...') {
            when {
                branch 'master'
            }
            steps {
                echo 'Deploying to production server....'
            }
        }
    }
    post {
        always {
            echo 'Publish reports'
            junit 'target/surefire-reports/*.xml'
            junit 'target/failsafe-reports/*.xml'
            recordIssues enabledForFailure: true, tool: checkStyle()
            recordIssues enabledForFailure: true, tool: spotBugs()
            recordIssues enabledForFailure: true, tool: pmdParser(pattern: '**/target/pmd.xml')
            echo 'Sending email to DevOps...'
            emailext attachLog: true, body: '$DEFAULT_CONTENT', compressLog: true, subject: 'Robot Jenkins - $DEFAULT_SUBJECT', to: 'marcin.grzegorz.ogorzalek@gmail.com'
        }
        success {
            echo 'Archiving artifacts'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            echo 'Sending email to developers on fail...'
            emailext attachLog: true, body: '$DEFAULT_CONTENT', compressLog: true, recipientProviders: [developers(), culprits()], subject: '$DEFAULT_SUBJECT'
        }
        unstable {
            echo 'Sending email to developers on unstable...'
            emailext attachLog: true, body: '$DEFAULT_CONTENT', compressLog: true, recipientProviders: [developers(), culprits()], subject: '$DEFAULT_SUBJECT'
        }
        changed {
            echo 'Sending email to developers on change...'
            emailext attachLog: true, body: '$DEFAULT_CONTENT', compressLog: true, recipientProviders: [developers(), culprits()], subject: '$DEFAULT_SUBJECT'
        }
        cleanup {
            echo 'Cleaning workspace'
            deleteDir()
        }
    }
}