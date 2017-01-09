/**
    This file is a template groovy script used by Jenkins pipeline.
    It has following steps:
        1. checkout code
        2. run npm install 
        3. run npm test
    This script does not need user interaction
*/

// Please provide following information in Jenkins Pipeline Parameters

//credentialId is ssh key pairs credential's id stored in jenkins. This can be found (added) in credentials page in jenkins
def credentialId=env["Credential Id"] 
//the git url from RHMAP cloud app
def cloudGitUrl=env["Cloud Git Url"]
//the branch to check out as source branch. e.g. develop
def branchName=env["Branch Name"]
//the configured node.js runtime name from node.js plugin in jenkins. Need node.js plugin and configure it in jenkins.
def nodeName=env["Jenkins Node Name"]


//Script start 
def cloud = fileLoader.fromGit('cloud.groovy', 
        'https://github.com/redhat-mobile-consulting/fhc-jenkins-groovy.git', 'master', null, '')
node {
    stage "Prepare"
        cloud.setupNode(nodeName)
        cloud.checkoutCode(credentialId, cloudGitUrl, branchName)
    stage "Build"
        cloud.npmInstall()
        cloud.npmTest() 
}