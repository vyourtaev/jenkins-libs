package com.callfire.ansible

ansibleEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(pipelineParams) {
//    ansibleEnv = [
//            args: args,
//            inventory: 'inventory/hosts_cf_stage',
//            subset: 'vault',
//            pattern: 'vault'
//    ]
    ansibleEnv = pipelineParams

    node {
        checkout([
                $class: 'GitSCM',
                branches: [[name: pipelineParams.ANSIBLE_BRANCH]],
                doGenerateSubmoduleConfigurations: false,
                extensions: [[$class: 'RelativeTargetDirectory',relativeTargetDir: 'ansible']],
                submoduleCfg: [],
                userRemoteConfigs: [[
                   credentialsId: '6115acaa-96d8-485d-b890-1acc47d58788',
                   url: 'git@github.com:CallFire/ansible.git'
                ]]
        ])
    }
}

def getAnsibleEnv() {
    return ansibleEnv
}

def play(String args) {
    def command = "ansible $args $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def playbook(String args) {
    def command = "ansible-plabook $args $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}
