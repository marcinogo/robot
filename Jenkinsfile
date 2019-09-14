pipeline {
    agent any
      options {
        buildDiscarder(
          logRotator(
            daysToKeepStr: '5',
            numToKeepStr: '10',
            artifactNumToKeepStr: '3'))
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
               retry(3) {
                  echo 'Integration testing...'
                  sh 'mvn pre-integration-test failsafe:integration-test -DskipSurefire=true'
               }
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
                gatlingCheck(metrics: [
                    'global.okRate = 60',
                ])
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
        stage('Deploy') {
            steps {
                echo 'Deploying....'
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
            echo 'Cleaning workspace'
            deleteDir()
        }
        success {
            echo 'Archiving artifacts'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}