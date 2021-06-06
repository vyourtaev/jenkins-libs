package com.callfire.ansible

ansibleEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(Map pipelineParams) {
    ansibleEnv = [
        inventory: 'inventory/hosts_cf_stage',
        subset: 'vault',
        pattern: 'vault'
    ]
    ansibleEnv = ansibleEnv + pipelineParams

    node {
        checkout([
                $class: 'GitSCM',
                branches: [[name: params.ANSIBLE_BRANCH]],
                doGenerateSubmoduleConfigurations: false,
                extensions: [],
                submoduleCfg: [],
                userRemoteConfigs: [[
                   credentialsId: '6115acaa-96d8-485d-b890-1acc47d58788',
                   url: params.ANSIBLE_REPO
                ]]
        ])
    }
}

def getAnsibleEnv() {
    return ansibleEnv
}

def play(args) {
    def command = "ansible $ansibleEnv $params"
    return sh (script: "echo $command", returnStdout: true)
}

def playbook(String args) {
    def command = "ansible-plabook $args $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}
